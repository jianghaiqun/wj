package com.sinosoft.framework.messages;

import java.util.ArrayList;

import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Mapx;

public class MessageSender {
	private Mapx map = new Mapx();

	private ArrayList list = new ArrayList();

	private Transaction tran;

	private static long id = System.currentTimeMillis();

	private String messageName;

	private Message message;

	public void addContentVar(String varName, Object value) {
		map.put(varName, value);
	}

	public void setTransaction(Transaction tran) {
		this.tran = tran;
	}

	public void send() {
		Message msg = new Message();
		msg.setContent(map);
		msg.setTransaction(tran);
		msg.setName(getMessageName());
		msg.setID(getMessageName() + id++);
		message = msg;
		MessageBus.send(this);
	}

	public Message getMessage() {
		return message;
	}

	public void receiveFeedback(Mapx fmap) {
		list.add(fmap);
	}

	public Mapx[] getFeedback() {
		Mapx[] arr = new Mapx[list.size()];
		for (int i = 0; i < list.size(); i++) {
			arr[i] = (Mapx) list.get(i);
		}
		return arr;
	}

	public String getMessageName() {
		return messageName;
	}

	public void setMessageName(String messageName) {
		this.messageName = messageName;
	}
}
