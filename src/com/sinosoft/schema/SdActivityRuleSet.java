package com.sinosoft.schema;

import com.sinosoft.schema.SdActivityRuleSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SdActivityRuleSet extends SchemaSet {
	public SdActivityRuleSet() {
		this(10,0);
	}

	public SdActivityRuleSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SdActivityRuleSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SdActivityRuleSchema._TableCode;
		Columns = SdActivityRuleSchema._Columns;
		NameSpace = SdActivityRuleSchema._NameSpace;
		InsertAllSQL = SdActivityRuleSchema._InsertAllSQL;
		UpdateAllSQL = SdActivityRuleSchema._UpdateAllSQL;
		FillAllSQL = SdActivityRuleSchema._FillAllSQL;
		DeleteSQL = SdActivityRuleSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SdActivityRuleSet();
	}

	public boolean add(SdActivityRuleSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SdActivityRuleSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SdActivityRuleSchema aSchema) {
		return super.remove(aSchema);
	}

	public SdActivityRuleSchema get(int index) {
		SdActivityRuleSchema tSchema = (SdActivityRuleSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SdActivityRuleSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SdActivityRuleSet aSet) {
		return super.set(aSet);
	}
}
 