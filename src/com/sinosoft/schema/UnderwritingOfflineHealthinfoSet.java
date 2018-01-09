package com.sinosoft.schema;

import com.sinosoft.schema.UnderwritingOfflineHealthinfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class UnderwritingOfflineHealthinfoSet extends SchemaSet {
	public UnderwritingOfflineHealthinfoSet() {
		this(10,0);
	}

	public UnderwritingOfflineHealthinfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public UnderwritingOfflineHealthinfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = UnderwritingOfflineHealthinfoSchema._TableCode;
		Columns = UnderwritingOfflineHealthinfoSchema._Columns;
		NameSpace = UnderwritingOfflineHealthinfoSchema._NameSpace;
		InsertAllSQL = UnderwritingOfflineHealthinfoSchema._InsertAllSQL;
		UpdateAllSQL = UnderwritingOfflineHealthinfoSchema._UpdateAllSQL;
		FillAllSQL = UnderwritingOfflineHealthinfoSchema._FillAllSQL;
		DeleteSQL = UnderwritingOfflineHealthinfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new UnderwritingOfflineHealthinfoSet();
	}

	public boolean add(UnderwritingOfflineHealthinfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(UnderwritingOfflineHealthinfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(UnderwritingOfflineHealthinfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public UnderwritingOfflineHealthinfoSchema get(int index) {
		UnderwritingOfflineHealthinfoSchema tSchema = (UnderwritingOfflineHealthinfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, UnderwritingOfflineHealthinfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(UnderwritingOfflineHealthinfoSet aSet) {
		return super.set(aSet);
	}
}
