package com.sinosoft.schema;

import com.sinosoft.schema.GiftClassifySchema;
import com.sinosoft.framework.orm.SchemaSet;

public class GiftClassifySet extends SchemaSet {
	public GiftClassifySet() {
		this(10,0);
	}

	public GiftClassifySet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public GiftClassifySet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = GiftClassifySchema._TableCode;
		Columns = GiftClassifySchema._Columns;
		NameSpace = GiftClassifySchema._NameSpace;
		InsertAllSQL = GiftClassifySchema._InsertAllSQL;
		UpdateAllSQL = GiftClassifySchema._UpdateAllSQL;
		FillAllSQL = GiftClassifySchema._FillAllSQL;
		DeleteSQL = GiftClassifySchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new GiftClassifySet();
	}

	public boolean add(GiftClassifySchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(GiftClassifySet aSet) {
		return super.add(aSet);
	}

	public boolean remove(GiftClassifySchema aSchema) {
		return super.remove(aSchema);
	}

	public GiftClassifySchema get(int index) {
		GiftClassifySchema tSchema = (GiftClassifySchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, GiftClassifySchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(GiftClassifySet aSet) {
		return super.set(aSet);
	}
}
 