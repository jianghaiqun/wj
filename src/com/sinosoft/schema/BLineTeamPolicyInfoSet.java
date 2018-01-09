package com.sinosoft.schema;

import com.sinosoft.schema.BLineTeamPolicyInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class BLineTeamPolicyInfoSet extends SchemaSet {
	public BLineTeamPolicyInfoSet() {
		this(10,0);
	}

	public BLineTeamPolicyInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BLineTeamPolicyInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BLineTeamPolicyInfoSchema._TableCode;
		Columns = BLineTeamPolicyInfoSchema._Columns;
		NameSpace = BLineTeamPolicyInfoSchema._NameSpace;
		InsertAllSQL = BLineTeamPolicyInfoSchema._InsertAllSQL;
		UpdateAllSQL = BLineTeamPolicyInfoSchema._UpdateAllSQL;
		FillAllSQL = BLineTeamPolicyInfoSchema._FillAllSQL;
		DeleteSQL = BLineTeamPolicyInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BLineTeamPolicyInfoSet();
	}

	public boolean add(BLineTeamPolicyInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BLineTeamPolicyInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BLineTeamPolicyInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public BLineTeamPolicyInfoSchema get(int index) {
		BLineTeamPolicyInfoSchema tSchema = (BLineTeamPolicyInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BLineTeamPolicyInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BLineTeamPolicyInfoSet aSet) {
		return super.set(aSet);
	}
	
}
 