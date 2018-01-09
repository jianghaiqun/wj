package com.sinosoft.schema;

import com.sinosoft.schema.PartnerAccountBalanceRecordSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class PartnerAccountBalanceRecordSet extends SchemaSet {
	public PartnerAccountBalanceRecordSet() {
		this(10,0);
	}

	public PartnerAccountBalanceRecordSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public PartnerAccountBalanceRecordSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = PartnerAccountBalanceRecordSchema._TableCode;
		Columns = PartnerAccountBalanceRecordSchema._Columns;
		NameSpace = PartnerAccountBalanceRecordSchema._NameSpace;
		InsertAllSQL = PartnerAccountBalanceRecordSchema._InsertAllSQL;
		UpdateAllSQL = PartnerAccountBalanceRecordSchema._UpdateAllSQL;
		FillAllSQL = PartnerAccountBalanceRecordSchema._FillAllSQL;
		DeleteSQL = PartnerAccountBalanceRecordSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new PartnerAccountBalanceRecordSet();
	}

	public boolean add(PartnerAccountBalanceRecordSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(PartnerAccountBalanceRecordSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(PartnerAccountBalanceRecordSchema aSchema) {
		return super.remove(aSchema);
	}

	public PartnerAccountBalanceRecordSchema get(int index) {
		PartnerAccountBalanceRecordSchema tSchema = (PartnerAccountBalanceRecordSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, PartnerAccountBalanceRecordSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(PartnerAccountBalanceRecordSet aSet) {
		return super.set(aSet);
	}
}
 