package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;
import java.util.Date;

/**
 * 琛ㄥ悕绉帮細浠ｇ爜琛�br> 琛ㄤ唬鐮侊細ZDCode<br>
 * 琛ㄤ富閿細CodeType, ParentCode, CodeValue<br>
 */
public class ZDCodeSchema extends Schema {
	private String CodeType;

	private String ParentCode;

	private String CodeValue;

	private String CodeName;

	private Long CodeOrder;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

	private String Memo;

	private Date AddTime;

	private String AddUser;

	private Date ModifyTime;

	private String ModifyUser;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] { new SchemaColumn("CodeType", DataColumn.STRING, 0, 40, 0, true, true),
			new SchemaColumn("ParentCode", DataColumn.STRING, 1, 40, 0, true, true), new SchemaColumn("CodeValue", DataColumn.STRING, 2, 40, 0, true, true),
			new SchemaColumn("CodeName", DataColumn.STRING, 3, 100, 0, true, false), new SchemaColumn("CodeOrder", DataColumn.LONG, 4, 0, 0, true, false),
			new SchemaColumn("Prop1", DataColumn.STRING, 5, 40, 0, false, false), new SchemaColumn("Prop2", DataColumn.STRING, 6, 40, 0, false, false),
			new SchemaColumn("Prop3", DataColumn.STRING, 7, 40, 0, false, false), new SchemaColumn("Prop4", DataColumn.STRING, 8, 40, 0, false, false),
			new SchemaColumn("Memo", DataColumn.STRING, 9, 400, 0, false, false), new SchemaColumn("AddTime", DataColumn.DATETIME, 10, 0, 0, true, false),
			new SchemaColumn("AddUser", DataColumn.STRING, 11, 200, 0, true, false), new SchemaColumn("ModifyTime", DataColumn.DATETIME, 12, 0, 0, false, false),
			new SchemaColumn("ModifyUser", DataColumn.STRING, 13, 200, 0, false, false) };

	public static final String _TableCode = "ZDCode";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into ZDCode values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZDCode set CodeType=?,ParentCode=?,CodeValue=?,CodeName=?,CodeOrder=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,Memo=?,AddTime=?,AddUser=?,ModifyTime=?,ModifyUser=? where CodeType=? and ParentCode=? and CodeValue=?";

	protected static final String _DeleteSQL = "delete from ZDCode  where CodeType=? and ParentCode=? and CodeValue=?";

	protected static final String _FillAllSQL = "select * from ZDCode  where CodeType=? and ParentCode=? and CodeValue=?";

	public ZDCodeSchema() {
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[14];
	}

	protected Schema newInstance() {
		return new ZDCodeSchema();
	}

	protected SchemaSet newSet() {
		return new ZDCodeSet();
	}

	public ZDCodeSet query() {
		return query(null, -1, -1);
	}

	public ZDCodeSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZDCodeSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZDCodeSet query(QueryBuilder qb, int pageSize, int pageIndex) {
		return (ZDCodeSet) querySet(qb, pageSize, pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0) {
			CodeType = (String) v;
			return;
		}
		if (i == 1) {
			ParentCode = (String) v;
			return;
		}
		if (i == 2) {
			CodeValue = (String) v;
			return;
		}
		if (i == 3) {
			CodeName = (String) v;
			return;
		}
		if (i == 4) {
			if (v == null) {
				CodeOrder = null;
			} else {
				CodeOrder = new Long(v.toString());
			}
			return;
		}
		if (i == 5) {
			Prop1 = (String) v;
			return;
		}
		if (i == 6) {
			Prop2 = (String) v;
			return;
		}
		if (i == 7) {
			Prop3 = (String) v;
			return;
		}
		if (i == 8) {
			Prop4 = (String) v;
			return;
		}
		if (i == 9) {
			Memo = (String) v;
			return;
		}
		if (i == 10) {
			AddTime = (Date) v;
			return;
		}
		if (i == 11) {
			AddUser = (String) v;
			return;
		}
		if (i == 12) {
			ModifyTime = (Date) v;
			return;
		}
		if (i == 13) {
			ModifyUser = (String) v;
			return;
		}
	}

	public Object getV(int i) {
		if (i == 0) {
			return CodeType;
		}
		if (i == 1) {
			return ParentCode;
		}
		if (i == 2) {
			return CodeValue;
		}
		if (i == 3) {
			return CodeName;
		}
		if (i == 4) {
			return CodeOrder;
		}
		if (i == 5) {
			return Prop1;
		}
		if (i == 6) {
			return Prop2;
		}
		if (i == 7) {
			return Prop3;
		}
		if (i == 8) {
			return Prop4;
		}
		if (i == 9) {
			return Memo;
		}
		if (i == 10) {
			return AddTime;
		}
		if (i == 11) {
			return AddUser;
		}
		if (i == 12) {
			return ModifyTime;
		}
		if (i == 13) {
			return ModifyUser;
		}
		return null;
	}

	/**
	 * 鑾峰彇瀛楁CodeType鐨勫�锛岃瀛楁鐨�br> 瀛楁鍚嶇О :浠ｇ爜绫诲埆<br>
	 * 鏁版嵁绫诲瀷 :varchar(40)<br>
	 * 鏄惁涓婚敭 :true<br>
	 * 鏄惁蹇呭～ :true<br>
	 */
	public String getCodeType() {
		return CodeType;
	}

	/**
	 * 璁剧疆瀛楁CodeType鐨勫�锛岃瀛楁鐨�br> 瀛楁鍚嶇О :浠ｇ爜绫诲埆<br>
	 * 鏁版嵁绫诲瀷 :varchar(40)<br>
	 * 鏄惁涓婚敭 :true<br>
	 * 鏄惁蹇呭～ :true<br>
	 */
	public void setCodeType(String codeType) {
		this.CodeType = codeType;
	}

	/**
	 * 鑾峰彇瀛楁ParentCode鐨勫�锛岃瀛楁鐨�br> 瀛楁鍚嶇О :浠ｇ爜鐖剁被<br>
	 * 鏁版嵁绫诲瀷 :varchar(40)<br>
	 * 鏄惁涓婚敭 :true<br>
	 * 鏄惁蹇呭～ :true<br>
	 */
	public String getParentCode() {
		return ParentCode;
	}

	/**
	 * 璁剧疆瀛楁ParentCode鐨勫�锛岃瀛楁鐨�br> 瀛楁鍚嶇О :浠ｇ爜鐖剁被<br>
	 * 鏁版嵁绫诲瀷 :varchar(40)<br>
	 * 鏄惁涓婚敭 :true<br>
	 * 鏄惁蹇呭～ :true<br>
	 */
	public void setParentCode(String parentCode) {
		this.ParentCode = parentCode;
	}

	/**
	 * 鑾峰彇瀛楁CodeValue鐨勫�锛岃瀛楁鐨�br> 瀛楁鍚嶇О :浠ｇ爜鍊�br> 鏁版嵁绫诲瀷 :varchar(40)<br>
	 * 鏄惁涓婚敭 :true<br>
	 * 鏄惁蹇呭～ :true<br>
	 */
	public String getCodeValue() {
		return CodeValue;
	}

	/**
	 * 璁剧疆瀛楁CodeValue鐨勫�锛岃瀛楁鐨�br> 瀛楁鍚嶇О :浠ｇ爜鍊�br> 鏁版嵁绫诲瀷 :varchar(40)<br>
	 * 鏄惁涓婚敭 :true<br>
	 * 鏄惁蹇呭～ :true<br>
	 */
	public void setCodeValue(String codeValue) {
		this.CodeValue = codeValue;
	}

	/**
	 * 鑾峰彇瀛楁CodeName鐨勫�锛岃瀛楁鐨�br> 瀛楁鍚嶇О :浠ｇ爜鍚嶇О<br>
	 * 鏁版嵁绫诲瀷 :varchar(100)<br>
	 * 鏄惁涓婚敭 :false<br>
	 * 鏄惁蹇呭～ :true<br>
	 */
	public String getCodeName() {
		return CodeName;
	}

	/**
	 * 璁剧疆瀛楁CodeName鐨勫�锛岃瀛楁鐨�br> 瀛楁鍚嶇О :浠ｇ爜鍚嶇О<br>
	 * 鏁版嵁绫诲瀷 :varchar(100)<br>
	 * 鏄惁涓婚敭 :false<br>
	 * 鏄惁蹇呭～ :true<br>
	 */
	public void setCodeName(String codeName) {
		this.CodeName = codeName;
	}

	/**
	 * 鑾峰彇瀛楁CodeOrder鐨勫�锛岃瀛楁鐨�br> 瀛楁鍚嶇О :浠ｇ爜椤哄簭<br>
	 * 鏁版嵁绫诲瀷 :bigint<br>
	 * 鏄惁涓婚敭 :false<br>
	 * 鏄惁蹇呭～ :true<br>
	 */
	public long getCodeOrder() {
		if (CodeOrder == null) {
			return 0;
		}
		return CodeOrder.longValue();
	}

	/**
	 * 璁剧疆瀛楁CodeOrder鐨勫�锛岃瀛楁鐨�br> 瀛楁鍚嶇О :浠ｇ爜椤哄簭<br>
	 * 鏁版嵁绫诲瀷 :bigint<br>
	 * 鏄惁涓婚敭 :false<br>
	 * 鏄惁蹇呭～ :true<br>
	 */
	public void setCodeOrder(long codeOrder) {
		this.CodeOrder = new Long(codeOrder);
	}

	/**
	 * 璁剧疆瀛楁CodeOrder鐨勫�锛岃瀛楁鐨�br> 瀛楁鍚嶇О :浠ｇ爜椤哄簭<br>
	 * 鏁版嵁绫诲瀷 :bigint<br>
	 * 鏄惁涓婚敭 :false<br>
	 * 鏄惁蹇呭～ :true<br>
	 */
	public void setCodeOrder(String codeOrder) {
		if (codeOrder == null) {
			this.CodeOrder = null;
			return;
		}
		this.CodeOrder = new Long(codeOrder);
	}

	/**
	 * 鑾峰彇瀛楁Prop1鐨勫�锛岃瀛楁鐨�br> 瀛楁鍚嶇О :澶囩敤灞炴�1<br>
	 * 鏁版嵁绫诲瀷 :varchar(40)<br>
	 * 鏄惁涓婚敭 :false<br>
	 * 鏄惁蹇呭～ :false<br>
	 */
	public String getProp1() {
		return Prop1;
	}

	/**
	 * 璁剧疆瀛楁Prop1鐨勫�锛岃瀛楁鐨�br> 瀛楁鍚嶇О :澶囩敤灞炴�1<br>
	 * 鏁版嵁绫诲瀷 :varchar(40)<br>
	 * 鏄惁涓婚敭 :false<br>
	 * 鏄惁蹇呭～ :false<br>
	 */
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
	}

	/**
	 * 鑾峰彇瀛楁Prop2鐨勫�锛岃瀛楁鐨�br> 瀛楁鍚嶇О :澶囩敤灞炴�2<br>
	 * 鏁版嵁绫诲瀷 :varchar(40)<br>
	 * 鏄惁涓婚敭 :false<br>
	 * 鏄惁蹇呭～ :false<br>
	 */
	public String getProp2() {
		return Prop2;
	}

	/**
	 * 璁剧疆瀛楁Prop2鐨勫�锛岃瀛楁鐨�br> 瀛楁鍚嶇О :澶囩敤灞炴�2<br>
	 * 鏁版嵁绫诲瀷 :varchar(40)<br>
	 * 鏄惁涓婚敭 :false<br>
	 * 鏄惁蹇呭～ :false<br>
	 */
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
	}

	/**
	 * 鑾峰彇瀛楁Prop3鐨勫�锛岃瀛楁鐨�br> 瀛楁鍚嶇О :澶囩敤灞炴�3<br>
	 * 鏁版嵁绫诲瀷 :varchar(40)<br>
	 * 鏄惁涓婚敭 :false<br>
	 * 鏄惁蹇呭～ :false<br>
	 */
	public String getProp3() {
		return Prop3;
	}

	/**
	 * 璁剧疆瀛楁Prop3鐨勫�锛岃瀛楁鐨�br> 瀛楁鍚嶇О :澶囩敤灞炴�3<br>
	 * 鏁版嵁绫诲瀷 :varchar(40)<br>
	 * 鏄惁涓婚敭 :false<br>
	 * 鏄惁蹇呭～ :false<br>
	 */
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
	}

	/**
	 * 鑾峰彇瀛楁Prop4鐨勫�锛岃瀛楁鐨�br> 瀛楁鍚嶇О :澶囩敤灞炴�4<br>
	 * 鏁版嵁绫诲瀷 :varchar(40)<br>
	 * 鏄惁涓婚敭 :false<br>
	 * 鏄惁蹇呭～ :false<br>
	 * 澶囨敞淇℃伅 :<br>
	 * 'S' 鐗规畩浠ｇ爜澶勭悊<br>
	 */
	public String getProp4() {
		return Prop4;
	}

	/**
	 * 璁剧疆瀛楁Prop4鐨勫�锛岃瀛楁鐨�br> 瀛楁鍚嶇О :澶囩敤灞炴�4<br>
	 * 鏁版嵁绫诲瀷 :varchar(40)<br>
	 * 鏄惁涓婚敭 :false<br>
	 * 鏄惁蹇呭～ :false<br>
	 * 澶囨敞淇℃伅 :<br>
	 * 'S' 鐗规畩浠ｇ爜澶勭悊<br>
	 */
	public void setProp4(String prop4) {
		this.Prop4 = prop4;
	}

	/**
	 * 鑾峰彇瀛楁Memo鐨勫�锛岃瀛楁鐨�br> 瀛楁鍚嶇О :澶囨敞<br>
	 * 鏁版嵁绫诲瀷 :varchar(400)<br>
	 * 鏄惁涓婚敭 :false<br>
	 * 鏄惁蹇呭～ :false<br>
	 */
	public String getMemo() {
		return Memo;
	}

	/**
	 * 璁剧疆瀛楁Memo鐨勫�锛岃瀛楁鐨�br> 瀛楁鍚嶇О :澶囨敞<br>
	 * 鏁版嵁绫诲瀷 :varchar(400)<br>
	 * 鏄惁涓婚敭 :false<br>
	 * 鏄惁蹇呭～ :false<br>
	 */
	public void setMemo(String memo) {
		this.Memo = memo;
	}

	/**
	 * 鑾峰彇瀛楁AddTime鐨勫�锛岃瀛楁鐨�br> 瀛楁鍚嶇О :澧炲姞鏃堕棿<br>
	 * 鏁版嵁绫诲瀷 :datetime<br>
	 * 鏄惁涓婚敭 :false<br>
	 * 鏄惁蹇呭～ :true<br>
	 */
	public Date getAddTime() {
		return AddTime;
	}

	/**
	 * 璁剧疆瀛楁AddTime鐨勫�锛岃瀛楁鐨�br> 瀛楁鍚嶇О :澧炲姞鏃堕棿<br>
	 * 鏁版嵁绫诲瀷 :datetime<br>
	 * 鏄惁涓婚敭 :false<br>
	 * 鏄惁蹇呭～ :true<br>
	 */
	public void setAddTime(Date addTime) {
		this.AddTime = addTime;
	}

	/**
	 * 鑾峰彇瀛楁AddUser鐨勫�锛岃瀛楁鐨�br> 瀛楁鍚嶇О :澧炲姞浜�br> 鏁版嵁绫诲瀷 :varchar(200)<br>
	 * 鏄惁涓婚敭 :false<br>
	 * 鏄惁蹇呭～ :true<br>
	 */
	public String getAddUser() {
		return AddUser;
	}

	/**
	 * 璁剧疆瀛楁AddUser鐨勫�锛岃瀛楁鐨�br> 瀛楁鍚嶇О :澧炲姞浜�br> 鏁版嵁绫诲瀷 :varchar(200)<br>
	 * 鏄惁涓婚敭 :false<br>
	 * 鏄惁蹇呭～ :true<br>
	 */
	public void setAddUser(String addUser) {
		this.AddUser = addUser;
	}

	/**
	 * 鑾峰彇瀛楁ModifyTime鐨勫�锛岃瀛楁鐨�br> 瀛楁鍚嶇О :淇敼鏃堕棿<br>
	 * 鏁版嵁绫诲瀷 :datetime<br>
	 * 鏄惁涓婚敭 :false<br>
	 * 鏄惁蹇呭～ :false<br>
	 */
	public Date getModifyTime() {
		return ModifyTime;
	}

	/**
	 * 璁剧疆瀛楁ModifyTime鐨勫�锛岃瀛楁鐨�br> 瀛楁鍚嶇О :淇敼鏃堕棿<br>
	 * 鏁版嵁绫诲瀷 :datetime<br>
	 * 鏄惁涓婚敭 :false<br>
	 * 鏄惁蹇呭～ :false<br>
	 */
	public void setModifyTime(Date modifyTime) {
		this.ModifyTime = modifyTime;
	}

	/**
	 * 鑾峰彇瀛楁ModifyUser鐨勫�锛岃瀛楁鐨�br> 瀛楁鍚嶇О :淇敼浜�br> 鏁版嵁绫诲瀷 :varchar(200)<br>
	 * 鏄惁涓婚敭 :false<br>
	 * 鏄惁蹇呭～ :false<br>
	 */
	public String getModifyUser() {
		return ModifyUser;
	}

	/**
	 * 璁剧疆瀛楁ModifyUser鐨勫�锛岃瀛楁鐨�br> 瀛楁鍚嶇О :淇敼浜�br> 鏁版嵁绫诲瀷 :varchar(200)<br>
	 * 鏄惁涓婚敭 :false<br>
	 * 鏄惁蹇呭～ :false<br>
	 */
	public void setModifyUser(String modifyUser) {
		this.ModifyUser = modifyUser;
	}

}