package com.sinosoft.schema;

import com.sinosoft.schema.CommentPraisedInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class CommentPraisedInfoSet extends SchemaSet {
	public CommentPraisedInfoSet() {
		this(10,0);
	}

	public CommentPraisedInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public CommentPraisedInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = CommentPraisedInfoSchema._TableCode;
		Columns = CommentPraisedInfoSchema._Columns;
		NameSpace = CommentPraisedInfoSchema._NameSpace;
		InsertAllSQL = CommentPraisedInfoSchema._InsertAllSQL;
		UpdateAllSQL = CommentPraisedInfoSchema._UpdateAllSQL;
		FillAllSQL = CommentPraisedInfoSchema._FillAllSQL;
		DeleteSQL = CommentPraisedInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new CommentPraisedInfoSet();
	}

	public boolean add(CommentPraisedInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(CommentPraisedInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(CommentPraisedInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public CommentPraisedInfoSchema get(int index) {
		CommentPraisedInfoSchema tSchema = (CommentPraisedInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, CommentPraisedInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(CommentPraisedInfoSet aSet) {
		return super.set(aSet);
	}
}
 