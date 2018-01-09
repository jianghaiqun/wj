package com.sinosoft.schema;

import com.sinosoft.schema.ZDMailConfigImageSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class ZDMailConfigImageSet extends SchemaSet {
	public ZDMailConfigImageSet() {
		this(10,0);
	}

	public ZDMailConfigImageSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZDMailConfigImageSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZDMailConfigImageSchema._TableCode;
		Columns = ZDMailConfigImageSchema._Columns;
		NameSpace = ZDMailConfigImageSchema._NameSpace;
		InsertAllSQL = ZDMailConfigImageSchema._InsertAllSQL;
		UpdateAllSQL = ZDMailConfigImageSchema._UpdateAllSQL;
		FillAllSQL = ZDMailConfigImageSchema._FillAllSQL;
		DeleteSQL = ZDMailConfigImageSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZDMailConfigImageSet();
	}

	public boolean add(ZDMailConfigImageSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZDMailConfigImageSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZDMailConfigImageSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZDMailConfigImageSchema get(int index) {
		ZDMailConfigImageSchema tSchema = (ZDMailConfigImageSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZDMailConfigImageSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZDMailConfigImageSet aSet) {
		return super.set(aSet);
	}
}
 