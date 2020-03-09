package org.eed.autotest.test2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * ���ڰ�OutputStream ת��Ϊ InputStream�� �ʺ�����������������һ����ר�Ÿ���������ݣ���һ���ฺ���ȡ���ݡ�
 * 
 * @author ��ѧ�� www.java2000.net
 */
public class Test2 {
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// ʹ��Piped �����������
		PipedInputStream in = new PipedInputStream();
		final PipedOutputStream out = new PipedOutputStream(in);
		// �����̣߳������ݲ����ߵ�������
		new Thread(new Runnable() {
			public void run() {
				try {
					byte[] bs = new byte[2];
					for (int i = 0; i <= 100; i++) {
						bs[0] = (byte) i;
						bs[1] = (byte) (i + 1);
						// ����д���ֽ�����
						out.write(bs);
						out.flush();
						// �ȴ�0.1��
						Thread.sleep(100);
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
		// ����ʹ���ߴ�������
		// Ҳ����ʹ���߳������в��д���
		byte[] bs = new byte[1024];
		int len;
		// ��ȡ���ݣ������д���
		try {
			while ((len = in.read(bs)) != -1) {
				for (int i = 0; i < len; i++) {
					System.out.println(bs[i]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

/**
 * ���ڰ�OutputStream ת��Ϊ InputStream�� �ʺ����������������ڴ��㹻ȫ��������Щ���ݵ������
 * 
 * @author ��ѧ�� www.java2000.net
 *
 */
class Test1 {
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] bs = new byte[] { 1, 2, 3, 4, 5 };
		out.write(bs);

		ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
		byte[] bss = new byte[1024];
		int len = in.read(bss);
		for (int i = 0; i < len; i++) {
			System.out.println(bss[i]);
		}
	}
}
