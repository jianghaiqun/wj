package com.sinosoft.schema;

import com.sinosoft.schema.OtTravelPeopleInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class OtTravelPeopleInfoSet extends SchemaSet {
	public OtTravelPeopleInfoSet() {
		this(10,0);
	}

	public OtTravelPeopleInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public OtTravelPeopleInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = OtTravelPeopleInfoSchema._TableCode;
		Columns = OtTravelPeopleInfoSchema._Columns;
		NameSpace = OtTravelPeopleInfoSchema._NameSpace;
		InsertAllSQL = OtTravelPeopleInfoSchema._InsertAllSQL;
		UpdateAllSQL = OtTravelPeopleInfoSchema._UpdateAllSQL;
		FillAllSQL = OtTravelPeopleInfoSchema._FillAllSQL;
		DeleteSQL = OtTravelPeopleInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new OtTravelPeopleInfoSet();
	}

	public boolean add(OtTravelPeopleInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(OtTravelPeopleInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(OtTravelPeopleInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public OtTravelPeopleInfoSchema get(int index) {
		OtTravelPeopleInfoSchema tSchema = (OtTravelPeopleInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, OtTravelPeopleInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(OtTravelPeopleInfoSet aSet) {
		return super.set(aSet);
	}
}
 