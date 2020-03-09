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
 * ��Ÿ��ָ���ABDshell��������
 * 
 * @author EED
 *
 */

public class ADBShellCommand {

	/**
	 * ����ADBϵͳ
	 * 
	 * @throws IOException
	 */
	public static void resetAdbSserver() throws IOException {
		RunCmdADB.runCMD("adb kill-server");
		RunCmdADB.runCMD("adb start-server");
	}

	/**
	 * ��ȡDevice��
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
	 * ���
	 * 
	 * @param deviceName �豸����
	 * @param x          xֵ
	 * @param y          yֵ
	 * @throws IOException
	 */
	public static void adbShellClick(String deviceName, int x, int y) throws IOException {
		RunCmdADB.runCMD("adb -s " + deviceName + "shell input tap " + x + " " + y);
	}

	/**
	 * ��ͼ
	 * 
	 * @deprecated
	 * @param deviceName �豸����
	 * @param toFile     ͼƬ���֣�������data�ļ�����
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
	 * screen ��ͼ
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
