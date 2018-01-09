package com.sinosoft.schema;

import com.sinosoft.schema.SDPointRuleSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDPointRuleSet extends SchemaSet {
	public SDPointRuleSet() { 
		this(10,0);
	}

	public SDPointRuleSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDPointRuleSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDPointRuleSchema._TableCode;
		Columns = SDPointRuleSchema._Columns;
		NameSpace = SDPointRuleSchema._NameSpace;
		InsertAllSQL = SDPointRuleSchema._InsertAllSQL;
		UpdateAllSQL = SDPointRuleSchema._UpdateAllSQL;
		FillAllSQL = SDPointRuleSchema._FillAllSQL;
		DeleteSQL = SDPointRuleSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDPointRuleSet();
	}

	public boolean add(SDPointRuleSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDPointRuleSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDPointRuleSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDPointRuleSchema get(int index) {
		SDPointRuleSchema tSchema = (SDPointRuleSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDPointRuleSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDPointRuleSet aSet) {
		return super.set(aSet);
	}
}
 