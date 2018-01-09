package com.sinosoft.platform.service;

import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.extend.AbstractExtendService;
import com.sinosoft.platform.service.FixedCodeType.FixedCodeItem;
import com.sinosoft.schema.ZDCodeSchema;
import com.sinosoft.schema.ZDCodeSet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CodeService extends AbstractExtendService<FixedCodeType> {
	public static CodeService getInstance() {
		return (CodeService) findInstance(CodeService.class);
	}

	public static void init() {
		Transaction trans = new Transaction();
		ArrayList dbSet = new ArrayList();
		ZDCodeSet zdSet = new ZDCodeSchema().query();

		for (int i = 0; zdSet != null && i < zdSet.size(); i++) {
			ZDCodeSchema code = zdSet.get(i);
			dbSet.add("@CodeType=" + code.getCodeType() + "@ParentCode=" + code.getParentCode() + "@CodeValue=" + code.getCodeValue());
		}
		for (FixedCodeType fct : getInstance().getAll()) {
			ZDCodeSchema code = new ZDCodeSchema();
			code.setCodeType(fct.getCodeType());
			code.setParentCode("System");
			code.setCodeValue("System");

			if (!dbSet.contains("@CodeType=" + fct.getCodeType() + "@ParentCode=System@CodeValue=System")) {
				code.setCodeName(fct.getCodeName());
				code.setCodeOrder(System.currentTimeMillis());
				code.setAddTime(new Date());
				code.setAddUser("System");
				trans.add(code, 1);
			}

			List<FixedCodeItem> items = fct.getFixedItems();
			for (FixedCodeType.FixedCodeItem item : items) {
				if (dbSet.contains("@CodeType=" + code.getCodeType() + "@ParentCode=" + code.getCodeType() + "@CodeValue=" + item.getValue()))
					continue;
				ZDCodeSchema codeChild = new ZDCodeSchema();
				codeChild.setCodeType(code.getCodeType());
				codeChild.setParentCode(code.getCodeType());
				codeChild.setCodeValue(item.getValue());
				codeChild.setCodeName(item.getName());
				codeChild.setCodeOrder(System.currentTimeMillis());
				codeChild.setAddTime(new Date());
				codeChild.setAddUser("System");
				trans.add(codeChild, 6);
			}
		}

		if (!trans.commit())
			logger.error("Code 初始化失败！");
	}
}