package com.sinosoft.schema;

import com.sinosoft.schema.SDInformationRiskTypeSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDInformationRiskTypeSet extends SchemaSet {
	public SDInformationRiskTypeSet() {
		this(10,0);
	}

	public SDInformationRiskTypeSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDInformationRiskTypeSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDInformationRiskTypeSchema._TableCode;
		Columns = SDInformationRiskTypeSchema._Columns;
		NameSpace = SDInformationRiskTypeSchema._NameSpace;
		InsertAllSQL = SDInformationRiskTypeSchema._InsertAllSQL;
		UpdateAllSQL = SDInformationRiskTypeSchema._UpdateAllSQL;
		FillAllSQL = SDInformationRiskTypeSchema._FillAllSQL;
		DeleteSQL = SDInformationRiskTypeSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDInformationRiskTypeSet();
	}

	public boolean add(SDInformationRiskTypeSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDInformationRiskTypeSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDInformationRiskTypeSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDInformationRiskTypeSchema get(int index) {
		SDInformationRiskTypeSchema tSchema = (SDInformationRiskTypeSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDInformationRiskTypeSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDInformationRiskTypeSet aSet) {
		return super.set(aSet);
	}
	
	
	
}
 