package com.sinosoft.schema;

import com.sinosoft.schema.InformationAppntSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class InformationAppntSet extends SchemaSet {
	public InformationAppntSet() {
		this(10,0);
	}

	public InformationAppntSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public InformationAppntSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = InformationAppntSchema._TableCode;
		Columns = InformationAppntSchema._Columns;
		NameSpace = InformationAppntSchema._NameSpace;
		InsertAllSQL = InformationAppntSchema._InsertAllSQL;
		UpdateAllSQL = InformationAppntSchema._UpdateAllSQL;
		FillAllSQL = InformationAppntSchema._FillAllSQL;
		DeleteSQL = InformationAppntSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new InformationAppntSet();
	}

	public boolean add(InformationAppntSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(InformationAppntSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(InformationAppntSchema aSchema) {
		return super.remove(aSchema);
	}

	public InformationAppntSchema get(int index) {
		InformationAppntSchema tSchema = (InformationAppntSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, InformationAppntSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(InformationAppntSet aSet) {
		return super.set(aSet);
	}
}
 