<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="controls" prefix="z"%>
<%@page import="com.sinosoft.framework.data.DataTable"%>
<%@page import="com.sinosoft.framework.data.QueryBuilder"%>
<%@page import="com.sinosoft.framework.data.DataRow"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>

<%
	Map<String,String> maps = new HashMap<String,String>();
    maps.put("personnelName", "a.bnfName");
    maps.put("sexCode", "a.bnfSex");
    maps.put("certificateType", "a.bnfIDType");
    maps.put("certificateNo", "a.bnfIDNo");
    maps.put("birthday", "a.bnfBirthday");
    maps.put("mobileTelephone", "a.mobile");
    maps.put("email", "a.email");
    maps.put("certificateEffStartDate", "a.bnfStartID");
    maps.put("certificateEffEndDate", "a.bnfEndID");
    maps.put("professionName", "(SELECT CODE FROM occupation WHERE id=a.bnfOccupation3)");
    maps.put("BeneRate", "a.benePer");
    maps.put("relation", "a.relationToInsured");
    maps.put("address", "a.postalAddress");
    maps.put("PostCode", "a.zipCode");
    maps.put("BeneOrder", "\\'1\\'");
    maps.put("BnfArea1", "(SELECT NAME FROM AREA WHERE id=a.bnfArea1)");
    maps.put("BnfArea2", "(SELECT NAME FROM AREA WHERE id=a.bnfArea2)");
    maps.put("BnfArea3", "(SELECT NAME FROM AREA WHERE id=a.bnfArea3)");
    maps.put("post-code", "a.zipCode");
    maps.put("bene-order", "\\'1\\'");
    maps.put("bene-rate", "a.benePer");

    QueryBuilder selectAll  = new QueryBuilder("select DISTINCT ManageCom,XMLName from tabvxml");
    DataTable dtAll = selectAll.executeDataTable();
    for(int i=0;i<dtAll.getRowCount();i++){
        String ManageCom = dtAll.getString(i, 0);
        String xmlname = dtAll.getString(i, 1);
                //检查是否处理过
        QueryBuilder checksql = new QueryBuilder("select ElementNo from tabvxml "
        + "where ManageCom='"+ManageCom+"' and xmlname='"+xmlname+"' and element = 'beneficiaryInfoList'");
        if(checksql.executeDataTable().getRowCount() > 0){
            System.out.println("已处理过");
            continue;
        }
        
        
        QueryBuilder qb = new QueryBuilder("select ElementNo,ParentElementNo "
                + "From tabvxml "
                + "where ManageCom='"+ManageCom+"' and xmlname='"+xmlname+"' and element = 'beneficiaryInfo' order by ElementNo ASC");
        DataTable dt = qb.executeDataTable();
        if(dt.getRowCount() > 0){
            if(dt.getRowCount() > 1){
                System.out.println("beneficiaryInfo数据大于1条,删除多余数据");
                for(int k=1;k<dt.getRowCount();k++){
                    String delBeneElementNo = dt.getString(k,0);
                    //删除beneficiaryInfo
                    new QueryBuilder("delete from tabvxml where ManageCom='"+ManageCom+"' and xmlname='"+xmlname+"' and ElementNo='"+delBeneElementNo+"'").executeNoQuery();
                    //删除beneficiaryInfo子节点
                    new QueryBuilder("delete from tabvxml where ManageCom='"+ManageCom+"' and xmlname='"+xmlname+"' and ParentElementNo='"+delBeneElementNo+"'").executeNoQuery();
                }
            }
            DataRow dr = dt.getDataRow(0);
            String beneElementNo = dr.getString("ElementNo");
            String beneParentElementNo = dr.getString("ParentElementNo");
            //查询当前最大节点号
            QueryBuilder q = new QueryBuilder("select max(ElementNo) from tabvxml "
                    + "where ManageCom='"+ManageCom+"' and xmlname='"+xmlname+"'");
            int beneListElementNo = Integer.parseInt(q.executeString()) + 1;
            //查询数据sql
            QueryBuilder q2 = new QueryBuilder("select elementNo,element from tabvxml a where a.ManageCom='"+ManageCom+"' and a.xmlname='"+xmlname+"' and a.Element in ('personnelName','sexCode','certificateType','certificateNo',"
                    + "'birthday','mobileTelephone','email','certificateEffStartDate','certificateEffEndDate','professionName','BeneRate','relation','address','PostCode',"
                    + "'BnfArea1','BnfArea2','BnfArea3','BeneOrder','post-code','bene-order','bene-rate') and a.ParentElementNo = '"+beneElementNo+"' order by elementno");
            DataTable dt2 = q2.executeDataTable();
            StringBuffer dataSql = new StringBuffer();
            dataSql.append("SELECT ");
            for(int j=0;j<dt2.getRowCount();j++){
                String elementNo = dt2.getString(j, 0);
                String element = dt2.getString(j, 1);
                dataSql.append(maps.get(element)+" as \\'"+elementNo+"\\'");
                if(j < dt2.getRowCount() - 1){
                    dataSql.append(",");
                }
            }
            dataSql.append(" FROM sdinformationbnf a,sdinformationinsured b where a.recognizeeSn = b.recognizeeSn and a.ordersn =? and b.insuredSn=?");
            String insertSql = "INSERT INTO `tabvxml` (`ElementNo`, `XMLName`, `XMLEncoding`, `XMLFilePath`, `Element`, `ParentElementNo`, `Attribute`, `OrderNo`, `Function`, `LevelId`, `ElementValue`, `AttributeValue`, `ElementSQL`, `ElementSQLVar`, `AttributeSQL`, `AttributeSQLVar`, `ManageCom`, `ForFlag`, `AttributeNo`) "
                    + "VALUES ('"+beneListElementNo+"', '"+xmlname+"', 'UTF-8', NULL, 'beneficiaryInfoList', '"+beneParentElementNo+"', 'name,sum', '"+beneListElementNo+"', NULL, '7', NULL, NULL, '"+dataSql.toString()+"', 'OrderNo,InsuredSn', NULL, NULL, '"+ManageCom+"', NULL, '"+beneListElementNo+"a0,"+beneListElementNo+"a1');";
            System.out.println("insertSql:");
            System.out.println(insertSql);
            new QueryBuilder(insertSql).executeNoQuery();
            //更新beneficiaryInfo节点
            String updateSql = "update tabvxml set parentElementNo = '"+beneListElementNo+"',LevelId = 8,"
                    + "ElementSQL = null,ElementSQLVar = null,AttributeNo = null "
                    + "where ManageCom='"+ManageCom+"' and xmlname='"+xmlname+"' and element = 'beneficiaryInfo';";
            System.out.println("updateSql:");
            System.out.println(updateSql);
            new QueryBuilder(updateSql).executeNoQuery();
            //更新beneficiaryInfo子节点层级为9
            new QueryBuilder("update tabvxml set LevelId = 9 where parentElementNo = '"+beneElementNo+"' and ManageCom = '"+ManageCom+"' and xmlname = '"+xmlname+"';").executeNoQuery();
        }else{
            System.out.println("无beneficiaryInfo节点，无需处理");
        }
    }  
    out.println("完成");
%>

