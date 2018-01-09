package com.sinosoft.schema;

import com.sinosoft.schema.CustomClaimsDataInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class CustomClaimsDataInfoSet extends SchemaSet {
	public CustomClaimsDataInfoSet() {
		this(10,0); 
	}

	public CustomClaimsDataInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public CustomClaimsDataInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = CustomClaimsDataInfoSchema._TableCode;
		Columns = CustomClaimsDataInfoSchema._Columns;
		NameSpace = CustomClaimsDataInfoSchema._NameSpace;
		InsertAllSQL = CustomClaimsDataInfoSchema._InsertAllSQL;
		UpdateAllSQL = CustomClaimsDataInfoSchema._UpdateAllSQL;
		FillAllSQL = CustomClaimsDataInfoSchema._FillAllSQL;
		DeleteSQL = CustomClaimsDataInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new CustomClaimsDataInfoSet();
	}

	public boolean add(CustomClaimsDataInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(CustomClaimsDataInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(CustomClaimsDataInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public CustomClaimsDataInfoSchema get(int index) {
		CustomClaimsDataInfoSchema tSchema = (CustomClaimsDataInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, CustomClaimsDataInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(CustomClaimsDataInfoSet aSet) {
		return super.set(aSet);
	}
}
 