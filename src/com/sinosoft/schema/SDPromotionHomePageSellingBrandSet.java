package com.sinosoft.schema;

import com.sinosoft.schema.SDPromotionHomePageSellingBrandSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDPromotionHomePageSellingBrandSet extends SchemaSet {
	public SDPromotionHomePageSellingBrandSet() {
		this(10,0);
	}

	public SDPromotionHomePageSellingBrandSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDPromotionHomePageSellingBrandSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDPromotionHomePageSellingBrandSchema._TableCode;
		Columns = SDPromotionHomePageSellingBrandSchema._Columns;
		NameSpace = SDPromotionHomePageSellingBrandSchema._NameSpace;
		InsertAllSQL = SDPromotionHomePageSellingBrandSchema._InsertAllSQL;
		UpdateAllSQL = SDPromotionHomePageSellingBrandSchema._UpdateAllSQL;
		FillAllSQL = SDPromotionHomePageSellingBrandSchema._FillAllSQL;
		DeleteSQL = SDPromotionHomePageSellingBrandSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDPromotionHomePageSellingBrandSet();
	}

	public boolean add(SDPromotionHomePageSellingBrandSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDPromotionHomePageSellingBrandSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDPromotionHomePageSellingBrandSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDPromotionHomePageSellingBrandSchema get(int index) {
		SDPromotionHomePageSellingBrandSchema tSchema = (SDPromotionHomePageSellingBrandSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDPromotionHomePageSellingBrandSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDPromotionHomePageSellingBrandSet aSet) {
		return super.set(aSet);
	}
}
 