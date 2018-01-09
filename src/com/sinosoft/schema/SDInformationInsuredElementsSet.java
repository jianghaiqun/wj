package com.sinosoft.schema;

import com.sinosoft.schema.SDInformationInsuredElementsSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDInformationInsuredElementsSet extends SchemaSet {
	public SDInformationInsuredElementsSet() {
		this(10,0);
	}

	public SDInformationInsuredElementsSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDInformationInsuredElementsSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDInformationInsuredElementsSchema._TableCode;
		Columns = SDInformationInsuredElementsSchema._Columns;
		NameSpace = SDInformationInsuredElementsSchema._NameSpace;
		InsertAllSQL = SDInformationInsuredElementsSchema._InsertAllSQL;
		UpdateAllSQL = SDInformationInsuredElementsSchema._UpdateAllSQL;
		FillAllSQL = SDInformationInsuredElementsSchema._FillAllSQL;
		DeleteSQL = SDInformationInsuredElementsSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDInformationInsuredElementsSet();
	}

	public boolean add(SDInformationInsuredElementsSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDInformationInsuredElementsSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDInformationInsuredElementsSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDInformationInsuredElementsSchema get(int index) {
		SDInformationInsuredElementsSchema tSchema = (SDInformationInsuredElementsSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDInformationInsuredElementsSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDInformationInsuredElementsSet aSet) {
		return super.set(aSet);
	}
}
 