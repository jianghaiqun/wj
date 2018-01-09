package cn.com.sinosoft.service;

import java.util.List;

import cn.com.sinosoft.entity.Notify;

/**
 * 发送短信
 * 
 * @author guobin
 * 
 */
public interface SendNoteService extends BaseService<Object, String> {

	/**
	 * 发送短信
	 * 
	 * @param notify
	 * @return 发送状态
	 */
	public boolean sendNote(Notify notify);

	/**
	 * 批量发送短信
	 * 
	 * @param notify
	 * @return
	 */
	public boolean sendListNote(List<Notify> notify);
}
