package com.sinosoft.schema;

import com.sinosoft.schema.RefundResultInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class RefundResultInfoSet extends SchemaSet {
	public RefundResultInfoSet() {
		this(10,0);
	}

	public RefundResultInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public RefundResultInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = RefundResultInfoSchema._TableCode;
		Columns = RefundResultInfoSchema._Columns;
		NameSpace = RefundResultInfoSchema._NameSpace;
		InsertAllSQL = RefundResultInfoSchema._InsertAllSQL;
		UpdateAllSQL = RefundResultInfoSchema._UpdateAllSQL;
		FillAllSQL = RefundResultInfoSchema._FillAllSQL;
		DeleteSQL = RefundResultInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new RefundResultInfoSet();
	}

	public boolean add(RefundResultInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(RefundResultInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(RefundResultInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public RefundResultInfoSchema get(int index) {
		RefundResultInfoSchema tSchema = (RefundResultInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, RefundResultInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(RefundResultInfoSet aSet) {
		return super.set(aSet);
	}
}
 