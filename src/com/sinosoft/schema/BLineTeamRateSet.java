package com.sinosoft.schema;

import com.sinosoft.schema.BLineTeamRateSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class BLineTeamRateSet extends SchemaSet {
	public BLineTeamRateSet() {
		this(10,0);
	}

	public BLineTeamRateSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BLineTeamRateSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BLineTeamRateSchema._TableCode;
		Columns = BLineTeamRateSchema._Columns;
		NameSpace = BLineTeamRateSchema._NameSpace;
		InsertAllSQL = BLineTeamRateSchema._InsertAllSQL;
		UpdateAllSQL = BLineTeamRateSchema._UpdateAllSQL;
		FillAllSQL = BLineTeamRateSchema._FillAllSQL;
		DeleteSQL = BLineTeamRateSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BLineTeamRateSet();
	}

	public boolean add(BLineTeamRateSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BLineTeamRateSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BLineTeamRateSchema aSchema) {
		return super.remove(aSchema);
	}

	public BLineTeamRateSchema get(int index) {
		BLineTeamRateSchema tSchema = (BLineTeamRateSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BLineTeamRateSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BLineTeamRateSet aSet) {
		return super.set(aSet);
	}
}
 