package com.sinosoft.schema;

import com.sinosoft.schema.PartnersManageSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class PartnersManageSet extends SchemaSet {
	public PartnersManageSet() {
		this(10,0);
	}

	public PartnersManageSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public PartnersManageSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = PartnersManageSchema._TableCode;
		Columns = PartnersManageSchema._Columns;
		NameSpace = PartnersManageSchema._NameSpace;
		InsertAllSQL = PartnersManageSchema._InsertAllSQL;
		UpdateAllSQL = PartnersManageSchema._UpdateAllSQL;
		FillAllSQL = PartnersManageSchema._FillAllSQL;
		DeleteSQL = PartnersManageSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new PartnersManageSet();
	}

	public boolean add(PartnersManageSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(PartnersManageSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(PartnersManageSchema aSchema) {
		return super.remove(aSchema);
	}

	public PartnersManageSchema get(int index) {
		PartnersManageSchema tSchema = (PartnersManageSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, PartnersManageSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(PartnersManageSet aSet) {
		return super.set(aSet);
	}
}
 