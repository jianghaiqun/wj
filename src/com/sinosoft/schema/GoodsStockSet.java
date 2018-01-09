package com.sinosoft.schema;

import com.sinosoft.schema.GoodsStockSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class GoodsStockSet extends SchemaSet {
	public GoodsStockSet() {
		this(10,0);
	}

	public GoodsStockSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public GoodsStockSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = GoodsStockSchema._TableCode;
		Columns = GoodsStockSchema._Columns;
		NameSpace = GoodsStockSchema._NameSpace;
		InsertAllSQL = GoodsStockSchema._InsertAllSQL;
		UpdateAllSQL = GoodsStockSchema._UpdateAllSQL;
		FillAllSQL = GoodsStockSchema._FillAllSQL;
		DeleteSQL = GoodsStockSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new GoodsStockSet();
	}

	public boolean add(GoodsStockSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(GoodsStockSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(GoodsStockSchema aSchema) {
		return super.remove(aSchema);
	}

	public GoodsStockSchema get(int index) {
		GoodsStockSchema tSchema = (GoodsStockSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, GoodsStockSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(GoodsStockSet aSet) {
		return super.set(aSet);
	}
}
 