/**
 * 核保返回处理接口，目前只有一个dealData接口方法，只是为了实现映射调用
 * @author zhangjinquan 11180
 * @creaeDate 2012-11-14
 */
package cn.com.sinosoft.action.shop.uw;

import cn.com.sinosoft.action.shop.OrderAction;

/**
 * @author zhangjinquan 11180
 *
 */
public interface UWCheckInterface
{
	public String dealData(OrderAction orderAction);
}
