package org.eed.auto1_7;

import java.awt.Graphics2D;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * 图片哈希
 * @author EED
 *
 */
public class ImageHash {
	private int size = 32;
	private int smallerSize = 8;
	private ColorConvertOp colorConvert = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
	private double[] c;
	/**
	 * 默认状态</p>
	 * size = 32</p>
	 * smallerSize = 8</p>
	 */
	public ImageHash() {
		initCoefficients();
	}
	/**
	 * 
	 * @param size 第一次调整
	 * @param smallerSize 第二次调整
	 */
	public ImageHash(int size, int smallerSize) {
		this.size = size;
		this.smallerSize = smallerSize;
		initCoefficients();
	}
	/**
	 * 获得图片Hash值
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public String getHash(InputStream is) throws Exception {
		BufferedImage img = ImageIO.read(is);
		img = resize(img, size, size);
		img = grayscale(img);
		double[][] vals = new double[size][size];
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				vals[x][y] = getBlue(img, x, y);
			}
		}
		double[][] dctVals = applyDCT(vals);
		double total = 0;
		for (int x = 0; x < smallerSize; x++) {
			for (int y = 0; y < smallerSize; y++) {
				total += dctVals[x][y];
			}
		}
		total -= dctVals[0][0];
		double avg = total / (double) ((smallerSize * smallerSize) - 1);
		String hash = "";
		for (int x = 0; x < smallerSize; x++) {
			for (int y = 0; y < smallerSize; y++) {
				if (x != 0 && y != 0) {
					hash += (dctVals[x][y] > avg ? "1" : "0");
				}
			}
		}
		return hash;
	}
	private BufferedImage resize(BufferedImage image, int width, int height) {
		BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(image, 0, 0, width, height, null);
		g.dispose();
		return resizedImage;
	}
	private BufferedImage grayscale(BufferedImage img) {
		colorConvert.filter(img, img);
		return img;
	}
	private static int getBlue(BufferedImage img, int x, int y) {
		return (img.getRGB(x, y)) & 0xff;
	}
	private void initCoefficients() {
		c = new double[size];

		for (int i = 1; i < size; i++) {
			c[i] = 1;
		}
		c[0] = 1 / Math.sqrt(2.0);
	}

	private double[][] applyDCT(double[][] f) {
		int N = size;

		double[][] F = new double[N][N];
		for (int u = 0; u < N; u++) {
			for (int v = 0; v < N; v++) {
				double sum = 0.0;
				for (int i = 0; i < N; i++) {
					for (int j = 0; j < N; j++) {
						sum += Math.cos(((2 * i + 1) / (2.0 * N)) * u * Math.PI)
								* Math.cos(((2 * j + 1) / (2.0 * N)) * v * Math.PI) * (f[i][j]);
					}
				}
				sum *= ((c[u] * c[v]) / 4.0);
				F[u][v] = sum;
			}
		}
		return F;
	}

}
