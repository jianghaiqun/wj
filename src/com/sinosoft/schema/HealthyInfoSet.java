package com.sinosoft.schema;

import com.sinosoft.schema.HealthyInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class HealthyInfoSet extends SchemaSet {
	public HealthyInfoSet() {
		this(10,0);
	}

	public HealthyInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public HealthyInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = HealthyInfoSchema._TableCode;
		Columns = HealthyInfoSchema._Columns;
		NameSpace = HealthyInfoSchema._NameSpace;
		InsertAllSQL = HealthyInfoSchema._InsertAllSQL;
		UpdateAllSQL = HealthyInfoSchema._UpdateAllSQL;
		FillAllSQL = HealthyInfoSchema._FillAllSQL;
		DeleteSQL = HealthyInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new HealthyInfoSet();
	}

	public boolean add(HealthyInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(HealthyInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(HealthyInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public HealthyInfoSchema get(int index) {
		HealthyInfoSchema tSchema = (HealthyInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, HealthyInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(HealthyInfoSet aSet) {
		return super.set(aSet);
	}
}
 