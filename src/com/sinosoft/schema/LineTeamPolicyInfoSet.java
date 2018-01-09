package com.sinosoft.schema;

import com.sinosoft.schema.LineTeamPolicyInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class LineTeamPolicyInfoSet extends SchemaSet {
	public LineTeamPolicyInfoSet() {
		this(10,0);
	}

	public LineTeamPolicyInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public LineTeamPolicyInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = LineTeamPolicyInfoSchema._TableCode;
		Columns = LineTeamPolicyInfoSchema._Columns;
		NameSpace = LineTeamPolicyInfoSchema._NameSpace;
		InsertAllSQL = LineTeamPolicyInfoSchema._InsertAllSQL;
		UpdateAllSQL = LineTeamPolicyInfoSchema._UpdateAllSQL;
		FillAllSQL = LineTeamPolicyInfoSchema._FillAllSQL;
		DeleteSQL = LineTeamPolicyInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new LineTeamPolicyInfoSet();
	}

	public boolean add(LineTeamPolicyInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(LineTeamPolicyInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(LineTeamPolicyInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public LineTeamPolicyInfoSchema get(int index) {
		LineTeamPolicyInfoSchema tSchema = (LineTeamPolicyInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, LineTeamPolicyInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(LineTeamPolicyInfoSet aSet) {
		return super.set(aSet);
	}
	
}
 