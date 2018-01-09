package cn.com.sinosoft.action.admin;

import cn.com.sinosoft.entity.Occupation;
import cn.com.sinosoft.service.OccupationService;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ParentPackage("admin")
public class OccupationAction extends BaseAdminAction {

	private static final long serialVersionUID = -995551094891797465L;
	private Occupation occupation ;
	private Occupation parent ;
	private String parentId;
	private List<Occupation> occupationList = new ArrayList<Occupation>();
	@Resource
	private OccupationService occupationService;

	// 是否已存在ajax验证名称
	public String checkName() {
		String oldName = getParameter("oldValue");
		String parentId = getParameter("parentId");
		String newName = occupation.getName();
		if (occupationService.isNameUnique(parentId, oldName, newName)) {
			return ajaxText("true");
		} else {
			return ajaxText("false");
		}
	}
	// 是否已存在ajax验证编码
	public String checkCode() {
		String oldCode = getParameter("oldValue");
		String parentId = getParameter("parentId");
		String newCode = occupation.getCode();
		if (occupationService.isCodeUnique(parentId, oldCode, newCode)) {
			return ajaxText("true");
		} else {
			return ajaxText("false");
		}
	}

	// 添加
	public String add() {
		if (StringUtils.isNotEmpty(parentId)) {
			parent = occupationService.load(parentId);
		}
		return INPUT;
	}

	// 编辑
	public String edit() {
		occupation = occupationService.load(id);
		parent = occupation.getParent();
		return INPUT;
	}

	/**
	 * 列表
	 */
	public String list() {
		if (StringUtils.isNotEmpty(parentId)) {
			parent = occupationService.load(parentId);
			occupationList = new ArrayList<Occupation>(parent.getChildren());
		} else {
			occupationList = occupationService.getRootOPTList();
		}
		return LIST;
	}

	// 删除
	public String delete() {
		occupationService.delete(id);
		return ajaxJsonSuccessMessage("删除成功！");
	}

	// 保存
	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "occupation.name", message = "职业名称不允许为空!") })
	@InputConfig(resultName = "error")
	public String save() {
		String newName = occupation.getName();
		if (!occupationService.isNameUnique(parentId, null, newName)) {
			addActionError("职业名称已存在!");
			return ERROR;
		}
		if (StringUtils.isNotEmpty(parentId)) {
			occupation.setParent(occupationService.load(parentId));
		} else {
			occupation.setParent(null);
		}
		occupationService.save(occupation);
		redirectionUrl = "occupation!list.action?parentId=" + parentId;
		return SUCCESS;
	}

	// 更新
	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "occupation.name", message = "职业名称不允许为空!") })
	public String update() {
		Occupation persistent = occupationService.load(id);
		Occupation parent = persistent.getParent();
		if (parent != null) {
			parentId = parent.getId();
		}
		if (!occupationService.isNameUnique(parentId, persistent.getName(),
				occupation.getName())) {
			addActionError("地区名称已存在!");
			return ERROR;
		}
		persistent.setName(occupation.getName());
		occupationService.update(persistent);
		redirectionUrl = "occupation!list.action?parentId=" + parentId;
		return SUCCESS;
	}

	// 根据地区Path值获取下级地区JSON数据
	public String ajaxChildrenArea() {
		String path = getParameter("path");
		if (StringUtils.contains(path, Occupation.PATH_SEPARATOR)) {
			id = StringUtils.substringAfterLast(path, Occupation.PATH_SEPARATOR);
		} else {
			id = path;
		}
		List<Occupation> childrenOPTList = new ArrayList<Occupation>();
		if (StringUtils.isEmpty(id)) {
			childrenOPTList = occupationService.getRootOPTList();
		} else {
			childrenOPTList = new ArrayList<Occupation>(occupationService.load(
					id).getChildren());
		}
		List<Map<String, String>> optionList = new ArrayList<Map<String, String>>();
		for (Occupation opt : childrenOPTList) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("title", opt.getName());
			map.put("value", opt.getPath());
			optionList.add(map);
		}
		JSONArray jsonArray = JSONArray.fromObject(optionList);
		return ajaxJson(jsonArray.toString());
	}

	public Occupation getOccupation() {
		return occupation;
	}

	public void setOccupation(Occupation occupation) {
		this.occupation = occupation;
	}

	public Occupation getParent() {
		return parent;
	}

	public void setParent(Occupation parent) {
		this.parent = parent;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<Occupation> getOccupationList() {
		return occupationList;
	}

	public void setOccupationList(List<Occupation> occupationList) {
		this.occupationList = occupationList;
	}

}
