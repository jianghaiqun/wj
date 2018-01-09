package com.baifendian;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.Mapx;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 批量删除商品信息的样例程序。
 * 
 * @description 百分点科技提供了离线数据接口，使得用户可以离线传递订单数据给百分点。使用此接口的流程如下： 
 * 	1. 登录。调用https://opends.api.baifendian.com/2.0/Login.do，需要POST的数据是"cid=客户ID&pwd=密码"，验证成功后会返回如下格式的数据： 
 *         [0, "OK","80d5b45d5523bfa2858c3edbebd6d95350da9c6b"]
 *     其中的"80d5b45d5523bfa2858c3edbebd6d95350da9c6b"是一个SessionKey，后续操作都要带着这个Key。
 *  2. 提交订单信息。调用https://opends.api.baifendian.com/2.0/RmItem.do?cid=客户ID，需要POST的数据是"ssk=登录时得到的SessionKey&data=订单数据"。 提交成功会返回
 *         [0,"OK"]，
 *     否则会返回相应的出错信息。
 * 
 *  服务的正式环境是https://opends.api.baifendian.com/2.0，另外我们还提供了一个测试环境https://opendstest.api.baifendian.com/2.0供客户调试程序。
 *   
 *  特别注意：
 *      Java中请求HTTPS服务有一些繁琐，需要用keytool加入SSL密钥，本样例中的bfd.cacerts是我们用keytool生成的密钥，如无必要，请直接使用这份文件。
 *  
 *  
 * @author Yijing.liu
 * @date 2012-12-26
 * @contact peng.zhang@baifendian.co
 * 
 **/

public class UploadRmItem {
	private static final Logger logger = LoggerFactory.getLogger(UploadRmItem.class);
	static boolean DEBUG = true; // 是否链接测试服务器
	static String SERVER = "https://opends.api.baifendian.com/2.0/"; // 正式服务器
	//static String TEST_SERVER = "https://opendstest.api.baifendian.com/2.0/"; // 测试服务器

	/**
	 * 构造批量删除商品数据
	 * 1. 批量数据是一个JSON数组，其中每一个元素代表一个商品信息。
	 * 2. 商品信息中必须的数据项有:
	 *       iid, 商品ID, 字符串类型；
	 * 3. 商品信息中可选的数据项有：
	 *       del, 是否彻底删除, 布尔类型；
	 *       
	 * @return
	 * @throws JSONException 
	 */
	public static String construct_order_data(String productId) throws JSONException {
		// 订单数据是一个JSON数组，其中每一个元素代表一个订单，这个例子里我们加入两个订单到数组中
		JSONArray item_info_list = new JSONArray();
		
		// 第一个商品，必须有iid（商品ID）
		JSONObject fst_item = new JSONObject();
		fst_item.put("iid", productId); // 商品ID
		item_info_list.put(fst_item); // 加入第一个商品
		
		// 第一二商品
		//JSONObject snd_item = new JSONObject();
		//snd_item.put("iid", "第二个商品ID"); // 商品ID
		//item_info_list.put(snd_item); // 加入第二个商品
		
		// 返回JSON字符串
		return item_info_list.toString();
	}

	public static void downProduct(String productId) throws Exception {
		// 初始化HTTPS环境，将"opends.api.baifendian.com"加入到JRE信任库中，只需要初始化一次
        // 整数存放路径可以任意指定，但必须存在
        // keystore密码需要指定JRE默认keystore的密码，默认是changeit
        HttpsPoster.initHttpsURLConnection(Config.getContextRealPath() + "/WEB-INF/classes", "opends.api.baifendian.com",
                443, "changeit");

		QueryBuilder qb = new QueryBuilder(" SELECT CodeValue,CodeName FROM zdcode WHERE parentcode = 'BFD_Config' ");
		DataTable dt = qb.executeDataTable();
		Mapx mapx = dt.toMapx(0, 1);
		String server = null;
		//if ("false".equals(mapx.get("isProduction"))) {
		//	server = TEST_SERVER;
		//} else {
		server = SERVER;
		//}
		String cid = (String) mapx.get("clientId"); // 客户ID，"Ctest_111"是开放的测试ID，用于客户调试程序，只在测试环境下可用
		String pwd = (String) mapx.get("password"); // 客户密码，默认由百分点提供，客户可以要求修改
		// 登录，获取Session Key
		String login_res_str = HttpsPoster.post(server+"Login.do", "cid=" + cid + "&pwd=" + pwd);
		// res 是一个JSON数据，在这个例子里面我们简化处理，不解析JSON
		JSONArray login_res = new JSONArray(login_res_str);
		if (login_res.getInt(0) == 0) { // 登录成功
			String ssk = login_res.getString(2); // 得到Session Key
			
			String order_data = construct_order_data(productId);
			//System.out.print(order_data);
			String commit_res_str = HttpsPoster.post(server+"RmItem.do?cid="+cid, "ssk=" + ssk + "&data=" + order_data);
			
			JSONArray commit_res = new JSONArray(commit_res_str);
			
			if (commit_res.getInt(0) == 0) { // 上传成功
				logger.info("百分点下架上传成功");
			}
			else { // 上传失败
				logger.error("百分点下架上传失败，结果为: {}", commit_res_str);
			}
		}
		else { // 登录失败
			logger.error("百分点下架登录失败");
		}
	}
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// 初始化HTTPS环境，将"opends.api.baifendian.com"加入到JRE信任库中，只需要初始化一次
        // 整数存放路径可以任意指定，但必须存在
        // keystore密码需要指定JRE默认keystore的密码，默认是changeit
        HttpsPoster.initHttpsURLConnection(Config.getContextRealPath() + "/WEB-INF/classes", "opends.api.baifendian.com",
                443, "changeit");

        QueryBuilder qb = new QueryBuilder(" SELECT CodeValue,CodeName FROM zdcode WHERE parentcode = 'BFD_Config' ");
		DataTable dt = qb.executeDataTable();
		Mapx mapx = dt.toMapx(0, 1);
		String server = null;
		//if ("true".equals(mapx.get("isProduction"))) {
		//String server = null;
		//if (DEBUG) {
		//	server = TEST_SERVER;
		//} else {
		server = SERVER;
		//}
		//String cid = "Ctest_kaixinbao"; // 客户ID，"Ctest_111"是开放的测试ID，用于客户调试程序，只在测试环境下可用
		//String pwd = "mI2c$MxEN1~U0d7LB8pTvP#tubRgsS>ZYaAzeHqX"; // 客户密码，默认由百分点提供，客户可以要求修改
		String cid = (String) mapx.get("clientId"); // 客户ID，"Ctest_111"是开放的测试ID，用于客户调试程序，只在测试环境下可用
		String pwd = (String) mapx.get("password"); // 客户密码，默认由百分点提供，客户可以要求修改
		// 登录，获取Session Key
		String login_res_str = HttpsPoster.post(server+"Login.do", "cid=" + cid + "&pwd=" + pwd);
		// res 是一个JSON数据，在这个例子里面我们简化处理，不解析JSON
		JSONArray login_res = new JSONArray(login_res_str);
		if (login_res.getInt(0) == 0) { // 登录成功
			String ssk = login_res.getString(2); // 得到Session Key
			
			String order_data = construct_order_data("101401014");
			//System.out.print(order_data);
			String commit_res_str = HttpsPoster.post(server+"RmItem.do?cid="+cid, "ssk=" + ssk + "&data=" + order_data);
			
			JSONArray commit_res = new JSONArray(commit_res_str);
			
			if (commit_res.getInt(0) == 0) { // 上传成功
//				System.out.println("上传成功");
			}
			else { // 上传失败
//				System.out.println("上传失败，结果为: " + commit_res_str);
			}
		}
		else { // 登录失败
//			System.out.println("登录失败");
		}
	}

}
