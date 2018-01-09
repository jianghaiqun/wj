package com.sinosoft.schema;

import com.sinosoft.schema.SCSendEmailLogSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SCSendEmailLogSet extends SchemaSet {
	public SCSendEmailLogSet() {
		this(10,0);
	}

	public SCSendEmailLogSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SCSendEmailLogSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SCSendEmailLogSchema._TableCode;
		Columns = SCSendEmailLogSchema._Columns;
		NameSpace = SCSendEmailLogSchema._NameSpace;
		InsertAllSQL = SCSendEmailLogSchema._InsertAllSQL;
		UpdateAllSQL = SCSendEmailLogSchema._UpdateAllSQL;
		FillAllSQL = SCSendEmailLogSchema._FillAllSQL;
		DeleteSQL = SCSendEmailLogSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SCSendEmailLogSet();
	}

	public boolean add(SCSendEmailLogSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SCSendEmailLogSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SCSendEmailLogSchema aSchema) {
		return super.remove(aSchema);
	}

	public SCSendEmailLogSchema get(int index) {
		SCSendEmailLogSchema tSchema = (SCSendEmailLogSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SCSendEmailLogSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SCSendEmailLogSet aSet) {
		return super.set(aSet);
	}
}
 