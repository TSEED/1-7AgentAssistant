package org.eed.auto1_7;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * ������
 * @author EED
 *
 */
public class ToolsADB {
	/**
	 * �̶��ֽ�
	 * @param src
	 * @param len
	 * @return
	 */
	public byte[] fixBytes(byte[] src, int len) {
		java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
		for (int i = 0; i < len; i++) {
			if (src[i] == 0x0D) {
				if (i + 2 < len) {
					if (src[i + 1] == 0x0D && src[i + 2] == 0x0A) {
						i += 1;
						continue;
					} else {

					}
				} else {

				}
			}
			baos.write(src[i]);
		}
		
		try {
			baos.close();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		return baos.toByteArray();
	}

	
	public static int[][] imageFind(BufferedImage bfImage) {
        int width = bfImage.getWidth();
        int height = bfImage.getHeight();
        int[][] result = new int[height][width];
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                //ʹ��getRGB(w, h)��ȡ�õ����ɫֵ��ARGB������ʵ��Ӧ����ʹ�õ���RGB��������Ҫ��ARGBת����RGB����bufImg.getRGB(w, h) & 0xFFFFFF��
                result[h][w] = bfImage.getRGB(w, h) & 0xFFFFFF;
            }
        }
        return result;
	}
	
	/**
	 * �����������
	 * @param s1
	 * @param s2
	 * @return
	 */
    public int distance(String s1, String s2) {
        int counter = 0;
        for (int k = 0; k < s1.length(); k++) {
            if (s1.charAt(k) != s2.charAt(k)) {
                counter++;
            }
        }
        return counter;
    }
	
}

/**
 * ����˫����
 * @author EED
 *
 * @param <A> 
 * @param <B>
 */
class TwoTuple<A, B> {
	/**
	 * ��һ��ֵ
	 */
	public final A first;
	/**
	 * �ڶ���ֵ
	 */
	public final B second;

	/**
	 * �½�˫����
	 * @param a
	 * @param b
	 */
	public TwoTuple(A a, B b) {
		first = a;
		second = b;
	}
	/**
	 * ����д��toString()</p>
	 * ���ص�һ��ֵ�͵ڶ���ֵ��toString()</p>
	 */
	public String toString() {
		return "(" + first + ", " + second + ")";
	}
	

}