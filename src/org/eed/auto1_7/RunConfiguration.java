package org.eed.auto1_7;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

public class RunConfiguration {

	public RunConfiguration() {

	}

	public static ArrayList<ADBXMLBean> ReadXML() throws SAXException, IOException, ParserConfigurationException {
		ArrayList<ADBXMLBean> xml = new ArrayList<ADBXMLBean>();
		ADBXML adbxml = new ADBXML(xml);
		SAXParserFactory.newInstance().newSAXParser().parse("config/1-7RunConfig.xml", adbxml);
		return xml;
	}

	static int i = 1;

	private static String Value = "";
	private static String Move = "";
	private static String TapX = "";
	private static String TapY = "";
	private static String X = "";
	private static String Y = "";

	private static String Value1 = "";
	private static String Move1 = "";
	private static String TapX1 = "";
	private static String TapY1 = "";

	private static String Value2 = "";

	/**
	 * "*" + Value + "@" + Move + "#" + TapX + "$" + TapY + "%" + X + "&" + Y <p/>
	 * "*" + Value + "@" + Move + "#" + TapX + "$" + TapY<p/>
	 * "*" + Value<p/>
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>>> getADBXMLBeanMaps()
			throws SAXException, IOException, ParserConfigurationException {

		ArrayList<ADBXMLBean> xml = ReadXML();
		LinkedHashMap<String, ArrayList<String>> rgbList = new LinkedHashMap<String, ArrayList<String>>();
		LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> listList = new LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>>();
		LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>>> runStepsList = new LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>>>();
		xml.forEach((xmll) -> {
//			new ArrayList<String>();
			if (xmll.getValue() == Value 
					&& xmll.getMove() == Move 
					&& xmll.getTapAxis(ADBAxis.X_axis) == TapX
					&& xmll.getTapAxis(ADBAxis.Y_axis) == TapY 
					&& xmll.getAxis(ADBAxis.X_axis) == X
					&& xmll.getAxis(ADBAxis.Y_axis) == Y) {
				i++;
			} else {
				Value = xmll.getValue();
				Move = xmll.getMove();
				TapX = xmll.getTapAxis(ADBAxis.X_axis);
				TapY = xmll.getTapAxis(ADBAxis.Y_axis);
				X = xmll.getAxis(ADBAxis.X_axis);
				Y = xmll.getAxis(ADBAxis.Y_axis);
				i = 1;
				rgbList.put("*" + Value + "@" + Move + "#" + TapX + "$" + TapY + "%" + X + "&" + Y , new ArrayList<String>());
				System.out.println("----------------------");
			}
			rgbList.get("*" + Value + "@" + Move + "#" + TapX + "$" + TapY + "%" + X + "&" + Y).add(xmll.getRGB());
//			System.out.println(Value+"-\t"+Move+"-\t"+TapX+"-\t"+TapY+"-\t"+X+"-\t"+Y+"-\t"+xmll.getRGB());
//			System.out.println(
//			xmll.getValue()+"\t"+
//			xmll.getMove()+"\t"+
//			xmll.getTapAxis(ADBAxis.X_axis)+"\t"+
//			xmll.getTapAxis(ADBAxis.Y_axis)+"\t"+
//			xmll.getAxis(ADBAxis.X_axis)+"\t"+
//			xmll.getAxis(ADBAxis.Y_axis)+"\t"+
//			xmll.getRGB()
//			);
//			System.out.println(i);
		});
		System.out.println("=============================================================================");
		rgbList.forEach((k, v) -> {
//			System.out.println(k+"="+v);
			if (k.substring(k.indexOf("*"), k.indexOf("@")).equals(Value1)
					&& k.substring(k.indexOf("@"), k.indexOf("#")).equals(Move1)
					&& k.substring(k.indexOf("#"), k.indexOf("$")).equals(TapX1)
					&& k.substring(k.indexOf("$"), k.indexOf("%")).equals(TapY1)) {
			} else {
				Value1 = k.substring(k.indexOf("*"), k.indexOf("@"));
				Move1 = k.substring(k.indexOf("@"), k.indexOf("#"));
				TapX1 = k.substring(k.indexOf("#"), k.indexOf("$"));
				TapY1 = k.substring(k.indexOf("$"), k.indexOf("%"));
				listList.put("*" + Value1 + "@" + Move1 + "#" + TapX1 + "$" + TapY1,
						new LinkedHashMap<String, ArrayList<String>>());
			}
			listList.get("*" + Value1 + "@" + Move1 + "#" + TapX1 + "$" + TapY1).put(
					"%" + k.substring(k.indexOf("%"), k.indexOf("&")) + 
					"&" + k.substring(k.indexOf("&"), k.length()),v);
		});

		listList.forEach((k, v) -> {
			if (k.substring(k.indexOf("*"), k.indexOf("@")).equals(Value2)) {
			} else {
				Value2 = k.substring(k.indexOf("*"), k.indexOf("@"));
				runStepsList.put("*" + Value2, new LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>>());
			}
			runStepsList.get("*" + Value2).put("@" + k.substring(k.indexOf("@"), k.indexOf("#")) 
					+ "#" + k.substring(k.indexOf("#"), k.indexOf("$")) 
					+ "$" + k.substring(k.indexOf("$"), k.length()), v);
		});
		return runStepsList;

	}
	
	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
		ArrayList<ADBXMLBean> x = RunConfiguration.ReadXML();
		x.forEach((s)->{
//			if(s.getValue().equals("1-7")&&s.getMove().equals("3")||s.getMove().equals("2")) { 
				System.out.println(s.getValue()+"\t"+s.getMove()+"\t"+s.getTapAxis(ADBAxis.X_axis)+"\t"+s.getTapAxis(ADBAxis.Y_axis)+"\t"+s.getAxis(ADBAxis.X_axis)+"\t"+s.getAxis(ADBAxis.Y_axis)+"\t"+s.getRGB());
//			}
		});
	}

}

class ADBXML extends DefaultHandler {

	private String value;
	private String move, tapX, tapY;
	private String x, y;
	private ArrayList<ADBXMLBean> xml;
	private String string;
//	private String RGB,runSteps,list,getRGB;

	public ADBXML(ArrayList<ADBXMLBean> xml) {
		this.xml = xml;
		if (xml == null) {
			throw new NullPointerException("你必须先构建一个ADBXMLBean！！！！");
		}
	}

	public ArrayList<ADBXMLBean> getADBConfig() {
		return xml;
	}

	// 遍历xml文件开始标签
	@Override
	public void startDocument() throws SAXException {

		System.out.println("<<SAX解析开始>>");

	}

	// 遍历xml文件结束标签
	@Override
	public void endDocument() throws SAXException {

		System.out.println("<<SAX解析结束>>");
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if ("runSteps".equals(qName)) {
			value = attributes.getValue("value");
		} else if ("list".equals(qName)) {
			move = attributes.getValue("move");
			tapX = attributes.getValue("tapX");
			tapY = attributes.getValue("tapY");
		} else if ("getRGB".equals(qName)) {
			x = attributes.getValue("x");
			y = attributes.getValue("y");
		} else if ("RGB".equals(qName)) {

		}

	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {

	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		string = new String(ch, start, length).trim();
		if (string != null && !"".equals(string)) {
			xml.add(new ADBXMLBean(value, move, tapX, tapY, x, y, string));
			// System.out.println(string);
		}

	}

}
