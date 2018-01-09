package com.sinosoft.schema;

import com.sinosoft.schema.SDProductHighlightsSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDProductHighlightsSet extends SchemaSet {
	public SDProductHighlightsSet() {
		this(10,0);
	}

	public SDProductHighlightsSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDProductHighlightsSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDProductHighlightsSchema._TableCode;
		Columns = SDProductHighlightsSchema._Columns;
		NameSpace = SDProductHighlightsSchema._NameSpace;
		InsertAllSQL = SDProductHighlightsSchema._InsertAllSQL;
		UpdateAllSQL = SDProductHighlightsSchema._UpdateAllSQL;
		FillAllSQL = SDProductHighlightsSchema._FillAllSQL;
		DeleteSQL = SDProductHighlightsSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDProductHighlightsSet();
	}

	public boolean add(SDProductHighlightsSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDProductHighlightsSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDProductHighlightsSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDProductHighlightsSchema get(int index) {
		SDProductHighlightsSchema tSchema = (SDProductHighlightsSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDProductHighlightsSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDProductHighlightsSet aSet) {
		return super.set(aSet);
	}
}
 