package client.core.gui.components;

import java.awt.Dimension;
import javax.swing.BorderFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class ExtendedChartBar extends ChartPanel
{
	private static final long serialVersionUID = 401032619803921371L;
	
	private final DefaultCategoryDataset dataset;
	
	public ExtendedChartBar(JFreeChart chart, DefaultCategoryDataset dataset, int width, int height)
	{
		super(chart);
		
		this.dataset = dataset;
		
		setPreferredSize(new Dimension(width, height));
		setBorder(BorderFactory.createLoweredBevelBorder());
		
		ChartViewer viewer = new ChartViewer();
		viewer.run(this);
	}
	
	public void clear()
	{
		this.dataset.clear();
	}
	
	public void addItem(String group, String item, double value)
	{
		this.dataset.setValue(value, item, group);
	}
}