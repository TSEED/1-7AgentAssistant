package org.eed.auto1_7;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 工具类
 * @author EED
 *
 */
public class ToolsADB {
	/**
	 * 固定字节
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
			// TODO 自动生成的 catch 块
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
                //使用getRGB(w, h)获取该点的颜色值是ARGB，而在实际应用中使用的是RGB，所以需要将ARGB转化成RGB，即bufImg.getRGB(w, h) & 0xFFFFFF。
                result[h][w] = bfImage.getRGB(w, h) & 0xFFFFFF;
            }
        }
        return result;
	}
	
	/**
	 * 汉明距离计算
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
 * 传递双函数
 * @author EED
 *
 * @param <A> 
 * @param <B>
 */
class TwoTuple<A, B> {
	/**
	 * 第一个值
	 */
	public final A first;
	/**
	 * 第二个值
	 */
	public final B second;

	/**
	 * 新建双函数
	 * @param a
	 * @param b
	 */
	public TwoTuple(A a, B b) {
		first = a;
		second = b;
	}
	/**
	 * 被重写的toString()</p>
	 * 返回第一个值和第二个值的toString()</p>
	 */
	public String toString() {
		return "(" + first + ", " + second + ")";
	}
	

}