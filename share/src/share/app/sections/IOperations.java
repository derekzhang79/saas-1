package share.app.sections;

public interface IOperations
{
	public static final String BASE_OPERATIONS = "server.app.sections.Operations:";
	public static final String GET_SECTIONS = IOperations.BASE_OPERATIONS + "getSections";
	public static final String ADD_SECTION = IOperations.BASE_OPERATIONS + "addSection";
	public static final String EDIT_SECTION = IOperations.BASE_OPERATIONS + "editSection";
	public static final String DELETE_SECTION = IOperations.BASE_OPERATIONS + "deleteSection";
	
	public Section[] getSections();
	
	public Section addSection(Section section);
	
	public Boolean editSection(Section original, Section newSection);
	
	public Boolean deleteSection(Section section);
}