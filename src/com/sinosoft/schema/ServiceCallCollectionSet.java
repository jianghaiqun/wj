package com.sinosoft.schema;

import com.sinosoft.schema.ServiceCallCollectionSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class ServiceCallCollectionSet extends SchemaSet {
	public ServiceCallCollectionSet() {
		this(10,0);
	}

	public ServiceCallCollectionSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ServiceCallCollectionSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ServiceCallCollectionSchema._TableCode;
		Columns = ServiceCallCollectionSchema._Columns;
		NameSpace = ServiceCallCollectionSchema._NameSpace;
		InsertAllSQL = ServiceCallCollectionSchema._InsertAllSQL;
		UpdateAllSQL = ServiceCallCollectionSchema._UpdateAllSQL;
		FillAllSQL = ServiceCallCollectionSchema._FillAllSQL;
		DeleteSQL = ServiceCallCollectionSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ServiceCallCollectionSet();
	}

	public boolean add(ServiceCallCollectionSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ServiceCallCollectionSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ServiceCallCollectionSchema aSchema) {
		return super.remove(aSchema);
	}

	public ServiceCallCollectionSchema get(int index) {
		ServiceCallCollectionSchema tSchema = (ServiceCallCollectionSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ServiceCallCollectionSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ServiceCallCollectionSet aSet) {
		return super.set(aSet);
	}
}
 