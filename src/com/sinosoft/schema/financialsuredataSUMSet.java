package com.sinosoft.schema;

import com.sinosoft.schema.financialsuredataSUMSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class financialsuredataSUMSet extends SchemaSet {
	public financialsuredataSUMSet() {
		this(10,0);
	}

	public financialsuredataSUMSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public financialsuredataSUMSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = financialsuredataSUMSchema._TableCode;
		Columns = financialsuredataSUMSchema._Columns;
		NameSpace = financialsuredataSUMSchema._NameSpace;
		InsertAllSQL = financialsuredataSUMSchema._InsertAllSQL;
		UpdateAllSQL = financialsuredataSUMSchema._UpdateAllSQL;
		FillAllSQL = financialsuredataSUMSchema._FillAllSQL;
		DeleteSQL = financialsuredataSUMSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new financialsuredataSUMSet();
	}

	public boolean add(financialsuredataSUMSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(financialsuredataSUMSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(financialsuredataSUMSchema aSchema) {
		return super.remove(aSchema);
	}

	public financialsuredataSUMSchema get(int index) {
		financialsuredataSUMSchema tSchema = (financialsuredataSUMSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, financialsuredataSUMSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(financialsuredataSUMSet aSet) {
		return super.set(aSet);
	}
}
 