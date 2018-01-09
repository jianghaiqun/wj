package com.sinosoft.schema;

import com.sinosoft.schema.SDInformationInsuredSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDInformationInsuredSet extends SchemaSet {
	public SDInformationInsuredSet() {
		this(10,0); 
	}

	public SDInformationInsuredSet(int initialCapacity) { 
		this(initialCapacity,0);
	}

	public SDInformationInsuredSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDInformationInsuredSchema._TableCode;
		Columns = SDInformationInsuredSchema._Columns;
		NameSpace = SDInformationInsuredSchema._NameSpace;
		InsertAllSQL = SDInformationInsuredSchema._InsertAllSQL;
		UpdateAllSQL = SDInformationInsuredSchema._UpdateAllSQL;
		FillAllSQL = SDInformationInsuredSchema._FillAllSQL;
		DeleteSQL = SDInformationInsuredSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDInformationInsuredSet();
	}

	public boolean add(SDInformationInsuredSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDInformationInsuredSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDInformationInsuredSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDInformationInsuredSchema get(int index) {
		SDInformationInsuredSchema tSchema = (SDInformationInsuredSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDInformationInsuredSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDInformationInsuredSet aSet) {
		return super.set(aSet);
	}
}
 