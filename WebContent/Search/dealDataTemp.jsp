<%@ page import="com.sinosoft.framework.data.DataTable" %>
<%@ page import="com.sinosoft.framework.data.QueryBuilder" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="static org.exolab.castor.xml.AccessRights.write" %>
<!DOCTYPE html >
<%@ page session="false" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<%
    // 此方法只是临时使用，为了更新历史数据，避免发布全部文章。但是不可以视为正常逻辑处理。

    String sql = "SELECT riskcode,GROUP_CONCAT(BrightSpotName ORDER BY BrightSpotOrder ASC) AS brightSpotName FROM FEMRiskBrightSpotList GROUP BY riskcode";

    QueryBuilder qb = new QueryBuilder(sql);
    DataTable dt = qb.executeDataTable();

    if (dt != null && dt.getRowCount() > 0) {
        Map<String, String> map = new HashMap<String, String>();
        String brightSpotName = null;
        String subBrightSpotName = null;
        for (int i = 0; i < dt.getRowCount(); i++) {
            String tHTML = "<ul class=\"d_list_con\">";
            brightSpotName = dt.getString(i, "brightSpotName");
            String[] array = brightSpotName.split(",");
            for (int j = 0; j < array.length; j++) {
                // 亮点最多显示6个
                if (j > 5) {
                    break;
                }
                // 每个亮点长度不能超过10个字符
                subBrightSpotName = array[j];
                if (subBrightSpotName != null && subBrightSpotName.length() > 10) {
                    subBrightSpotName = subBrightSpotName.substring(0, 10);
                }
                tHTML += "<li><span>" + subBrightSpotName + "</span></li>";
            }
            tHTML += "</ul>";
            map.put(dt.getString(i, "riskcode"), tHTML);
        }

        String key = null;
        String value = null;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            key = entry.getKey();
            value = entry.getValue();

            sql = "UPDATE sdsearchrelaproduct a, zdcolumnvalue b SET a.femriskbrightspot = '"+ value +"', b.textvalue = '"+ value +"' WHERE a.productid = '"+ key +"' AND b.columncode = 'FEMRiskBrightSpot' AND a.prop1 = b.relaid " +
                    "AND textvalue != '' AND textvalue IS NOT NULL";

            qb = new QueryBuilder(sql);

            qb.executeNoQuery();
        }

        sql = "UPDATE sdsearchrelaproduct SET femriskbrightspot = REPLACE(femriskbrightspot, 'ld_list_box', 'd_list_con')";
        new QueryBuilder(sql).executeNoQuery();

        sql = "UPDATE zdcolumnvalue SET textvalue = REPLACE(textvalue, 'ld_list_box', 'd_list_con')";
        new QueryBuilder(sql).executeNoQuery();

        out.println("<script>alert('数据处理完成');</script>");
    }


%>
</html>