package com.wangjin.infoseeker;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Channel;

import java.util.HashMap;
import java.util.Map;

public class QueryUtil extends Page {
	
	/**
	 * @param cChannelCode 渠道内部编码
	 * @param cSource 来源 b2b,
	 * @return
	 */
	public static String getChannelInfo(String cChannelCode,String cSource){
		
		// 惠租车作为渠道，如果只选中惠租车条件直接返回huizuche字符串
		if ("huizuche".equals(cChannelCode)) {
			return "'" + cChannelCode + "'";
		}
		StringBuffer tChannelInfo = new StringBuffer("");
		if(StringUtil.isEmpty(cChannelCode)){
			return "";
		}
		try{
			
			String[] arr = new String[]{cChannelCode};
			
			if(cChannelCode.indexOf(",")!=-1){
				arr = cChannelCode.split(",");
			}
			int arrlen = arr.length;
			for(int i=0;i<arrlen;i++){
				//得到子渠道内部编码
				String tCode =new QueryBuilder(" select InnerChannelCode from ChannelInfo where ChannelCode =?",arr[i]).executeString();
				if (StringUtil.isEmpty(tCode)) {
					continue;
				}
				String[] arrChild = Channel.dealChildList(tCode);
				//根据子渠道内部编码查询渠道编码
				int arrclen = arrChild.length;
				for(int j=0;j<arrclen;j++){
					if(!"$".equals(arrChild[j])){
						String channelCode = new QueryBuilder(" select ChannelCode from ChannelInfo where InnerChannelCode =?",arrChild[j]).executeString();
						//组装b2b渠道信息
						if("b2b".equals(cSource)){
							if(channelCode.startsWith("b2b")){
								tChannelInfo.append("'"+channelCode+"',");
							}
						}else{
							tChannelInfo.append("'"+channelCode+"',");
						}
					}
				}
				
			}
			if(tChannelInfo.indexOf(",")!=-1){
				tChannelInfo.deleteCharAt(tChannelInfo.length()-1);
			}
		}catch(Exception e){
			logger.error("销售统计，根据渠道内部编码查询渠道编码异常,InnerChannelCode:"+cChannelCode + e.getMessage(), e);
		}
		
		return tChannelInfo.toString();
	}
	
	/**
	 * @param cChannelCode 渠道内部编码
	 * @param cSource 来源 b2b,
	 * @return
	 */
	public static String getKXBChannelInfo(String cChannelCode,String cSource){
		
		// 惠租车作为渠道，如果只选中惠租车条件直接返回huizuche字符串
		if ("huizuche".equals(cChannelCode)) {
			return "'" + cChannelCode + "'";
		}
		StringBuffer tChannelInfo = new StringBuffer("");
		if(StringUtil.isEmpty(cChannelCode)){
			return "";
		}
		try{
			
			String[] arr = new String[]{cChannelCode};
			
			if(cChannelCode.indexOf(",")!=-1){
				arr = cChannelCode.split(",");
			}
			int arrlen = arr.length;
			for(int i=0;i<arrlen;i++){
				//得到子渠道内部编码
				String tCode =new QueryBuilder(" select InnerChannelCode from KXBChannelInfo where ChannelCode =?",arr[i]).executeString();
				if (StringUtil.isEmpty(tCode)) {
					continue;
				}
				String[] arrChild = Channel.dealChildList(tCode);
				//根据子渠道内部编码查询渠道编码
				int arrclen = arrChild.length;
				for(int j=0;j<arrclen;j++){
					if(!"$".equals(arrChild[j])){
						String channelCode = new QueryBuilder(" select ChannelCode from KXBChannelInfo where InnerChannelCode =?",arrChild[j]).executeString();
						//组装b2b渠道信息
						if("b2b".equals(cSource)){
							if(channelCode.startsWith("b2b")){
								tChannelInfo.append("'"+channelCode+"',");
							}
						}else{
							tChannelInfo.append("'"+channelCode+"',");
						}
					}
				}
				
			}
			if(tChannelInfo.indexOf(",")!=-1){
				tChannelInfo.deleteCharAt(tChannelInfo.length()-1);
			}
		}catch(Exception e){
			logger.error("销售统计，根据渠道内部编码查询渠道编码异常,InnerChannelCode:"+cChannelCode + e.getMessage(), e);
		}
		
		return tChannelInfo.toString();
	}
	
	/**
	 * 得到子渠道编码，用于创建活动
	 * @param innercode
	 * @return
	 */
	public static String getChildChannelList(String innercode){
		
		//StringBuffer tChannelInnerInfo = new StringBuffer("");
		//处理王然，渠道信息；针对该渠道下的所有子渠道增加活动信息
		StringBuffer tChannelInfo = new StringBuffer("");
		String[] arr = innercode.split(",");
		Map<String,String> channelMap = new HashMap<String,String>();
		for(int k=0;k<arr.length;k++){
			//tChannelInnerInfo.append("'"+arr[k]+"',");
			String innerChannelCode = new QueryBuilder(" select InnerChannelCode from ChannelInfo where ChannelCode in('"+arr[k]+"')").executeString();
			// 添加淘宝渠道 by 20151208 wangwenying
			//if (!"tb".equals(arr[k])) {
				if(StringUtil.isNotEmpty(innerChannelCode)){
					String[] arrChild = Channel.dealChildList(innerChannelCode);
					//根据子渠道内部编码查询渠道编码
					int arrclen = arrChild.length;
					for(int j=0;j<arrclen;j++){
						if(!"$".equals(arrChild[j])){
							String channelCode = new QueryBuilder(" select ChannelCode from ChannelInfo where InnerChannelCode =?",arrChild[j]).executeString();
							if(channelMap!=null && channelMap.size()>=1){
								String tFlag = channelMap.get(channelCode);
								if("Y".equals(tFlag)){
									continue;
								}
							}
							if (!"tbsd".equals(channelCode)) {
								tChannelInfo.append(""+channelCode+",");
								channelMap.put(channelCode, "Y");
							}
						}
					}
				}else{
					if(channelMap!=null && channelMap.size()>=1){
						String tFlag = channelMap.get(arr[k]);
						if("Y".equals(tFlag)){
							continue;
						}
					}
					tChannelInfo.append(""+arr[k]+",");
					channelMap.put(arr[k], "Y");
				}
			//} else {
			//	tChannelInfo.append(arr[k]+",");
			//}
			
		}
		//if(tChannelInfo.indexOf(",")!=-1){
		//	tChannelInfo.deleteCharAt(tChannelInfo.length()-1);
		//}
		
		return tChannelInfo.toString();
	}
}
