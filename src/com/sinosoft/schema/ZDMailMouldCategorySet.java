package com.sinosoft.schema;

import com.sinosoft.schema.ZDMailMouldCategorySchema;
import com.sinosoft.framework.orm.SchemaSet;

public class ZDMailMouldCategorySet extends SchemaSet {
	public ZDMailMouldCategorySet() {
		this(10,0);
	}

	public ZDMailMouldCategorySet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZDMailMouldCategorySet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZDMailMouldCategorySchema._TableCode;
		Columns = ZDMailMouldCategorySchema._Columns;
		NameSpace = ZDMailMouldCategorySchema._NameSpace;
		InsertAllSQL = ZDMailMouldCategorySchema._InsertAllSQL;
		UpdateAllSQL = ZDMailMouldCategorySchema._UpdateAllSQL;
		FillAllSQL = ZDMailMouldCategorySchema._FillAllSQL;
		DeleteSQL = ZDMailMouldCategorySchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZDMailMouldCategorySet();
	}

	public boolean add(ZDMailMouldCategorySchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZDMailMouldCategorySet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZDMailMouldCategorySchema aSchema) {
		return super.remove(aSchema);
	}

	public ZDMailMouldCategorySchema get(int index) {
		ZDMailMouldCategorySchema tSchema = (ZDMailMouldCategorySchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZDMailMouldCategorySchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZDMailMouldCategorySet aSet) {
		return super.set(aSet);
	}
}
 