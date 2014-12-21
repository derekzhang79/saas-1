package share.core.xml;

import java.io.File;
import java.io.StringReader;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import share.core.AppError;
import share.core.Resource;

public class XMLUtils
{
	public static Document readFromFile(String file)
	{
		Document result = null;
		SAXBuilder builder = new SAXBuilder();

		try
		{
			result = builder.build(new File(file));
		}
		catch (Exception e)
		{
			AppError.setError(e);
		}

		return result;
	}

	public static Document readFromResource(String file)
	{
		Document result = null;
		SAXBuilder builder = new SAXBuilder();

		try
		{
			result = builder.build(Resource.get(file));
		}
		catch (Exception e)
		{
			AppError.setError(e);
		}

		return result;
	}

	public static Element readFromString(String xml)
	{
		Element result = null;
		SAXBuilder builder = new SAXBuilder();

		try
		{
			result = builder.build(new StringReader(xml)).getRootElement();
		}
		catch (Exception e)
		{
			AppError.setError(e);
		}

		return result;
	}

	public static String getString(Element root)
	{
		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());

		return outputter.outputString(new Document(root));
	}

	public static String getString(Document document)
	{
		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());

		return outputter.outputString(document);
	}

	public static String getString(String xml)
	{
		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());

		return outputter.outputString(XMLUtils.readFromString(xml));
	}

	public static String getRootName(File file)
	{
		Element xml = XMLUtils.readFromFile(file.getAbsolutePath()).getRootElement();

		return xml.getName();
	}

	public static String validate(String xsdPath, String xmlPath)
	{
		String response = "OK";

		try
		{
			SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
			File schemaLocation = new File(xsdPath);
			Schema schema = factory.newSchema(schemaLocation);
			Validator validator = schema.newValidator();
			Source source = new StreamSource(xmlPath);
			Result result = null;
			validator.validate(source, result);
		}
		catch (Exception e)
		{
			response = e.getMessage();
		}

		return response;
	}
}