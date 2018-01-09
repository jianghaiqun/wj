package com.sinosoft.schema;

import com.sinosoft.framework.orm.SchemaSet;

public class ServiceCallRecordSet extends SchemaSet {
	public ServiceCallRecordSet() {
		this(10,0);
	}

	public ServiceCallRecordSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ServiceCallRecordSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ServiceCallRecordSchema._TableCode;
		Columns = ServiceCallRecordSchema._Columns;
		NameSpace = ServiceCallRecordSchema._NameSpace;
		InsertAllSQL = ServiceCallRecordSchema._InsertAllSQL;
		UpdateAllSQL = ServiceCallRecordSchema._UpdateAllSQL;
		FillAllSQL = ServiceCallRecordSchema._FillAllSQL;
		DeleteSQL = ServiceCallRecordSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ServiceCallRecordSet();
	}

	public boolean add(ServiceCallRecordSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ServiceCallRecordSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ServiceCallRecordSchema aSchema) {
		return super.remove(aSchema);
	}

	public ServiceCallRecordSchema get(int index) {
		ServiceCallRecordSchema tSchema = (ServiceCallRecordSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ServiceCallRecordSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ServiceCallRecordSet aSet) {
		return super.set(aSet);
	}
}
 