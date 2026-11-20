package it.giustizia.smp.utility;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;

public class CustomStringEditor extends StringTrimmerEditor {

	public CustomStringEditor() {

		super(true);
	}

	public void setAsText(String text) {

		if (text != null) {
			text = StringUtils.upperCase(text).trim();
		}
		super.setAsText(text);
	}
}
