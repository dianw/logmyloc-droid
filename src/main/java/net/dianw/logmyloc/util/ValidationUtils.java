package net.dianw.logmyloc.util;

import android.widget.EditText;

public class ValidationUtils {
	/**
	 * 
	 * @param editTexts
	 *            The {@code EditText} to be validated
	 * @return {@code true} if there is {@link EditText} with blank value
	 */
	public static boolean isBlank(EditText... editTexts) {
		if (editTexts == null)
			return false;

		for (EditText editText : editTexts) {
			String value = editText.getText().toString();
			if (value == null) {
				editText.requestFocus();
				return true;
			} else if (value.trim().equalsIgnoreCase("")) {
				editText.requestFocus();
				return true;
			}
		}

		return false;
	}

	public static boolean isNotDouble(EditText... editTexts) {
		if (editTexts == null)
			return false;

		for (EditText editText : editTexts) {
			String value = editText.getText().toString();
			if (value == null) {
				editText.requestFocus();
				return true;
			} else {
				try {
					value = Double.toString(Double.parseDouble(value));
					editText.setText(value);
				} catch (NumberFormatException e) {
					editText.requestFocus();

					return true;
				}
			}
		}

		return false;
	}
}
