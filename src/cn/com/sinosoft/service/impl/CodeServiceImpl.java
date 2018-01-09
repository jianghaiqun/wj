package cn.com.sinosoft.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.CodeDao;
import cn.com.sinosoft.entity.Code;
import cn.com.sinosoft.service.CodeService;

@Service
public class CodeServiceImpl extends BaseServiceImpl<Code, String> implements CodeService {
	@Resource
	public void setBaseDao(CodeDao codeDao) {
		super.setBaseDao(codeDao);
	}
} 