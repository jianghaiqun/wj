package com.sinosoft.schema;

import com.sinosoft.schema.ElementRelaInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class ElementRelaInfoSet extends SchemaSet {
	public ElementRelaInfoSet() {
		this(10,0);
	}

	public ElementRelaInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ElementRelaInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ElementRelaInfoSchema._TableCode;
		Columns = ElementRelaInfoSchema._Columns;
		NameSpace = ElementRelaInfoSchema._NameSpace;
		InsertAllSQL = ElementRelaInfoSchema._InsertAllSQL;
		UpdateAllSQL = ElementRelaInfoSchema._UpdateAllSQL;
		FillAllSQL = ElementRelaInfoSchema._FillAllSQL;
		DeleteSQL = ElementRelaInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ElementRelaInfoSet();
	}

	public boolean add(ElementRelaInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ElementRelaInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ElementRelaInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public ElementRelaInfoSchema get(int index) {
		ElementRelaInfoSchema tSchema = (ElementRelaInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ElementRelaInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ElementRelaInfoSet aSet) {
		return super.set(aSet);
	}
}
 