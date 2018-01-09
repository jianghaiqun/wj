package com.sinosoft.schema;

import com.sinosoft.schema.SDPromotionHomePageActivitySchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDPromotionHomePageActivitySet extends SchemaSet {
	public SDPromotionHomePageActivitySet() {
		this(10,0);
	}

	public SDPromotionHomePageActivitySet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDPromotionHomePageActivitySet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDPromotionHomePageActivitySchema._TableCode;
		Columns = SDPromotionHomePageActivitySchema._Columns;
		NameSpace = SDPromotionHomePageActivitySchema._NameSpace;
		InsertAllSQL = SDPromotionHomePageActivitySchema._InsertAllSQL;
		UpdateAllSQL = SDPromotionHomePageActivitySchema._UpdateAllSQL;
		FillAllSQL = SDPromotionHomePageActivitySchema._FillAllSQL;
		DeleteSQL = SDPromotionHomePageActivitySchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDPromotionHomePageActivitySet();
	}

	public boolean add(SDPromotionHomePageActivitySchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDPromotionHomePageActivitySet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDPromotionHomePageActivitySchema aSchema) {
		return super.remove(aSchema);
	}

	public SDPromotionHomePageActivitySchema get(int index) {
		SDPromotionHomePageActivitySchema tSchema = (SDPromotionHomePageActivitySchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDPromotionHomePageActivitySchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDPromotionHomePageActivitySet aSet) {
		return super.set(aSet);
	}
}
 