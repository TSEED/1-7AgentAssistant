package org.eed.auto1_7;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * 连接CMD跟CMD信息通话
 * 
 * @author EED
 *
 */
public class RunCmdADB {

	// 启动 Windows 命令解释器的一个新实例(启动真实cmd)
	// /C执行字符串指定的命令然后终止
	// 由于未知原因，无法直接使用exec命令运行adb，所以再套娃一个真实cmd来启动adb
	static String run = "lib/";

	/**
	 * <font size="5" color="red">执行标准CMD命令</font>
	 * </p>
	 * <font size="3" color="blue">建议在带CMD的Windows系统上运行，其他系统可能会出错</font>
	 * 
	 * @param cmd CMD命令字符串
	 * @throws IOException
	 */
	public static void runCMD(String cmd) throws IOException {
		run = "lib/" + cmd;
		System.out.println("-->  " + run);

		Process p = Runtime.getRuntime().exec(run);
		BufferedReader br = null;
		br = new BufferedReader(new InputStreamReader(p.getInputStream()));

		String r = br.readLine();
		StringBuilder b = new StringBuilder();

		b.append((r != null) ? r + "\n" : "");
		while (r != null) {

			r = br.readLine();
			b.append(r + "\n");
		}
		System.out.println(b);
		p.destroy();
		br.close();
	}

	/**
	 * <font size="5" color="red">执行标准CMD命令</font>
	 * </p>
	 * 
	 * @param cmd CMD命令字符串
	 * @return <font color="green">返回命令输出列表</font>
	 * @throws IOException
	 */
	public static ArrayList<String> runCMDSpecial(String cmd) throws IOException {
		run = "lib/" + cmd;
		System.out.println("=->  " + run);

		Process p = Runtime.getRuntime().exec(run);
		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(),"UTF-8"));

		String r = br.readLine();
		ArrayList<String> output = new ArrayList<String>();

		output.add((r != null) ? r : "");

		while (r != null) {
			r = br.readLine();
			output.add(r);
		}
		p.destroy();
		br.close();
		return output;
	}
	
	/**
	 * <font size="5" color="red">CMD命令</font>
	 * </p>
	 * @deprecated
	 * @param cmd 
	 * @return 
	 * @throws IOException
	 */
	public static TwoTuple<Process,BufferedInputStream> runCMDSLongOut(String cmd) throws IOException {
		run = "lib/" + cmd;
		System.out.println("-!>  " + run);
		Runtime runtime = Runtime.getRuntime();
		Process p = runtime.exec(run);
		BufferedInputStream bis = new BufferedInputStream(p.getInputStream());
		p.destroy();
		TwoTuple<Process,BufferedInputStream> r = new TwoTuple<Process, BufferedInputStream>(p, bis);
		return r;
		
	}
	
	
	
	/**
	 * <font size="5" color="red">ADB使用screencap截图</font>
	 * @param deviceName
	 * @param toPath
	 * @param toFile
	 * @return 
	 */
	public static File directSnapshot(String deviceName, String toPath, String toFile) {
		try {
			
			File file = new File(toPath + "/" + toFile);
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			
			String command = "lib/adb -s " + deviceName + " shell screencap -p";
			Runtime runtime = Runtime.getRuntime();
			Process p = runtime.exec(command);
			BufferedInputStream bis = new BufferedInputStream(p.getInputStream());
			
			
			
			byte[] b = new byte[1024 * 1024 * 4];
			int len = bis.read(b);
			
			while (len != -1) {
				bos.write(new ToolsADB().fixBytes(b, len));
				len = bis.read(b);
			}
			bis.close();
			bos.close();
			p.destroy();
			//getProcess.destroy();
			return file;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	

	
	
	
	
	
	
	



}
