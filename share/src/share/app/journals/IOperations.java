package share.app.journals;

public interface IOperations
{
	public static final String GET_JOURNALS = "server.app.journals.Operations:getJournals";
	public static final String GET_JOURNAL_DETAIL = "server.app.journals.Operations:getJournalDetail";
	public static final String ADD_JOURNAL = "server.app.journals.Operations:addJournal";
	public static final String EDIT_JOURNAL = "server.app.journals.Operations:editJournal";
	public static final String EDIT_JOURNAL_DETAIL = "server.app.journals.Operations:editJournalDetail";
	public static final String DELETE_JOURNAL = "server.app.journals.Operations:deleteJournal";;
	
	public Journal[] getJournals(Integer year, String month);
	
	public JournalDetail[] getJournalDetail(Integer journal);
	
	public Journal addJournal(Journal journal);
	
	public Boolean editJournal(Journal original, Journal newJournal);
	
	public Boolean editJournalDetail(JournalDetail original, JournalDetail newJournalDetail);
	
	public Boolean deleteJournal(Journal journal);
}