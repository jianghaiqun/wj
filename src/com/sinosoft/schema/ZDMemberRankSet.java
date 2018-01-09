package com.sinosoft.schema;

import com.sinosoft.schema.ZDMemberRankSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class ZDMemberRankSet extends SchemaSet {
	public ZDMemberRankSet() {
		this(10,0);
	}

	public ZDMemberRankSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZDMemberRankSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZDMemberRankSchema._TableCode;
		Columns = ZDMemberRankSchema._Columns;
		NameSpace = ZDMemberRankSchema._NameSpace;
		InsertAllSQL = ZDMemberRankSchema._InsertAllSQL;
		UpdateAllSQL = ZDMemberRankSchema._UpdateAllSQL;
		FillAllSQL = ZDMemberRankSchema._FillAllSQL;
		DeleteSQL = ZDMemberRankSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZDMemberRankSet();
	}

	public boolean add(ZDMemberRankSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZDMemberRankSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZDMemberRankSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZDMemberRankSchema get(int index) {
		ZDMemberRankSchema tSchema = (ZDMemberRankSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZDMemberRankSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZDMemberRankSet aSet) {
		return super.set(aSet);
	}
}
 