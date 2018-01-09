package com.sinosoft.cms.api;

import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.orm.Schema;

public interface APIInterface {
	public long insert();
	
	public long insert(Transaction trans);
	
	public boolean update();
	
	public boolean delete();
	
	public boolean setSchema(Schema schema);

}
