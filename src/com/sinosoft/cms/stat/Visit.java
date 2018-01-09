package com.sinosoft.cms.stat;

public class Visit {
	/**
	 * 被访问的URL
	 */
	public String URL;

	/**
	 * 访问来源URL
	 */
	public String Referer;

	/**
	 * 客户端地址
	 */
	public String IP;

	/**
	 * 是否是独立用户;
	 */
	public boolean UVFlag;

	/**
	 * 是否是回头客;
	 */
	public boolean RVFlag;

	/**
	 * 唯一访客标志;
	 */
	public String UniqueID;

	/**
	 * 是否是新IP;
	 */
	public boolean IPFlag;

	/**
	 * 屏幕大小
	 */
	public String Screen;

	/**
	 * 显示器色深
	 */
	public String ColorDepth;

	/**
	 * 语言
	 */
	public String Language;

	/**
	 * 浏览器信息
	 */
	public String UserAgent;

	/**
	 * 操作系统
	 */
	public String OS;

	/**
	 * 是否支持Java
	 */
	public boolean JavaEnabled;

	/**
	 * 是否支持Flash
	 */
	public boolean FlashEnabled;

	/**
	 * Flash版本
	 */
	public String FlashVersion;

	/**
	 * 是否支持Cookie
	 */
	public boolean CookieEnabled;

	/**
	 * 访问类别
	 */
	public String Type;

	/**
	 * 分类内部编码
	 */
	public String CatalogInnerCode;

	/**
	 * 叶子节点ID(文章ID，商品ID，图片ID等)
	 */
	public long LeafID;

	/**
	 * 站点ID
	 */
	public long SiteID;

	/**
	 * 访问时间
	 */
	public long VisitTime;

	/**
	 * 主机名称
	 */
	public String Host;

	/**
	 * 事件类型,当页面关闭调用统计后台时值为Unload
	 */
	public String Event;

	/**
	 * 浏览器类型
	 */
	public String Browser;

	/**
	 * 地区
	 */
	public String District;

	/**
	 * 统计页面停留时间
	 */
	public int StickTime;

	/**
	 * 访问频度
	 */
	public int Frequency;
	
	/**
	 * SessionID
	 */
	public String SessionID;
	
	/**
	 * 会员ID
	 */
	public String MemberID;
	
	/**
	 * 用户具体操作
	 */
	public String Operation;
	
	/**
	 * 明细记录页面停留时间
	 */
	public String StayTime;
	
	/**
	 * 访问开始时间
	 */
	public Long StartTime;
	
	/**
	 * 访问结束时间
	 */
	public Long LeaveTime;
	
	/**
	 * 当前页面跳出或者关闭标志
	 */
	public String IsJumpOrClose;
	
	/**
	 * 渠道编码
	 */
	public String channel;
	
	/**
	 * 订单号
	 */
	public String OrderSn;
	
	/**
	 * 渠道id
	 */
	public String channelId;
	/**
	 * 渠道
	 */
	public String channelWay;
	
}
