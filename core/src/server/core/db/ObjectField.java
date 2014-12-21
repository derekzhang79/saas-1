package server.core.db;

import java.lang.reflect.Field;
import java.sql.Time;

import server.core.ServerError;
import share.core.Date;

public class ObjectField {
	
	private Field field = null;
	private Object root = null;
	
	public ObjectField(Field field, Object root) {
		this.field = field;
		this.root = root;
	}
	
	public String getName() {
		return this.field.getName();
	}
	
	public Object getObject() {
		Object object = null;
		
		try {
			object = this.field.get(this.root);
		} catch (Exception e) {
			ServerError.setError(e);
		}
		
		return object;
	}
	
	public void set(Object value) {
		try {
			this.field.set(this.root, value);
		} catch (Exception e) {
			ServerError.setError(e);
		}
	}
	
	public boolean isString() {
		Class<?> clazz = this.field.getType();
		
		return clazz.equals(String.class);
	}
	
	public boolean isInteger() {
		Class<?> clazz = this.field.getType();
		
		return clazz.equals(Integer.class);
	}
	
	public boolean isLong() {
		Class<?> clazz = this.field.getType();
		
		return clazz.equals(Long.class);
	}
	
	public boolean isFloat() {
		Class<?> clazz = this.field.getType();
		
		return clazz.equals(Float.class);
	}
	
	public boolean isDouble() {
		Class<?> clazz = this.field.getType();
		
		return clazz.equals(Double.class);
	}
	
	public boolean isBoolean() {
		Class<?> clazz = this.field.getType();
		
		return clazz.equals(Boolean.class);
	}
	
	public boolean isDate() {
		Class<?> clazz = this.field.getType();
		
		return clazz.equals(Date.class);
	}
	
	public boolean isTime() {
		Class<?> clazz = this.field.getType();
		
		return clazz.equals(Time.class);
	}
	
	public boolean isBinary() {
		Class<?> clazz = this.field.getType();
		
		return clazz.equals(Byte[].class);
	}
}