package test;

import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Test {
    private static final Logger logger = LoggerFactory.getLogger(Test.class);

//    public static void main(String[] args) {
//        logger.error("错误消息");
//        logger.warn("警告消息");
//        logger.info("通知消息");
//    }

    /*public static void main(String[] args) {
        int sumStarNum = 51;
        int sumCommentNum = 13;
        NumberFormat nt = NumberFormat.getPercentInstance();
        //设置百分数精确度2即保留两位小数
        nt.setMinimumFractionDigits(0);
        float fPercent = (float)sumStarNum/(sumCommentNum * 5);
        System.out.println(nt.format(fPercent));

    }*/

//    public static void main(String[] args) {
//        // 判断产品是否有现金价值页
//        String sql = "select memo from zdcode where CodeType = 'BuyFlow.CodeFlag' and CodeValue = 'ShowCashValueProducts'";
//        QueryBuilder qb = new QueryBuilder(sql);
//        String riskCodes = qb.executeString();
//
//        System.out.println(riskCodes);
//    }

    public static void main(String[] args) {
        String sql = "SELECT riskcode,GROUP_CONCAT(BrightSpotName) as brightSpotName FROM FEMRiskBrightSpotList GROUP BY riskcode";

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
                    tHTML += "<li>" + subBrightSpotName + "</li>";
                }
                tHTML += "</ul>";
                map.put(dt.getString(i, "riskcode"), tHTML);
            }

            String key = null;
            String value = null;
            for (Map.Entry<String, String> entry : map.entrySet()) {
                key = entry.getKey();
                value = entry.getValue();

                sql = "UPDATE sdsearchrelaproduct a, zdcolumnvalue b SET a.femriskbrightspot = '" + value + "', b.textvalue = '" + value + "' WHERE a.productid = '" + key + "' AND b.columncode = 'FEMRiskBrightSpot' AND a.prop1 = b.relaid " +
                        "AND textvalue != '' AND textvalue IS NOT NULL AND EXISTS(SELECT id FROM zcarticle WHERE id=relaid AND TYPE='1' AND CatalogInnerCode LIKE '002313%' AND `Status`='30')";

                qb = new QueryBuilder(sql);

                qb.executeNoQuery();
            }
        }
    }
}
