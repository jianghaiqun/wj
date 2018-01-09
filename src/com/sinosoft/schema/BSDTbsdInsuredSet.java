package com.sinosoft.schema;

import com.sinosoft.schema.BSDTbsdInsuredSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class BSDTbsdInsuredSet extends SchemaSet {
	public BSDTbsdInsuredSet() {
		this(10,0);
	}

	public BSDTbsdInsuredSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BSDTbsdInsuredSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BSDTbsdInsuredSchema._TableCode;
		Columns = BSDTbsdInsuredSchema._Columns;
		NameSpace = BSDTbsdInsuredSchema._NameSpace;
		InsertAllSQL = BSDTbsdInsuredSchema._InsertAllSQL;
		UpdateAllSQL = BSDTbsdInsuredSchema._UpdateAllSQL;
		FillAllSQL = BSDTbsdInsuredSchema._FillAllSQL;
		DeleteSQL = BSDTbsdInsuredSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BSDTbsdInsuredSet();
	}

	public boolean add(BSDTbsdInsuredSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BSDTbsdInsuredSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BSDTbsdInsuredSchema aSchema) {
		return super.remove(aSchema);
	}

	public BSDTbsdInsuredSchema get(int index) {
		BSDTbsdInsuredSchema tSchema = (BSDTbsdInsuredSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BSDTbsdInsuredSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BSDTbsdInsuredSet aSet) {
		return super.set(aSet);
	}
}
 