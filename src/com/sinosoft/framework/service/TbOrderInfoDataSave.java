package com.sinosoft.framework.service;

import java.util.LinkedHashMap;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.jdt.ParseXMLToObject;

@Transactional(propagation = Propagation.REQUIRES_NEW)
public class TbOrderInfoDataSave {
	public boolean saveAll(ParseXMLToObject parseXMLToObject ,LinkedHashMap<Object, String> lmap) {
		return parseXMLToObject.saveAll(lmap);
	}
}
