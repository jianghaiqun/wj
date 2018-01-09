package com.sinosoft.schema;

import com.sinosoft.schema.UnderwritingOfflineInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class UnderwritingOfflineInfoSet extends SchemaSet {
	public UnderwritingOfflineInfoSet() {
		this(10,0);
	}

	public UnderwritingOfflineInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public UnderwritingOfflineInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = UnderwritingOfflineInfoSchema._TableCode;
		Columns = UnderwritingOfflineInfoSchema._Columns;
		NameSpace = UnderwritingOfflineInfoSchema._NameSpace;
		InsertAllSQL = UnderwritingOfflineInfoSchema._InsertAllSQL;
		UpdateAllSQL = UnderwritingOfflineInfoSchema._UpdateAllSQL;
		FillAllSQL = UnderwritingOfflineInfoSchema._FillAllSQL;
		DeleteSQL = UnderwritingOfflineInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new UnderwritingOfflineInfoSet();
	}

	public boolean add(UnderwritingOfflineInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(UnderwritingOfflineInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(UnderwritingOfflineInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public UnderwritingOfflineInfoSchema get(int index) {
		UnderwritingOfflineInfoSchema tSchema = (UnderwritingOfflineInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, UnderwritingOfflineInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(UnderwritingOfflineInfoSet aSet) {
		return super.set(aSet);
	}
}
