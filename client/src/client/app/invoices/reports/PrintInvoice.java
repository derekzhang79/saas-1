package client.app.invoices.reports;

import share.app.contacts.clients.Client;
import share.app.dictionary.Category.TAX;
import share.app.invoices.Invoice;
import share.app.invoices.InvoiceDetail;
import share.app.taxes.Tax;
import share.core.Constants;
import share.core.objects.Company;
import client.core.gui.DataFormatter;
import client.core.gui.reports.Report;
import client.core.gui.reports.ReportParameter;
import client.core.profile.Profile;

public class PrintInvoice {
	
	private Invoice invoice = null;
	private InvoiceDetail[] details = null;
	private Client client = null;
	private Tax[] taxes = null;
	
	public PrintInvoice(Invoice invoice, InvoiceDetail[] details, Client client, Tax[] taxes) {
		this.invoice = invoice;
		this.details = details;
		this.client = client;
		this.taxes = taxes;
	}
	
	public void show() {
		ReportParameter parameters = new ReportParameter();
		parameters.add("invoice_id", this.invoice.number);
		parameters.add("invoice_date", this.invoice.date);
		
		Company company = Profile.getCompany();
		
		parameters.add("company_name", company.getOwnerName());
		parameters.add("company_address", company.getAddress());
		parameters.add("company_city", company.getPostCodeCity());
		parameters.add("company_identification", company.getIdentification());
		
		parameters.add("client_name", this.client.name);
		parameters.add("client_address", this.client.address);
		parameters.add("client_city", this.client.getPostCodeCity());
		parameters.add("client_identification", this.client.identification);
		parameters.add("comments", this.invoice.comments);
		parameters.add("payment_method", this.invoice.paymentMethodDescription);
		
		double subTotal = getSubTotalAmount();
		double discountPercentage = this.invoice.discount;
		double discountAmount = (discountPercentage / 100) * subTotal;
		double taxBase = subTotal - discountAmount;
		
		parameters.add("subtotal", subTotal);
		parameters.add("discount_percentage", DataFormatter.formatDecimal(discountPercentage));
		parameters.add("discount_amount", DataFormatter.formatDecimal(discountAmount));
		parameters.add("tax_base", DataFormatter.formatDecimal(taxBase));
		
		parameters.add("tax_type_1", getTaxValue(TAX.SUPER_LOW));
		parameters.add("tax_type_2", getTaxValue(TAX.LOW));
		parameters.add("tax_type_3", getTaxValue(TAX.GENERAL));
		
		double taxAmountType1 = getTaxAmount(TAX.SUPER_LOW, discountPercentage);
		double taxAmountType2 = getTaxAmount(TAX.LOW, discountPercentage);
		double taxAmountType3 = getTaxAmount(TAX.GENERAL, discountPercentage);
		double taxAmountTotal = taxAmountType1 + taxAmountType2 + taxAmountType3;
		
		parameters.add("tax_amount_1", DataFormatter.formatDecimal(taxAmountType1));
		parameters.add("tax_amount_2", DataFormatter.formatDecimal(taxAmountType2));
		parameters.add("tax_amount_3", DataFormatter.formatDecimal(taxAmountType3));
		parameters.add("total_tax_amount", DataFormatter.formatDecimal(taxAmountTotal));
		
		parameters.add("total_invoice", DataFormatter.formatDecimal(taxBase + taxAmountTotal));
		
		parameters.addTable("details", this.details);
		
		Report report = new Report(Constants.REPORT_BASE_PATH + "invoices/reports/invoice", parameters);
		report.show();
	}
	
	private String getTaxValue(String type) {
		String result = "";
		
		for (Tax tax : this.taxes) {
			if (tax.type.equals(type)) {
				result = DataFormatter.formatDecimal(tax.value);
				break;
			}
		}
		
		return result;
	}
	
	private double getTaxAmount(String taxType, double discount) {
		double result = 0;
		
		for (InvoiceDetail invoiceDetail : this.details) {
			
			if (invoiceDetail.tax.equals(taxType)) {
				
				double subTotal = invoiceDetail.priceWithoutTax - (invoiceDetail.priceWithoutTax * (discount / 100));
				
				result += (subTotal * (invoiceDetail.taxValue / 100));
			}
		}
		
		return result;
	}
	
	private double getSubTotalAmount() {
		double result = 0;
		
		for (InvoiceDetail invoiceDetail : this.details) {
			result += invoiceDetail.priceWithoutTax;
		}
		
		return result;
	}
}