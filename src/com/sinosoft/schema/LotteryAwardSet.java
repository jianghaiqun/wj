package com.sinosoft.schema;

import com.sinosoft.schema.LotteryAwardSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class LotteryAwardSet extends SchemaSet {
	public LotteryAwardSet() {
		this(10,0);
	}

	public LotteryAwardSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public LotteryAwardSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = LotteryAwardSchema._TableCode;
		Columns = LotteryAwardSchema._Columns;
		NameSpace = LotteryAwardSchema._NameSpace;
		InsertAllSQL = LotteryAwardSchema._InsertAllSQL;
		UpdateAllSQL = LotteryAwardSchema._UpdateAllSQL;
		FillAllSQL = LotteryAwardSchema._FillAllSQL;
		DeleteSQL = LotteryAwardSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new LotteryAwardSet();
	}

	public boolean add(LotteryAwardSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(LotteryAwardSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(LotteryAwardSchema aSchema) {
		return super.remove(aSchema);
	}

	public LotteryAwardSchema get(int index) {
		LotteryAwardSchema tSchema = (LotteryAwardSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, LotteryAwardSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(LotteryAwardSet aSet) {
		return super.set(aSet);
	}
}
 