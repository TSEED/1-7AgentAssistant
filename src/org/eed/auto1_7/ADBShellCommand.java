package org.eed.auto1_7;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * 存放各种各种ABDshell的命令行
 * 
 * @author EED
 *
 */

public class ADBShellCommand {

	/**
	 * 重置ADB系统
	 * 
	 * @throws IOException
	 */
	public static void resetAdbSserver() throws IOException {
		RunCmdADB.runCMD("adb kill-server");
		RunCmdADB.runCMD("adb start-server");
	}

	/**
	 * 获取Device名
	 * 
	 * @return
	 * @throws IOException
	 */
	public static ArrayList<String> displayAdbDevice() throws IOException {
		ArrayList<String> a = RunCmdADB.runCMDSpecial("adb devices -l");
		ArrayList<String> deviceName = new ArrayList<String>();
		for (String s : a) {
			if (s != null) {
				if (s.indexOf(" device ") != -1) {
					deviceName.add(s.substring(0, s.indexOf("device ")));
				}
				// System.out.println(s.indexOf("device "));
				// System.out.println(s.substring(0, s.indexOf("device ")));
			}
			System.out.println(s);
		}

		return deviceName;
	}

	/**
	 * 点击
	 * 
	 * @param deviceName 设备名字
	 * @param x          x值
	 * @param y          y值
	 * @throws IOException
	 */
	public static void adbShellClick(String deviceName, int x, int y) throws IOException {
		RunCmdADB.runCMD("adb -s " + deviceName + "shell input tap " + x + " " + y);
	}

	/**
	 * 截图
	 * 
	 * @deprecated
	 * @param deviceName 设备名字
	 * @param toFile     图片名字，储存在data文件夹下
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void adbShellSnapshot(String deviceName, String toFile) throws FileNotFoundException, IOException {
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("data/" + toFile));
		TwoTuple<Process, BufferedInputStream> tt = RunCmdADB.runCMDSLongOut("adb -s " + deviceName + " shell screencap -p");

		byte[] buf = new byte[1024 * 1024 * 4];
		int len = tt.second.read(buf);
		while (len != -1) {
			bos.write(new ToolsADB().fixBytes(buf, len));
			len = tt.second.read(buf);
		}
		tt.first.destroy();
		tt.second.close();
		bos.close();
	}

	/**
	 * screen 截图
	 * 
	 * @param deviceName
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage screenSnapshot(String deviceName ) throws IOException {
		BufferedImage image = ImageIO.read(RunCmdADB.directSnapshot(deviceName , "data/", deviceName +".png"));
		System.out.println("screen size <- x:"+image.getWidth()+" y:"+image.getHeight());
		
		return image;
	}
}
