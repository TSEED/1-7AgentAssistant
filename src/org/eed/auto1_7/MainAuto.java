package org.eed.auto1_7;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainAuto {

	public static void main(String[] args) {

		System.out.print("------------------ Auto 1-7 ------------------\n" 
				+ "Enter number to select item\n"
				+ "----------------------------------------------\n" 
				+ "1-Standard Run\n" 
				+ "2-Query Device\n"
				+ "3-Restart ADB Service\n"
				+ "4-LAN ADB Connect\n" 
				+ "5-Exit Procedure\n"
				+ "----------------------------------------------\n");

		int iten;
		Scanner scan = new Scanner(System.in);
		while (true) {
			iten = -1;
			System.out.print("$=>>");
			iten = scan.nextInt();
			if (iten == 1) {
				
				System.out.println("--Standard Run--");
				try {
					ArrayList<String> ss = new ArrayList<String>();
					Pattern p = Pattern.compile("\\s*|\t|\r|\n");
					System.out.println("<[[<Run Mode?>]]>\n");
					while (true) {
						iten = -1;
						System.out.print("#=>");
						iten = scan.nextInt();
						if (iten == 1 || iten == 2 || iten == 3) {
							break;
						} else {
							try {
								throw new Throwable("##Invalid Mode!## -->>无效模式!!重新输入!");
							} catch (Throwable e) {
								e.printStackTrace();
							}
							Thread.sleep(500);
						}
					}

					System.out.println("\nMode " + iten);
					for (String s : ADBShellCommand.displayAdbDevice()) {
						Matcher m = p.matcher(s);
						s = m.replaceAll("");
						ss.add(s);
						LongThreadADB ms = new LongThreadADB();
						switch (iten) {
						case 1:
							ms.monitorScreen(s);
							break;
						case 2:
							ms.monitorScreen(s, "data/", s + ".png");
							break;
						case 3:
							ms.monitorScreen(s, "data/" + s + ".png");
						}
						ms.start();
						Thread.sleep(500);
					}
				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
				}

				break;

			} else if (iten == 2) {
				try {
					int i = 0;
					for (String string : ADBShellCommand.displayAdbDevice()) {
						i = i + 1;
						System.out.println("Connectable Device For_" + i + "-->" + string);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			} else if (iten == 3) {
				try {
					ADBShellCommand.resetAdbSserver();
					System.out.println(">>>>Restart Complete!<<<<");
					Thread.sleep(500);
				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
				}

			} else if (iten == 4) {
				
				System.out.print("\n ### LAN ADB Connect ### \n"
						+ "#####Connect Menu#####\n"
						+ "1_Get LAN IP\n"
						+ "2_LAN Connect Device\n"
						+ "3_Disconnect Device\n"
						+ "4_Go Back\n");
				a:for (boolean c=true ; c == true;) {
					iten = -1;
					System.out.print("\n#&~>");
					iten = scan.nextInt();
					switch (iten) {
					case 1:
						LocalAreaNetADBMod.TraversalLANIP(0);
						c = false;
						break;
					case 2:
						while (true) {
							String ipl ;
							System.out.print("\n@#->");
							ipl = scan.next();
							if(ipl!=null&&ipl!="") {
								System.out.println("->Connect:"+ipl);
								LocalAreaNetADBMod.ConnectDevice(ipl);
								break a;
							}
						}

					case 3:
						while (true) {
							String ipl ;
							System.out.print("\n@&->");
							ipl = scan.next(); 
							if (ipl != null && ipl != "") {
								System.out.println("->Disconnect:"+ipl);
								LocalAreaNetADBMod.DisconnectDevice(ipl);
								break a;
							}
						}
					case 4:
						break a;
					default:
						try {
							
							throw new Throwable("##Invalid Input!## -->>无效输入!!重新输入!");
						} catch (Throwable e) {
							e.printStackTrace();
						}
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						break;
					}
				}
			} else if (iten == 5) {
				System.out.println("--Exit Procedure--");
				break;
			} else {
				try {
					throw new Throwable("##Invalid Input!## -->>无效输入!!重新输入!");
				} catch (Throwable e) {
					e.printStackTrace();
				}
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}

		}

		scan.close();
	}

}
