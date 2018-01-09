package com.sinosoft.schema;

import com.sinosoft.schema.UnderwritingOfflineImagesSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class UnderwritingOfflineImagesSet extends SchemaSet {
	public UnderwritingOfflineImagesSet() {
		this(10,0);
	}

	public UnderwritingOfflineImagesSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public UnderwritingOfflineImagesSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = UnderwritingOfflineImagesSchema._TableCode;
		Columns = UnderwritingOfflineImagesSchema._Columns;
		NameSpace = UnderwritingOfflineImagesSchema._NameSpace;
		InsertAllSQL = UnderwritingOfflineImagesSchema._InsertAllSQL;
		UpdateAllSQL = UnderwritingOfflineImagesSchema._UpdateAllSQL;
		FillAllSQL = UnderwritingOfflineImagesSchema._FillAllSQL;
		DeleteSQL = UnderwritingOfflineImagesSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new UnderwritingOfflineImagesSet();
	}

	public boolean add(UnderwritingOfflineImagesSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(UnderwritingOfflineImagesSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(UnderwritingOfflineImagesSchema aSchema) {
		return super.remove(aSchema);
	}

	public UnderwritingOfflineImagesSchema get(int index) {
		UnderwritingOfflineImagesSchema tSchema = (UnderwritingOfflineImagesSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, UnderwritingOfflineImagesSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(UnderwritingOfflineImagesSet aSet) {
		return super.set(aSet);
	}
}
