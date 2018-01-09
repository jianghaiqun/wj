/**
 * @CopyRight:Sinosoft
 * @File:ResolvingTempleateJQX.java
 * @CreateTime:2012-5-11 上午7:36:46
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
public class ResolvingTempleateJQX {
	private static final Logger logger = LoggerFactory.getLogger(ResolvingTempleateJQX.class);
	public PremiumRateTemp resolving(HSSFRow row, PremiumRateTemp prt) {
		logger.info("RowNum:{}", row.getRowNum());
		switch (row.getRowNum()) {
		case 2: {// 家庭自用汽车
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_PERSONAL);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_UnBusiness);
			prt.setPassengerLower(0);
			prt.setPasserngerUpper(6);
			break;
		}
		case 3: {
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_PERSONAL);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_UnBusiness);
			prt.setPassengerLower(6);
			prt.setPasserngerUpper(Integer.MAX_VALUE);
			break;
		}
		case 4: {// 企业非营业客车
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_UnBusiness);
			prt.setPassengerLower(0);
			prt.setPasserngerUpper(6);
			break;
		}
		case 5: {
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_UnBusiness);
			prt.setPassengerLower(6);
			prt.setPasserngerUpper(10);
			break;
		}
		case 6: {
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_UnBusiness);
			prt.setPassengerLower(10);
			prt.setPasserngerUpper(20);
			break;
		}
		case 7: {
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_UnBusiness);
			prt.setPassengerLower(20);
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
		case 12: {// 营运客车（出租、租赁）
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Lease);
			prt.setPassengerLower(0);
			prt.setPasserngerUpper(6);
			break;
		}
		case 13: {// 营运客车（出租、租赁）
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Lease);
			prt.setPassengerLower(6);
			prt.setPasserngerUpper(10);
			break;
		}
		case 14: {// 营运客车（出租、租赁）
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Lease);
			prt.setPassengerLower(10);
			prt.setPasserngerUpper(20);
			break;
		}
		case 15: {// 营运客车（出租、租赁）
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Lease);
			prt.setPassengerLower(20);
			prt.setPasserngerUpper(36);
			break;
		}
		case 16: {// 营运客车（出租、租赁）
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Lease);
			prt.setPassengerLower(36);
			prt.setPasserngerUpper(Integer.MAX_VALUE);
			break;
		}
		case 17: {// 城市公交
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Bus);
			prt.setPassengerLower(6);
			prt.setPasserngerUpper(10);
			break;
		}
		case 18: {
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Bus);
			prt.setPassengerLower(10);
			prt.setPasserngerUpper(20);
			break;
		}
		case 19: {
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Bus);
			prt.setPassengerLower(20);
			prt.setPasserngerUpper(36);
			break;
		}
		case 20: {
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Bus);
			prt.setPassengerLower(36);
			prt.setPasserngerUpper(Integer.MAX_VALUE);
			break;
		}
		case 21: {// 公路客运
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Highway);
			prt.setPassengerLower(6);
			prt.setPasserngerUpper(10);
			break;
		}
		case 22: {
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Highway);
			prt.setPassengerLower(10);
			prt.setPasserngerUpper(20);
			break;
		}
		case 23: {
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Highway);
			prt.setPassengerLower(20);
			prt.setPasserngerUpper(36);
			break;
		}
		case 24: {
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Highway);
			prt.setPassengerLower(36);
			prt.setPasserngerUpper(Integer.MAX_VALUE);
			break;
		}
		case 25: {// 非营业货车--个人，企业，机关、事业单位无关
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Lorry);
			prt.setLoadLower(0);
			prt.setLoadUpper(2);
			break;
		}
		case 26: {
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Lorry);
			prt.setLoadLower(2);
			prt.setLoadUpper(5);
			break;
		}
		case 27: {
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Lorry);
			prt.setLoadLower(5);
			prt.setLoadUpper(10);
			break;
		}
		case 28: {
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Lorry);
			prt.setLoadLower(10);
			prt.setLoadUpper(Integer.MAX_VALUE);
			break;
		}
		case 29: {// 营业货车--个人，企业，机关、事业单位无关
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Lorry);
			prt.setLoadLower(0);
			prt.setLoadUpper(2);
			break;
		}
		case 30: {
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Lorry);
			prt.setLoadLower(2);
			prt.setLoadUpper(5);
			break;
		}
		case 31: {
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Lorry);
			prt.setLoadLower(5);
			prt.setLoadUpper(10);
			break;
		}
		case 32: {
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Lorry);
			prt.setLoadLower(10);
			prt.setLoadUpper(Integer.MAX_VALUE);
			break;
		}
		case 33: {// 特种车1
			prt.setUsedCode(null);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Special_1);
			break;
		}
		case 34: {// 特种车2
			prt.setUsedCode(null);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Special_2);
			break;
		}
		case 35: {// 特种车3
			prt.setUsedCode(null);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Special_3);
			break;
		}
		case 36: {// 特种车4
			prt.setUsedCode(null);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Special_4);
			break;
		}
		}
		return prt;
	}

	/**
	 * 导入交强险保费
	 * 
	 */
	public PremiumRateTemp initJQX(HSSFCell cell, PremiumRateTemp prt) {
		prt.setRiskCode(Constant.RISK_JQX);
		prt.setRateType(Constant.RT_BASE);
		if (cell.getColumnIndex() == 3) {
			prt.setPremium(cell.getNumericCellValue());
		}
		return prt;
	}
}
