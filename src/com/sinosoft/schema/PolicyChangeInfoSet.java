package com.sinosoft.schema;

import com.sinosoft.schema.PolicyChangeInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class PolicyChangeInfoSet extends SchemaSet {
	public PolicyChangeInfoSet() {
		this(10,0);
	}

	public PolicyChangeInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public PolicyChangeInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = PolicyChangeInfoSchema._TableCode;
		Columns = PolicyChangeInfoSchema._Columns;
		NameSpace = PolicyChangeInfoSchema._NameSpace;
		InsertAllSQL = PolicyChangeInfoSchema._InsertAllSQL;
		UpdateAllSQL = PolicyChangeInfoSchema._UpdateAllSQL;
		FillAllSQL = PolicyChangeInfoSchema._FillAllSQL;
		DeleteSQL = PolicyChangeInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new PolicyChangeInfoSet();
	}

	public boolean add(PolicyChangeInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(PolicyChangeInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(PolicyChangeInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public PolicyChangeInfoSchema get(int index) {
		PolicyChangeInfoSchema tSchema = (PolicyChangeInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, PolicyChangeInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(PolicyChangeInfoSet aSet) {
		return super.set(aSet);
	}
}
 