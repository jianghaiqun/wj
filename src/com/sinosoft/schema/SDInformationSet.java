package com.sinosoft.schema;

import com.sinosoft.schema.SDInformationSchema; 
import com.sinosoft.framework.orm.SchemaSet;

public class SDInformationSet extends SchemaSet { 
	public SDInformationSet() { 
		this(10,0);
	}

	public SDInformationSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDInformationSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDInformationSchema._TableCode;
		Columns = SDInformationSchema._Columns;
		NameSpace = SDInformationSchema._NameSpace;
		InsertAllSQL = SDInformationSchema._InsertAllSQL;
		UpdateAllSQL = SDInformationSchema._UpdateAllSQL;
		FillAllSQL = SDInformationSchema._FillAllSQL;
		DeleteSQL = SDInformationSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDInformationSet();
	}

	public boolean add(SDInformationSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDInformationSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDInformationSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDInformationSchema get(int index) {
		SDInformationSchema tSchema = (SDInformationSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDInformationSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDInformationSet aSet) {
		return super.set(aSet);
	}
}
 