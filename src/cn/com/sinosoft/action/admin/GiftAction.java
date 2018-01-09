package cn.com.sinosoft.action.admin;

import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import cn.com.sinosoft.entity.Gift;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.Stock;
import cn.com.sinosoft.service.GiftService;
import cn.com.sinosoft.service.MemberService;
import cn.com.sinosoft.service.StockService;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.sinosoft.framework.User;
import com.sinosoft.framework.security.EncryptUtil;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;

/**
 * 后台Action类 - 库存管理
 * ============================================================================
 * KEY:SINOSOFT483FBE0A69F1A3F1D081F3F2D838E89C
 * ============================================================================
 */

@ParentPackage("admin")
public class GiftAction extends BaseAdminAction {
	private static final long serialVersionUID = -6892797310128684890L;

	@Resource
	private GiftService giftService;

	@Resource
	private StockService stockService;

	@Resource
	private MemberService memberService;

	private List<Gift> giftList;

	private Gift gift;
	private Stock stock;
	/* zhangjinquan 11180 库存管理需求-增加密码、有效期、说明 2012-10-30 001 start */
	private String oldpd;
	private String newpd;
	private String newpd1;
	private String pdchflag;/* 密码修改标记 */
	private String errmessage;/* 保存出错信息 */
	private String errid;/* 保存出错节点id */
	private String needReturnBtn;
	/* zhangjinquan 11180 库存管理需求-增加密码、有效期、说明 2012-10-30 001  end   */

	// 添加
	public String add() {
		gift = new Gift();
		gift.setStatus("Y");
		return INPUT;
	}

	// 编辑
	public String edit() {
		gift = giftService.load(id);
		return INPUT;
	}

	// 列表
	public String list() {
		/* zhangjinquan 11180 判断请求的URL中是否有参数信息-没有参数信息的情况，标记不需要显示返回按钮 */
		this.setNeedReturnBtn((null == getRequest().getQueryString())?"noneed":"need");
		if (StringUtil.isEmpty(id)) {
			id = (String) getSession("Stock_ID");
		}
		if (StringUtil.isNotEmpty(id)) {
			setSession("Stock_ID", id);
			
			stock = stockService.load(id);
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Gift.class);
			detachedCriteria.add(Restrictions.eq("stock", stock));
			pager = giftService.findByPager(pager, detachedCriteria);

		} else {
			pager = giftService.findByPager(pager);
		}
		if (null != pager)
		{
			List list = pager.getList();
			for (int i=0; ((null!=list) && (i<list.size()));i++)
			{
				Gift gift = (Gift)list.get(i);
				if (null != gift)
				{
					/*
					//zhangjinquan 11180 王恩建2012-11-15要求隐藏密码列，不进行转换
					String password = gift.getPassword();
					if (StringUtil.isNotEmpty(password))
					{
						gift.setPassword(EncryptUtil.decrypt3DES(password, EncryptUtil.DEFAULT_KEY));
					}
					*/
					String memberId = gift.getMemberID();
					if (StringUtil.isNotEmpty(memberId))
					{
						Member member = memberService.load(memberId);
						memberId = member.getUsername();
						if (StringUtil.isEmpty(memberId))
						{
							memberId = member.getEmail();
							if (StringUtil.isEmpty(memberId))
							{
								memberId = member.getMobileNO();
							}
						}
						if (StringUtil.isNotEmpty(memberId))
						{
							gift.setMemberID(memberId);
						}
					}
				}
			}
		}
		return LIST;
	}

	// 保存
	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "gift.cardNo", message = "卡号不允许为空!") })
	@InputConfig(resultName = "error")
	public String save() {
		stock = gift.getStock();
		if (null == stock.getResidue()) {
			stock = stockService.load(stock.getId());
		}
		if ("Y".equals(gift.getStatus()))
			stock.setResidue(stock.getResidue() + 1);
		else
			stock.setBetake(stock.getBetake() + 1);

		stockService.update(stock);
		
		/* zhangjinquan 11180 库存管理需求-增加密码、有效期、说明 2012-10-30 002 start */
		gift.setPassword(EncryptUtil.encrypt3DES(gift.getPassword(), EncryptUtil.DEFAULT_KEY));
		gift.setExpireDate(DateUtil.setTimeOfDate(gift.getExpireDate(), 23, 59, 59));
		/* zhangjinquan 11180 库存管理需求-增加密码、有效期、说明 2012-10-30 002 end   */
		//gift.setMemberID(User.getUserName());
		giftService.save(gift);
		redirectionUrl = "gift!list.action";
		return SUCCESS;
	}

	// 更新
	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "gift.cardNo", message = "卡号不允许为空!") })
	@InputConfig(resultName = "error")
	public String update() {
		Gift gift1 = giftService.load(id);
		if (gift == null) {
			addActionError("查询数据失败!");
			return ERROR;
		}
		/* zhangjinquan 11180 库存管理需求-增加密码、有效期、说明 2012-10-30 003 start */
		if ("1".equals(this.getPdchflag()))
		{
			String syspd = gift1.getPassword();
			if (StringUtil.isNotEmpty(syspd))
			{
				if (StringUtil.isEmpty(oldpd) || !((oldpd).equals(EncryptUtil.decrypt3DES(syspd, EncryptUtil.DEFAULT_KEY))))
				{
					this.setErrmessage("旧密码与系统记录不一致！");
					this.setErrid("oldpd");
					return INPUT;
				}
			}
			else if (StringUtil.isNotEmpty(oldpd))
			{
				this.setErrmessage("旧密码与系统记录不一致！");
				this.setErrid("oldpd");
				return INPUT;
			}
			
			if (StringUtil.isEmpty(newpd))
			{
				this.setErrmessage("设置新密码不能为空！");
				this.setErrid("newpd");
				return INPUT;
			}
			if (StringUtil.isEmpty(newpd1))
			{
				this.setErrmessage("重复新密码不能为空！");
				this.setErrid("newpd1");
				return INPUT;
			}
			if (!(newpd1.equals(newpd)))
			{
				this.setErrmessage("两次输入的新密码不一致！");
				this.setErrid("newpd1");
				return INPUT;
			}
			gift1.setPassword(EncryptUtil.encrypt3DES(newpd1, EncryptUtil.DEFAULT_KEY));
		}
		gift1.setExpireDate(DateUtil.setTimeOfDate(gift.getExpireDate(), 23, 59, 59));
		gift1.setDescription(gift.getDescription());
		/* zhangjinquan 11180 库存管理需求-增加密码、说明、有效期 2012-10-30 003 end   */
		
		if (!gift1.getStatus().equals(gift.getStatus())) {
			stock = gift.getStock();
			if (null == stock.getResidue()) {
				stock = stockService.load(stock.getId());
			}
			if ("Y".equals(gift1.getStatus()) && "N".equals(gift.getStatus())) {
				stock.setResidue(stock.getResidue() - 1);
				stock.setBetake(stock.getBetake() + 1);

			} else if ("N".equals(gift1.getStatus()) && "Y".equals(gift.getStatus())) {
				stock.setResidue(stock.getResidue() + 1);
				stock.setBetake(stock.getBetake() - 1); 
			}
			stockService.update(stock);
			gift1.setStatus(gift.getStatus());
			gift1.setStock(stock);
		}
		gift1.setCardNo(gift.getCardNo());
		gift1.setStock(gift.getStock());
		//gift1.setMemberID(User.getUserName());
		giftService.update(gift1);
		redirectionUrl = "gift!list.action";
		return SUCCESS;
	}

	// 获取礼品分类树
	public List<Stock> getStockList() {
		return stockService.getAll();
	}

	// 删除
	public String delete() {
		gift = giftService.load(id);
		if (gift == null || "Y".equals(gift.getStatus())) {
			addActionError("此库存未使用，无法删除!");
			return ERROR;
		}
		giftService.delete(id);
		redirectionUrl = "gift!list.action";
		return SUCCESS;
	}

	public List<Gift> getGiftList() {
		return giftList;
	}

	public void setGiftList(List<Gift> giftList) {
		this.giftList = giftList;
	}

	public Gift getGift() {
		return gift;
	}

	public void setGift(Gift gift) {
		this.gift = gift;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}
	
	/* zhangjinquan 11180 库存管理需求-增加密码、有效期、说明 2012-10-30 004 start */
	public String getOldpd()
	{
		return oldpd;
	}
	public void setOldpd(String oldpd)
	{
		this.oldpd = oldpd;
	}
	
	public String getNewpd()
	{
		return newpd;
	}
	public void setNewpd(String newpd)
	{
		this.newpd = newpd;
	}
	
	public String getNewpd1()
	{
		return newpd1;
	}
	public void setNewpd1(String newpd1)
	{
		this.newpd1 = newpd1;
	}
	
	public String getPdchflag()
	{
		return pdchflag;
	}
	public void setPdchflag(String pdchflag)
	{
		this.pdchflag = pdchflag;
	}
	
	public String getErrmessage()
	{
		return errmessage;
	}
	public void setErrmessage(String errmessage)
	{
		this.errmessage = errmessage;
	}
	
	public String getErrid()
	{
		return errid;
	}
	public void setErrid(String errid)
	{
		this.errid = errid;
	}
	
	public String getNeedReturnBtn()
	{
		return needReturnBtn;
	}
	public void setNeedReturnBtn(String needReturnBtn)
	{
		this.needReturnBtn = needReturnBtn;
	}
	/* zhangjinquan 11180 库存管理需求-增加密码、有效期、说明 2012-10-30 004 end   */

}