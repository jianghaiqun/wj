package cn.com.sinosoft.action.shop;


import java.util.HashMap;
import java.util.Map;
import net.sf.json.JSONObject;
import org.apache.struts2.convention.annotation.ParentPackage;
import com.sinosoft.cms.dataservice.ColumnUtil;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;

@ParentPackage("shop") 
public class ActivityAction extends BaseShopAction {
	private static final long serialVersionUID = 1L;
		/**
		 * 点赞触发方法（个数）.
		 * @return ajax
		 * @author jiaomengying
		 */
		public String CountSupport(){
			String team = getRequest().getParameter("team");
			String CatalogID = getRequest().getParameter("CatalogId");
			int SuppNoB = 0;
			QueryBuilder qb = new QueryBuilder(
					"SELECT TextValue FROM ZDColumnValue WHERE RelaID =? AND RelaType='"+ ColumnUtil.RELA_TYPE_CATALOG_EXTEND +"' AND columncode=?", CatalogID,team);
			DataTable dt = qb.executeDataTable();
			if(dt!=null && dt.getRowCount()>0){
				SuppNoB = Integer.parseInt(dt.getString(0, "TextValue"));
		    }
			SuppNoB +=1; 
			QueryBuilder qbupdate = new QueryBuilder(
					"UPDATE ZDColumnValue SET TextValue="+SuppNoB+" WHERE RelaID =? AND RelaType='"+ ColumnUtil.RELA_TYPE_CATALOG_EXTEND +"' AND columncode=?", CatalogID,team);
			qbupdate.executeNoQuery();
	
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Support", SuppNoB);
			JSONObject jsonObject = JSONObject.fromObject(map);
			
			return ajax(jsonObject.toString(), "text/html");
		}
		/**
		 * 世界杯首页初始化.
		 * @return
		 */
		public String initSupp(){
			QueryBuilder qb = new QueryBuilder("SELECT b.columncode,b.TextValue FROM ZDColumnValue b,zccatalog c " +
					"WHERE b.RelaID=c.id AND b.RelaType='"+ ColumnUtil.RELA_TYPE_CATALOG_EXTEND +"' AND c.name ='世界杯一' ");
			DataTable dt = qb.executeDataTable();
			
			Map<String, Object> map = new HashMap<String, Object>();
			if(dt!=null && dt.getRowCount()>0){
				for(int i=0;i<dt.getRowCount();i++){
					map.put(dt.getString(i, "columncode"), dt.getString(i, "TextValue"));
				}
			}
			
			JSONObject jsonObject = JSONObject.fromObject(map);
			
			return ajax(jsonObject.toString(), "text/html");
		}
}