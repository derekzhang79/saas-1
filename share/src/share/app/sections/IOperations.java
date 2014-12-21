package share.app.sections;

public interface IOperations
{
	public static final String GET_SECTIONS = "server.app.sections.Operations:getSections";
	public static final String ADD_SECTION = "server.app.sections.Operations:addSection";
	public static final String EDIT_SECTION = "server.app.sections.Operations:editSection";
	public static final String DELETE_SECTION = "server.app.sections.Operations:deleteSection";

	public Section[] getSections();

	public Section addSection(Section section);

	public Boolean editSection(Section original, Section newSection);

	public Boolean deleteSection(Section section);
}