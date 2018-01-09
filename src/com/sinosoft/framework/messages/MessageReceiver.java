package com.sinosoft.framework.messages;

import com.sinosoft.framework.utility.Mapx;

public abstract class MessageReceiver {
	public String[] getMessageTypeNames() {
		return MessageBus.getMessageNames(this);
	}

	public abstract Mapx receive(Message message);
}