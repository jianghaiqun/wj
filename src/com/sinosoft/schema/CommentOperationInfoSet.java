package com.sinosoft.schema;

import com.sinosoft.schema.CommentOperationInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class CommentOperationInfoSet extends SchemaSet {
	public CommentOperationInfoSet() {
		this(10,0);
	}

	public CommentOperationInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public CommentOperationInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = CommentOperationInfoSchema._TableCode;
		Columns = CommentOperationInfoSchema._Columns;
		NameSpace = CommentOperationInfoSchema._NameSpace;
		InsertAllSQL = CommentOperationInfoSchema._InsertAllSQL;
		UpdateAllSQL = CommentOperationInfoSchema._UpdateAllSQL;
		FillAllSQL = CommentOperationInfoSchema._FillAllSQL;
		DeleteSQL = CommentOperationInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new CommentOperationInfoSet();
	}

	public boolean add(CommentOperationInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(CommentOperationInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(CommentOperationInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public CommentOperationInfoSchema get(int index) {
		CommentOperationInfoSchema tSchema = (CommentOperationInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, CommentOperationInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(CommentOperationInfoSet aSet) {
		return super.set(aSet);
	}
}
 