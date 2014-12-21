package client.core.gui.components;

import java.awt.Dimension;

import javax.swing.BorderFactory;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class ExtendedChartLine extends ChartPanel {
	
	private static final long serialVersionUID = 1L;
	
	private XYSeriesCollection dataset = null;
	
	public ExtendedChartLine(JFreeChart chart, XYSeriesCollection dataset, int width, int height) {
		super(chart);
		
		this.dataset = dataset;
		
		setPreferredSize(new Dimension(width, height));
		setBorder(BorderFactory.createLoweredBevelBorder());
		
		ChartViewer viewer = new ChartViewer();
		viewer.run(this);
	}
	
	public void clear() {
		this.dataset.removeAllSeries();
	}
	
	private boolean existsGroup(String group) {
		boolean valid = false;
		
		try {
			this.dataset.getSeries(group);
			valid = true;
		} catch (Exception e) {
		}
		
		return valid;
	}
	
	public void addItem(String group, double x, double y) {
		if (existsGroup(group)) {
			XYSeries series = this.dataset.getSeries(group);
			series.add(x, y);
		} else {
			XYSeries series = new XYSeries(group);
			series.add(x, y);
			this.dataset.addSeries(series);
		}
	}
}