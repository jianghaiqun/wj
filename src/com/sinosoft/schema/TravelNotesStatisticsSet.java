package com.sinosoft.schema;

import com.sinosoft.schema.TravelNotesStatisticsSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class TravelNotesStatisticsSet extends SchemaSet {
	public TravelNotesStatisticsSet() {
		this(10,0);
	}

	public TravelNotesStatisticsSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public TravelNotesStatisticsSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = TravelNotesStatisticsSchema._TableCode;
		Columns = TravelNotesStatisticsSchema._Columns;
		NameSpace = TravelNotesStatisticsSchema._NameSpace;
		InsertAllSQL = TravelNotesStatisticsSchema._InsertAllSQL;
		UpdateAllSQL = TravelNotesStatisticsSchema._UpdateAllSQL;
		FillAllSQL = TravelNotesStatisticsSchema._FillAllSQL;
		DeleteSQL = TravelNotesStatisticsSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new TravelNotesStatisticsSet();
	}

	public boolean add(TravelNotesStatisticsSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(TravelNotesStatisticsSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(TravelNotesStatisticsSchema aSchema) {
		return super.remove(aSchema);
	}

	public TravelNotesStatisticsSchema get(int index) {
		TravelNotesStatisticsSchema tSchema = (TravelNotesStatisticsSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, TravelNotesStatisticsSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(TravelNotesStatisticsSet aSet) {
		return super.set(aSet);
	}
}
 