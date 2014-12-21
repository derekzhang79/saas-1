package client.app.sections.operations;

import share.app.sections.IOperations;
import share.app.sections.Section;
import client.core.operations.Operation;

public class OperationsSections implements IOperations
{
	private static OperationsSections instance = new OperationsSections();

	public static OperationsSections call()
	{
		return OperationsSections.instance;
	}

	@Override
	public Section[] getSections()
	{
		Operation<Section[]> operation = new Operation<Section[]>(IOperations.GET_SECTIONS);

		return operation.run();
	}

	@Override
	public Section addSection(Section section)
	{
		Operation<Section> operation = new Operation<Section>(IOperations.ADD_SECTION);

		return operation.run(section);
	}

	@Override
	public Boolean editSection(Section original, Section newSection)
	{
		Operation<Boolean> operation = new Operation<Boolean>(IOperations.EDIT_SECTION);

		return operation.run(original, newSection);
	}

	@Override
	public Boolean deleteSection(Section section)
	{
		Operation<Boolean> operation = new Operation<Boolean>(IOperations.DELETE_SECTION);

		return operation.run(section);
	}
}