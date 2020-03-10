package org.eed.auto1_7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class LocalAreaNetADBMod {

	/**
	 * 遍历网络ip地址
	 */
	public static HashMap<String, String> TraversalLANIP(int ips) {
		HashMap<String, String> ip = new HashMap<String, String>();
		try {
			InetAddress ip4 = Inet4Address.getLocalHost();
			if (ips < 20)
				ips = ips + 20;
			Process process = Runtime.getRuntime().exec("cmd cmd /c start cmd /c for /L %i IN (1,1," + ips + ") DO ping -w 2 -n 1 192.168.0.%i ");// 俄罗斯套娃出一个cmd窗口来遍历当前ip地址
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			br.readLine();
			br.readLine();// 这两个readLine目的是堵塞线程，没有任何实质输出 

			process.destroy();
			br.close();// b我不new，从我做起
			Thread.sleep(500);

			process = Runtime.getRuntime().exec("arp -a");// 俄罗斯套娃出一个cmd窗口来遍历当前ip地址
			br = new BufferedReader(new InputStreamReader(process.getInputStream()));

			StringBuilder b = new StringBuilder();
			String content = br.readLine();

			b.append((content != null) ? content : "");

			while (content != null) {
				content = br.readLine();
				int i = -1;
				if (content != null && content != "") {
					b.append(content + "\n");
					if (!content.contains(ip4.getHostAddress()) && !content.contains("192.168.0.255") && !content.contains("192.168.0.1 ")) {
						i = content.indexOf("192.168.0.");
						if (i != -1) {
							int c = content.indexOf("192.168");
							ip.put(content.substring(c, c + 13), content.substring(c + 22, c + 22 + 17));
						}
					}
				}
			}

			System.out.println(b);
			
			for (Map.Entry<String, String> entry :ip.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}
			br.close();
			process.destroy();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return ip;
	}
	/**
	 * 连接设备 
	 * @param string
	 */
	public static void ConnectDevice(String ip) {
		try {
			Process process = Runtime.getRuntime().exec("lib/adb connect "+ip);
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(),"UTF-8"));
			String r = br.readLine();
			while (r!=null) {
				System.out.println(r);
				r = br.readLine();
			}
			br.close();
			process.destroy();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	/**
	 * 断开设备
	 * @param string
	 */
	public static void DisconnectDevice(String ip) {
		try {
			Process process = Runtime.getRuntime().exec("lib/adb disconnect "+ip);
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(),"UTF-8"));
			String r = br.readLine();
			while (r!=null) {
				System.out.println(r);
				r = br.readLine();
			}
			br.close();
			process.destroy();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

}
