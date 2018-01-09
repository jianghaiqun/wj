package com.sinosoft.schema;

import com.sinosoft.schema.TaobaoMemberRepurchaseDetailSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class TaobaoMemberRepurchaseDetailSet extends SchemaSet {

	private static final long serialVersionUID = 1L;
	public TaobaoMemberRepurchaseDetailSet() {
		this(10,0);
	}

	public TaobaoMemberRepurchaseDetailSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public TaobaoMemberRepurchaseDetailSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = TaobaoMemberRepurchaseDetailSchema._TableCode;
		Columns = TaobaoMemberRepurchaseDetailSchema._Columns;
		NameSpace = TaobaoMemberRepurchaseDetailSchema._NameSpace;
		InsertAllSQL = TaobaoMemberRepurchaseDetailSchema._InsertAllSQL;
		UpdateAllSQL = TaobaoMemberRepurchaseDetailSchema._UpdateAllSQL;
		FillAllSQL = TaobaoMemberRepurchaseDetailSchema._FillAllSQL;
		DeleteSQL = TaobaoMemberRepurchaseDetailSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new TaobaoMemberRepurchaseDetailSet();
	}

	public boolean add(TaobaoMemberRepurchaseDetailSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(TaobaoMemberRepurchaseDetailSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(TaobaoMemberRepurchaseDetailSchema aSchema) {
		return super.remove(aSchema);
	}

	public TaobaoMemberRepurchaseDetailSchema get(int index) {
		TaobaoMemberRepurchaseDetailSchema tSchema = (TaobaoMemberRepurchaseDetailSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, TaobaoMemberRepurchaseDetailSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(TaobaoMemberRepurchaseDetailSet aSet) {
		return super.set(aSet);
	}
}
