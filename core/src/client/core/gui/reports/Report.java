package client.core.gui.reports;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import share.core.AppError;
import share.core.Constants;
import share.core.Environment;
import share.core.MapTable;
import share.core.Resource;
import share.core.xml.XMLUtils;
import client.core.Debug;
import client.core.gui.DataFormatter;
import client.core.gui.window.NodeElement;
import client.core.gui.window.WindowManager.Attribute;
import client.core.gui.window.WindowManager.Section;
import client.core.images.ImageStore;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEvent;
import com.itextpdf.text.pdf.PdfWriter;

public class Report
{
	
	private Document document = null;
	private PdfWriter writer = null;
	private File file = null;
	
	private int pageWidth = 0;
	private int pageHeight = 0;
	
	private final MapTable<String, FontReport> fonts = new MapTable<String, FontReport>();
	private final MapTable<String, BaseColor> colors = new MapTable<String, BaseColor>();
	
	private ReportParameter paramters = null;
	
	private enum Alignment
	{
		left, right, center
	}
	
	private enum Elements
	{
		text, text_block, table, header, column_header, rows, column_row, image, rectangle
	};
	
	public Report(float marginLeft, float marginRight, float marginTop, float marginBottom)
	{
		initDocument(PageSize.A4, marginLeft, marginRight, marginTop, marginBottom);
	}
	
	public Report(String xml, ReportParameter parameters)
	{
		this.paramters = parameters;
		
		NodeElement root = new NodeElement(XMLUtils.readFromResource(xml + Constants.PRL_EXTENSION).getRootElement());
		
		initDocument(PageSize.getRectangle(root.getString(Attribute.size)), root.getInt(Attribute.margin_left), root.getInt(Attribute.margin_right), root.getInt(Attribute.margin_top), root.getInt(Attribute.margin_bottom));
		
		for (NodeElement node : root.getNodes())
		{
			
			Section type = Section.valueOf(node.getName());
			
			switch (type)
			{
			
				case colors:
					loadColors(node);
					break;
				
				case fonts:
					loadFonts(node);
					break;
				
				case elements:
					loadElements(0, 0, node);
					break;
				
				default:
					break;
			
			}
		}
	}
	
	private void initDocument(Rectangle type, float marginLeft, float marginRight, float marginTop, float marginBottom)
	{
		this.document = new Document(type, marginLeft, marginRight, marginTop, marginBottom);
		this.file = Environment.createTempFile(Constants.PDF_EXTENSION);
		
		try
		{
			this.writer = PdfWriter.getInstance(this.document, new FileOutputStream(this.file));
		}
		catch (Exception e)
		{
			AppError.setError(e);
		}
		
		this.document.open();
		
		this.pageWidth = (int)this.document.getPageSize().getWidth();
		this.pageHeight = (int)this.document.getPageSize().getHeight();
	}
	
	private void loadFonts(NodeElement root)
	{
		for (NodeElement node : root.getNodes())
		{
			FontReport font = new FontReport(node.getString(Attribute.family), node.getInt(Attribute.size), node.getBoolean(Attribute.bold), node.getBoolean(Attribute.italic));
			this.fonts.put(node.getString(Attribute.name), font);
		}
	}
	
	private void loadColors(NodeElement root)
	{
		for (NodeElement node : root.getNodes())
		{
			
			int red = Integer.parseInt(node.getString(Attribute.value).substring(0, 2), 16);
			int green = Integer.parseInt(node.getString(Attribute.value).substring(2, 4), 16);
			int blue = Integer.parseInt(node.getString(Attribute.value).substring(4, 6), 16);
			
			this.colors.put(node.getString(Attribute.name), new BaseColor(red, green, blue));
		}
	}
	
	private void loadElements(int initialX, int initialY, NodeElement root)
	{
		for (NodeElement node : root.getNodes())
		{
			
			Elements type = Elements.valueOf(node.getName());
			
			switch (type)
			{
			
				case text:
					processText(initialX, initialY, node);
					break;
				
				case text_block:
					processTextBlock(initialX, initialY, node);
					break;
				
				case table:
					processTable(initialX, initialY, node);
					break;
				
				case image:
					processImage(initialX, initialY, node);
					break;
				
				case rectangle:
					processRectangle(initialX, initialY, node);
					break;
				
				default:
					break;
			
			}
		}
	}
	
	private void processText(int initialX, int initialY, NodeElement node)
	{
		FontReport font = this.fonts.get(node.getString(Attribute.font));
		BaseColor color = this.colors.get(node.getString(Attribute.color));
		String value = "";
		
		if (node.exist(Attribute.prefix))
		{
			value += node.getStringNoTrim(Attribute.prefix);
		}
		
		if (node.exist(Attribute.value))
		{
			value += node.getString(Attribute.value);
		}
		else if (node.exist(Attribute.param))
		{
			value += this.paramters.getValue(node.getString(Attribute.param));
		}
		
		if (node.exist(Attribute.extra))
		{
			value += node.getStringNoTrim(Attribute.extra);
		}
		
		addText(value, font.getBaseFont(), font.getSize(), color, Alignment.valueOf(node.getString(Attribute.alignment)), initialX + node.getInt(Attribute.x), initialY + node.getInt(Attribute.y));
	}
	
	private void processTextBlock(int initialX, int initialY, NodeElement node)
	{
		FontReport font = this.fonts.get(node.getString(Attribute.font));
		BaseColor color = this.colors.get(node.getString(Attribute.color));
		String value = "";
		
		if (node.exist(Attribute.value))
		{
			value = node.getString(Attribute.value);
		}
		else if (node.exist(Attribute.param))
		{
			value = this.paramters.getValue(node.getString(Attribute.param));
		}
		
		addTextBlock(value, font.getBaseFont(), font.getSize(), color, Alignment.valueOf(node.getString(Attribute.alignment)), node.getInt(Attribute.leading), initialX + node.getInt(Attribute.x), initialY + node.getInt(Attribute.y), node.getInt(Attribute.width), node.getInt(Attribute.height));
	}
	
	private void processTable(int initialX, int initialY, NodeElement root)
	{
		PdfPTable table = null;
		BaseColor tableColor = this.colors.get(root.getString(Attribute.color));
		float tableBorder = root.getDecimal(Attribute.thick);
		
		for (NodeElement node : root.getNodes())
		{
			
			Elements type = Elements.valueOf(node.getName());
			
			switch (type)
			{
				case header:
					table = new PdfPTable(node.getNodes().length);
					List<Integer> columnWidthsList = new ArrayList<Integer>();
					int hearderHeight = 0;
					
					if (node.exist(Attribute.height))
					{
						hearderHeight = node.getInt(Attribute.height);
					}
					
					int totalWidth = 0;
					
					for (NodeElement columnHeader : node.getNodes())
					{
						columnWidthsList.add(columnHeader.getInt(Attribute.width));
						totalWidth += columnHeader.getInt(Attribute.width);
						
						if (columnHeader.exist(Attribute.value))
						{
							table.addCell(getCell(columnHeader.getString(Attribute.value), this.fonts.get(columnHeader.getString(Attribute.font)).getFont(), this.colors.get(columnHeader.getString(Attribute.color)), this.colors.get(columnHeader.getString(Attribute.bg_color)), tableColor, tableBorder, Alignment.valueOf(columnHeader.getString(Attribute.alignment)), columnHeader.getIntValue(Attribute.left_padding), columnHeader.getIntValue(Attribute.top_padding), columnHeader.getIntValue(Attribute.right_padding), columnHeader.getIntValue(Attribute.bottom_padding), hearderHeight));
						}
					}
					
					Integer[] columnWidths = new Integer[columnWidthsList.size()];
					columnWidthsList.toArray(columnWidths);
					
					setTableWidth(table, columnWidths);
					table.setTotalWidth(totalWidth);
					table.setLockedWidth(true);
					break;
				
				case rows:
					
					if (node.exist(Attribute.param))
					{
						int rowsHeight = node.getInt(Attribute.height);
						Object[] rows = this.paramters.getTable(node.getString(Attribute.param));
						
						for (Object row : rows)
						{
							for (NodeElement columnRow : node.getNodes())
							{
								String value = getObjectValue(row, columnRow.getString(Attribute.value));
								
								if (columnRow.exist(Attribute.extra))
								{
									value += columnRow.getStringNoTrim(Attribute.extra);
								}
								
								table.addCell(getCell(value, this.fonts.get(columnRow.getString(Attribute.font)).getFont(), this.colors.get(columnRow.getString(Attribute.color)), this.colors.get(columnRow.getString(Attribute.bg_color)), tableColor, tableBorder, Alignment.valueOf(columnRow.getString(Attribute.alignment)), columnRow.getIntValue(Attribute.left_padding), columnRow.getIntValue(Attribute.top_padding), columnRow.getIntValue(Attribute.right_padding), columnRow.getIntValue(Attribute.bottom_padding), rowsHeight));
							}
						}
						
						if (node.exist(Attribute.min))
						{
							for (int i = 0; i < (node.getInt(Attribute.min) - rows.length); i++)
							{
								for (NodeElement columnRow : node.getNodes())
								{
									PdfPCell emptyCell = new PdfPCell(new Paragraph(""));
									emptyCell.setBackgroundColor(this.colors.get(columnRow.getString(Attribute.bg_color)));
									emptyCell.setFixedHeight(rowsHeight);
									emptyCell.setBorderColor(tableColor);
									emptyCell.setBorderWidth(tableBorder);
									table.addCell(emptyCell);
								}
							}
						}
					}
					else
					{
						for (NodeElement row : node.getNodes())
						{
							int rowHeight = row.getInt(Attribute.height);
							
							for (NodeElement columnRow : row.getNodes())
							{
								String value = "";
								
								if (columnRow.exist(Attribute.value))
								{
									value = columnRow.getString(Attribute.value);
								}
								else if (columnRow.exist(Attribute.param))
								{
									value = this.paramters.getValue(columnRow.getString(Attribute.param));
								}
								
								if (columnRow.exist(Attribute.extra))
								{
									value += columnRow.getStringNoTrim(Attribute.extra);
								}
								
								table.addCell(getCell(value, this.fonts.get(columnRow.getString(Attribute.font)).getFont(), this.colors.get(columnRow.getString(Attribute.color)), this.colors.get(columnRow.getString(Attribute.bg_color)), tableColor, tableBorder, Alignment.valueOf(columnRow.getString(Attribute.alignment)), columnRow.getIntValue(Attribute.left_padding), columnRow.getIntValue(Attribute.top_padding), columnRow.getIntValue(Attribute.right_padding), columnRow.getIntValue(Attribute.bottom_padding), rowHeight));
							}
						}
					}
					
					break;
				
				default:
					break;
			
			}
		}
		
		addTable(table, initialX + root.getInt(Attribute.x), initialY + root.getInt(Attribute.y));
	}
	
	private void processRectangle(int initialX, int initialY, NodeElement root)
	{
		PdfPTable table = new PdfPTable(1);
		setTableWidth(table, root.getInt(Attribute.width));
		table.setTotalWidth(root.getInt(Attribute.width));
		table.setLockedWidth(true);
		
		PdfPCell emptyCell = new PdfPCell(new Paragraph(""));
		emptyCell.setBackgroundColor(this.colors.get(root.getString(Attribute.bg_color)));
		emptyCell.setFixedHeight(root.getInt(Attribute.height));
		emptyCell.setBorderColor(this.colors.get(root.getString(Attribute.color)));
		emptyCell.setBorderWidth(root.getDecimal(Attribute.thick));
		table.addCell(emptyCell);
		
		addTable(table, initialX + root.getInt(Attribute.x), initialY + root.getInt(Attribute.y));
		
		loadElements(initialX + root.getInt(Attribute.x), initialY + root.getInt(Attribute.y), root);
	}
	
	private String getObjectValue(Object object, String fieldName)
	{
		String result = "";
		
		if (!fieldName.isEmpty())
		{
			try
			{
				Class<?> clazz = object.getClass();
				Field field = clazz.getField(fieldName);
				
				if (field.getType().getName().equals("double"))
				{
					result = DataFormatter.formatDecimal(field.getDouble(object));
				}
				else
				{
					result = field.get(object).toString();
				}
			}
			catch (Exception e)
			{
				Debug.setError(e);
			}
		}
		
		return result;
	}
	
	private void processImage(int initialX, int initialY, NodeElement node)
	{
		addImage(node.getString(Attribute.path), initialX + node.getInt(Attribute.x), initialY + node.getInt(Attribute.y), node.getDecimalValue(Attribute.scale_width), node.getDecimalValue(Attribute.scale_height));
	}
	
	private void addText(String text, BaseFont font, int size, BaseColor color, Alignment alignment, float x, float y)
	{
		PdfContentByte cb = this.writer.getDirectContent();
		cb.beginText();
		cb.setColorFill(color);
		cb.setFontAndSize(font, size);
		cb.showTextAligned(getAlignment(alignment), text, x, this.pageHeight - y, 0);
		cb.endText();
	}
	
	private void addTextBlock(String text, BaseFont font, int size, BaseColor color, Alignment alignment, int leading, float x, float y, int width, int height)
	{
		PdfContentByte cb = this.writer.getDirectContent();
		cb.setColorFill(color);
		cb.setFontAndSize(font, size);
		ColumnText ct = new ColumnText(cb);
		Phrase phrase = new Phrase(text);
		phrase.setFont(new Font(font, Font.NORMAL, size));
		ct.setSimpleColumn(phrase, x, this.pageHeight - y, width, height, leading, getNormalAlignment(alignment));
		
		try
		{
			ct.go();
		}
		catch (Exception e)
		{
			Debug.setError(e);
		}
	}
	
	private void addTable(PdfPTable table, int x, int y)
	{
		table.writeSelectedRows(0, -1, x, this.pageHeight - y, this.writer.getDirectContent());
	}
	
	private void setTableWidth(PdfPTable table, Integer... widths)
	{
		float[] arrayWidth = new float[widths.length];
		
		for (int i = 0; i < widths.length; i++)
		{
			arrayWidth[i] = widths[i];
		}
		
		try
		{
			table.setWidths(arrayWidth);
		}
		catch (Exception e)
		{
			Debug.setError(e);
		}
	}
	
	private void addImage(String path, float x, float y, float scaleWidth, float scaleHeight)
	{
		try
		{
			Image image = Image.getInstance(ImageStore.getImageBytes(path));
			float yValue = (this.pageHeight - (image.getHeight() * (scaleHeight / 100))) - y;
			image.setAbsolutePosition(x, yValue);
			
			if (scaleWidth != 0)
			{
				image.scaleAbsoluteWidth(image.getWidth() * (scaleWidth / 100));
			}
			
			if (scaleHeight != 0)
			{
				image.scaleAbsoluteHeight(image.getHeight() * (scaleHeight / 100));
			}
			
			add(image);
		}
		catch (Exception e)
		{
			AppError.setError(e);
		}
	}
	
	private PdfPCell getCell(String text, Font font, BaseColor color, BaseColor bgColor, BaseColor borderColor, float thick, Alignment alignment, int leftPadding, int topPadding, int rightPadding, int bottomPadding, int height)
	{
		font.setColor(color);
		
		PdfPCell cell = new PdfPCell(new Paragraph(text, font));
		
		cell.setBackgroundColor(bgColor);
		cell.setHorizontalAlignment(getAlignment(alignment));
		cell.setPaddingLeft(leftPadding);
		cell.setPaddingTop(topPadding);
		cell.setPaddingRight(rightPadding);
		cell.setPaddingBottom(bottomPadding);
		cell.setFixedHeight(height);
		cell.setBorderColor(borderColor);
		cell.setBorderWidth(thick);
		
		return cell;
	}
	
	private int getAlignment(Alignment name)
	{
		int result = 0;
		
		switch (name)
		{
		
			case left:
				result = PdfContentByte.ALIGN_LEFT;
				break;
			
			case right:
				result = PdfContentByte.ALIGN_RIGHT;
				break;
			
			case center:
				result = PdfContentByte.ALIGN_CENTER;
				break;
			
			default:
				break;
		}
		
		return result;
	}
	
	private int getNormalAlignment(Alignment name)
	{
		int result = 0;
		
		switch (name)
		{
		
			case left:
				result = Element.ALIGN_LEFT;
				break;
			
			case right:
				result = Element.ALIGN_RIGHT;
				break;
			
			case center:
				result = Element.ALIGN_CENTER;
				break;
			
			default:
				break;
		}
		
		return result;
	}
	
	// ==================
	
	public void show()
	{
		this.document.close();
		Resource.open(this.file);
	}
	
	protected void add(Element element)
	{
		try
		{
			this.document.add(element);
		}
		catch (Exception e)
		{
			AppError.setError(e);
		}
	}
	
	protected PdfWriter getWriter()
	{
		return this.writer;
	}
	
	protected int getWidth()
	{
		return this.pageWidth;
	}
	
	protected int getHeight()
	{
		return this.pageHeight;
	}
	
	protected void addPageEvent(PdfPageEvent event)
	{
		if (this.writer != null)
		{
			this.writer.setPageEvent(event);
		}
	}
}