package com.sinosoft.misc;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.orm.DBImport;

public class DBConvert {

	public static void main(String[] args) {
		// new DBExport().exportDB(Config.getContextRealPath() +
		// "WEB-INF/data/installer/Install.dat");
		new DBImport().importDB(Config.getContextRealPath() + "WEB-INF/data/installer/Install.dat");
	}

}
