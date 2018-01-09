package com.sinosoft.schema;

import com.sinosoft.schema.InformationInsuredSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class InformationInsuredSet extends SchemaSet {
	public InformationInsuredSet() {
		this(10,0);
	}

	public InformationInsuredSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public InformationInsuredSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = InformationInsuredSchema._TableCode;
		Columns = InformationInsuredSchema._Columns;
		NameSpace = InformationInsuredSchema._NameSpace;
		InsertAllSQL = InformationInsuredSchema._InsertAllSQL;
		UpdateAllSQL = InformationInsuredSchema._UpdateAllSQL;
		FillAllSQL = InformationInsuredSchema._FillAllSQL;
		DeleteSQL = InformationInsuredSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new InformationInsuredSet();
	}

	public boolean add(InformationInsuredSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(InformationInsuredSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(InformationInsuredSchema aSchema) {
		return super.remove(aSchema);
	}

	public InformationInsuredSchema get(int index) {
		InformationInsuredSchema tSchema = (InformationInsuredSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, InformationInsuredSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(InformationInsuredSet aSet) {
		return super.set(aSet);
	}
}
 