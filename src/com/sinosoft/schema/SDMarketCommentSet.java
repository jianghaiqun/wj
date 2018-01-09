package com.sinosoft.schema;

import com.sinosoft.schema.SDMarketCommentSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDMarketCommentSet extends SchemaSet {
	public SDMarketCommentSet() {
		this(10,0);
	}

	public SDMarketCommentSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDMarketCommentSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDMarketCommentSchema._TableCode;
		Columns = SDMarketCommentSchema._Columns;
		NameSpace = SDMarketCommentSchema._NameSpace;
		InsertAllSQL = SDMarketCommentSchema._InsertAllSQL;
		UpdateAllSQL = SDMarketCommentSchema._UpdateAllSQL;
		FillAllSQL = SDMarketCommentSchema._FillAllSQL;
		DeleteSQL = SDMarketCommentSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDMarketCommentSet();
	}

	public boolean add(SDMarketCommentSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDMarketCommentSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDMarketCommentSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDMarketCommentSchema get(int index) {
		SDMarketCommentSchema tSchema = (SDMarketCommentSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDMarketCommentSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDMarketCommentSet aSet) {
		return super.set(aSet);
	}
}
 