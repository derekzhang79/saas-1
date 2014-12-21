package client.core.gui.export;

import client.core.debug.Debug;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class HeaderFooter extends PdfPageEventHelper
{

	private PdfPCell[] header = null;
	private String title = "";

	public HeaderFooter(String title, PdfPCell[] header)
	{
		this.title = title;
		this.header = header;
	}

	@Override
	public void onOpenDocument(PdfWriter writer, Document document)
	{
	}

	@Override
	public void onChapter(PdfWriter writer, Document document, float paragraphPosition, Paragraph titleParagraph)
	{
	}

	@Override
	public void onStartPage(PdfWriter writer, Document document)
	{
		PdfContentByte cb = writer.getDirectContent();
		cb.beginText();
		try
		{
			cb.setFontAndSize(BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.EMBEDDED), 15);
		}
		catch (Exception e)
		{
			Debug.setError(e);
		}
		cb.showTextAligned(PdfContentByte.ALIGN_CENTER, this.title, document.getPageSize().getWidth() / 2, document.getPageSize().getHeight() - 30, 0);
		cb.endText();

		PdfPTable table = new PdfPTable(this.header.length);
		table.setWidthPercentage(100);

		for (PdfPCell cell : this.header)
		{
			table.addCell(cell);
		}

		try
		{
			document.add(table);
		}
		catch (DocumentException e)
		{
			Debug.setError(e);
		}
	}

	@Override
	public void onEndPage(PdfWriter writer, Document document)
	{
		ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(String.format("%d", writer.getPageNumber())), 300f, 20f, 0);
	}
}