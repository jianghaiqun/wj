package cn.com.sinosoft.action.shop;

import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.search.ProductIndexer;
import com.sinosoft.search.SearchResult;
import org.apache.commons.collections.map.HashedMap;
import org.apache.struts2.convention.annotation.ParentPackage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by liyinfeng on 2016/11/2.
 */
@ParentPackage("shop")
public class IndexerAction extends BaseShopAction {
    private static final long serialVersionUID = -8062028974045078820L;
    
   
    public String search() {
        Mapx paramsMap = new Mapx();
        paramsMap.put("keyword",getParameter("keyword"));
        paramsMap.put("channel",getParameter("channel"));
        paramsMap.put("sortColumn",getParameter("sortColumn"));
        paramsMap.put("sortDesc",getParameter("sortDesc"));

        ProductIndexer productIndexer = new ProductIndexer(55);
        SearchResult sr1 = productIndexer.doSearch(paramsMap);

        // 总数
        int total = 0;
        if (sr1 != null) {
            total = sr1.Total;
        }

        paramsMap.put("page",getParameter("currentPage"));
        paramsMap.put("size",getParameter("pageSize"));
        SearchResult sr2 = productIndexer.doSearch(paramsMap);

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if (sr2 != null) {
            Map<String, Object> map;
            for (int i = 0; i < sr2.Data.getRowCount(); i++) {
                DataRow dr = sr2.Data.get(i);
                map = new HashedMap();
                map.put("productid", dr.getString("productid"));
                map.put("ProductName", dr.getString("Title"));
                map.put("CompanyCode", dr.getString("companyCode"));
                map.put("URL", dr.getString("htmlPath"));
                map.put("calhtml", dr.getString("calhtml"));
                map.put("AdaptPeopleInfo", dr.getString("AdaptPeopleInfo"));
                map.put("OccupSection", dr.getString("OccupSection"));
                map.put("TextAgeSection", dr.getString("TextAgeSection"));
                map.put("PeriodSection", dr.getString("PeriodSection"));
                map.put("initPrem", dr.getString("InitPrem"));
                map.put("salesVolume", dr.getString("SalesVolume"));
                map.put("complateDate", dr.getString("ComplateDate"));
                list.add(map);
            }
        }

        Map<String, Object> jsonMap = new HashedMap();
        jsonMap.put("ProductInfoData", list);
        jsonMap.put("total", total);
       return ajaxMap2Json(jsonMap);
    }
}
