/**
 * Copyright (c) 2006 sinosoft Co. Ltd.
 * All right reserved.
 */
package cn.com.sinosoft.common;

import java.util.Map;



/**
 * <p>
 * ClassName: alesCalInterface
 * </p>
 * <p>
 * Company: Sinosoft Co. Ltd.
 * </p>
 * @author xijiahui
 * @version 1.0
 */
public interface SalesCalInterface
{
	/**
	 * submitData
	 * ͨ�ýӿڣ����մ�����ݣ�������ݽ��д���
	 * @param SalesTransferData data
	 */
    public boolean submitData(Map data);

	/**
	 * getResult
	 * ��ݷ���
	 */
    public String getResult();
}
