package cn.com.sinosoft.dao.impl;

import org.springframework.stereotype.Repository;

import cn.com.sinosoft.dao.CodeDao;
import cn.com.sinosoft.entity.Code;

@Repository
public class CodeDaoImpl extends BaseDaoImpl<Code, String> implements CodeDao {
}