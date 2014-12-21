package server.app.workshop;

import java.sql.Connection;
import server.app.database.tables.TableFixOrder;
import server.app.database.tables.TableFixOrderDetail;
import server.core.connection.ServerOperation;
import share.app.workshop.FixOrder;
import share.app.workshop.FixOrderDetail;
import share.app.workshop.IOperations;

public class Operations extends ServerOperation implements IOperations
{
	public Operations(Connection appConnection, Connection sysConnection)
	{
		super(appConnection, sysConnection);
	}

	@Override
	public FixOrder[] getFixOrders(Integer clientParam, String statusParam)
	{
		TableFixOrder table = new TableFixOrder(getConnection());

		return table.getFixOrders(clientParam, statusParam);
	}

	@Override
	public FixOrder addFixOrder(FixOrder fixOrder)
	{
		TableFixOrder table = new TableFixOrder(getConnection());

		return table.add(fixOrder);
	}

	@Override
	public Boolean editFixOrder(FixOrder original, FixOrder newFixOrder)
	{
		TableFixOrder table = new TableFixOrder(getConnection());

		return table.edit(original, newFixOrder);
	}

	@Override
	public Boolean deleteFixOrder(FixOrder fixOrder)
	{
		TableFixOrder table = new TableFixOrder(getConnection());

		return table.delete(fixOrder);
	}

	@Override
	public FixOrderDetail[] getFixOrdersDetail(Integer fixOrderID)
	{
		TableFixOrderDetail table = new TableFixOrderDetail(getConnection());

		return table.getFixOrdersDetail(fixOrderID);
	}

	@Override
	public Boolean addFixOrderDetail(FixOrderDetail fixOrderDetail)
	{
		TableFixOrderDetail table = new TableFixOrderDetail(getConnection());

		return table.add(fixOrderDetail);
	}

	@Override
	public Boolean editFixOrderDetail(FixOrderDetail original, FixOrderDetail newFixOrderDetail)
	{
		TableFixOrderDetail table = new TableFixOrderDetail(getConnection());

		return table.edit(original, newFixOrderDetail);
	}

	@Override
	public Boolean deleteFixOrderDetail(FixOrderDetail fixOrderDetail)
	{
		TableFixOrderDetail table = new TableFixOrderDetail(getConnection());

		return table.delete(fixOrderDetail);
	}
}