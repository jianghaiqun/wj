package com.sinosoft.schema;

import com.sinosoft.schema.PartnerRefundApplySchema;
import com.sinosoft.framework.orm.SchemaSet;

public class PartnerRefundApplySet extends SchemaSet {
	public PartnerRefundApplySet() {
		this(10,0);
	}

	public PartnerRefundApplySet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public PartnerRefundApplySet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = PartnerRefundApplySchema._TableCode;
		Columns = PartnerRefundApplySchema._Columns;
		NameSpace = PartnerRefundApplySchema._NameSpace;
		InsertAllSQL = PartnerRefundApplySchema._InsertAllSQL;
		UpdateAllSQL = PartnerRefundApplySchema._UpdateAllSQL;
		FillAllSQL = PartnerRefundApplySchema._FillAllSQL;
		DeleteSQL = PartnerRefundApplySchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new PartnerRefundApplySet();
	}

	public boolean add(PartnerRefundApplySchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(PartnerRefundApplySet aSet) {
		return super.add(aSet);
	}

	public boolean remove(PartnerRefundApplySchema aSchema) {
		return super.remove(aSchema);
	}

	public PartnerRefundApplySchema get(int index) {
		PartnerRefundApplySchema tSchema = (PartnerRefundApplySchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, PartnerRefundApplySchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(PartnerRefundApplySet aSet) {
		return super.set(aSet);
	}
}
 