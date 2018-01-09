package com.sinosoft.schema;

import com.sinosoft.schema.ZCLinkTeamSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class ZCLinkTeamSet extends SchemaSet {
	public ZCLinkTeamSet() {
		this(10,0);
	}

	public ZCLinkTeamSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCLinkTeamSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCLinkTeamSchema._TableCode;
		Columns = ZCLinkTeamSchema._Columns;
		NameSpace = ZCLinkTeamSchema._NameSpace;
		InsertAllSQL = ZCLinkTeamSchema._InsertAllSQL;
		UpdateAllSQL = ZCLinkTeamSchema._UpdateAllSQL;
		FillAllSQL = ZCLinkTeamSchema._FillAllSQL;
		DeleteSQL = ZCLinkTeamSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCLinkTeamSet();
	}

	public boolean add(ZCLinkTeamSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCLinkTeamSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCLinkTeamSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCLinkTeamSchema get(int index) {
		ZCLinkTeamSchema tSchema = (ZCLinkTeamSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCLinkTeamSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCLinkTeamSet aSet) {
		return super.set(aSet);
	}
}
 