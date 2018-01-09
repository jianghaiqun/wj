package com.sinosoft.schema;

import com.sinosoft.schema.SDPromotionHomePageSpecialProductSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDPromotionHomePageSpecialProductSet extends SchemaSet {
	public SDPromotionHomePageSpecialProductSet() {
		this(10,0);
	}

	public SDPromotionHomePageSpecialProductSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDPromotionHomePageSpecialProductSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDPromotionHomePageSpecialProductSchema._TableCode;
		Columns = SDPromotionHomePageSpecialProductSchema._Columns;
		NameSpace = SDPromotionHomePageSpecialProductSchema._NameSpace;
		InsertAllSQL = SDPromotionHomePageSpecialProductSchema._InsertAllSQL;
		UpdateAllSQL = SDPromotionHomePageSpecialProductSchema._UpdateAllSQL;
		FillAllSQL = SDPromotionHomePageSpecialProductSchema._FillAllSQL;
		DeleteSQL = SDPromotionHomePageSpecialProductSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDPromotionHomePageSpecialProductSet();
	}

	public boolean add(SDPromotionHomePageSpecialProductSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDPromotionHomePageSpecialProductSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDPromotionHomePageSpecialProductSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDPromotionHomePageSpecialProductSchema get(int index) {
		SDPromotionHomePageSpecialProductSchema tSchema = (SDPromotionHomePageSpecialProductSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDPromotionHomePageSpecialProductSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDPromotionHomePageSpecialProductSet aSet) {
		return super.set(aSet);
	}
}
 