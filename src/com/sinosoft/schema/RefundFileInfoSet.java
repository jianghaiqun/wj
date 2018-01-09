package com.sinosoft.schema;

import com.sinosoft.schema.RefundFileInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class RefundFileInfoSet extends SchemaSet {
	public RefundFileInfoSet() {
		this(10,0);
	}

	public RefundFileInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public RefundFileInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = RefundFileInfoSchema._TableCode;
		Columns = RefundFileInfoSchema._Columns;
		NameSpace = RefundFileInfoSchema._NameSpace;
		InsertAllSQL = RefundFileInfoSchema._InsertAllSQL;
		UpdateAllSQL = RefundFileInfoSchema._UpdateAllSQL;
		FillAllSQL = RefundFileInfoSchema._FillAllSQL;
		DeleteSQL = RefundFileInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new RefundFileInfoSet();
	}

	public boolean add(RefundFileInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(RefundFileInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(RefundFileInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public RefundFileInfoSchema get(int index) {
		RefundFileInfoSchema tSchema = (RefundFileInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, RefundFileInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(RefundFileInfoSet aSet) {
		return super.set(aSet);
	}
}
 