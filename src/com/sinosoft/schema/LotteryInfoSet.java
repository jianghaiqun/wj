package com.sinosoft.schema;

import com.sinosoft.schema.LotteryInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class LotteryInfoSet extends SchemaSet {
	public LotteryInfoSet() {
		this(10,0);
	}

	public LotteryInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public LotteryInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = LotteryInfoSchema._TableCode;
		Columns = LotteryInfoSchema._Columns;
		NameSpace = LotteryInfoSchema._NameSpace;
		InsertAllSQL = LotteryInfoSchema._InsertAllSQL;
		UpdateAllSQL = LotteryInfoSchema._UpdateAllSQL;
		FillAllSQL = LotteryInfoSchema._FillAllSQL;
		DeleteSQL = LotteryInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new LotteryInfoSet();
	}

	public boolean add(LotteryInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(LotteryInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(LotteryInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public LotteryInfoSchema get(int index) {
		LotteryInfoSchema tSchema = (LotteryInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, LotteryInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(LotteryInfoSet aSet) {
		return super.set(aSet);
	}
}
 