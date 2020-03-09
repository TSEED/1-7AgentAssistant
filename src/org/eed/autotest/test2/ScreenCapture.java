package org.eed.autotest.test2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

public class ScreenCapture {
	public static String[] getDevices() {
		String command = "lib/adb devices";
		System.out.println(command);
		ArrayList<String> devices = new ArrayList<>();
 
		try {
			Process process = Runtime.getRuntime().exec(command);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
 
			String line = bufferedReader.readLine();
			while (line != null) {
				System.out.println(line);
				if (line.endsWith("device")) {
					String device = line.substring(0, line.length() - "device".length()).trim();
					devices.add(device);
				}
 
				line = bufferedReader.readLine();
			}
			process.destroy();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return devices.toArray(new String[] {});
	}
 
	public static String getModel(String device) {
		String command = "lib/adb -s " + device + " shell getprop";
		System.out.println(command);
		String model = null;
 
		try {
			Process process = Runtime.getRuntime().exec(command);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
 
			String line = bufferedReader.readLine();
			while (line != null) {
				if (line.contains("[ro.product.model]:")) {
					model = line.substring(("[ro.product.model]:").length()).trim();
					model = model.substring(1, model.length() - 1);
					break;
				}
				line = bufferedReader.readLine();
			}
			process.destroy();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
 
	public static void snapshot(String device, String toPath, String toFile) {
		String temp = "scrsnp.png";
		long t0 = new Date().getTime();
		String command1 = "lib/adb -s " + device + " shell screencap -p /sdcard/" + temp;
		System.out.println(command1);
		cmdExecute(command1);
		long t1 = new Date().getTime();
		System.out.println(t1 - t0);
		String command2 = "lib/adb -s " + device + " pull /sdcard/" + temp + " " + toPath + "/" + toFile;
		System.out.println(command2);
		cmdExecute(command2);
		long t2 = new Date().getTime();
		System.out.println("ÏûºÄÊ±¼ä:"+(t2 - t1));
		String command3 = "lib/adb -s " + device + " shell rm /sdcard/" + temp;
		System.out.println(command3);
		cmdExecute(command3);
		long t3 = new Date().getTime();
		System.out.println(t3 - t2);
	}
 
	public static void directSnapshot(String device, String toPath, String toFile) {
 
		try {
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(toPath + "/" + toFile));
 
			String command = "lib/adb -s " + device + " shell screencap -p";
			Runtime runtime = Runtime.getRuntime();
			Process getProcess = runtime.exec(command);
			BufferedInputStream bis = new BufferedInputStream(getProcess.getInputStream());
			byte[] buf = new byte[1024 * 1024 * 4];
			int len = bis.read(buf);
			while (len != -1) {
				bos.write(fixBytes(buf, len));
				len = bis.read(buf);
			}
			bos.close();
			getProcess.destroy();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 
	static byte[] fixBytes(byte[] src, int len) {
		java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
		for (int i = 0; i < len; i++) {
			if (src[i] == 0x0D) {
				if(i+2<len) {					
					if(src[i+1] == 0x0D && src[i+2] == 0x0A) {
						i+=1;
						continue;
					}else {
						
					}
				}else {
					
				}
			}
			baos.write(src[i]);
		}
		return baos.toByteArray();
	}
 
	private static void cmdExecute(String command){
		try{
		Process process = Runtime.getRuntime().exec(command);
		process.waitFor();
		process.destroy();
		}catch(Exception e) {
		e.printStackTrace();
		}
	}
 
	public static void main(String[] args) throws Exception {
		String[] ds = ScreenCapture.getDevices();
		if (ds.length > 0) {
//			String model = ScreenCapture.getModel(ds[0]);
			//System.out.println(model);
			long t0 = new Date().getTime();
			ScreenCapture.snapshot(ds[0], ".", "a.png");
//			//ScreenCapture.snapshot(ds[0], "d:\\temp\\", "a.png");
			long t1 = new Date().getTime();
			ScreenCapture.directSnapshot(ds[0], ".", "b.png");
			long t2 = new Date().getTime();
			System.out.println(t1 - t0);
			System.out.println(t2 - t1);
		}
	}
}

