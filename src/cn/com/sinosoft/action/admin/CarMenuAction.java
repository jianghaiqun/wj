package cn.com.sinosoft.action.admin;


import cn.com.sinosoft.bean.QueryBuilder;
import cn.com.sinosoft.entity.CarMenu;
import cn.com.sinosoft.service.CarMenuService;
import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author LiuXin
 *
 */

@ParentPackage("admin")
public class CarMenuAction extends BaseAdminAction {
	private static final long serialVersionUID = 5359991085432906677L;
	private HttpServletRequest request = ServletActionContext.getRequest();
	private CarMenu carMenu;
	@Resource
	private CarMenuService carMenuService;
	public String modify(){
		return "modify";
	}
	public String update(){
		if(findCarMenuIsExit(carMenu.getCode())){
			CarMenu cm = getCarMenuById();
			carMenuService.update(cm);
		}else{
			carMenu.setCreateDate(new Date());
			carMenu.setModifyDate(new Date());
			carMenuService.save(carMenu);
		}
		return "modify";
	}
	private CarMenu getCarMenuById() {
		CarMenu cm = carMenuService.get(carMenu.getId());
		cm.setR001(carMenu.getR001());
		cm.setR001_premium(carMenu.getR001_premium());
		cm.setR002(carMenu.getR002());
		cm.setR002_premium(carMenu.getR002_premium());
		cm.setR0030(carMenu.getR0030());
		cm.setR0030_premium(carMenu.getR0030_premium());
		cm.setR0031(carMenu.getR0031());
		cm.setR0031_premium(carMenu.getR0031_premium());
		cm.setR004(carMenu.getR004());
		cm.setR004_premium(carMenu.getR004_premium());
		cm.setR006(carMenu.getR006());
		cm.setR006_type(carMenu.getR006_type());
		cm.setR008(carMenu.getR008());
		cm.setR008_premium(carMenu.getR008_premium());
		cm.setSclar(carMenu.getSclar());
		cm.setSclar_premium(carMenu.getSclar_premium());
		cm.setNR001(carMenu.getNR001());
		cm.setNR002(carMenu.getNR002());
		cm.setNR0030(carMenu.getNR0030());
		cm.setNR0031(carMenu.getNR0031());
		cm.setNR004(carMenu.getNR004());
		cm.setNR008(carMenu.getNR008());
		cm.setModifyDate(new Date());
		return cm;
	}
	private boolean findCarMenuIsExit(String code) {
		List<CarMenu> list = new ArrayList<CarMenu>();
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>(); 
		qbs.add(createQB("code","=",code));
		list = carMenuService.findByQBs(qbs, "id", "desc");
		if(list!=null&&list.size()>0){
			return true;
		}
		return false;
	}
	public String selectCarMenu(){
		String code = request.getParameter("code");
		List<CarMenu> list = new ArrayList<CarMenu>();
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>(); 
		qbs.add(createQB("code","=",code));
		list = carMenuService.findByQBs(qbs, "id", "desc");
		JSONArray jsonArray = JSONArray.fromObject(list);
		String jsonstr = jsonArray.toString();
		return ajax(jsonstr, "text/html");
	}
	private QueryBuilder createQB(String property,String sign,String value){
		QueryBuilder qb = new QueryBuilder();
		qb.setProperty(property);
		qb.setSign(sign);
		qb.setValue(value);
		return qb;
	}
	public CarMenu getCarMenu() {
		return carMenu;
	}
	public void setCarMenu(CarMenu carMenu) {
		this.carMenu = carMenu;
	}

}
