package share.app.journals;

import share.core.utils.Shareable;

public class JournalDetail extends Shareable
{
	private static final long serialVersionUID = -7189382194664616534L;
	
	public final int id;
	public final int journal;
	public final int section;
	public final double amount;
	public final String sectionName;

	public JournalDetail(int id, int journal, int section, String sectionName, double amount)
	{
		this.id = id;
		this.journal = journal;
		this.section = section;
		this.sectionName = sectionName;
		this.amount = amount;
	}
}