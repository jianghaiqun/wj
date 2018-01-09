package com.sinosoft.schema;

import com.sinosoft.schema.PartnerInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class PartnerInfoSet extends SchemaSet {
	public PartnerInfoSet() {
		this(10,0);
	}

	public PartnerInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public PartnerInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = PartnerInfoSchema._TableCode;
		Columns = PartnerInfoSchema._Columns;
		NameSpace = PartnerInfoSchema._NameSpace;
		InsertAllSQL = PartnerInfoSchema._InsertAllSQL;
		UpdateAllSQL = PartnerInfoSchema._UpdateAllSQL;
		FillAllSQL = PartnerInfoSchema._FillAllSQL;
		DeleteSQL = PartnerInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new PartnerInfoSet();
	}

	public boolean add(PartnerInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(PartnerInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(PartnerInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public PartnerInfoSchema get(int index) {
		PartnerInfoSchema tSchema = (PartnerInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, PartnerInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(PartnerInfoSet aSet) {
		return super.set(aSet);
	}
}
 