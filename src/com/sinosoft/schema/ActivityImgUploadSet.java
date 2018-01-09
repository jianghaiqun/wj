package com.sinosoft.schema;

import com.sinosoft.framework.orm.SchemaSet;

public class ActivityImgUploadSet extends SchemaSet {
	public ActivityImgUploadSet() {
		this(10,0);
	}

	public ActivityImgUploadSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ActivityImgUploadSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ActivityImgUploadSchema._TableCode;
		Columns = ActivityImgUploadSchema._Columns;
		NameSpace = ActivityImgUploadSchema._NameSpace;
		InsertAllSQL = ActivityImgUploadSchema._InsertAllSQL;
		UpdateAllSQL = ActivityImgUploadSchema._UpdateAllSQL;
		FillAllSQL = ActivityImgUploadSchema._FillAllSQL;
		DeleteSQL = ActivityImgUploadSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ActivityImgUploadSet();
	}

	public boolean add(ActivityImgUploadSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ActivityImgUploadSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ActivityImgUploadSchema aSchema) {
		return super.remove(aSchema);
	}

	public ActivityImgUploadSchema get(int index) {
		ActivityImgUploadSchema tSchema = (ActivityImgUploadSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ActivityImgUploadSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ActivityImgUploadSet aSet) {
		return super.set(aSet);
	}
}
