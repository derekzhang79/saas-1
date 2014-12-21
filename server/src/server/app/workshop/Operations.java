package server.app.workshop;

import java.sql.Connection;

import server.app.db.tables.TableFixOrder;
import server.app.db.tables.TableFixOrderDetail;
import server.core.ServerOperation;
import share.app.workshop.FixOrder;
import share.app.workshop.FixOrderDetail;
import share.app.workshop.IOperations;

public class Operations extends ServerOperation implements IOperations {
	
	public Operations(Connection appConnection, Connection sysConnection) {
		super(appConnection, sysConnection);
	}
	
	public FixOrder[] getFixOrders(Integer clientParam, String statusParam) {
		TableFixOrder table = new TableFixOrder(getConnection());
		
		return table.getFixOrders(clientParam, statusParam);
	}
	
	public FixOrder addFixOrder(FixOrder fixOrder) {
		TableFixOrder table = new TableFixOrder(getConnection());
		
		return table.add(fixOrder);
	}
	
	public Boolean editFixOrder(FixOrder original, FixOrder newFixOrder) {
		TableFixOrder table = new TableFixOrder(getConnection());
		
		return table.edit(original, newFixOrder);
	}
	
	public Boolean deleteFixOrder(FixOrder fixOrder) {
		TableFixOrder table = new TableFixOrder(getConnection());
		
		return table.delete(fixOrder);
	}
	
	public FixOrderDetail[] getFixOrdersDetail(Integer fixOrderID) {
		TableFixOrderDetail table = new TableFixOrderDetail(getConnection());
		
		return table.getFixOrdersDetail(fixOrderID);
	}
	
	public Boolean addFixOrderDetail(FixOrderDetail fixOrderDetail) {
		TableFixOrderDetail table = new TableFixOrderDetail(getConnection());
		
		return table.add(fixOrderDetail);
	}
	
	public Boolean editFixOrderDetail(FixOrderDetail original, FixOrderDetail newFixOrderDetail) {
		TableFixOrderDetail table = new TableFixOrderDetail(getConnection());
		
		return table.edit(original, newFixOrderDetail);
	}
	
	public Boolean deleteFixOrderDetail(FixOrderDetail fixOrderDetail) {
		TableFixOrderDetail table = new TableFixOrderDetail(getConnection());
		
		return table.delete(fixOrderDetail);
	}
}