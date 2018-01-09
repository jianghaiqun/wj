package com.sinosoft.platform.meta;

import java.util.List;

import com.sinosoft.framework.extend.IExtendItem;

public abstract interface IMetaModelType extends IExtendItem {
	public abstract String getID();

	public abstract String getName();

	public abstract boolean isSystemModel();

	public abstract List<IMetaModelTemplateType> getTemplateTypes();

	public abstract String getDefautlTemplateHtml();
}