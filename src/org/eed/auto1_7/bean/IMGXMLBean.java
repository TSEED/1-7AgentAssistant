package org.eed.auto1_7.bean;

public class IMGXMLBean {

	private String valuef;
	private String movef;
	private String tapXf;
	private String tapYf;
	private String residuals;
	private String fileLocat;

	public IMGXMLBean(String valuef, String movef, String tapXf, String tapYf, String residuals, String fileLocat) {
		this.valuef=valuef;
		this.movef=movef;
		this.tapXf=tapXf;
		this.tapYf=tapYf;
		this.residuals=residuals;
		this.fileLocat=fileLocat;
	}

	public String getValuef() {
		return valuef;
	}

	public void setValuef(String valuef) {
		this.valuef = valuef;
	}

	public String getMovef() {
		return movef;
	}

	public void setMovef(String movef) {
		this.movef = movef;
	}

	public String getTapAxis(ADBAxis a) {
		switch (a) {
		case X_axis:
			return tapXf;
		case Y_axis:
			return tapYf;
		}
		return null;
	}
	public void setTapAxis(String ax ,ADBAxis a) {
		switch (a) {
		case X_axis:
			tapXf=ax;
		case Y_axis:
			tapYf=ax;
		}
	}

	public String getResiduals() {
		return residuals;
	}

	public void setResiduals(String residuals) {
		this.residuals = residuals;
	}

	public String getFileLocat() {
		return fileLocat;
	}

	public void setFileLocat(String fileLocat) {
		this.fileLocat = fileLocat;
	}
	
}
