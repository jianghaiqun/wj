package cn.com.sinosoft.action.car;
import cn.com.sinosoft.action.shop.BaseShopAction;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
/**
 * 用户信息保存
 * @author heyang
 *
 */
public class CarUser extends BaseShopAction{
	private static final long serialVersionUID = 1L;
	//车主姓名
	private String carUser;
	//车主电话
	private String carPhone;
	//车牌号
	private String carNO;
	//首页跟他一样返回标记
	private String type;
	
	/**
	 * 用户信息保存
	 * @return
	 */
	public String save(){
		try {
			boolean status = true;
			carUser = java.net.URLDecoder.decode(carUser, "UTF-8");
			carNO = java.net.URLDecoder.decode(carNO, "UTF-8");
			String sql = "INSERT INTO `carUser` (`id`, `carName`, `carPhone`, `carNO`) VALUES(?,?,?,?)";
			QueryBuilder qb = new QueryBuilder(sql);
			qb.add(NoUtil.getMaxID("CarId"));
			if(StringUtil.isEmpty(carUser))
				status = false;
			if(StringUtil.isEmpty(carPhone))
				status = false;
			if(StringUtil.isEmpty(carNO))
				status = false;
			if(status){
				qb.add(carUser);
				qb.add(carPhone);
				qb.add(carNO);
				qb.executeNoQuery();
			}
			JSONObject jsonObject = JSONObject.fromObject(status);
			return ajax(jsonObject.toString(), "text/html");
		} catch (Exception e) {
			logger.error("车主信息保存异常" + e.getMessage(), e);
			return ERROR;
		}
		
	}
	public String show(){
		try {
			// 返回前台数据
			Map<String, Object> sumMap = new HashMap<String, Object>();
			String Sql = "SELECT memo FROM zdcode WHERE codename='车险跟他一样' and codetype ='cainiao' ";
			String Sql1 = "SELECT memo FROM zdcode WHERE codename='车险跟他一样' and codetype ='OldDriver' ";
			String Sql2 = "SELECT memo FROM zdcode WHERE codename='车险跟他一样' and codetype ='women' ";
			sumMap.put("cainiao",  new QueryBuilder(Sql).executeOneValue());
			sumMap.put("OldDriver",  new QueryBuilder(Sql1).executeOneValue());
			sumMap.put("women",  new QueryBuilder(Sql2).executeOneValue());
			JSONObject jsonObject = JSONObject.fromObject(sumMap);
			return ajax(jsonObject.toString(), "text/html");  
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ERROR;
		}
	}
	public String change(){
		try {
			
			// 返回前台数据
			Map<String, Object> sumMap = new HashMap<String, Object>();
			String Sql = "SELECT memo FROM zdcode WHERE codename='车险跟他一样' and codetype ='cainiao' ";
			String Sql1 = "SELECT memo FROM zdcode WHERE codename='车险跟他一样' and codetype ='OldDriver' ";
			String Sql2 = "SELECT memo FROM zdcode WHERE codename='车险跟他一样' and codetype ='women' ";
			String SqlUpdate = "UPDATE  zdcode SET memo =? WHERE codename='车险跟他一样' and codetype ='cainiao' ";
			String SqlUpdate1 = "UPDATE zdcode SET memo =? WHERE codename='车险跟他一样' and codetype ='OldDriver' ";
			String SqlUpdate2 = "UPDATE zdcode SET memo =? WHERE codename='车险跟他一样' and codetype ='women' ";
			if("cainiao".equals(type)){
				int sum = new QueryBuilder(Sql).executeInt();
				sum = sum+1;
				sumMap.put("cainiao",  sum);
				sumMap.put("OldDriver",  new QueryBuilder(Sql1).executeOneValue());
				sumMap.put("women",  new QueryBuilder(Sql2).executeOneValue());
				new QueryBuilder(SqlUpdate, sum).executeNoQuery();
			}
			if("man".equals(type)){
				int sum = new QueryBuilder(Sql1).executeInt();
				sum = sum+1;
				sumMap.put("cainiao",  new QueryBuilder(Sql).executeOneValue());
				sumMap.put("OldDriver", sum);
				sumMap.put("women",  new QueryBuilder(Sql2).executeOneValue());
				new QueryBuilder(SqlUpdate1, sum).executeNoQuery();
			}
			if("women".equals(type)){
				int sum = new QueryBuilder(Sql2).executeInt();
				sum = sum+1;
				sumMap.put("cainiao",  new QueryBuilder(Sql).executeOneValue());
				sumMap.put("OldDriver", new QueryBuilder(Sql1).executeOneValue());
				sumMap.put("women",  sum);
				new QueryBuilder(SqlUpdate2, sum).executeNoQuery();
			}
			JSONObject jsonObject = JSONObject.fromObject(sumMap);
			return ajax(jsonObject.toString(), "text/html");  
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ERROR;
		}
	}

	public String getCarUser() {
		return carUser;
	}

	public void setCarUser(String carUser) {
		this.carUser = carUser;
	}

	public String getCarPhone() {
		return carPhone;
	}

	public void setCarPhone(String carPhone) {
		this.carPhone = carPhone;
	}

	public String getCarNO() {
		return carNO;
	}

	public void setCarNO(String carNO) {
		this.carNO = carNO;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
