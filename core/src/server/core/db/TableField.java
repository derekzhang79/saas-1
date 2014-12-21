package server.core.db;

import java.sql.Time;

import share.core.Date;

public class TableField {
	
	private String name = "";
	private Object value = null;
	private String condition = Condition.EQUALS;
	private boolean modified = false;
	
	public TableField(String name, Object value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Object getValue() {
		return this.value;
	}
	
	public String getCondition() {
		return this.condition;
	}
	
	public void setCondition(String value) {
		if (value != null) {
			this.condition = value;
		}
	}
	
	public boolean isModified() {
		return this.modified;
	}
	
	public void setModified(boolean value) {
		this.modified = value;
	}
	
	public void setValue(Object newValue) {
		this.value = newValue;
		this.modified = false;
		this.condition = Condition.EQUALS;
	}
	
	public boolean isString() {
		return this.value instanceof String;
	}
	
	public String getString() {
		return (String)this.value;
	}
	
	public boolean isInteger() {
		return this.value instanceof Integer;
	}
	
	public Integer getInteger() {
		return (Integer)this.value;
	}
	
	public boolean isLong() {
		return this.value instanceof Long;
	}
	
	public Long getLong() {
		return (Long)this.value;
	}
	
	public boolean isFloat() {
		return this.value instanceof Float;
	}
	
	public Float getFloat() {
		return (Float)this.value;
	}
	
	public boolean isDouble() {
		return this.value instanceof Double;
	}
	
	public Double getDouble() {
		return (Double)this.value;
	}
	
	public boolean isBoolean() {
		return this.value instanceof Boolean;
	}
	
	public Boolean getBoolean() {
		return (Boolean)this.value;
	}
	
	public boolean isDate() {
		return this.value instanceof Date;
	}
	
	public Date getDate() {
		return (Date)this.value;
	}
	
	public boolean isTime() {
		return this.value instanceof Time;
	}
	
	public Time getTime() {
		return (Time)this.value;
	}
	
	public boolean isBinary() {
		return this.value instanceof Byte[];
	}
	
	public Byte[] getBinary() {
		return (Byte[])this.value;
	}
	
	public byte[] getBinaryPrimitive() {
		Byte[] original = (Byte[])this.value;
		byte[] bytes = new byte[original.length];
		
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = original[i];
		}
		
		return bytes;
	}
}