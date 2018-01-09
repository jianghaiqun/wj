package com.sinosoft.schema;

import com.sinosoft.schema.LineTeamRateSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class LineTeamRateSet extends SchemaSet {
	public LineTeamRateSet() {
		this(10,0);
	}

	public LineTeamRateSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public LineTeamRateSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = LineTeamRateSchema._TableCode;
		Columns = LineTeamRateSchema._Columns;
		NameSpace = LineTeamRateSchema._NameSpace;
		InsertAllSQL = LineTeamRateSchema._InsertAllSQL;
		UpdateAllSQL = LineTeamRateSchema._UpdateAllSQL;
		FillAllSQL = LineTeamRateSchema._FillAllSQL;
		DeleteSQL = LineTeamRateSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new LineTeamRateSet();
	}

	public boolean add(LineTeamRateSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(LineTeamRateSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(LineTeamRateSchema aSchema) {
		return super.remove(aSchema);
	}

	public LineTeamRateSchema get(int index) {
		LineTeamRateSchema tSchema = (LineTeamRateSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, LineTeamRateSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(LineTeamRateSet aSet) {
		return super.set(aSet);
	}
}
 