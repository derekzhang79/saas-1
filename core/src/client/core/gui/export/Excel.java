package client.core.gui.export;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import share.core.constants.Constants;
import share.core.objects.Date;
import share.core.utils.Environment;
import share.core.utils.Resource;
import client.core.debug.Debug;
import client.core.gui.components.ColumnType;
import client.core.gui.format.DataFormatter;
import client.core.gui.messages.Message;

public class Excel
{
	public static void export(String title, ColumnType[] columns, Object[] rows)
	{
		HSSFWorkbook excelFile = new HSSFWorkbook();
		HSSFSheet sheet = excelFile.createSheet(title);
		
		HSSFRow rowTitle = sheet.createRow(0);
		Excel.getColumns(excelFile, rowTitle, columns);
		
		for (int i = 0; i < rows.length; i++)
		{
			Object row = rows[i];
			Class<?> clazz = row.getClass();
			HSSFRow currentRow = sheet.createRow(i + 1);
			currentRow.setHeightInPoints((short)22);
			
			for (int j = 0; j < columns.length; j++)
			{
				ColumnType column = columns[j];
				
				try
				{
					Field field = clazz.getField(column.getCode());
					HSSFCell cell = currentRow.createCell(j);
					CellStyle style = excelFile.createCellStyle();
					
					if (column.getRealType().equals(ColumnType.Type.STRING))
					{
						cell.setCellValue(field.get(row).toString());
					}
					else if (column.getRealType().equals(ColumnType.Type.BOOLEAN))
					{
						style.setAlignment(CellStyle.ALIGN_CENTER);
						cell.setCellValue(Message.getBooleanString(field.getBoolean(row)));
					}
					else if (column.getRealType().equals(ColumnType.Type.DATE))
					{
						Date dateValue = (Date)field.get(row);
						cell.setCellValue(dateValue.toString());
						style.setAlignment(CellStyle.ALIGN_CENTER);
					}
					else if (column.getRealType().equals(ColumnType.Type.INTEGER))
					{
						cell.setCellValue(field.getLong(row));
					}
					else if (column.getRealType().equals(ColumnType.Type.DECIMAL))
					{
						HSSFDataFormat df = excelFile.createDataFormat();
						style.setDataFormat(df.getFormat("#,##0.00"));
						cell.setCellValue(field.getDouble(row));
					}
					else if (column.getRealType().equals(ColumnType.Type.MONEY))
					{
						double doubleValue = field.getDouble(row);
						style.setAlignment(CellStyle.ALIGN_RIGHT);
						cell.setCellValue(DataFormatter.formatDecimal(doubleValue) + " " + Constants.CURRENCY_EURO);
					}
					
					if ((i % 2) != 0)
					{
						HSSFColor color = Excel.setColor(excelFile, HSSFColor.LAVENDER.index, (byte)0xF0, (byte)0xF0, (byte)0xF0);
						style.setFillForegroundColor(color.getIndex());
						style.setFillPattern(CellStyle.SOLID_FOREGROUND);
					}
					
					style.setBorderLeft((short)1);
					style.setBorderBottom((short)1);
					style.setBorderTop((short)1);
					style.setBorderRight((short)1);
					
					Font font = excelFile.createFont();
					font.setFontName(HSSFFont.FONT_ARIAL);
					font.setFontHeightInPoints((short)15);
					style.setFont(font);
					
					cell.setCellStyle(style);
					
				}
				catch (Exception e)
				{
					Debug.setError(e);
				}
			}
		}
		
		for (int j = 0; j < columns.length; j++)
		{
			sheet.autoSizeColumn(j);
		}
		
		try
		{
			File file = Environment.createTempFile(".xls");
			FileOutputStream fos = new FileOutputStream(file);
			excelFile.write(fos);
			fos.flush();
			fos.close();
			Resource.open(file);
		}
		catch (Exception e)
		{
			Debug.setError(e);
		}
	}
	
	public static HSSFColor setColor(HSSFWorkbook workbook, short index, byte r, byte g, byte b)
	{
		HSSFColor hssfColor = null;
		
		try
		{
			HSSFPalette palette = workbook.getCustomPalette();
			hssfColor = palette.findColor(r, g, b);
			
			if (hssfColor == null)
			{
				palette.setColorAtIndex(index, r, g, b);
				hssfColor = palette.getColor(index);
			}
		}
		catch (Exception e)
		{
			Debug.setError(e);
		}
		
		return hssfColor;
	}
	
	private static void getColumns(HSSFWorkbook excelFile, HSSFRow row, ColumnType[] columns)
	{
		for (int i = 0; i < columns.length; i++)
		{
			ColumnType column = columns[i];
			
			Font font = excelFile.createFont();
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			font.setFontName(HSSFFont.FONT_ARIAL);
			font.setFontHeightInPoints((short)15);
			
			CellStyle style = excelFile.createCellStyle();
			HSSFColor color = Excel.setColor(excelFile, HSSFColor.BLUE.index, (byte)0xD0, (byte)0xD0, (byte)0xD0);
			style.setFillForegroundColor(color.getIndex());
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style.setAlignment(CellStyle.ALIGN_CENTER);
			style.setFont(font);
			style.setBorderLeft((short)1);
			style.setBorderBottom((short)1);
			style.setBorderTop((short)1);
			style.setBorderRight((short)1);
			
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(column.getName());
			cell.setCellStyle(style);
			
			row.setHeightInPoints((short)20);
		}
	}
}