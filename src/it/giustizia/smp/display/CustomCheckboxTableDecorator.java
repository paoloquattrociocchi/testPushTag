package it.giustizia.smp.display;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.ObjectUtils;
import org.displaytag.decorator.TableDecorator;
import org.displaytag.model.TableModel;

public class CustomCheckboxTableDecorator extends TableDecorator {

	private String id = "id";
	private String fieldName = "_chk";
	private String disabled;
	private String onClick;
	private String store;
	private Map params;
	private List checkedIds;

	public void setId(String id) {
		this.id = id;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}

	public void setStore(String store) {
		this.store = store;
	}

	private boolean storeCheck() {
		boolean ret = false;
		if (store != null && !"".equals(store)) {
			ret = true;
		}
		return ret;
	}

	public void init(PageContext pageContext, Object decorated, TableModel tableModel) {
		super.init(pageContext, decorated, tableModel);
		String[] params = pageContext.getRequest().getParameterValues(fieldName);
		checkedIds = params != null ? new ArrayList(Arrays.asList(params)) : new ArrayList(0);
	}

	public void finish() {

		if (!checkedIds.isEmpty() && storeCheck()) {
			JspWriter writer = getPageContext().getOut();
			for (Iterator it = checkedIds.iterator(); it.hasNext();) {
				String name = (String) it.next();
				StringBuffer buffer = new StringBuffer();
				buffer.append("<input type=\"hidden\" name=\"");
				buffer.append(fieldName);
				buffer.append("\" value=\"");
				buffer.append(name);
				buffer.append("\">");
				try {
					writer.write(buffer.toString());
				} catch (IOException e) {
					// should never happen
				}
			}
		}

		super.finish();

	}

	public String getCheckbox() {

		String evaluatedId = ObjectUtils.toString(evaluate(id));

		boolean checked = checkedIds.contains(evaluatedId);

		StringBuffer buffer = new StringBuffer();
		String idx = fieldName + "_" + this.getListIndex();
		buffer.append("<input type=\"checkbox\" id=\"" + idx + "\" name=\"" + fieldName + "\" value=\"");
		buffer.append(evaluatedId);
		buffer.append("\"");

		if (checked && storeCheck()) {
			checkedIds.remove(evaluatedId);
			buffer.append(" checked=\"checked\"");
		}

		if ("true".equals(disabled)) {
			buffer.append(" disabled=\"disabled\"");
		}

		if (onClick != null && !"".equals(onClick)) {
			buffer.append(" onclick=\"" + onClick + "\"");
		}

		buffer.append("/>");

		return buffer.toString();
	}

}
