package com.sinosoft.weixin;

import java.util.List;

public interface WeiXinMessageListSender {

	boolean send(List<WeiXinMessage> weiXinMessageList);

}
