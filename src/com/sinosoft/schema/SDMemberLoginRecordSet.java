package com.sinosoft.schema;

import com.sinosoft.schema.SDMemberLoginRecordSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDMemberLoginRecordSet extends SchemaSet {
	public SDMemberLoginRecordSet() {
		this(10,0);
	}

	public SDMemberLoginRecordSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDMemberLoginRecordSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDMemberLoginRecordSchema._TableCode;
		Columns = SDMemberLoginRecordSchema._Columns;
		NameSpace = SDMemberLoginRecordSchema._NameSpace;
		InsertAllSQL = SDMemberLoginRecordSchema._InsertAllSQL;
		UpdateAllSQL = SDMemberLoginRecordSchema._UpdateAllSQL;
		FillAllSQL = SDMemberLoginRecordSchema._FillAllSQL;
		DeleteSQL = SDMemberLoginRecordSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDMemberLoginRecordSet();
	}

	public boolean add(SDMemberLoginRecordSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDMemberLoginRecordSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDMemberLoginRecordSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDMemberLoginRecordSchema get(int index) {
		SDMemberLoginRecordSchema tSchema = (SDMemberLoginRecordSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDMemberLoginRecordSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDMemberLoginRecordSet aSet) {
		return super.set(aSet);
	}
}
 