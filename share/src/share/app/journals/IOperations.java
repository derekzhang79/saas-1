package share.app.journals;

public interface IOperations
{
	public static final String BASE_OPERATIONS = "server.app.journals.Operations:";
	public static final String GET_JOURNALS = IOperations.BASE_OPERATIONS + "getJournals";
	public static final String GET_JOURNAL_DETAIL = IOperations.BASE_OPERATIONS + "getJournalDetail";
	public static final String ADD_JOURNAL = IOperations.BASE_OPERATIONS + "addJournal";
	public static final String EDIT_JOURNAL = IOperations.BASE_OPERATIONS + "editJournal";
	public static final String EDIT_JOURNAL_DETAIL = IOperations.BASE_OPERATIONS + "editJournalDetail";
	public static final String DELETE_JOURNAL = IOperations.BASE_OPERATIONS + "deleteJournal";;

	public Journal[] getJournals(Integer year, String month);

	public JournalDetail[] getJournalDetail(Integer journal);

	public Journal addJournal(Journal journal);

	public Boolean editJournal(Journal original, Journal newJournal);

	public Boolean editJournalDetail(JournalDetail original, JournalDetail newJournalDetail);

	public Boolean deleteJournal(Journal journal);
}