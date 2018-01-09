package com.sinosoft.schema;

import com.sinosoft.framework.orm.SchemaSet;

public class SdActivityRewardSet extends SchemaSet {
	public SdActivityRewardSet() {
		this(10,0);
	}

	public SdActivityRewardSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SdActivityRewardSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SdActivityRewardSchema._TableCode;
		Columns = SdActivityRewardSchema._Columns;
		NameSpace = SdActivityRewardSchema._NameSpace;
		InsertAllSQL = SdActivityRewardSchema._InsertAllSQL;
		UpdateAllSQL = SdActivityRewardSchema._UpdateAllSQL;
		FillAllSQL = SdActivityRewardSchema._FillAllSQL;
		DeleteSQL = SdActivityRewardSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SdActivityRewardSet();
	}

	public boolean add(SdActivityRewardSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SdActivityRewardSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SdActivityRewardSchema aSchema) {
		return super.remove(aSchema);
	}

	public SdActivityRewardSchema get(int index) {
		SdActivityRewardSchema tSchema = (SdActivityRewardSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SdActivityRewardSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SdActivityRewardSet aSet) {
		return super.set(aSet);
	}
}
