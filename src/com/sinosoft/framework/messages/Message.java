package com.sinosoft.framework.messages;

import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Mapx;

public class Message {
	private String ID;

	private String name;

	private Transaction transaction;

	private Mapx content;

	public Mapx getContent() {
		return content;
	}

	public void setContent(Mapx content) {
		this.content = content;
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	public boolean isTransactional() {
		return transaction != null;
	}

	public String getID() {
		return ID;
	}

	public void setID(String id) {
		ID = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
