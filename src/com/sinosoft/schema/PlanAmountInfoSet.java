package com.sinosoft.schema;

import com.sinosoft.schema.PlanAmountInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class PlanAmountInfoSet extends SchemaSet {
	public PlanAmountInfoSet() {
		this(10,0);
	}

	public PlanAmountInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public PlanAmountInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = PlanAmountInfoSchema._TableCode;
		Columns = PlanAmountInfoSchema._Columns;
		NameSpace = PlanAmountInfoSchema._NameSpace;
		InsertAllSQL = PlanAmountInfoSchema._InsertAllSQL;
		UpdateAllSQL = PlanAmountInfoSchema._UpdateAllSQL;
		FillAllSQL = PlanAmountInfoSchema._FillAllSQL;
		DeleteSQL = PlanAmountInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new PlanAmountInfoSet();
	}

	public boolean add(PlanAmountInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(PlanAmountInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(PlanAmountInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public PlanAmountInfoSchema get(int index) {
		PlanAmountInfoSchema tSchema = (PlanAmountInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, PlanAmountInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(PlanAmountInfoSet aSet) {
		return super.set(aSet);
	}
}
 