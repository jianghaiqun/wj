package com.wxpay.wxap.util;

import com.sinosoft.framework.Config;

import java.util.HashMap;
import java.util.Map;


/* 微信配置文件
 * TODO : 此文件不可以随意修改！！！如果要求修改需要与郭斌联系！！！
 * @author lenovo
 */
public class WxPayConfig {
	
	private WxPayConfig() {
	}
	
	public static String PARTNER		= "1221051501" ;	//财付通商户号
	public static String PARTNER_KEY	= "";	//财付通密钥
	public static String APP_ID		= "wx8604d814682e9f12";	//appid
	public static String APP_SECRET	= "0671b7847081c0e45757806735b17e74";	//appsecret
	public static String APP_KEY		= "0671b7847081c0e45757806735b17e74";	//paysignkey 128位字符串(非appkey)
	public static String NOTIFY_URL	= Config.getValue("PayServerContext")+"/shop/pay!wxNotify.action"; //支付完成后的回调处理页面,*替换成notify_url.asp所在路径
	//public static String NOTIFY_URL	= Config.getValue("PayServerContext")+"/wj/shop/pay!wxNotify.action";  //支付完成后的回调处理页面,*替换成notify_url.asp所在路径
    //Config.getValue("PayServerContext");
	public static String LOGING_DIR	= "";  //日志保存路径
	public static String TO_POSTURL = "https://api.mch.weixin.qq.com/pay/unifiedorder"; //微信统一订单接口地址
	public static String TO_QUERYURL = "https://api.mch.weixin.qq.com/pay/orderquery";//微信查询订单接口地址
	public static String WXPAYSTATUSSUCCESS = "SUCCESS";
	
	public static Map<String, Object> ERRORMAP = new HashMap<String, Object>() {
		/**
		 * 微信支付错误码
		 */
		private static final long serialVersionUID = -3480507229861371916L;

		{
			put("NOAUTH", "商户无此接口权限");//请商户前往申请此接口权限
			put("NOTENOUGH", "余额不足");//用户帐号余额不足，请用户充值或更换支付卡后再支付
			put("ORDERPAID", "商户订单已支付");//商户订单已支付，无需更多操作
			put("ORDERCLOSED", "订单已关闭");//当前订单已关闭，请重新下单
			put("SYSTEMERROR", "系统错误");//系统异常，请用相同参数重新调用
			put("APPID_NOT_EXIST", "APPID不存在");//请检查APPID是否正确
			put("MCHID_NOT_EXIST", "MCHID不存在");//请检查MCHID是否正确
			put("APPID_MCHID_NOT_MATCH", "appid和mch_id不匹配");//请确认appid和mch_id是否匹配
			put("LACK_PARAMS", "缺少参数");//请检查参数是否齐全
			put("OUT_TRADE_NO_USED", "商户订单号重复");//请核实商户订单号是否重复提交
			put("SIGNERROR", "签名错误");//请检查签名参数和方法是否都符合签名算法要求
			put("XML_FORMAT_ERROR", "XML格式错误");//请检查XML参数格式是否正确
			put("REQUIRE_POST_METHOD", "请使用post方法");//请检查请求参数是否通过post方法提交
			put("POST_DATA_EMPTY", "post数据为空");//请检查post数据是否为空
			put("NOT_UTF8", "编码格式错误");//请使用NOT_UTF8编码格式
			
			//微信支付结果
			put("REFUND", "转入退款");
			put("NOTPAY", "未支付");
			put("CLOSED", "已关闭");
			put("REVOKED", "已撤销");
			put("USERPAYING", "用户支付中");
			put("PAYERROR", "支付失败(其他原因，如银行返回失败)");
		}
	};
}
