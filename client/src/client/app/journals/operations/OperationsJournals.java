package client.app.journals.operations;

import share.app.journals.IOperations;
import share.app.journals.Journal;
import share.app.journals.JournalDetail;
import client.core.Operation;

public class OperationsJournals implements IOperations {
	
	private static OperationsJournals instance = new OperationsJournals();
	
	public static OperationsJournals call() {
		return OperationsJournals.instance;
	}
	
	public Journal[] getJournals(Integer year, String month) {
		Operation<Journal[]> operation = new Operation<Journal[]>(IOperations.GET_JOURNALS);
		
		return operation.run(year, month);
	}
	
	public JournalDetail[] getJournalDetail(Integer journal) {
		Operation<JournalDetail[]> operation = new Operation<JournalDetail[]>(IOperations.GET_JOURNAL_DETAIL);
		
		return operation.run(journal);
	}
	
	public Journal addJournal(Journal journal) {
		Operation<Journal> operation = new Operation<Journal>(IOperations.ADD_JOURNAL);
		
		return operation.run(journal);
	}
	
	public Boolean editJournal(Journal original, Journal newJournal) {
		Operation<Boolean> operation = new Operation<Boolean>(IOperations.EDIT_JOURNAL);
		
		return operation.run(original, newJournal);
	}
	
	public Boolean editJournalDetail(JournalDetail original, JournalDetail newJournalDetail) {
		Operation<Boolean> operation = new Operation<Boolean>(IOperations.EDIT_JOURNAL_DETAIL);
		
		return operation.run(original, newJournalDetail);
	}
	
	public Boolean deleteJournal(Journal journal) {
		Operation<Boolean> operation = new Operation<Boolean>(IOperations.DELETE_JOURNAL);
		
		return operation.run(journal);
	}
}