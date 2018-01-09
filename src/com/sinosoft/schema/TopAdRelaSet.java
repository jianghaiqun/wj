package com.sinosoft.schema;

import com.sinosoft.schema.TopAdRelaSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class TopAdRelaSet extends SchemaSet {
	public TopAdRelaSet() {
		this(10,0);
	}

	public TopAdRelaSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public TopAdRelaSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = TopAdRelaSchema._TableCode;
		Columns = TopAdRelaSchema._Columns;
		NameSpace = TopAdRelaSchema._NameSpace;
		InsertAllSQL = TopAdRelaSchema._InsertAllSQL;
		UpdateAllSQL = TopAdRelaSchema._UpdateAllSQL;
		FillAllSQL = TopAdRelaSchema._FillAllSQL;
		DeleteSQL = TopAdRelaSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new TopAdRelaSet();
	}

	public boolean add(TopAdRelaSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(TopAdRelaSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(TopAdRelaSchema aSchema) {
		return super.remove(aSchema);
	}

	public TopAdRelaSchema get(int index) {
		TopAdRelaSchema tSchema = (TopAdRelaSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, TopAdRelaSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(TopAdRelaSet aSet) {
		return super.set(aSet);
	}
}
 