package com.sinosoft.schema;

import com.sinosoft.schema.ZDTBShopInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class ZDTBShopInfoSet extends SchemaSet {
	public ZDTBShopInfoSet() {
		this(10,0);
	}

	public ZDTBShopInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZDTBShopInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZDTBShopInfoSchema._TableCode;
		Columns = ZDTBShopInfoSchema._Columns;
		NameSpace = ZDTBShopInfoSchema._NameSpace;
		InsertAllSQL = ZDTBShopInfoSchema._InsertAllSQL;
		UpdateAllSQL = ZDTBShopInfoSchema._UpdateAllSQL;
		FillAllSQL = ZDTBShopInfoSchema._FillAllSQL;
		DeleteSQL = ZDTBShopInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZDTBShopInfoSet();
	}

	public boolean add(ZDTBShopInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZDTBShopInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZDTBShopInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZDTBShopInfoSchema get(int index) {
		ZDTBShopInfoSchema tSchema = (ZDTBShopInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZDTBShopInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZDTBShopInfoSet aSet) {
		return super.set(aSet);
	}
}
 