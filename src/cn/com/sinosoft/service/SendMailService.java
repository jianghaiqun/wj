package cn.com.sinosoft.service;

import java.util.List;

import cn.com.sinosoft.entity.Notify;

/**
 * 发送邮件
 * 
 * @author guobin
 * 
 */
public interface SendMailService extends BaseService<Object, String> {
	/**
	 * 发送邮件
	 * 
	 * @param notify
	 * @return 发送状态
	 */
	public boolean sendMail(Notify notify);

	/**
	 * 批量发送邮件
	 * 
	 * @param notify
	 * @return
	 */
	public boolean sendListMail(List<Notify> notify);
}
