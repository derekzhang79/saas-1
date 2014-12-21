package client.core.gui.components;

import java.awt.Dimension;

import javax.swing.BorderFactory;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class ExtendedChartPie extends ChartPanel {
	
	private static final long serialVersionUID = 1L;
	
	private DefaultPieDataset dataset = null;
	
	public ExtendedChartPie(JFreeChart chart, DefaultPieDataset dataset, int width, int height) {
		super(chart);
		
		this.dataset = dataset;
		
		setPreferredSize(new Dimension(width, height));
		setBorder(BorderFactory.createLoweredBevelBorder());
		
		ChartViewer viewer = new ChartViewer();
		viewer.run(this);
	}
	
	public void clear() {
		this.dataset.clear();
	}
	
	public void addItem(String text, double value) {
		this.dataset.setValue(text, value);
	}
}