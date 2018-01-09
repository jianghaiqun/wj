package com.sinosoft.product;

import com.sinosoft.framework.data.DataRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@SuppressWarnings("unchecked")
public class SortList<E>{
	private static final Logger logger = LoggerFactory.getLogger(SortList.class);

	public void Sort(List<E> list, final String field,final String sort){
		Collections.sort(list, new Comparator() {			
		    public int compare(Object a, Object b) {
		    	int ret = 0;
				try {
					DataRow dr0 = (DataRow) a;
					DataRow dr1 = (DataRow) b;

					if ("initPrem".equalsIgnoreCase(field)) {
						Float Num0 = Float.parseFloat(dr0.getString("initPrem").toString());
						Float Num1 = Float.parseFloat(dr1.getString("initPrem").toString());
						if (sort != null && "desc".equals(sort))// 倒序
							ret = Num0.compareTo(Num1);
						else
							// 正序
							ret = Num1.compareTo(Num0);
					} else if ("Popular".equalsIgnoreCase(field)) {
						Integer Num0 = Integer.parseInt(dr0.getString("Popular").toString());
						Integer Num1 = Integer.parseInt(dr1.getString("Popular").toString());
						if (sort != null && "desc".equals(sort))// 倒序
							ret = Num0.compareTo(Num1);
						else
							// 正序
							ret = Num1.compareTo(Num0);
					} else if ("SalesVolume".equalsIgnoreCase(field)) {
						Integer Num0 = Integer.parseInt(dr0.getString("SalesVolume").toString());
						Integer Num1 = Integer.parseInt(dr1.getString("SalesVolume").toString());
						if (sort != null && "desc".equals(sort))// 倒序
							ret = Num0.compareTo(Num1);
						else
							// 正序
							ret = Num1.compareTo(Num0);
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
		    	return ret;
		    }
		 });
	}
}

