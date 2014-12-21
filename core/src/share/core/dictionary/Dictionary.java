package share.core.dictionary;

import share.core.utils.MapTable;
import share.core.utils.Shareable;

public class Dictionary extends Shareable
{
	private static final long serialVersionUID = -8459174175584534940L;

	private final MapTable<String, DictionaryCategory> categories = new MapTable<String, DictionaryCategory>();

	public void add(String category, DictionaryCategory value)
	{
		this.categories.put(category, value);
	}

	public DictionaryCategory get(String category)
	{
		return this.categories.get(category);
	}

	public String get(String category, String code)
	{
		return this.categories.get(category).get(code);
	}
}