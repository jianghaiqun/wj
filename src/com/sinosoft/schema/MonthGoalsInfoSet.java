package com.sinosoft.schema;

import com.sinosoft.schema.MonthGoalsInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class MonthGoalsInfoSet extends SchemaSet {
	public MonthGoalsInfoSet() {
		this(10,0);
	}

	public MonthGoalsInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public MonthGoalsInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = MonthGoalsInfoSchema._TableCode;
		Columns = MonthGoalsInfoSchema._Columns;
		NameSpace = MonthGoalsInfoSchema._NameSpace;
		InsertAllSQL = MonthGoalsInfoSchema._InsertAllSQL;
		UpdateAllSQL = MonthGoalsInfoSchema._UpdateAllSQL;
		FillAllSQL = MonthGoalsInfoSchema._FillAllSQL;
		DeleteSQL = MonthGoalsInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new MonthGoalsInfoSet();
	}

	public boolean add(MonthGoalsInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(MonthGoalsInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(MonthGoalsInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public MonthGoalsInfoSchema get(int index) {
		MonthGoalsInfoSchema tSchema = (MonthGoalsInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, MonthGoalsInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(MonthGoalsInfoSet aSet) {
		return super.set(aSet);
	}
}
 