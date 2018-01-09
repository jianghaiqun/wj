package com.sinosoft.schema;

import com.sinosoft.schema.CustomerMsgSupplySchema;
import com.sinosoft.framework.orm.SchemaSet;

public class CustomerMsgSupplySet extends SchemaSet {
	public CustomerMsgSupplySet() {
		this(10,0);
	}

	public CustomerMsgSupplySet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public CustomerMsgSupplySet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = CustomerMsgSupplySchema._TableCode;
		Columns = CustomerMsgSupplySchema._Columns;
		NameSpace = CustomerMsgSupplySchema._NameSpace;
		InsertAllSQL = CustomerMsgSupplySchema._InsertAllSQL;
		UpdateAllSQL = CustomerMsgSupplySchema._UpdateAllSQL;
		FillAllSQL = CustomerMsgSupplySchema._FillAllSQL;
		DeleteSQL = CustomerMsgSupplySchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new CustomerMsgSupplySet();
	}

	public boolean add(CustomerMsgSupplySchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(CustomerMsgSupplySet aSet) {
		return super.add(aSet);
	}

	public boolean remove(CustomerMsgSupplySchema aSchema) {
		return super.remove(aSchema);
	}

	public CustomerMsgSupplySchema get(int index) {
		CustomerMsgSupplySchema tSchema = (CustomerMsgSupplySchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, CustomerMsgSupplySchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(CustomerMsgSupplySet aSet) {
		return super.set(aSet);
	}
}
 