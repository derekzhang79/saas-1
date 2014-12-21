package client.core.gui.export;

import java.lang.reflect.Field;

import client.core.Debug;
import client.core.gui.components.ColumnType;
import client.core.gui.reports.Report;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class PDF extends Report {
	
	private PDF(int marginLeft, int marginRight, int marginTop, int marginBottom) {
		super(marginLeft, marginRight, marginTop, marginBottom);
	}
	
	public static void export(String title, ColumnType[] columns, Object[] rows) {
		PDF pdf = new PDF(20, 20, 45, 40);
		
		PdfContentByte cb = pdf.getWriter().getDirectContent();
		cb.beginText();
		try {
			cb.setFontAndSize(BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.EMBEDDED), 15);
		} catch (Exception e) {
			Debug.setError(e);
		}
		cb.showTextAligned(PdfContentByte.ALIGN_CENTER, title, pdf.getWidth() / 2, pdf.getHeight() - 30, 0);
		cb.endText();
		
		PdfPCell[] header = new PdfPCell[columns.length];
		
		PdfPTable table = new PdfPTable(columns.length);
		table.setWidthPercentage(100);
		
		for (int i = 0; i < columns.length; i++) {
			ColumnType column = columns[i];
			PdfPCell cell = PDF.getCell(column.getName(), column.getRealType(), -1, true);
			header[i] = cell;
			table.addCell(cell);
		}
		
		pdf.addPageEvent(new HeaderFooter(title, header));
		
		for (int i = 0; i < rows.length; i++) {
			Object row = rows[i];
			Class<?> clazz = row.getClass();
			
			for (ColumnType column : columns) {
				try {
					Field field = clazz.getField(column.getCode());
					table.addCell(PDF.getCell(Exporter.getValue(field, row, column.getRealType()), column.getRealType(), i, false));
				} catch (Exception e) {
					Debug.setError(e);
				}
			}
		}
		
		pdf.add(table);
		pdf.show();
	}
	
	private static PdfPCell getCell(String title, ColumnType.Type type, int row, boolean bold) {
		PdfPCell cell = null;
		
		if (bold) {
			Font fontbold = FontFactory.getFont("Arial", 12, Font.BOLD);
			cell = new PdfPCell(new Paragraph(title, fontbold));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new BaseColor(210, 210, 210));
		} else {
			cell = new PdfPCell(new Paragraph(title));
			cell.setPaddingLeft(5);
			cell.setPaddingRight(5);
			
			if ((type.equals(ColumnType.Type.STRING))) {
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			} else if (((type.equals(ColumnType.Type.BOOLEAN))) || ((type.equals(ColumnType.Type.DATE)))) {
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			} else {
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			}
			
			if ((row % 2) != 0) {
				cell.setBackgroundColor(new BaseColor(240, 240, 240));
			} else {
				cell.setBackgroundColor(new BaseColor(255, 255, 255));
			}
		}
		
		cell.setPaddingBottom(5);
		
		return cell;
	}
}