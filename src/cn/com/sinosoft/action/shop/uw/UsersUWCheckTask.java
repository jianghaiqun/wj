package cn.com.sinosoft.action.shop.uw;

import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.SDInformationSchema;
import com.sinosoft.sms.messageinterface.pubfun.PubFun;

import java.util.Map;


public class UsersUWCheckTask implements Runnable{

	public static int insuredCount=0;
	private Map<String, String> map_in;
	private SDInformationSchema  mSDInformationSchema;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Transaction trans = new Transaction();
		Map<String, String> map = UsersUWCheck.UWCheck(mSDInformationSchema, map_in.get("InsuredSn"));
		map.put("OrderSn", mSDInformationSchema.getorderSn());
		map.put("InformationSn", mSDInformationSchema.getinformationSn());
		map.put("InsuredSn", map_in.get("InsuredSn"));
		map.put("RecognizeeName", map_in.get("RecognizeeName"));
		//list.add(map);
		String passFlag = map.get("passFlag");
		//记录核保结果
		//String uwDeleteSQL = " DELETE FROM UWCheckLog WHERE id= ?";
		//QueryBuilder qb = new QueryBuilder(uwDeleteSQL);
		//qb.add(mSDInformationSchema.getorderSn());
		//trans.add(qb);
		String uwInsertSQL = " INSERT INTO UWCheckLog VALUES(?,?,?,?,?,?,?,?,?,?)";
		QueryBuilder qb1 = new QueryBuilder(uwInsertSQL);
		qb1.add(map_in.get("InsuredSn"));
		qb1.add(PubFun.getCurrentDate()+" "+PubFun.getCurrentTime());
		qb1.add(PubFun.getCurrentDate()+" "+PubFun.getCurrentTime());
		qb1.add(mSDInformationSchema.getorderSn());
		qb1.add(mSDInformationSchema.getinformationSn());
		qb1.add(map_in.get("InsuredSn"));
		qb1.add(map_in.get("RecognizeeName"));
		qb1.add(map.get("rtnMessage"));
		qb1.add(map.get("passFlag"));
		qb1.add("uw");
		trans.add(qb1);
		//被保人表更新uwcheckflag字段
		if (StringUtil.isNotEmpty(mSDInformationSchema.getorderSn())) {
			String updateSDInformationInsured = "UPDATE sdinformationinsured SET uwcheckflag = ?  "
					+ "WHERE ordersn = '" + mSDInformationSchema.getorderSn() + "' ";
			QueryBuilder qb2 = new QueryBuilder(updateSDInformationInsured);
			if ("0".equals(map.get("passFlag"))) {
				qb2.add("N");
			} else {
				qb2.add("Y");
			}
			trans.add(qb2);
		}
		trans.commit();
	}

	public static int getInsuredCount() {
		return insuredCount;
	}

	public static void setInsuredCount(int insuredCount) {
		UsersUWCheckTask.insuredCount = insuredCount;
	}

	public Map<String, String> getMap_in() {
		return map_in;
	}

	public void setMap_in(Map<String, String> map_in) {
		this.map_in = map_in;
	}

	public SDInformationSchema getmSDInformationSchema() {
		return mSDInformationSchema;
	}

	public void setmSDInformationSchema(SDInformationSchema mSDInformationSchema) {
		this.mSDInformationSchema = mSDInformationSchema;
	}
	 
}
