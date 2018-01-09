package com.sinosoft.schema;

import com.sinosoft.schema.CustomServiceFileSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class CustomServiceFileSet extends SchemaSet {
	public CustomServiceFileSet() {
		this(10,0);
	}

	public CustomServiceFileSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public CustomServiceFileSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = CustomServiceFileSchema._TableCode;
		Columns = CustomServiceFileSchema._Columns;
		NameSpace = CustomServiceFileSchema._NameSpace;
		InsertAllSQL = CustomServiceFileSchema._InsertAllSQL;
		UpdateAllSQL = CustomServiceFileSchema._UpdateAllSQL;
		FillAllSQL = CustomServiceFileSchema._FillAllSQL;
		DeleteSQL = CustomServiceFileSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new CustomServiceFileSet();
	}

	public boolean add(CustomServiceFileSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(CustomServiceFileSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(CustomServiceFileSchema aSchema) {
		return super.remove(aSchema);
	}

	public CustomServiceFileSchema get(int index) {
		CustomServiceFileSchema tSchema = (CustomServiceFileSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, CustomServiceFileSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(CustomServiceFileSet aSet) {
		return super.set(aSet);
	}
}
 