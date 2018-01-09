package com.sinosoft.schema;

import com.sinosoft.schema.ZhenAiRenewalSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class ZhenAiRenewalSet extends SchemaSet {
	public ZhenAiRenewalSet() {
		this(10,0);
	}

	public ZhenAiRenewalSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZhenAiRenewalSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZhenAiRenewalSchema._TableCode;
		Columns = ZhenAiRenewalSchema._Columns;
		NameSpace = ZhenAiRenewalSchema._NameSpace;
		InsertAllSQL = ZhenAiRenewalSchema._InsertAllSQL;
		UpdateAllSQL = ZhenAiRenewalSchema._UpdateAllSQL;
		FillAllSQL = ZhenAiRenewalSchema._FillAllSQL;
		DeleteSQL = ZhenAiRenewalSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZhenAiRenewalSet();
	}

	public boolean add(ZhenAiRenewalSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZhenAiRenewalSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZhenAiRenewalSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZhenAiRenewalSchema get(int index) {
		ZhenAiRenewalSchema tSchema = (ZhenAiRenewalSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZhenAiRenewalSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZhenAiRenewalSet aSet) {
		return super.set(aSet);
	}
}
