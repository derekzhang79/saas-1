package client.core.gui.window;

import java.util.List;

import org.jdom2.Element;

import client.core.gui.window.WindowManager.Attribute;

public class NodeElement {
	
	private Element root = null;
	
	public NodeElement(Element root) {
		this.root = root;
	}
	
	public String getName() {
		return this.root.getName();
	}
	
	public boolean exist(Attribute name) {
		return this.root.getAttribute(name.toString()) != null;
	}
	
	public String getString(Attribute name) {
		return getStringNoTrim(name).trim();
	}
	
	public String getStringValue(Attribute name) {
		return exist(name) ? getString(name) : "";
	}
	
	public String getStringNoTrim(Attribute name) {
		return this.root.getAttributeValue(name.toString());
	}
	
	public boolean getBoolean(Attribute name) {
		return getString(name).equals("true");
	}
	
	public int getInt(Attribute name) {
		return Integer.parseInt(getString(name));
	}
	
	public int getIntValue(Attribute name) {
		return exist(name) ? Integer.parseInt(getString(name)) : 0;
	}
	
	public float getDecimal(Attribute name) {
		return Float.parseFloat(getString(name));
	}
	
	public float getDecimalValue(Attribute name) {
		return exist(name) ? Float.parseFloat(getString(name)) : 0;
	}
	
	public NodeElement[] getNodes() {
		List<Element> list = this.root.getChildren();
		
		NodeElement[] nodes = new NodeElement[list.size()];
		
		for (int i = 0; i < list.size(); i++) {
			Element current = list.get(i);
			nodes[i] = new NodeElement(current);
		}
		
		return nodes;
	}
}