package org.eed.auto1_7;

public class ADBXMLBean {

	private String runSteps;
	private String value;

	private String list;
	private String move;
	private String tapX;
	private String tapY;

	private String getRGB;
	private String x;
	private String y;

	private String RGB;

	public ADBXMLBean(String value, String move, String tapX, String tapY, String x, String y, String RGB) {
		// TODO 自动生成的构造函数存根
		this.value = value;
		this.move = move;
		this.tapX = tapX;
		this.tapY = tapY;
		this.x = x;
		this.y = y;
		this.RGB = RGB;
	}

	public String getRunSteps() {
		return runSteps;
	}

	public void setRunSteps(String runSteps) {
		this.runSteps = runSteps;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getList() {
		return list;
	}

	public void setList(String list) {
		this.list = list;
	}

	public String getMove() {
		return move;
	}

	public void setMove(String move) {
		this.move = move;
	}

	public String getTapAxis(ADBAxis axis) {
		if (axis == ADBAxis.X_axis) {
			return tapX;
		} else if (axis == ADBAxis.Y_axis) {
			return tapY;
		}
		return null;
	}

	public void setTapAxis(String tapAxis, ADBAxis axis) {

		if (axis == ADBAxis.X_axis) {
			this.tapX = tapAxis;
		} else if (axis == ADBAxis.Y_axis) {
			this.tapY = tapAxis;
		}
	}

	public String getGetRGB() {
		return getRGB;
	}

	public void setGetRGB(String getRGB) {
		this.getRGB = getRGB;
	}

	public String getAxis(ADBAxis axis) {
		if (axis == ADBAxis.X_axis) {
			return x;
		} else if (axis == ADBAxis.Y_axis) {
			return y;
		}
		return null;
	}

	public void setAxis(String saxis, ADBAxis axis) {
		if (axis == ADBAxis.X_axis) {
			this.x = saxis;
		} else if (axis == ADBAxis.Y_axis) {
			this.y = saxis;
		}
	}

	public String getRGB() {
		return RGB;
	}

	public void setRGB(String rGB) {
		RGB = rGB;
	}
}

enum ADBAxis {
	X_axis, Y_axis
}