package cn.com.sinosoft.service;

import cn.com.sinosoft.entity.RefundBase;

public interface RefundBaseService extends BaseService<RefundBase, String> {
public RefundBase getRefundBaseByReturnType(String returnType);
}
