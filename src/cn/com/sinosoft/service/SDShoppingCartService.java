package cn.com.sinosoft.service;

import java.util.List;
import java.util.Map;

import cn.com.sinosoft.entity.SDOrder;
import cn.com.sinosoft.entity.SDShoppingCart;

public interface SDShoppingCartService extends BaseService<SDShoppingCart,String>{

	/**
	 * 
	 * 创建一个新购物车对象
	 */
	public SDShoppingCart createCartByOrder(SDOrder sdorder);

	/**
	 * 核对购物车中同一款产品同一被保人是否有两个订单记录
	 */
	public boolean checkInsuredFromShopCart(SDOrder sdorder);
	/**
	 * 核对购物车中订单是否是同一渠道
	 */
	public boolean checkChannelFromShopCart(SDOrder sdorder);
	
	/**
	 * 校验产品是否可以加入购物车
	 */
	public boolean checkProduct(String orderSn);

	/**
	 * 
	 * 根据会员ID查出会员购物车中的商品数量
	 */
	public int getShopCartNo(String memId);

	/**
	 * 
	 * 根据会员ID查询购物车中的订单
	 * @return
	 */
	public List<SDShoppingCart> getShowShopCartList(String memberId);
	
	/**
	 * 根据订单判断订单保障期限是否过期
	 */
	public boolean checkPeriodEffectiveness(SDOrder orderSn);

	/**
	 * 
	 * 根据订单号查出需要删除的数据
	 */
	public List<SDShoppingCart> getDeleteInfoByOrderSn(String orderSn);

	/**
	 * 
	 * 根据订单号进行核保
	 */
	public Map<String, String> checkOrderByOrderSn(String orderSn,String KID);

	/**
	 * 
	 * 取得购物车对象集合的总价
	 */
	public double getShopTotleAmount(List<SDShoppingCart> cartList);

	/**
	 * 
	 * @param orderSn
	 * @return根据订单号得出一个shoppingcart对象
	 */
	public SDShoppingCart getShopCartByOrderSn(String orderSn);

	/**
	 * 
	 * 支付完成后根据会员id和订单号删除购物车表中数据
	 */
	public void deleteShopCartInfo(List<SDOrder> sdorderList);
	
	
}
