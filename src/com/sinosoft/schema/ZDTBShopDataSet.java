package com.sinosoft.schema;

import com.sinosoft.schema.ZDTBShopDataSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class ZDTBShopDataSet extends SchemaSet {
	public ZDTBShopDataSet() {
		this(10,0);
	}

	public ZDTBShopDataSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZDTBShopDataSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZDTBShopDataSchema._TableCode;
		Columns = ZDTBShopDataSchema._Columns;
		NameSpace = ZDTBShopDataSchema._NameSpace;
		InsertAllSQL = ZDTBShopDataSchema._InsertAllSQL;
		UpdateAllSQL = ZDTBShopDataSchema._UpdateAllSQL;
		FillAllSQL = ZDTBShopDataSchema._FillAllSQL;
		DeleteSQL = ZDTBShopDataSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZDTBShopDataSet();
	}

	public boolean add(ZDTBShopDataSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZDTBShopDataSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZDTBShopDataSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZDTBShopDataSchema get(int index) {
		ZDTBShopDataSchema tSchema = (ZDTBShopDataSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZDTBShopDataSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZDTBShopDataSet aSet) {
		return super.set(aSet);
	}
}
 