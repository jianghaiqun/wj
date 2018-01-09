package com.sinosoft.schema;

import com.sinosoft.schema.claimstatisticsdetailsSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class claimstatisticsdetailsSet extends SchemaSet {
	public claimstatisticsdetailsSet() {
		this(10,0);
	}

	public claimstatisticsdetailsSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public claimstatisticsdetailsSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = claimstatisticsdetailsSchema._TableCode;
		Columns = claimstatisticsdetailsSchema._Columns;
		NameSpace = claimstatisticsdetailsSchema._NameSpace;
		InsertAllSQL = claimstatisticsdetailsSchema._InsertAllSQL;
		UpdateAllSQL = claimstatisticsdetailsSchema._UpdateAllSQL;
		FillAllSQL = claimstatisticsdetailsSchema._FillAllSQL;
		DeleteSQL = claimstatisticsdetailsSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new claimstatisticsdetailsSet();
	}

	public boolean add(claimstatisticsdetailsSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(claimstatisticsdetailsSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(claimstatisticsdetailsSchema aSchema) {
		return super.remove(aSchema);
	}

	public claimstatisticsdetailsSchema get(int index) {
		claimstatisticsdetailsSchema tSchema = (claimstatisticsdetailsSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, claimstatisticsdetailsSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(claimstatisticsdetailsSet aSet) {
		return super.set(aSet);
	}
}
 