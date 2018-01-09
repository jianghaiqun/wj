package cn.com.sinosoft.action.shop;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import cn.com.sinosoft.entity.ModuleInfo;

public class ComparatorModule implements Comparator{

	@Override
	public int compare(Object arg0, Object arg1) {
		// TODO Auto-generated method stub
		ModuleInfo mi1 = (ModuleInfo)arg0;
		ModuleInfo mi2 = (ModuleInfo)arg1;
		
		//int flag=user0.getAge().compareTo(user1.getAge());
		int flag = String.valueOf(mi1.getModuleOrder()).compareTo(String.valueOf(mi2.getModuleOrder()));
		return flag;
	}
}
