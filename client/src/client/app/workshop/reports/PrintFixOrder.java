package client.app.workshop.reports;

import share.app.contacts.clients.Client;
import share.app.workshop.FixOrder;
import share.app.workshop.FixOrderDetail;
import share.core.Constants;
import share.core.objects.Company;
import client.core.Profile;
import client.core.gui.DataFormatter;
import client.core.gui.reports.Report;
import client.core.gui.reports.ReportParameter;

public class PrintFixOrder {
	
	private FixOrder fixOrder = null;
	private FixOrderDetail[] details = null;
	private Client client = null;
	
	public PrintFixOrder(FixOrder fixOrder, FixOrderDetail[] details, Client client) {
		this.fixOrder = fixOrder;
		this.details = details;
		this.client = client;
	}
	
	public void show() {
		ReportParameter parameters = new ReportParameter();
		parameters.add("fix_order_id", this.fixOrder.id);
		parameters.add("fix_order_date", this.fixOrder.start.toString());
		parameters.add("fix_order_client_name", this.fixOrder.clientName);
		
		Company company = Profile.getCompany();
		
		parameters.add("company_name", company.getOwnerName());
		parameters.add("company_address", company.getAddress());
		parameters.add("company_city", company.getPostCodeCity());
		parameters.add("company_identification", company.getIdentification());
		
		parameters.add("client_name", this.client.name);
		parameters.add("client_address", this.client.address);
		parameters.add("client_city", this.client.getPostCodeCity());
		parameters.add("client_identification", this.client.identification);
		
		parameters.add("comments", this.fixOrder.comments);
		parameters.add("total_fix_order", DataFormatter.formatDecimal(getTotalAmount(this.details)));
		parameters.addTable("details", this.details);
		
		Report report = new Report(Constants.REPORT_BASE_PATH + "workshop/reports/fix_order", parameters);
		report.show();
	}
	
	private double getTotalAmount(FixOrderDetail[] detailsList) {
		double result = 0;
		
		for (FixOrderDetail fixOrderDetail : detailsList) {
			result += fixOrderDetail.amount;
		}
		
		return result;
	}
}