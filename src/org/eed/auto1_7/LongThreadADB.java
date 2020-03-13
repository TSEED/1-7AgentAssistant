package org.eed.auto1_7;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.xml.parsers.ParserConfigurationException;

import org.eed.auto1_7.bean.ADBAxis;
import org.eed.auto1_7.bean.ADBXMLBean;
import org.eed.auto1_7.bean.IMGXMLBean;
import org.xml.sax.SAXException;

/**
 * 希望循环ADB运行的模式
 *
 * @author EED
 *
 */
enum LongThreadAdbModel {
	MODEL1, MODEL2, MODEL3
}

/**
 * 线程循环ADB
 * 
 * @author EED
 *
 */
public class LongThreadADB extends Thread {

	private String deviceName;
	private String toPath;
	private String toFile;
	private ByteArrayOutputStream baos;
	private BufferedOutputStream bos;
	private Runtime runtime;
	private Process process;
	private BufferedInputStream bis;
	private BufferedImage image;
	private ByteArrayInputStream bais;
	private FileInputStream fis;
	private LongThreadAdbModel mode;
	/**
	 * <font size="5" color="red">模式1</font>
	 * 
	 * @param deviceName
	 * @param toPath
	 * @param toFile
	 * @return
	 */
	public Thread monitorScreen(String deviceName) {
		mode = LongThreadAdbModel.MODEL1;
		this.deviceName = deviceName;
		return this;
	}
	
	/**
	 * <font size="5" color="red">模式2</font>
	 * 
	 * @param deviceName
	 * @return
	 */
	public Thread monitorScreen(String deviceName, String toPath, String toFile) {
		mode = LongThreadAdbModel.MODEL2;
		this.deviceName = deviceName;
		this.toPath = toPath;
		this.toFile = toFile;
		return this;
	}
	
	/**
	 * <font size="5" color="red">模式3</font>
	 * 
	 * @param deviceName
	 * @param toFile
	 * @return
	 */
	public Thread monitorScreen(String deviceName, String toFile) {
		mode = LongThreadAdbModel.MODEL3;
		this.deviceName = deviceName;
		this.toFile = toFile;
		return this;
	}

	/**
	 * 运行方法
	 */
	@Override
	public void run() {

		switch (mode) {
		case MODEL1:
			runAdbFor1();
			break;
		case MODEL2:
			runAdbFor2();
			break;
		case MODEL3:
			runAdbFor3();
			break;
		default:
			break;
		}

	}

	/**
	 * 运行循环方法1
	 */
	private void runAdbFor1() {
		System.out.println("MODE1\t-->\t" + deviceName + " " + toPath + toFile);
		ArrayList<ADBXMLBean> m;
		try {
			m = RunConfiguration.ReadXML();
			run: while (true) {
				if (currentThread().isInterrupted()) {
					// 处理中断逻辑
					break;
				}
				try {
					baos = new ByteArrayOutputStream();
					bos = new BufferedOutputStream(baos);
					runtime = Runtime.getRuntime();
					process = runtime.exec("lib/adb -s " + deviceName + " shell screencap -p");
					bis = new BufferedInputStream(process.getInputStream());
					byte[] b = new byte[1024 * 1024 * 4];
					int len = bis.read(b);
					while (len != -1) {
						bos.write(new ToolsADB().fixBytes(b, len));
						len = bis.read(b);
					}
					bais = new ByteArrayInputStream(baos.toByteArray());
					if (bais != null) {
						image = ImageIO.read(bais);
						if (image != null) {
							boolean bo = false;
							for (ADBXMLBean a : m) {
								if (a.getValue().equals("1-7")) {
									if (image.getRGB(Integer.parseInt(a.getAxis(ADBAxis.X_axis)), Integer.parseInt(a.getAxis(ADBAxis.Y_axis))) == Integer.parseInt(a.getRGB())) {
										if (a.getTapAxis(ADBAxis.X_axis).equals("0")
												|| a.getTapAxis(ADBAxis.Y_axis).equals("0")) {
											try {
												throw new Throwable("##STOP RUNNING!## -->>循环跳出条件中止");
											} catch (Throwable e) {
												e.printStackTrace();
											}
											break run;
										} else {
											runtime.exec("lib/adb -s " + deviceName + " shell input tap "+ a.getTapAxis(ADBAxis.X_axis) + " " + a.getTapAxis(ADBAxis.Y_axis)+ " ");
											System.out.println(deviceName + "-Click-" + this.toString() + "->"+ a.getTapAxis(ADBAxis.X_axis) + " "+ a.getTapAxis(ADBAxis.Y_axis));
											bo = true;
										}
									} else {
										sleep(1000);
										bo = false;
									}
								}
							}
							System.out.println(bo ? "runs-!-" : "null-?-");
							image.flush();
						} else {
							System.out.println("-!!!-");
//						continue; 
						}
					} else {
						System.out.println("-!?!-");
					}
					// getProcess.destroy();
					baos.close();
					bis.close();
					bos.close();
					process.destroy();
					bais.close();
					sleep(4000);
				} catch (NullPointerException e) {
					e.printStackTrace();
				} catch (InterruptedException | IOException e) {
					e.printStackTrace();
				}
			}
		} catch (SAXException | IOException | ParserConfigurationException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 运行循环方法2
	 */
	private void runAdbFor2() {
		System.out.println("MODE2\t-->\t" + deviceName + " " + toPath + toFile);
		ArrayList<ADBXMLBean> m;
		try {
			m = RunConfiguration.ReadXML();
			run: while (true) {
				try {
					runtime = Runtime.getRuntime();
					Process p = runtime.exec("cmd /c lib\\adb -s " + deviceName + " exec-out screencap -p > " + toPath + toFile);
					BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
					br.readLine();
					p.destroy();
					br.close();
					fis = new FileInputStream(toPath + toFile);
					image = ImageIO.read(fis);
					if (image != null) {
						boolean b = false;
						for (ADBXMLBean a : m) {
							if (a.getValue().equals("1-7")) {
								if (image.getRGB(Integer.parseInt(a.getAxis(ADBAxis.X_axis)),
										Integer.parseInt(a.getAxis(ADBAxis.Y_axis))) == Integer.parseInt(a.getRGB())) {
									if (a.getTapAxis(ADBAxis.X_axis).equals("0")
											|| a.getTapAxis(ADBAxis.Y_axis).equals("0")) {
										try {
											throw new Throwable("##STOP RUNNING!## -->>循环跳出条件中止");
										} catch (Throwable e) {
											// TODO 自动生成的 catch 块
											e.printStackTrace();
										}
										break run;
									} else {
										runtime.exec("lib/adb -s " + deviceName + " shell input tap "+ a.getTapAxis(ADBAxis.X_axis) + " " + a.getTapAxis(ADBAxis.Y_axis)+ " ");
										System.out.println(deviceName + "-Click-" + this.toString() + "->"+ a.getTapAxis(ADBAxis.X_axis) + " " + a.getTapAxis(ADBAxis.Y_axis));
										b = true;
									}
								} else {
									sleep(3000);
									b = false;
								}
							}
						}
						System.out.println(b ? "runs-!-" : "null-?-");
						image.flush();
						fis.close();
					} else {
						System.out.println("-!!!-");
						sleep(500);
						continue;
					}
					sleep(2000);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (SAXException | IOException | ParserConfigurationException e1) {
			e1.printStackTrace();
		}
	}

	private void runAdbFor3() {
		System.out.println("MODE3\t-->\t" + deviceName + " " + toFile);
		HashMap<IMGXMLBean, String> Hashimg = new HashMap<IMGXMLBean, String>();
		ImageHash ih = new ImageHash();
		ToolsADB tool = new ToolsADB();
		
		
		try {
			
			ArrayList<IMGXMLBean> m;
			m = RunConfiguration.ReadImage();
			
			for(IMGXMLBean b : m) {
				Hashimg.put(b, ih.getHash(new FileInputStream(b.getFileLocat())));
			}
			
			run:while (true) {
				try {
					runtime = Runtime.getRuntime();
					Process p = runtime.exec("cmd /c lib\\adb -s " + deviceName + " exec-out screencap -p > " + toFile);
//					Process p = runtime.exec("cmd /c lib\\adb -s emulator-5556 exec-out screencap -p > data/emulator-5556.png");
					BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
					br.readLine();
					p.destroy();
					br.close();
					
					fis = new FileInputStream(toFile);
//					fis = new FileInputStream("data/emulator-5556.png");
					String getH = ih.getHash(fis);
					boolean b = false;
					for(Entry<IMGXMLBean, String> imgs: Hashimg.entrySet()) {
						int ol= tool.distance(imgs.getValue(), getH);
						System.out.println(this.toString()+"\t"+"DH:\t"+ol);
						if(ol < Integer.parseInt(imgs.getKey().getResiduals())) {
							if(imgs.getKey().getTapAxis(ADBAxis.X_axis).equals("0")||imgs.getKey().getTapAxis(ADBAxis.Y_axis).equals("0")) {
								try {
									throw new Throwable("##STOP RUNNING!## -->>循环跳出条件中止");
								} catch (Throwable e) {
									e.printStackTrace();
								}
								break run;
							}else {
								runtime.exec("lib/adb -s " + deviceName + " shell input tap "+ imgs.getKey().getTapAxis(ADBAxis.X_axis) + " " + imgs.getKey().getTapAxis(ADBAxis.Y_axis)+ " ");
								System.out.println(deviceName + "-Click-" + this.toString() + "->"+ imgs.getKey().getTapAxis(ADBAxis.X_axis) + " " + imgs.getKey().getTapAxis(ADBAxis.Y_axis));
								b=true;
							}
						}else {
							b=false;
							sleep(4000);
						}
					}
					if(b)
					System.out.println("--?--");
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (SAXException | IOException | ParserConfigurationException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}


	}

}
