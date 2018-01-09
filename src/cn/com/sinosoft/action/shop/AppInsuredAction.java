package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.entity.AppInsured;
import cn.com.sinosoft.entity.Dict;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.service.AppInsuredService;
import cn.com.sinosoft.util.JdbcTemplateData;
import com.sinosoft.inter.ActionUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@ParentPackage("member")
public class AppInsuredAction extends BaseShopAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5210500493567328093L;
	private String memberId;
	private int page = 1;
	private int lastpage;
	private int count;
	private int pagesize = 10;
	private List<AppInsured> listq = new ArrayList<AppInsured>();
	private List<Dict> listid = new ArrayList<Dict>();
	private AppInsured appInsured;
	private String appInsuredId;
	private List<Map> listRelation;
	public List<Map> getListRelation() {
		return listRelation;
	}

	public void setListRelation(List<Map> listRelation) {
		this.listRelation = listRelation;
	}

	@Resource
	private AppInsuredService appInsuredService;

	public AppInsured getAppInsured() {
		return appInsured;
	}

	public void setAppInsured(AppInsured appInsured) {
		this.appInsured = appInsured;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public int getCount() {
		return count;
	}

	public List<AppInsured> getListq() {
		return listq;
	}

	public void setListq(List<AppInsured> listq) {
		this.listq = listq;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberId() {
		return memberId;
	}

	public Member getLoginMember() {
		String id = (String) getSession(Member.LOGIN_MEMBER_ID_SESSION_NAME);
		if (StringUtils.isEmpty(id)) {
			return null;
		}
		Member loginMember = memberService.load(id);
		return loginMember;
	}

	public List<Dict> getListid() {
		return listid;
	}

	public void setListid(List<Dict> listid) {
		this.listid = listid;
	}

	public String add() {
		JdbcTemplateData jtd = new JdbcTemplateData();
		String sql = "select codevalue,codename from zdcode where codetype=? and  parentcode=? order by codevalue asc";
		String[] sqltemp = {"member.IDType","IDType"};
		String sql2="select codevalue ,codename from  zdcode where codetype=? and parentcode=? ";
		String[] sql2temp = {"appinsured.relation","relation"};  
		try {
			List<Map> list = jtd.obtainData(sql,sqltemp); 
			listRelation=jtd.obtainData(sql2,sql2temp); 
			Iterator<Map> it = list.iterator();
			while (it.hasNext()) {
				Map map = it.next();
				Dict idType = new Dict();
				idType.setDictName((String)map.get("CodeName"));
				idType.setDictSerial((String)map.get("CodeValue"));
				listid.add(idType);

			}
		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
		return "input";
	}

	public String delete() {
		memberId = getLoginMember().getId();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String createDate = sdf.format(date);
		Date cmd = null;
		try {
			cmd = sdf.parse(createDate);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		appInsured=appInsuredService.get(appInsuredId);
		appInsured.setMemberID(memberId);
		appInsured.setModifyDate(cmd);
		appInsured.setGxState("2");
		appInsuredService.update(appInsured);
		
        Map<String,Object> map = new HashMap<String,Object>(); 
        try{ActionUtil.dealAction("wj00033", map, getRequest());}
        catch(Exception e){
			logger.error(e.getMessage(), e);
        }finally{
        	return scan();}
	}

	public String addToSave() {
		memberId = getLoginMember().getId();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String createDate = sdf.format(date);
		Date cmd = null;
		try {
			cmd = sdf.parse(createDate);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		appInsured.setCreateDate(cmd);
		appInsured.setModifyDate(cmd);
		appInsured.setMemberID(memberId);
		appInsured.setGxState("0");
		appInsuredService.save(appInsured);
        Map<String,Object> map = new HashMap<String,Object>(); 
        try{ActionUtil.dealAction("wj00034", map, getRequest());}
        catch(Exception e){
			logger.error(e.getMessage(), e);
        }finally{
        	return scan();}
		
	}

	public String update() {
		appInsured=appInsuredService.get(appInsuredId);
		JdbcTemplateData jtd = new JdbcTemplateData();
		String sql = "select codevalue,codename from zdcode where codetype=? and  parentcode=? order by codevalue asc";
		String[] sqltemp ={"member.IDType","IDType"};
		String sql2="select codevalue ,codename from  zdcode where codetype=? and parentcode=? ";
		String[] sql2temp ={"appinsured.relation","relation"};
		try {
			List<Map> list = jtd.obtainData(sql,sqltemp);
			listRelation=jtd.obtainData(sql2,sql2temp);
			Iterator<Map> it = list.iterator();
			while (it.hasNext()) {
				Map map = it.next();
				Dict idType = new Dict();
				idType.setDictName((String)map.get("CodeName"));
				idType.setDictSerial((String)map.get("CodeValue"));
				listid.add(idType);

			}
		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
		return "update";
	}

	public String updateToSave() {
		memberId = getLoginMember().getId();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String createDate = sdf.format(date);
		Date cmd = null;
		try {
			cmd = sdf.parse(createDate);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		appInsured.setMemberID(memberId);
		appInsured.setModifyDate(cmd);
		appInsured.setGxState("1");
		appInsuredService.update(appInsured);
        Map<String,Object> map = new HashMap<String,Object>(); 
        try{ActionUtil.dealAction("wj00035", map, getRequest());}
        catch(Exception e){
			logger.error(e.getMessage(), e);
        }finally{
        	return scan();}
}
		


	public String scan() {
		memberId = getLoginMember().getId();
		JdbcTemplateData jtd = new JdbcTemplateData();
		String sqlPart = " order by createDate desc  limit " + (page - 1)
				* pagesize + "," + pagesize;
		String sql = "select id,applicantArea ,applicantBirthday , applicantIdentityId ,"
				+ " applicantIdentityType ,applicantMail ,applicantName,applicantSex,applicantTel,occupation1,"
				+ " recognizeeArea,recognizeeBirthday,recognizeeIdentityId,recognizeeIdentityType,recognizeeMail,"
				+ " recognizeeName,recognizeeSex,recognizeeTel,recognizeeZipCode,createDate from sdappInsured  "
				+ " where memberId=? and gxstate<>'2'";
		sql = sql + sqlPart;
		String sqlCount = "select id,applicantArea ,applicantBirthday , applicantIdentityId ,"
				+ " applicantIdentityType ,applicantMail ,applicantName,applicantSex,applicantTel,occupation1,"
				+ " recognizeeArea,recognizeeBirthday,recognizeeIdentityId,recognizeeIdentityType,recognizeeMail,"
				+ " recognizeeName,recognizeeSex,recognizeeTel,recognizeeZipCode ,createDate from sdappInsured  "
				+ " where memberId=? and gxstate<>'2'";
      
		String[] temp ={memberId};
		try {
			List<Map> listcount = jtd.obtainData(sqlCount,temp);
			List<Map> list = jtd.obtainData(sql,temp);
			
			count = listcount.size();
			this.setLastpage((count + AppInsured.LIST_COUNT - 1)
					/ (AppInsured.LIST_COUNT));

			Iterator<Map> it = list.iterator();
			while (it.hasNext()) {
				Map map = it.next();
				AppInsured app = new AppInsured();
				app.setApplicantName((String) map.get("applicantName"));
				app.setRecognizeeName((String) map.get("recognizeeName"));
				app.setCreateDate((Date) map.get("createDate"));
				app.setId((String) map.get("id"));
				listq.add(app);

			}
		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
	        Map<String,Object> map = new HashMap<String,Object>(); 
	        try{ActionUtil.dealAction("wj00017", map, getRequest());}
	        catch(Exception e){
				logger.error(e.getMessage(), e);
	        }finally{
		return "list";}
	}

	public void setLastpage(int lastpage) {
		this.lastpage = lastpage;
	}

	public int getLastpage() {
		return lastpage;
	}

	public void setAppInsuredId(String appInsuredId) {
		this.appInsuredId = appInsuredId;
	}

	public String getAppInsuredId() {
		return appInsuredId;
	}
}
