package cn.com.sinosoft.action.shop;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import cn.com.sinosoft.entity.ModuleInfo;

public class ComparatorMap implements Comparator{

	@Override
	public int compare(Object arg0, Object arg1) {
		// TODO Auto-generated method stub

		String muti0 = (String) arg0; 
		String muti1 = (String) arg1; 
		
		int flag = muti0.compareTo(muti1);
		return  flag;
	}
}
