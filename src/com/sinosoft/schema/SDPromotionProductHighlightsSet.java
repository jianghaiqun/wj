package com.sinosoft.schema;

import com.sinosoft.schema.SDPromotionProductHighlightsSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDPromotionProductHighlightsSet extends SchemaSet {
	public SDPromotionProductHighlightsSet() {
		this(10,0);
	}

	public SDPromotionProductHighlightsSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDPromotionProductHighlightsSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDPromotionProductHighlightsSchema._TableCode;
		Columns = SDPromotionProductHighlightsSchema._Columns;
		NameSpace = SDPromotionProductHighlightsSchema._NameSpace;
		InsertAllSQL = SDPromotionProductHighlightsSchema._InsertAllSQL;
		UpdateAllSQL = SDPromotionProductHighlightsSchema._UpdateAllSQL;
		FillAllSQL = SDPromotionProductHighlightsSchema._FillAllSQL;
		DeleteSQL = SDPromotionProductHighlightsSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDPromotionProductHighlightsSet();
	}

	public boolean add(SDPromotionProductHighlightsSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDPromotionProductHighlightsSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDPromotionProductHighlightsSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDPromotionProductHighlightsSchema get(int index) {
		SDPromotionProductHighlightsSchema tSchema = (SDPromotionProductHighlightsSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDPromotionProductHighlightsSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDPromotionProductHighlightsSet aSet) {
		return super.set(aSet);
	}
}
 