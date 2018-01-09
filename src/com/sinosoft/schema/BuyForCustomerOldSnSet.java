package com.sinosoft.schema;

import com.sinosoft.schema.BuyForCustomerOldSnSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class BuyForCustomerOldSnSet extends SchemaSet {
	public BuyForCustomerOldSnSet() {
		this(10,0);
	}

	public BuyForCustomerOldSnSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BuyForCustomerOldSnSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BuyForCustomerOldSnSchema._TableCode;
		Columns = BuyForCustomerOldSnSchema._Columns;
		NameSpace = BuyForCustomerOldSnSchema._NameSpace;
		InsertAllSQL = BuyForCustomerOldSnSchema._InsertAllSQL;
		UpdateAllSQL = BuyForCustomerOldSnSchema._UpdateAllSQL;
		FillAllSQL = BuyForCustomerOldSnSchema._FillAllSQL;
		DeleteSQL = BuyForCustomerOldSnSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BuyForCustomerOldSnSet();
	}

	public boolean add(BuyForCustomerOldSnSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BuyForCustomerOldSnSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BuyForCustomerOldSnSchema aSchema) {
		return super.remove(aSchema);
	}

	public BuyForCustomerOldSnSchema get(int index) {
		BuyForCustomerOldSnSchema tSchema = (BuyForCustomerOldSnSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BuyForCustomerOldSnSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BuyForCustomerOldSnSet aSet) {
		return super.set(aSet);
	}
}
 