package com.sinosoft.schema;

import com.sinosoft.schema.PartnerRefundFundsRecordSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class PartnerRefundFundsRecordSet extends SchemaSet {
	public PartnerRefundFundsRecordSet() {
		this(10,0);
	}

	public PartnerRefundFundsRecordSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public PartnerRefundFundsRecordSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = PartnerRefundFundsRecordSchema._TableCode;
		Columns = PartnerRefundFundsRecordSchema._Columns;
		NameSpace = PartnerRefundFundsRecordSchema._NameSpace;
		InsertAllSQL = PartnerRefundFundsRecordSchema._InsertAllSQL;
		UpdateAllSQL = PartnerRefundFundsRecordSchema._UpdateAllSQL;
		FillAllSQL = PartnerRefundFundsRecordSchema._FillAllSQL;
		DeleteSQL = PartnerRefundFundsRecordSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new PartnerRefundFundsRecordSet();
	}

	public boolean add(PartnerRefundFundsRecordSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(PartnerRefundFundsRecordSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(PartnerRefundFundsRecordSchema aSchema) {
		return super.remove(aSchema);
	}

	public PartnerRefundFundsRecordSchema get(int index) {
		PartnerRefundFundsRecordSchema tSchema = (PartnerRefundFundsRecordSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, PartnerRefundFundsRecordSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(PartnerRefundFundsRecordSet aSet) {
		return super.set(aSet);
	}
}
 