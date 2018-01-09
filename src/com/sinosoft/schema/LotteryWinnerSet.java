package com.sinosoft.schema;

import com.sinosoft.schema.LotteryWinnerSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class LotteryWinnerSet extends SchemaSet {
	public LotteryWinnerSet() {
		this(10,0);
	}

	public LotteryWinnerSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public LotteryWinnerSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = LotteryWinnerSchema._TableCode;
		Columns = LotteryWinnerSchema._Columns;
		NameSpace = LotteryWinnerSchema._NameSpace;
		InsertAllSQL = LotteryWinnerSchema._InsertAllSQL;
		UpdateAllSQL = LotteryWinnerSchema._UpdateAllSQL;
		FillAllSQL = LotteryWinnerSchema._FillAllSQL;
		DeleteSQL = LotteryWinnerSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new LotteryWinnerSet();
	}

	public boolean add(LotteryWinnerSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(LotteryWinnerSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(LotteryWinnerSchema aSchema) {
		return super.remove(aSchema);
	}

	public LotteryWinnerSchema get(int index) {
		LotteryWinnerSchema tSchema = (LotteryWinnerSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, LotteryWinnerSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(LotteryWinnerSet aSet) {
		return super.set(aSet);
	}
}
 