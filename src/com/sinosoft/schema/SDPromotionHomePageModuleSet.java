package com.sinosoft.schema;

import com.sinosoft.schema.SDPromotionHomePageModuleSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDPromotionHomePageModuleSet extends SchemaSet {
	public SDPromotionHomePageModuleSet() {
		this(10,0);
	}

	public SDPromotionHomePageModuleSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDPromotionHomePageModuleSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDPromotionHomePageModuleSchema._TableCode;
		Columns = SDPromotionHomePageModuleSchema._Columns;
		NameSpace = SDPromotionHomePageModuleSchema._NameSpace;
		InsertAllSQL = SDPromotionHomePageModuleSchema._InsertAllSQL;
		UpdateAllSQL = SDPromotionHomePageModuleSchema._UpdateAllSQL;
		FillAllSQL = SDPromotionHomePageModuleSchema._FillAllSQL;
		DeleteSQL = SDPromotionHomePageModuleSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDPromotionHomePageModuleSet();
	}

	public boolean add(SDPromotionHomePageModuleSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDPromotionHomePageModuleSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDPromotionHomePageModuleSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDPromotionHomePageModuleSchema get(int index) {
		SDPromotionHomePageModuleSchema tSchema = (SDPromotionHomePageModuleSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDPromotionHomePageModuleSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDPromotionHomePageModuleSet aSet) {
		return super.set(aSet);
	}
}
 