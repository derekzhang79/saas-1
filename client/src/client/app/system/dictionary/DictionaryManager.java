package client.app.system.dictionary;

import share.app.dictionary.Category.Categories;
import share.core.dictionary.Dictionary;
import client.core.gui.components.ExtendedComboBoxItem;

public class DictionaryManager
{
	private static Dictionary dictionary = new Dictionary();

	public static void set(Dictionary value)
	{
		DictionaryManager.dictionary = value;
	}

	public static ExtendedComboBoxItem[] get(Categories category)
	{
		String[] codes = DictionaryManager.dictionary.get(category.toString()).getCodes();
		ExtendedComboBoxItem[] result = new ExtendedComboBoxItem[codes.length];

		for (int i = 0; i < codes.length; i++)
		{
			result[i] = new ExtendedComboBoxItem(codes[i], DictionaryManager.get(category.toString(), codes[i]));
		}

		return result;
	}

	public static String get(String category, String code)
	{
		return DictionaryManager.dictionary.get(category, code);
	}
}