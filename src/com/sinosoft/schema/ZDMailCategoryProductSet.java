package com.sinosoft.schema;

import com.sinosoft.schema.ZDMailCategoryProductSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class ZDMailCategoryProductSet extends SchemaSet {
	public ZDMailCategoryProductSet() {
		this(10,0);
	}

	public ZDMailCategoryProductSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZDMailCategoryProductSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZDMailCategoryProductSchema._TableCode;
		Columns = ZDMailCategoryProductSchema._Columns;
		NameSpace = ZDMailCategoryProductSchema._NameSpace;
		InsertAllSQL = ZDMailCategoryProductSchema._InsertAllSQL;
		UpdateAllSQL = ZDMailCategoryProductSchema._UpdateAllSQL;
		FillAllSQL = ZDMailCategoryProductSchema._FillAllSQL;
		DeleteSQL = ZDMailCategoryProductSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZDMailCategoryProductSet();
	}

	public boolean add(ZDMailCategoryProductSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZDMailCategoryProductSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZDMailCategoryProductSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZDMailCategoryProductSchema get(int index) {
		ZDMailCategoryProductSchema tSchema = (ZDMailCategoryProductSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZDMailCategoryProductSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZDMailCategoryProductSet aSet) {
		return super.set(aSet);
	}
}
 