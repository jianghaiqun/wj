package com.sinosoft.cmcore.tag;

import com.sinosoft.framework.utility.Mapx;

/**
 * 修饰符处理器，处理${Holder|Format=yyyy-MM-dd}中竖线后面的部分
 * 
 */
public interface ModifierHandler {
	public Object deal(Object value, Mapx modifiers);
}
