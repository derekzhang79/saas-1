package share.core.conf;

import java.lang.reflect.Field;
import java.util.List;
import org.jdom2.Element;
import share.core.AppError;
import share.core.Constants;
import share.core.Environment;
import share.core.xml.XMLUtils;

public class Configuration<ResponseClass>
{
	public ResponseClass get(Class<?> clazz, String xml)
	{
		@SuppressWarnings("unchecked")
		ResponseClass obj = (ResponseClass)Environment.instanceClass(clazz);
		Element root = XMLUtils.readFromResource(xml + Constants.XML_EXTENSION).getRootElement();
		fillObject(obj, root);
		
		return obj;
	}
	
	private void fillObject(Object obj, Element root)
	{
		List<Element> list = root.getChildren();
		
		for (int i = 0; i < list.size(); i++)
		{
			Element element = list.get(i);
			Field field = getField(obj.getClass(), element);
			
			if (hasChildren(element))
			{
				Object value = Environment.instanceClass(field.getType());
				fillObject(value, element);
				setField(obj, field, value);
			}
			else
			{
				setField(obj, field, element.getTextTrim());
			}
		}
	}
	
	private boolean hasChildren(Element element)
	{
		return (element.getChildren().size() != 0);
	}
	
	private Field getField(Class<?> clazz, Element element)
	{
		Field field = null;
		
		try
		{
			field = clazz.getField(element.getName());
		}
		catch (Exception e)
		{
			AppError.setError(e);
		}
		
		return field;
	}
	
	private void setField(Object obj, Field field, Object value)
	{
		try
		{
			field.set(obj, value);
		}
		catch (Exception e)
		{
			AppError.setError(e);
		}
	}
}