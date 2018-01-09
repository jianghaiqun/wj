package cn.com.sinosoft.action.shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import cn.com.sinosoft.entity.Area;
import cn.com.sinosoft.service.AreaService;

/**
 * 后台Action类 - 地区
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTAFBAC38E9CCED8343FEA91C4E57AB4B7
 * ============================================================================
 */

@ParentPackage("shop")
public class AreaAction extends BaseShopAction {

	private static final long serialVersionUID = 1321099291073671591L;

	@Resource
	private AreaService areaService;

	// 根据地区Path值获取下级地区JSON数据
	public String ajaxChildrenArea() {
		String path = getParameter("path");
		if (StringUtils.contains(path,  Area.PATH_SEPARATOR)) {
			id = StringUtils.substringAfterLast(path, Area.PATH_SEPARATOR);//取得最后一个指定字符串之后的字符串
		} else {
			id = path;
		}
		List<Area> childrenAreaList = new ArrayList<Area>();
		if (StringUtils.isEmpty(id)) {
			childrenAreaList = areaService.getRootAreaList();
		} else {
			childrenAreaList = new ArrayList<Area>(areaService.load(id).getChildren());
		}
		List<Map<String, String>> optionList = new ArrayList<Map<String, String>>();
		for (Area area : childrenAreaList) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("title", area.getName());
			map.put("value", area.getPath());
			optionList.add(map);
		}
		JSONArray jsonArray = JSONArray.fromObject(optionList);
		return ajaxJson(jsonArray.toString());
	}

}