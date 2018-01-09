package com.sinosoft.schema;

import com.sinosoft.schema.WapPictureUrlSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class WapPictureUrlSet extends SchemaSet {
	public WapPictureUrlSet() {
		this(10,0);
	}

	public WapPictureUrlSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public WapPictureUrlSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = WapPictureUrlSchema._TableCode;
		Columns = WapPictureUrlSchema._Columns;
		NameSpace = WapPictureUrlSchema._NameSpace;
		InsertAllSQL = WapPictureUrlSchema._InsertAllSQL;
		UpdateAllSQL = WapPictureUrlSchema._UpdateAllSQL;
		FillAllSQL = WapPictureUrlSchema._FillAllSQL;
		DeleteSQL = WapPictureUrlSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new WapPictureUrlSet();
	}

	public boolean add(WapPictureUrlSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(WapPictureUrlSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(WapPictureUrlSchema aSchema) {
		return super.remove(aSchema);
	}

	public WapPictureUrlSchema get(int index) {
		WapPictureUrlSchema tSchema = (WapPictureUrlSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, WapPictureUrlSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(WapPictureUrlSet aSet) {
		return super.set(aSet);
	}
}
 