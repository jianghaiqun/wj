package com.sinosoft.schema;

import com.sinosoft.schema.PurchaseRelaInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class PurchaseRelaInfoSet extends SchemaSet {
	public PurchaseRelaInfoSet() {
		this(10,0);
	}

	public PurchaseRelaInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public PurchaseRelaInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = PurchaseRelaInfoSchema._TableCode;
		Columns = PurchaseRelaInfoSchema._Columns;
		NameSpace = PurchaseRelaInfoSchema._NameSpace;
		InsertAllSQL = PurchaseRelaInfoSchema._InsertAllSQL;
		UpdateAllSQL = PurchaseRelaInfoSchema._UpdateAllSQL;
		FillAllSQL = PurchaseRelaInfoSchema._FillAllSQL;
		DeleteSQL = PurchaseRelaInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new PurchaseRelaInfoSet();
	}

	public boolean add(PurchaseRelaInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(PurchaseRelaInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(PurchaseRelaInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public PurchaseRelaInfoSchema get(int index) {
		PurchaseRelaInfoSchema tSchema = (PurchaseRelaInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, PurchaseRelaInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(PurchaseRelaInfoSet aSet) {
		return super.set(aSet);
	}
}
 