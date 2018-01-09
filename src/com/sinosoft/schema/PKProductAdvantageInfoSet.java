package com.sinosoft.schema;

import com.sinosoft.schema.PKProductAdvantageInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class PKProductAdvantageInfoSet extends SchemaSet {
	public PKProductAdvantageInfoSet() {
		this(10,0);
	}

	public PKProductAdvantageInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public PKProductAdvantageInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = PKProductAdvantageInfoSchema._TableCode;
		Columns = PKProductAdvantageInfoSchema._Columns;
		NameSpace = PKProductAdvantageInfoSchema._NameSpace;
		InsertAllSQL = PKProductAdvantageInfoSchema._InsertAllSQL;
		UpdateAllSQL = PKProductAdvantageInfoSchema._UpdateAllSQL;
		FillAllSQL = PKProductAdvantageInfoSchema._FillAllSQL;
		DeleteSQL = PKProductAdvantageInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new PKProductAdvantageInfoSet();
	}

	public boolean add(PKProductAdvantageInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(PKProductAdvantageInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(PKProductAdvantageInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public PKProductAdvantageInfoSchema get(int index) {
		PKProductAdvantageInfoSchema tSchema = (PKProductAdvantageInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, PKProductAdvantageInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(PKProductAdvantageInfoSet aSet) {
		return super.set(aSet);
	}
}
 