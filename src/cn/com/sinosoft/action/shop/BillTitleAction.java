package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.SDBillTitle;
import cn.com.sinosoft.service.SDBillTitleService;
import com.opensymphony.oscache.util.StringUtil;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 前台Action类 -发票台头
 * ============================================================================
 * 
 * ============================================================================
 */
public class BillTitleAction extends BaseShopAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1806998422106357845L;

	@Resource
	private SDBillTitleService mSDBillTitleService;
	
	private SDBillTitle sDBillTitle;
	private int count = 0;

	private String titleId;
	private String titleName;
	private String isDefault;
	private String memberId;
	private String billTitleCount;

	public String getBillTitleCount() {
		return billTitleCount;
	}

	public void setBillTitleCount(String billTitleCount) {
		this.billTitleCount = billTitleCount;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
	/**
	 * 删除台头信息
	 */
	public String deleteBillTitle() {
		Map<String, Object> tData = new HashMap<String, Object>();
		if (!checkLogin()) {
			tData.put("tFlag", "Err");
			tData.put("Msg", "请登陆后，再进行此操作！");
			JSONObject jsonObject = JSONObject.fromObject(tData);
			return ajax(jsonObject.toString(), "text/html");
		}

		tData.put("tFlag", "Suc");
		try {
			if (!StringUtil.isEmpty(titleId)) {
				sDBillTitle = mSDBillTitleService.get(titleId);
			}
			
			if (sDBillTitle != null){
				mSDBillTitleService.delete(sDBillTitle);
			}
		} catch (Exception e) {
			tData.put("tFlag", "Err");
			tData.put("Msg", "台头信息删除失败！");
		}
		JSONObject jsonObject = JSONObject.fromObject(tData);
		return ajax(jsonObject.toString(), "text/html");
	}
	
	/**
	 * 保存台头信息
	 * 
	 * @return
	 */
	public String saveOrUpdate() {
		Map<String, Object> tData = new HashMap<String, Object>();
		if (!checkLogin()) {
			tData.put("tFlag", "Err");
			tData.put("Msg", "请登陆后，再进行此操作！");
			JSONObject jsonObject = JSONObject.fromObject(tData);
			return ajax(jsonObject.toString(), "text/html");
		}

		tData.put("tFlag", "Suc");
		try {
			if (!StringUtil.isEmpty(titleId)) {
				sDBillTitle = mSDBillTitleService.get(titleId);
			}
			
			if (sDBillTitle == null){
				sDBillTitle = new SDBillTitle();
				
			}
			Member member = getLoginMember();
			sDBillTitle.setMemberId(member.getId());
			sDBillTitle.setName(titleName);
			if (StringUtil.isEmpty(titleId)) {
				sDBillTitle.setModifyUser(member.getUsername());
				sDBillTitle.setCreateUser(member.getUsername());
				sDBillTitle.setIsDefault("0");
				mSDBillTitleService.save(sDBillTitle);
			}else{
				sDBillTitle.setModifyUser(member.getUsername());
				mSDBillTitleService.update(sDBillTitle);
			}

		} catch (Exception e) {
			tData.put("tFlag", "Err");
			tData.put("Msg", "台头信息保存失败！");
		}
		JSONObject jsonObject = JSONObject.fromObject(tData);
		return ajax(jsonObject.toString(), "text/html");
	}

	/**
	 * 设置默认台头
	 * 
	 * @return
	 */
	public String updateDefault() {
		Map<String, Object> tData = new HashMap<String, Object>();
		if (!checkLogin()) {
			tData.put("tFlag", "Err");
			tData.put("Msg", "请登陆后，再进行此操作！");
			JSONObject jsonObject = JSONObject.fromObject(tData);
			return ajax(jsonObject.toString(), "text/html");
		}

		tData.put("tFlag", "Suc");
		try {
			
			mSDBillTitleService.updateDefault(titleId);

		} catch (Exception e) {
			tData.put("tFlag", "Err");
			tData.put("Msg", "台头信息保存失败！");
			logger.error(e.getMessage(), e);
		}
		JSONObject jsonObject = JSONObject.fromObject(tData);
		return ajax(jsonObject.toString(), "text/html");
	}
	
	/**
	 * @return
	 */
	public boolean checkLogin() {
		Member member = getLoginMember();

		if (member == null || "".equals(member)) {
			return false;
		}
		return true;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getTitleId() {
		return titleId;
	}

	public void setTitleId(String titleId) {
		this.titleId = titleId;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public SDBillTitle getsDBillTitle() {
		return sDBillTitle;
	}

	public void setsDBillTitle(SDBillTitle sDBillTitle) {
		this.sDBillTitle = sDBillTitle;
	}
}