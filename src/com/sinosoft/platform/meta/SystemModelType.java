package com.sinosoft.platform.meta;

import java.util.ArrayList;
import java.util.List;

public class SystemModelType implements IMetaModelType {
	public static final String ID = "System";

	public String getID() {
		return "System";
	}

	public String getName() {
		return "系统元数据";
	}

	public boolean isSystemModel() {
		return true;
	}

	public List<IMetaModelTemplateType> getTemplateTypes() {
		ArrayList list = new ArrayList();
		list.add(new SystemModelTemplateType());
		return list;
	}

	public String getDefautlTemplateHtml() {
		return "<model:fieldgroup><div class=\"z-legend\"><b>@{FieldGroup.Name}</b></div><table id=\"table@{FieldGroup.Code}\" width=\"500\" border=\"1\" cellpadding=\"3\" cellspacing=\"0\" bordercolor=\"#eeeeee\" class=\"formTable\"><model:field><tr><td width=\"160\">@{Field.Name}：</td><td>@{Field.ControlHtml}</td></tr></model:field></table></model:fieldgroup>";
	}
}