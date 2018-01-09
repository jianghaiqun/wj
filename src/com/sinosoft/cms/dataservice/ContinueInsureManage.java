package com.sinosoft.cms.dataservice;

import cn.com.sinosoft.util.CommonUtil;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ServiceCallCollectionSchema;
import com.sinosoft.schema.ServiceCallCollectionSet;
import com.sinosoft.schema.ServiceCallRecordSchema;
import com.wangjin.infoseeker.QueryUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liyinfeng on 2016/10/8.
 */
public class ContinueInsureManage extends Page {

    public static Mapx init(Mapx params) {
    	QueryBuilder qb1 = new QueryBuilder(" SELECT  c.RoleName FROM ZDUser a ,ZDUserRole b,ZDRole c  " );

    	qb1.append(" WHERE STATUS = 'N'  AND a.UserName = b.UserName   AND c.RoleCode = b.RoleCode "
    			+ "  AND c.RoleCode = b.RoleCode  AND a.userName = ?",User.getUserName());
    	
        params.put("RealName", qb1.executeOneValue());

    	
    	
    	
        params.put("createDate", getFormat("yyyy-MM-dd", new Date()));
        params.put("endCreateDate", getFormat("yyyy-MM-dd", new Date()));
        // 判断当前用户是否有保单一键变更的权限
        QueryBuilder qb = new QueryBuilder(
                "select count(1) from zdcode where CodeType='PolicyInfoChange' and CodeValue=?",
                User.getUserName());
        
         
//        if (qb.executeInt() > 0) {
//            // 有权限设置标识“1”
//            params.put("PolicyInfoChange", "1");
//        }
        params.put("channelsn", HtmlUtil.codeToOptions("Activity.channel", true));
        params.put("OrderStatus", HtmlUtil.codeToOptions("orderstatus", true));
        params.put("MailConfigRiskType", HtmlUtil.codeToOptions("MailConfigRiskType", true));
        params.put("ApprovalStatus", HtmlUtil.codeToOptions("ApprovalStatus", true));

    	
        
        
        
        return params;
    }

    /**
     * 添加呼出状态记录
     *
     */
    public void addCallStatus () {
        // 订单号
        String orderSn = $V("orderSn");
        // 是否接听 *******
        String callConnect =  $V("callConnect");
        // 呼出记录
        String remark = $V("remark");
        // 呼出时间
        Date nowTime = new Date();
        // 客服人员名称
        String servicePersonName = User.getRealName();
        // 会员编号
        String memberId = $V("memberId");
        String[] orders= orderSn.split(",");
        Transaction trans = new Transaction();
        for (String string : orders) {
			
		
	        // 客服呼出记录
	        ServiceCallRecordSchema schema = new ServiceCallRecordSchema();
	        schema.setid(CommonUtil.getUUID());
	        schema.setorderSn(string);
	        schema.setremark(remark);
	        schema.setcallConnect(callConnect);
	        schema.setcreateDate(nowTime);
	        schema.setservicePerson(servicePersonName);
	
	        // 客服呼出记录汇总
	        ServiceCallCollectionSchema cSchema = new ServiceCallCollectionSchema();
	        // 查询既存的数据
	        ServiceCallCollectionSet set = cSchema.query(new QueryBuilder("where oldOrderSn='" + string +"'"));
	        ServiceCallCollectionSchema cSchema1 = set.get(0);
	
	        // 没有既存数据，添加数据
	        if (cSchema1 == null) {
	            cSchema.setid(CommonUtil.getUUID());
	            cSchema.setoldOrderSn(string);
	            cSchema.setcallConnect(callConnect);
	            cSchema.setcallCount("1");
	            if ("1".equals(callConnect)) {
	                cSchema.setlastCallTime(nowTime);
	            }
	            cSchema.setcreateDate(nowTime);
	            cSchema.setmodifyDate(nowTime);
	            cSchema.setmemberId(memberId);
	            trans.add(cSchema, Transaction.INSERT);
	        } else {
	            // 是未接通状态，输入值
	            String callConn = cSchema1.getcallConnect();
	            if ("2".equals(callConn)) {
	                cSchema1.setcallConnect(callConnect);
	            }
	            String callCount = cSchema1.getcallCount();
	            int intCallCount = Integer.parseInt(callCount);
	            cSchema1.setcallCount((intCallCount + 1) + "");
	            if ("1".equals(callConnect)) {
	                cSchema1.setlastCallTime(nowTime);
	            }
	            cSchema1.setmodifyDate(nowTime);
	           //有既存数据，更新数据
	            trans.add(cSchema1, Transaction.UPDATE);
	        }
	        trans.add(schema, Transaction.INSERT);
    	}

        if (trans.commit()) {
            Response.setLogInfo(1, "添加成功");
        } else {
            Response.setLogInfo(0, "添加失败");
        }

    }

    /**
     * 获取订单信息列表
     *
     *
     * @param dga
     */
    public void orderInquery(DataGridAction dga) {
    	  
   		QueryBuilder qb1 = new QueryBuilder(" SELECT  c.RoleName FROM ZDUser a ,ZDUserRole b,ZDRole c  " );

    	qb1.append(" WHERE STATUS = 'N'  AND a.UserName = b.UserName   AND c.RoleCode = b.RoleCode "
    			+ "  AND c.RoleCode = b.RoleCode  AND a.userName = ?",User.getUserName());
    	
    	
        // 保单到期时间段的开始日期
        String createDate = dga.getParams().getString("createDate");
        // 保单到期时间段的结束日期
        String endCreateDate = dga.getParams().getString("endCreateDate");
        // 呼出次数
        String callCount = dga.getParams().getString("callCount");
        // 是否接通 1:是；2：否。
        String callConnect = dga.getParams().getString("callConnect");
        // 是否续保 1:是；2：否。
        String continueInsure = dga.getParams().getString("continueInsure");
        // 一年保单 1:是；2：否。
        String oneYearOrder = dga.getParams().getString("oneYearOrder");

        // 渠道
        String channelsn = dga.getParams().getString("channelsn");
        String channel = QueryUtil.getChannelInfo(channelsn,"");
        String channelinfo="";
        if(StringUtil.isNotEmpty(channel)){
            channelinfo = " AND EXISTS (SELECT 1 FROM ChannelInfo WHERE ChannelCode = a.channelsn and ChannelCode IN ("+channel+"))";
        }

        // sql
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT CASE WHEN ma.userName IS NULL THEN '未分配' ELSE '已分配' END distributionState, ma.userName, a.memberid,f.productGenera,j.callConnect,j.callCount,");
        sb.append(" CASE WHEN j.continueStatus = 1 THEN '是' ELSE '否'  END AS continueInsure,j.newOrderSn, ");
        sb.append("a.ordersn,a.orderStatus,");
        sb.append("(SELECT GROUP_CONCAT(e.policyNo) FROM sdinformationrisktype e WHERE a.ordersn = e.ordersn)  AS policyNo,");
        sb.append("(SELECT COUNT(1) FROM sdinformationrisktype e WHERE a.ordersn = e.ordersn) AS recognizeeNu,");
        sb.append("(SELECT COUNT(1) FROM paymentInfo p WHERE a.ordersn = p.ordersn) AS paymentReportConut,");
        sb.append("DATE_FORMAT(b.startdate,'%Y-%m-%d') AS svalidate,");
        sb.append("n.ChannelName,b.ProductName,b.planName,");
        sb.append("a.totalAmount,a.ModifyDate,a.ActivitySn,a.orderActivity,a.PayPrice,");
        sb.append("h.codeName AS orderstatusname, c.ApplicantName,");
        sb.append("if(m.mobileno != null or m.mobileno != '', m.mobileno, m.email) AS MID, ");
        sb.append(" a.couponsn as couponSn,a.orderCoupon as orderCoupon, a.offsetPoint as offsetPoint, a.orderIntegral as orderIntegral,b.productid,b.insurancecompany ,");
        sb.append("a.diyActivityDescription as diyActivityDescription ");
        sb.append(" FROM sdorders a INNER JOIN sdinformation b ON a.ordersn = b.ordersn ");
        sb.append(" INNER JOIN sdinformationappnt c ON  b.informationsn = c.informationsn  ");
        sb.append(" LEFT JOIN sdmark ma on ma.orderSn= a.orderSn  AND ma.type = '2' ");
        sb.append(" LEFT JOIN sdproduct f ON b.productId = f.ProductID   ");
        sb.append(" LEFT JOIN zdcode h ON h.codevalue = a.orderstatus AND h.CodeType = 'orderstatus' ");
        sb.append(" LEFT JOIN ServiceCallCollection j ON a.ordersn = j.oldordersn ");
        sb.append(" LEFT JOIN member m ON a.memberId = m.id ");
        sb.append(" LEFT JOIN channelinfo n ON a.channelsn = n.channelcode ");
        sb.append(" where b.enddate>='" + createDate + " 00:00:00' and b.enddate <='" + endCreateDate + " 23:59:59'");
        sb.append(" AND a.memberid is not null ");
        
        DataTable  dt1 = qb1.executeDataTable();
        for (int i = 0; i < dt1.getRowCount(); i++) {
			
        	if("客服岗".equals(dt1.getString(i, "RoleName"))){
        		
        		sb.append(" AND ma.userName = '"+User.getUserName()+"'");
        	}
		}



        //渠道
        sb.append(channelinfo);
        // 呼出次数
        if (StringUtil.isNotEmpty(callCount)) {
            if ("5".equals(callConnect)) {
                sb.append(" AND j.callCount >= '"+ callCount + "' ");
            } else {
                sb.append(" AND j.callCount = '"+ callCount + "' ");
            }
        }

        // 是否接听
        if (StringUtil.isNotEmpty(callConnect)) {
            sb.append(" AND j.callConnect = '"+ callConnect + "' ");
        }

        // 是否续保
        if ("1".equals(continueInsure)) {
            sb.append(" AND j.continueStatus = 1 ");
        } else if ("2".equals(continueInsure)) {
            sb.append(" AND j.continueStatus = 2");
        }

        // 是否是一年续保订单
        if ("1".equals(oneYearOrder)) {
            sb.append(" AND b.ensure IN('365D','12M','1Y') ");
        }

        // 会员ID过滤
        String mid = dga.getParams().getString("mid");
        if (StringUtil.isNotEmpty(mid)) {
            sb.append(" AND (m.email = '" + mid.trim() +"' or mobileno = '" + mid.trim() +"') ");
        }
        // 订单状态
        String OrderStatus = dga.getParams().getString("OrderStatus");
        if (StringUtil.isNotEmpty(OrderStatus)) {
			sb.append(" AND  h.codeValue = '" + OrderStatus + "' ");
        }
        
        // 品类
        String MailConfigRiskType = dga.getParams().getString("MailConfigRiskType");
        if (StringUtil.isNotEmpty(MailConfigRiskType)) {
			sb.append(" AND  f.productType = '" + MailConfigRiskType + "' ");
        }
        
        // 分配状态
        String ApprovalStatus = dga.getParams().getString("ApprovalStatus");
        if ("1".equals(ApprovalStatus)) {
			
        	 sb.append("  AND ma.userName IS  NULL") ;
        }else if("2".equals(ApprovalStatus)){
        	
        	 sb.append("  AND ma.userName IS NOT  NULL") ;
        }

        // 被分配人
        String assignUser = dga.getParams().getString("assignUser");
        if (StringUtil.isNotEmpty(assignUser)) {
            sb.append("  AND ma.userName like ('%"+ assignUser +"%') ") ;
        }

        
        
    

        sb.append(" GROUP BY a.ordersn order by a.modifydate desc,a.ordersn desc");

        // 测试输出结果SQL
        QueryBuilder qb = new QueryBuilder(sb.toString());
        DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
        dt.insertColumn("KID");
        // 添加保全记录列，方便下边for循环存放值
        dt.insertColumn("remark");
        // 添加是否报案列，方便下边for循环存放值
        dt.insertColumn("paymentReport");
        // 添加呼出记录列，方便下边for循环存放值
        dt.insertColumn("callRecord");
        // 当前用户角色
        dt.insertColumn("RealName");

        if (dt != null) {
            String queryRemark;
            QueryBuilder qbr;
            DataTable dtr;
            String remark;
            String remarkTem;
            int a;
            String queryCallRecord;
            String callRecord;
            String callRecordTemp;

            for (int i = 0; i < dt.getRowCount(); i++) {
            	dt.set(i, "RealName", qb1.executeOneValue());
                dt.set(i, "KID", StringUtil.md5Hex(com.sinosoft.cms.pub.PubFun.getKeyValue() + dt.getString(i, "orderSn")));
                if (dt.getString(i, "orderstatus").equals("7") ||
                        dt.getString(i, "orderstatus").equals("10") ||
                        dt.getString(i, "orderstatus").equals("12") ||
                        dt.getString(i, "orderstatus").equals("14")) {
                    dt.set(i, "totalAmount", "+" + dt.getString(i, "totalAmount"));
                }

                if (StringUtil.isEmpty(dt.getString(i,"callCount"))) {
                    dt.set(i, "callCount", "0次");
                    dt.set(i, "callConnect", "-");
                    dt.set(i, "continueInsure", "-");
                } else {
                    dt.set(i,"callCount", dt.getString(i,"callCount") + "次");
                }

                if ("1".equals(dt.getString(i, "callConnect"))) {
                    dt.set(i, "callConnect", "是");
                } else if ("2".equals(dt.getString(i, "callConnect"))) {
                    dt.set(i, "callConnect", "否");
                }
                // 保全记录查询
                queryRemark = "SELECT remark,OperateTime,OperateName FROM sdremark WHERE ordersn='" + dt.getString(i, "orderSn") + "' ORDER BY OperateTime DESC";
                qbr = new QueryBuilder(queryRemark);
                dtr = qbr.executeDataTable();
                remark = "";
                if (dtr != null && dtr.getRowCount() > 0) {
                    for (int j = 0; j < dtr.getRowCount(); j++) {
                        a = j + 1;
                        remarkTem = dtr.getString(j, "remark") + "  " + dtr.getString(j, "OperateTime") + "  " + dtr.getString(j, "OperateName") + " && ";
                        if (j == dtr.getRowCount() -1 && remarkTem.indexOf("变更：初始商家订单号") >= 0) {
                            remark = remarkTem + " " + remark;
                        } else {
                            remark += a + ", " + remarkTem;
                        }
                    }
                    dt.set(i, "remark", remark);
                }

                // 呼出记录查询
                queryCallRecord = "SELECT remark,createDate,servicePerson FROM servicecallrecord WHERE orderSn='" + dt.getString(i, "orderSn") + "' ORDER BY createDate ASC";
                qbr = new QueryBuilder(queryCallRecord);
                dtr = qbr.executeDataTable();
                callRecord = "";
                if (dtr != null && dtr.getRowCount() > 0) {
                    for (int j = 0; j < dtr.getRowCount(); j++) {
                        a = j + 1;
                        callRecordTemp = dtr.getString(j, "servicePerson") + "  " + getFormat("yyyy-MM-dd hh:mm", dtr.getDate(j, "createDate")) + "  " + dtr.getString(j, "remark") + " &&";
                        callRecord += a + ", " + callRecordTemp;
                    }
                    dt.set(i, "callRecord", callRecord);
                }

                if (Integer.valueOf(dt.getString(i, "paymentReportConut")) > 0) {
                    dt.set(i, "paymentReport", "是");

                }
                if (StringUtil.isEmpty(dt.getString(i, "PayPrice"))) {
                    if (Integer.valueOf(dt.getString(i, "orderstatus")) < 7 || "8".equals(dt.getString(i, "orderstatus").trim())) {
                        dt.set(i, "PayPrice", "");
                    }
                }
            }

        }
        dga.setTotal(qb);
        dga.bindData(dt);
    }

    /**
     * 获取呼出记录
     */
    public void showCallRecord (DataGridAction dga) {
        // 呼出记录查询
        String queryCallRecord = "SELECT id,remark,createDate,servicePerson FROM servicecallrecord WHERE orderSn='"
                + dga.getParam("orderSn") + "' ORDER BY createDate ASC";
        QueryBuilder qb = new QueryBuilder(queryCallRecord);
        DataTable dt = qb.executeDataTable();

        dga.bindData(dt);
    }

    /**
     * 更新呼出记录
     */
    public void updateCallRecord() {
        //id
        String id = $V("id");
        // 呼出记录
        String remark = $V("remark");

        ServiceCallRecordSchema schema = new ServiceCallRecordSchema();
        schema.setid(id);

        if (schema.fill()) {
            schema.setremark(remark);
            Transaction trans = new Transaction();
            trans.add(schema, Transaction.UPDATE);

            if (trans.commit()) {
                Response.setLogInfo(1, "更新成功");
            } else {
                Response.setLogInfo(0, "更新失败");
            }
        } else {
            Response.setLogInfo(0, "更新失败");
        }

    }
    private static String getFormat(String format, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
    
    
    /**
     * 
     * distribution:续保订单分配客服人员. <br/>
     * @author  
     */
    public void distribution(){
    	
    	
        String orderSns = $V("orderSns");
  		String userName = $V("dg1");
 
    	
		String[] orders= orderSns.split(",");
		Transaction trans = new Transaction();  
		
		for (String string : orders) {
			QueryBuilder qb1 = new QueryBuilder(" SELECT c.applicantName , c.applicantMobile,b.orderSn FROM  sdinformation b "
					+ "INNER JOIN sdinformationappnt c ON  b.informationSn = c.informationSn  WHERE  b.orderSn = ? ");
			qb1.add(string);
			DataTable dt =qb1.executeDataTable();
		
			
			QueryBuilder qb2 = new QueryBuilder(" select userName from sdmark where recognizeeMobile = ? and memberId = ? and type='2' ");
			qb2.add(dt.get(0, "applicantMobile"));
			qb2.add(dt.get(0, "applicantName"));
			DataTable dt1 =qb2.executeDataTable();
			boolean bl =false ;
		 
			for (int i = 0; i < dt.getRowCount(); i++) {
				if(dt1.getRowCount() ==0 || userName.equals(dt1.get(i, "userName"))){
					String id_index = NoUtil.getMaxNo("DICTIONARYID")+"";
					QueryBuilder qb = new QueryBuilder(" insert into sdmark VALUES (?,?,?,now(),?,?,now(),'2')");
					qb.add(id_index);
					qb.add(string);
					qb.add(userName);
					qb.add(dt.get(0, "applicantMobile"));
					qb.add(dt.get(0, "applicantName"));
					
					trans.add(qb);
					bl = true ;
				}
			}
			if(bl == true){
				if (trans.commit()) {
					Response.setLogInfo(1, "保存成功!");
				} else {
					Response.setLogInfo(0, "保存失败!");
				}
			}else{
				
				Response.setLogInfo(0, "订单号为："+string+"的订单已分配给"+dt1.get(0,"userName"));
			}
		}
		
    	
    }
    
    
    /**
     * 
     * distribution:删除续保订单分配客服人员. <br/>
     * @author  
     */
    public void delDistribution(){
    	
    	
        String orderSns = $V("orderSns");

		String[] orders= orderSns.split(",");
		Transaction trans = new Transaction();
		for (String string : orders) {
			
		 
			QueryBuilder qb = new QueryBuilder(" DELETE FROM  sdmark WHERE orderSn = ? and type ='2' ");
			qb.add(string);
			trans.add(qb);
		}
		
    	if (trans.commit()) {
			Response.setLogInfo(1, "删除成功!");
		} else {
			Response.setLogInfo(0, "删除失败!");
		}
    }

}
