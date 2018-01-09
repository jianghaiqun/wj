package cn.com.sinosoft.action.shop;

public class AreaTest {

	public static void main(String[] args) {

		// 非县级市地址顺序
		String str = "市，盟；州，县，区，旗；乡，镇，街，路，道，巷，委，段，里，社，园，苑；村，组，号，室，队，院，房，楼，栋";
		// 县级市地址顺序
		String str1 = "市，盟；乡，镇，街，路，道，巷，委，段，里，社，园，苑；村，组，号，室，队，院，房，楼，栋";

		System.out.println(AreaTest.isOrderContainAmong("福建省福州市鼓楼区杨桥中路267号茶山苑2座501单元", str));
		System.out.println(AreaTest.isOrderContainAmong("福建省长乐市鼓楼区杨桥中路267号", str1));
	}

	public static boolean isOrderContainAmong(String str, String str0) {

		// 排除小区和社区对校验的影响
		str = str.replace("小区", "").replace("社区", "");
		String[] str1 = new String[] {};
		str1 = str0.split(";");
		if (str1.length == 1) {
			str1 = str0.split("；");
		}
		// str1[i]=市，盟;||州，县，区，旗；
		int[] a = new int[str1.length];
		for (int i = 0; i < str1.length; i++) {
			String strString = str1[i];
			String[] str2 = new String[] {};
			str2 = strString.split(",");
			if (str2.length == 1) {
				str2 = strString.split("，");
			}
			// str2[j]= =市||盟 的数组
			for (int j = 0; j < str2.length; j++) {
				if (str.indexOf(str2[j]) != -1) {
					a[i] = a[i] < str.lastIndexOf(str2[j]) ? str.lastIndexOf(str2[j]) : a[i];
				}
			}
		}
		for (int k = 0; k < a.length - 1; k++) {
			if (a[k] > a[k + 1])
			{
				return false;
			}
		}
		return true;

	}
}
