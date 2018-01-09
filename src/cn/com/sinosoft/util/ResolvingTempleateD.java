/**
 * @CopyRight:Sinosoft
 * @File:ResolvingTempleateD.java
 * @CreateTime:2012-5-8 上午3:50:36
 * @Package:cn.com.sinosoft.util
 * @Author:LiuXin
 * @Version:1.0
 */
package cn.com.sinosoft.util;

import cn.com.sinosoft.entity.PremiumRateTemp;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author LiuXin
 *
 */
public class ResolvingTempleateD {
	private static final Logger logger = LoggerFactory.getLogger(ResolvingTempleateD.class);
	
	public PremiumRateTemp resolving(HSSFRow row, PremiumRateTemp prt) {
		logger.info("RowNum:{]", row.getRowNum());
		switch (row.getRowNum()) {
		case 6: {// 家庭自用汽车
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_PERSONAL);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_UnBusiness);
			prt.setPassengerLower(0);
			prt.setPasserngerUpper(6);
			break;
		}
		case 7: {// 家庭自用汽车
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_PERSONAL);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_UnBusiness);
			prt.setPassengerLower(6);
			prt.setPasserngerUpper(Integer.MAX_VALUE);
			break;
		}
		case 55: {
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_PERSONAL);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_UnBusiness);
			prt.setPassengerLower(0);
			prt.setPasserngerUpper(6);
			break;
		}
		case 56: {
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_PERSONAL);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_UnBusiness);
			prt.setPassengerLower(6);
			prt.setPasserngerUpper(Integer.MAX_VALUE);
			break;
		}
		case 8: {// 机关、事业单位非营业客车
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_OFFICE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_UnBusiness);
			prt.setPassengerLower(0);
			prt.setPasserngerUpper(6);
			break;
		}
		case 9: {
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_OFFICE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_UnBusiness);
			prt.setPassengerLower(6);
			prt.setPasserngerUpper(10);
			break;
		}
		case 10: {
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_OFFICE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_UnBusiness);
			prt.setPassengerLower(10);
			prt.setPasserngerUpper(20);
			break;
		}
		case 11: {
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_OFFICE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_UnBusiness);
			prt.setPassengerLower(20);
			prt.setPasserngerUpper(Integer.MAX_VALUE);
			break;
		}
		case 57: {// 机关、事业单位非营业客车
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_OFFICE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_UnBusiness);
			prt.setPassengerLower(0);
			prt.setPasserngerUpper(6);
			break;
		}
		case 58: {
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_OFFICE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_UnBusiness);
			prt.setPassengerLower(6);
			prt.setPasserngerUpper(10);
			break;
		}
		case 59: {
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_OFFICE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_UnBusiness);
			prt.setPassengerLower(10);
			prt.setPasserngerUpper(20);
			break;
		}
		case 60: {
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_OFFICE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_UnBusiness);
			prt.setPassengerLower(20);
			prt.setPasserngerUpper(Integer.MAX_VALUE);
			break;
		}
		case 12: {// 企业非营业客车
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_UnBusiness);
			prt.setPassengerLower(0);
			prt.setPasserngerUpper(6);
			break;
		}
		case 13: {
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_UnBusiness);
			prt.setPassengerLower(6);
			prt.setPasserngerUpper(10);
			break;
		}
		case 14: {
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_UnBusiness);
			prt.setPassengerLower(10);
			prt.setPasserngerUpper(20);
			break;
		}
		case 15: {
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_UnBusiness);
			prt.setPassengerLower(20);
			prt.setPasserngerUpper(Integer.MAX_VALUE);
		}
		case 61: {// 企业非营业客车
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_UnBusiness);
			prt.setPassengerLower(0);
			prt.setPasserngerUpper(6);
			break;
		}
		case 62: {
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_UnBusiness);
			prt.setPassengerLower(6);
			prt.setPasserngerUpper(10);
			break;
		}
		case 63: {
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_UnBusiness);
			prt.setPassengerLower(10);
			prt.setPasserngerUpper(20);
			break;
		}
		case 64: {
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_UnBusiness);
			prt.setPassengerLower(20);
			prt.setPasserngerUpper(Integer.MAX_VALUE);
		}
		case 16: {// 非营业货车--个人，企业，机关、事业单位无关
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Lorry);
			prt.setLoadLower(0);
			prt.setLoadUpper(2);
			break;
		}
		case 17: {
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Lorry);
			prt.setLoadLower(2);
			prt.setLoadUpper(5);
			break;
		}
		case 18: {
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Lorry);
			prt.setLoadLower(5);
			prt.setLoadUpper(10);
			break;
		}
		case 19: {
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Lorry);
			prt.setLoadLower(10);
			prt.setLoadUpper(Integer.MAX_VALUE);
			break;
		}
		case 65: {// 非营业货车--个人，企业，机关、事业单位无关
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Lorry);
			prt.setLoadLower(0);
			prt.setLoadUpper(2);
			break;
		}
		case 66: {
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Lorry);
			prt.setLoadLower(2);
			prt.setLoadUpper(5);
			break;
		}
		case 67: {
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Lorry);
			prt.setLoadLower(5);
			prt.setLoadUpper(10);
			break;
		}
		case 68: {
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Lorry);
			prt.setLoadLower(10);
			prt.setLoadUpper(Integer.MAX_VALUE);
			break;
		}
		case 20: {// 营运客车（出租、租赁）
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Lease);
			prt.setPassengerLower(0);
			prt.setPasserngerUpper(6);
			break;
		}
		case 21: {
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Lease);
			prt.setPassengerLower(6);
			prt.setPasserngerUpper(10);
			break;
		}
		case 22: {
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Lease);
			prt.setPassengerLower(10);
			prt.setPasserngerUpper(20);
			break;
		}
		case 23: {
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Lease);
			prt.setPassengerLower(20);
			prt.setPasserngerUpper(36);
			break;
		}
		case 24: {
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Lease);
			prt.setPassengerLower(36);
			prt.setPasserngerUpper(Integer.MAX_VALUE);
			break;
		}
		case 69: {// 营运客车（出租、租赁）
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Lease);
			prt.setPassengerLower(0);
			prt.setPasserngerUpper(6);
			break;
		}
		case 70: {
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Lease);
			prt.setPassengerLower(6);
			prt.setPasserngerUpper(10);
			break;
		}
		case 71: {
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Lease);
			prt.setPassengerLower(10);
			prt.setPasserngerUpper(20);
			break;
		}
		case 72: {
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Lease);
			prt.setPassengerLower(20);
			prt.setPasserngerUpper(36);
			break;
		}
		case 73: {
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Lease);
			prt.setPassengerLower(36);
			prt.setPasserngerUpper(Integer.MAX_VALUE);
			break;
		}
		case 25: {// 城市公交
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Bus);
			prt.setPassengerLower(6);
			prt.setPasserngerUpper(10);
			break;
		}
		case 26: {
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Bus);
			prt.setPassengerLower(10);
			prt.setPasserngerUpper(20);
			break;
		}
		case 27: {
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Bus);
			prt.setPassengerLower(20);
			prt.setPasserngerUpper(36);
			break;
		}
		case 28: {
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Bus);
			prt.setPassengerLower(36);
			prt.setPasserngerUpper(Integer.MAX_VALUE);
			break;
		}
		case 74: {// 城市公交
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Bus);
			prt.setPassengerLower(6);
			prt.setPasserngerUpper(10);
			break;
		}
		case 75: {
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Bus);
			prt.setPassengerLower(10);
			prt.setPasserngerUpper(20);
			break;
		}
		case 76: {
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Bus);
			prt.setPassengerLower(20);
			prt.setPasserngerUpper(36);
			break;
		}
		case 77: {
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Bus);
			prt.setPassengerLower(36);
			prt.setPasserngerUpper(Integer.MAX_VALUE);
			break;
		}
		case 29: {// 公路客运
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Highway);
			prt.setPassengerLower(6);
			prt.setPasserngerUpper(10);
			break;
		}
		case 30: {
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Highway);
			prt.setPassengerLower(10);
			prt.setPasserngerUpper(20);
			break;
		}
		case 31: {
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Highway);
			prt.setPassengerLower(20);
			prt.setPasserngerUpper(36);
			break;
		}
		case 32: {
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Highway);
			prt.setPassengerLower(36);
			prt.setPasserngerUpper(Integer.MAX_VALUE);
			break;
		}
		case 78: {// 公路客运
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Highway);
			prt.setPassengerLower(6);
			prt.setPasserngerUpper(10);
			break;
		}
		case 79: {
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Highway);
			prt.setPassengerLower(10);
			prt.setPasserngerUpper(20);
			break;
		}
		case 80: {
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Highway);
			prt.setPassengerLower(20);
			prt.setPasserngerUpper(36);
			break;
		}
		case 81: {
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Highway);
			prt.setPassengerLower(36);
			prt.setPasserngerUpper(Integer.MAX_VALUE);
			break;
		}
		case 33: {// 营业货车--个人，企业，机关、事业单位无关
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Lorry);
			prt.setLoadLower(0);
			prt.setLoadUpper(2);
			break;
		}
		case 34: {
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Lorry);
			prt.setLoadLower(2);
			prt.setLoadUpper(5);
			break;
		}
		case 35: {
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Lorry);
			prt.setLoadLower(5);
			prt.setLoadUpper(10);
			break;
		}
		case 36: {
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Lorry);
			prt.setLoadLower(10);
			prt.setLoadUpper(Integer.MAX_VALUE);
			break;
		}
		case 82: {// 营业货车--个人，企业，机关、事业单位无关
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Lorry);
			prt.setLoadLower(0);
			prt.setLoadUpper(2);
			break;
		}
		case 83: {
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Lorry);
			prt.setLoadLower(2);
			prt.setLoadUpper(5);
			break;
		}
		case 84: {
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Lorry);
			prt.setLoadLower(5);
			prt.setLoadUpper(10);
			break;
		}
		case 85: {
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Lorry);
			prt.setLoadLower(10);
			prt.setLoadUpper(Integer.MAX_VALUE);
			break;
		}
		case 37: {// 特种车1
			prt.setUsedCode(null);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Special_1);
			break;
		}
		case 38: {// 特种车2
			prt.setUsedCode(null);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Special_2);
			break;
		}
		case 39: {// 特种车3
			prt.setUsedCode(null);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Special_3);
			break;
		}
		case 40: {// 特种车4
			prt.setUsedCode(null);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Special_4);
			break;
		}
		case 86: {// 特种车1
			prt.setUsedCode(null);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Special_1);
			break;
		}
		case 87: {// 特种车2
			prt.setUsedCode(null);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Special_2);
			break;
		}
		case 88: {// 特种车3
			prt.setUsedCode(null);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Special_3);
			break;
		}
		case 89: {// 特种车4
			prt.setUsedCode(null);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Special_4);
			break;
		}
		case 41: {// 摩托车
			prt.setUsedCode(null);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Motorcycle_Below_50);
			break;
		}
		case 42: {// 摩托车
			prt.setUsedCode(null);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Motorcycle_50_250);
			break;
		}
		case 43: {// 摩托车
			prt.setUsedCode(null);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Motorcycle_Above_250);
			break;
		}
		case 90: {// 摩托车
			prt.setUsedCode(null);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Motorcycle_Below_50);
			break;
		}
		case 91: {// 摩托车
			prt.setUsedCode(null);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Motorcycle_50_250);
			break;
		}
		case 92: {// 摩托车
			prt.setUsedCode(null);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Motorcycle_Above_250);
			break;
		}
		case 44: {// 拖拉机
			prt.setUsedCode(null);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Tractor_DualPurpose);
			break;
		}
		case 45: {// 拖拉机
			prt.setUsedCode(null);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Tractor_DualPurpose_Above147);
			break;
		}
		case 46: {// 拖拉机
			prt.setUsedCode(null);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Tractor_Transport);
			break;
		}
		case 47: {// 拖拉机
			prt.setUsedCode(null);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Tractor_Transport_Above147);
			break;
		}
		case 93: {// 拖拉机
			prt.setUsedCode(null);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Tractor_DualPurpose);
			break;
		}
		case 94: {// 拖拉机
			prt.setUsedCode(null);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Tractor_DualPurpose_Above147);
			break;
		}
		case 95: {// 拖拉机
			prt.setUsedCode(null);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Tractor_Transport);
			break;
		}
		case 96: {// 拖拉机
			prt.setUsedCode(null);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Tractor_Transport_Above147);
			break;
		}
		case 108: {//非营业客车自然损失险
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_UnBusiness);
			break;
		}
		case 109: {//非营业特种车自然损失险
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Special_1);
			break;
		}
		case 110: {//营业出租自然损失险
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Hire);
			break;
		}
		case 111: {//营业租赁自然损失险
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Lease);
			break;
		}
		case 112: {//城市公交自然损失险
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Bus);
			break;
		}
		case 113: {//公路客运自然损失险
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Highway);
			break;
		}
		case 114: {//货车自然损失险
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Lorry);
			break;
		}
		case 115: {//营业特种车自然损失险
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Special_1);
			break;
		}
		}
		return prt;
	}

	/**
	 * 导入D模板-初始化第三者责任险数据
	 * 
	 * @param colum
	 * @return
	 */
	public PremiumRateTemp initDSZD(HSSFCell cell, PremiumRateTemp prt) {
		logger.info("行号：{}||列号：{}",cell.getRowIndex(), cell.getColumnIndex());
		prt.setRiskCode(Constant.RISK_1);
		prt.setRateType(Constant.RT_BASE);
		switch (cell.getColumnIndex()) {
		case 4: {
			prt.setSumassured(5);
			break;
		}
		case 5: {
			prt.setSumassured(10);
			break;
		}
		case 6: {
			prt.setSumassured(15);
			break;
		}
		case 7: {
			prt.setSumassured(20);
			break;
		}
		case 8: {
			prt.setSumassured(30);
			break;
		}
		case 9: {
			prt.setSumassured(50);
			break;
		}
		case 10: {
			prt.setSumassured(100);
			break;
		}
		}
		if (cell.getColumnIndex() >= 4 && cell.getColumnIndex() <= 10) {
			prt.setPremium(cell.getNumericCellValue());
		}
		return prt;
	}

	/**
	 * 导入D模板车损险
	 * 
	 * @param colum
	 * @return
	 */
	public PremiumRateTemp initCSXD(HSSFCell cell, PremiumRateTemp prt) {
		prt.setRiskCode(Constant.RISK_2);
		prt.setRateType(Constant.RT_BASE);
		switch (cell.getColumnIndex()) {
		case 11: {// 取得 基础保费
			prt.setPremium(cell.getNumericCellValue());
			break;
		}
		case 12: {// 取得费率并保存记录
			prt.setAgeLower(0);
			prt.setAgeUpper(1);
			prt.setRate(cell.getNumericCellValue() / 100);
			break;
		}
		case 13: {
			prt.setAgeLower(1);
			prt.setAgeUpper(4);
			prt.setRate(cell.getNumericCellValue() / 100);
			break;
		}
		case 14: {
			prt.setAgeLower(4);
			prt.setAgeUpper(6);
			prt.setRate(cell.getNumericCellValue() / 100);
			break;
		}
		case 15: {
			prt.setAgeLower(6);
			prt.setAgeUpper(8);
			prt.setRate(cell.getNumericCellValue() / 100);
			break;
		}
		case 16: {
			prt.setAgeLower(8);
			prt.setAgeUpper(Integer.MAX_VALUE);
			prt.setRate(cell.getNumericCellValue() / 100);
			break;
		}
		}
		return prt;
	}

	/**
	 * 导入D模板车碰车损失险
	 * 
	 * @param colum
	 * @return
	 */
	public PremiumRateTemp initCRASHD(HSSFCell cell, PremiumRateTemp prt) {
		switch (cell.getColumnIndex()) {
		case 4: {// 取得 基础保费
			prt.setAgeLower(0);
			prt.setAgeUpper(1);
			prt.setPremium(cell.getNumericCellValue());
			break;
		}
		case 5: {// 取得费率并保存记录
			prt.setRate(cell.getNumericCellValue() / 100);
			break;
		}
		case 6: {
			prt.setAgeLower(1);
			prt.setAgeUpper(4);
			prt.setPremium(cell.getNumericCellValue());
			break;
		}
		case 7: {
			prt.setRate(cell.getNumericCellValue() / 100);
			break;
		}
		case 8: {
			prt.setAgeLower(4);
			prt.setAgeUpper(6);
			prt.setPremium(cell.getNumericCellValue());
			break;
		}
		case 9: {
			prt.setRate(cell.getNumericCellValue() / 100);
			break;
		}
		case 10: {
			prt.setAgeLower(6);
			prt.setAgeUpper(8);
			prt.setPremium(cell.getNumericCellValue());
			break;
		}
		case 11: {
			prt.setRate(cell.getNumericCellValue() / 100);
			break;
		}
		case 12: {
			prt.setAgeLower(8);
			prt.setAgeUpper(Integer.MAX_VALUE);
			prt.setPremium(cell.getNumericCellValue());
			break;
		}
		case 13: {
			prt.setRate(cell.getNumericCellValue() / 100);
			break;
		}
		}
		return prt;
	}

	/**
	 * 导入D模板车辆综合损失险
	 * 
	 * @param colum
	 * @return
	 */
	public PremiumRateTemp initCOMPREHENSIVED(HSSFCell cell, PremiumRateTemp prt) {
		switch (cell.getColumnIndex()) {
		case 14: {// 取得 基础保费
			prt.setAgeLower(0);
			prt.setAgeUpper(1);
			prt.setPremium(cell.getNumericCellValue());
			break;
		}
		case 15: {// 取得费率并保存记录
			prt.setRate(cell.getNumericCellValue() / 100);
			break;
		}
		case 16: {
			prt.setAgeLower(1);
			prt.setAgeUpper(4);
			prt.setPremium(cell.getNumericCellValue());
			break;
		}
		case 17: {
			prt.setRate(cell.getNumericCellValue() / 100);
			break;
		}
		case 18: {
			prt.setAgeLower(4);
			prt.setAgeUpper(6);
			prt.setPremium(cell.getNumericCellValue());
			break;
		}
		case 19: {
			prt.setRate(cell.getNumericCellValue() / 100);
			break;
		}
		case 20: {
			prt.setAgeLower(6);
			prt.setAgeUpper(8);
			prt.setPremium(cell.getNumericCellValue());
			break;
		}
		case 21: {
			prt.setRate(cell.getNumericCellValue() / 100);
			break;
		}
		case 22: {
			prt.setAgeLower(8);
			prt.setAgeUpper(Integer.MAX_VALUE);
			prt.setPremium(cell.getNumericCellValue());
			break;
		}
		case 23: {
			prt.setRate(cell.getNumericCellValue() / 100);
			break;
		}
		}
		return prt;
	}

	/**
	 * 导入D模板车上人员责任险
	 * 
	 * @param colum
	 * @return
	 */
	public PremiumRateTemp initRYXD(HSSFCell cell, PremiumRateTemp prt) {
		prt.setRiskCode(Constant.RISK_3);
		switch (cell.getColumnIndex()) {
		case 17: {// 取得 驾驶人费率
			prt.setRateType(Constant.RT_DRIVER);
			prt.setRate(cell.getNumericCellValue()/100);
			break;
		}
		case 18: {// 取得 乘客费率
			prt.setRateType(Constant.RT_PASSENGER);
			prt.setRate(cell.getNumericCellValue()/100);
			break;
		}
		}
		return prt;
	}

	/**
	 * 导入D模板盗抢险
	 * 
	 * @param colum
	 * @return
	 */
	public PremiumRateTemp initDQXD(HSSFCell cell, PremiumRateTemp prt) {
		prt.setRiskCode(Constant.RISK_4);
		prt.setRateType(Constant.RT_BASE);
		switch (cell.getColumnIndex()) {
		case 19: {// 取得基础费率
			prt.setRate(cell.getNumericCellValue()/100);
			break;
		}
		}
		return prt;
	}

	/**
	 * 导入D模板玻璃险
	 * 
	 * @param colum
	 * @return
	 */
	public PremiumRateTemp initBLXD(HSSFCell cell,PremiumRateTemp prt) {
		prt.setRiskCode(Constant.RISK_6);
		switch (cell.getColumnIndex()) {
		case 20: {// 取得国产玻璃费率
			prt.setRateType(Constant.RT_GLASS_LOCAL);
			prt.setRate(cell.getNumericCellValue()/100);
			break;
		}
		case 21: {// 取得进口玻璃费率
			prt.setRateType(Constant.RT_GLASS_IMPORT);
			prt.setRate(cell.getNumericCellValue()/100);
			break;
		}
		}
		return prt;
	}

	/**
	 * 导入D模板车身划痕险
	 * 
	 * @param colum
	 * @return
	 */
	public PremiumRateTemp initHHXD(HSSFCell cell,PremiumRateTemp prt) {
		switch (cell.getColumnIndex()) {
		case 3: {
			prt.setPriceLower(0);
			prt.setPriceUpper(30*10000);
			prt.setPremium(cell.getNumericCellValue());
			break;
		}
		case 4: {
			prt.setPriceLower(30*10000);
			prt.setPriceUpper(50*10000);
			prt.setPremium(cell.getNumericCellValue());
			break;
		}
		case 5: {
			prt.setPriceLower(50*10000);
			prt.setPriceUpper(Integer.MAX_VALUE);
			prt.setPremium(cell.getNumericCellValue());
			break;
		}
		}
		return prt;
	}

	/**
	 * 导入D模板不计免赔率特约条款
	 * 
	 * @param colum
	 * @return
	 */
	public PremiumRateTemp initIOPD(HSSFCell cell,PremiumRateTemp prt) {
		prt.setRiskCode(Constant.RISK_5);
		prt.setRateType(Constant.RT_BASE);
		if (cell.getColumnIndex() == 9) {
			prt.setRate(cell.getNumericCellValue());
		}
		return prt;
	}
	/* 导出D模板自燃损失险费率*/
	public PremiumRateTemp initZRSSX(HSSFCell cell,PremiumRateTemp prt) {
		prt.setRiskCode(Constant.RISK_SCLAR);
		prt.setRateType(Constant.RT_BASE);
		switch (cell.getColumnIndex()) {
		case 3: {
			prt.setAgeLower(0);
			prt.setAgeUpper(1);
			prt.setRate(cell.getNumericCellValue());
			break;
		}
		case 4: {
			prt.setAgeLower(1);
			prt.setAgeUpper(2);
			prt.setRate(cell.getNumericCellValue());
			break;
		}
		case 5: {
			prt.setAgeLower(2);
			prt.setAgeUpper(3);
			prt.setRate(cell.getNumericCellValue());
			break;
		}
		case 6: {
			prt.setAgeLower(3);
			prt.setAgeUpper(4);
			prt.setRate(cell.getNumericCellValue());
			break;
		}
		case 7: {
			prt.setAgeLower(4);
			prt.setAgeUpper(5);
			prt.setRate(cell.getNumericCellValue());
			break;
		}
		case 8: {
			prt.setAgeLower(5);
			prt.setAgeUpper(6);
			prt.setRate(cell.getNumericCellValue());
			break;
		}
		case 9: {
			prt.setAgeLower(6);
			prt.setAgeUpper(Integer.MAX_VALUE);
			prt.setRate(cell.getNumericCellValue());
			break;
		}
		}
		return prt;
	}
}
