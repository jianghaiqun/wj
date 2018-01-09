package com.sinosoft.schema;

import com.sinosoft.schema.LotteryActSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class LotteryActSet extends SchemaSet {
	public LotteryActSet() {
		this(10,0);
	}

	public LotteryActSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public LotteryActSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = LotteryActSchema._TableCode;
		Columns = LotteryActSchema._Columns;
		NameSpace = LotteryActSchema._NameSpace;
		InsertAllSQL = LotteryActSchema._InsertAllSQL;
		UpdateAllSQL = LotteryActSchema._UpdateAllSQL;
		FillAllSQL = LotteryActSchema._FillAllSQL;
		DeleteSQL = LotteryActSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new LotteryActSet();
	}

	public boolean add(LotteryActSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(LotteryActSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(LotteryActSchema aSchema) {
		return super.remove(aSchema);
	}

	public LotteryActSchema get(int index) {
		LotteryActSchema tSchema = (LotteryActSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, LotteryActSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(LotteryActSet aSet) {
		return super.set(aSet);
	}
}
 