package com.sinosoft.schema;

import com.sinosoft.schema.SDInformationAppntSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDInformationAppntSet extends SchemaSet {
	public SDInformationAppntSet() {
		this(10,0);
	}

	public SDInformationAppntSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDInformationAppntSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDInformationAppntSchema._TableCode;
		Columns = SDInformationAppntSchema._Columns;
		NameSpace = SDInformationAppntSchema._NameSpace;
		InsertAllSQL = SDInformationAppntSchema._InsertAllSQL;
		UpdateAllSQL = SDInformationAppntSchema._UpdateAllSQL;
		FillAllSQL = SDInformationAppntSchema._FillAllSQL;
		DeleteSQL = SDInformationAppntSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDInformationAppntSet();
	}

	public boolean add(SDInformationAppntSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDInformationAppntSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDInformationAppntSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDInformationAppntSchema get(int index) {
		SDInformationAppntSchema tSchema = (SDInformationAppntSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDInformationAppntSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDInformationAppntSet aSet) {
		return super.set(aSet);
	}
}
 