package com.sinosoft.schema;

import com.sinosoft.schema.TaobaoMemberAnalysisSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class TaobaoMemberAnalysisSet extends SchemaSet {

	private static final long serialVersionUID = 1L;
	public TaobaoMemberAnalysisSet() {
		this(10,0);
	}

	public TaobaoMemberAnalysisSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public TaobaoMemberAnalysisSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = TaobaoMemberAnalysisSchema._TableCode;
		Columns = TaobaoMemberAnalysisSchema._Columns;
		NameSpace = TaobaoMemberAnalysisSchema._NameSpace;
		InsertAllSQL = TaobaoMemberAnalysisSchema._InsertAllSQL;
		UpdateAllSQL = TaobaoMemberAnalysisSchema._UpdateAllSQL;
		FillAllSQL = TaobaoMemberAnalysisSchema._FillAllSQL;
		DeleteSQL = TaobaoMemberAnalysisSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new TaobaoMemberAnalysisSet();
	}

	public boolean add(TaobaoMemberAnalysisSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(TaobaoMemberAnalysisSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(TaobaoMemberAnalysisSchema aSchema) {
		return super.remove(aSchema);
	}

	public TaobaoMemberAnalysisSchema get(int index) {
		TaobaoMemberAnalysisSchema tSchema = (TaobaoMemberAnalysisSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, TaobaoMemberAnalysisSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(TaobaoMemberAnalysisSet aSet) {
		return super.set(aSet);
	}
}
