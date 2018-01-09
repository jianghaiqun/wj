/**
 * 
 */
package com.sinosoft.framework.controls;

import com.sinosoft.framework.Ajax;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.utility.Mapx;

/**
 * 每个项目需要有一个类实现CodeSource,并将类名配置于framework.xml
 * 
 */
public abstract class CodeSource extends Ajax {
	public abstract DataTable getCodeData(String codeType,
			Mapx params);
}
