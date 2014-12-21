package share.core.dictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import share.core.MapTable;
import share.core.Shareable;

public class DictionaryCategory extends Shareable
{
	private static final long serialVersionUID = 4984994081199296808L;

	private final MapTable<String, String> list = new MapTable<String, String>();

	public void add(String code, String value)
	{
		this.list.put(code, value);
	}

	public String get(String code)
	{
		return this.list.get(code);
	}

	public String[] getCodes()
	{
		List<String> codes = new ArrayList<String>();

		Enumeration<String> codeList = this.list.keys();

		while (codeList.hasMoreElements())
		{
			codes.add(codeList.nextElement());
		}

		Collections.sort(codes);

		String[] result = new String[this.list.size()];
		codes.toArray(result);

		return result;
	}
}