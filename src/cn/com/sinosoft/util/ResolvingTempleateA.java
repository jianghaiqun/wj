/**
 * @CopyRight:Sinosoft
 * @File:ResolvingTempleateA.java
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
public class ResolvingTempleateA {
	private static final Logger logger = LoggerFactory.getLogger(ResolvingTempleateA.class);

	public PremiumRateTemp resolving(HSSFRow row, PremiumRateTemp prt) {
		logger.info("RowNum:{}", row.getRowNum());
		switch (row.getRowNum()) {
		case 5: {// 家庭自用汽车
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_PERSONAL);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_UnBusiness);
			prt.setPassengerLower(0);
			prt.setPasserngerUpper(6);
			break;
		}
		case 6: {
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_PERSONAL);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_UnBusiness);
			prt.setPassengerLower(6);
			prt.setPasserngerUpper(10);
			break;
		}
		case 7: {
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_PERSONAL);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_UnBusiness);
			prt.setPassengerLower(10);
			prt.setPasserngerUpper(Integer.MAX_VALUE);
			break;
		}
		case 8: {// 企业非营业客车
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_UnBusiness);
			prt.setPassengerLower(0);
			prt.setPasserngerUpper(6);
			break;
		}
		case 9: {
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_UnBusiness);
			prt.setPassengerLower(6);
			prt.setPasserngerUpper(10);
			break;
		}
		case 10: {
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_UnBusiness);
			prt.setPassengerLower(10);
			prt.setPasserngerUpper(20);
			break;
		}
		case 11: {
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_UnBusiness);
			prt.setPassengerLower(20);
			prt.setPasserngerUpper(Integer.MAX_VALUE);
			break;
		}
		case 12: {// 机关、事业单位非营业客车
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_OFFICE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_UnBusiness);
			prt.setPassengerLower(0);
			prt.setPasserngerUpper(6);
			break;
		}
		case 13: {
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_OFFICE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_UnBusiness);
			prt.setPassengerLower(6);
			prt.setPasserngerUpper(10);
			break;
		}
		case 14: {
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_OFFICE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_UnBusiness);
			prt.setPassengerLower(10);
			prt.setPasserngerUpper(20);
			break;
		}
		case 15: {
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_OFFICE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_UnBusiness);
			prt.setPassengerLower(20);
			prt.setPasserngerUpper(Integer.MAX_VALUE);
			break;
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
		case 20: {
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Lorry_Slow);
			break;
		}
		case 24: {// 营运客车（出租、租赁）
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Lease);
			prt.setPassengerLower(0);
			prt.setPasserngerUpper(6);
			break;
		}
		case 25: {
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Lease);
			prt.setPassengerLower(6);
			prt.setPasserngerUpper(10);
			break;
		}
		case 26: {
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Lease);
			prt.setPassengerLower(10);
			prt.setPasserngerUpper(20);
			break;
		}
		case 27: {
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Lease);
			prt.setPassengerLower(20);
			prt.setPasserngerUpper(36);
			break;
		}
		case 28: {
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Lease);
			prt.setPassengerLower(36);
			prt.setPasserngerUpper(Integer.MAX_VALUE);
			break;
		}
		case 29: {// 城市公交
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Bus);
			prt.setPassengerLower(6);
			prt.setPasserngerUpper(10);
			break;
		}
		case 30: {
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Bus);
			prt.setPassengerLower(10);
			prt.setPasserngerUpper(20);
			break;
		}
		case 31: {
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Bus);
			prt.setPassengerLower(20);
			prt.setPasserngerUpper(36);
			break;
		}
		case 32: {
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Bus);
			prt.setPassengerLower(36);
			prt.setPasserngerUpper(Integer.MAX_VALUE);
			break;
		}
		case 33: {// 公路客运
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Highway);
			prt.setPassengerLower(6);
			prt.setPasserngerUpper(10);
			break;
		}
		case 34: {
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Highway);
			prt.setPassengerLower(10);
			prt.setPasserngerUpper(20);
			break;
		}
		case 35: {
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Highway);
			prt.setPassengerLower(20);
			prt.setPasserngerUpper(36);
			break;
		}
		case 36: {
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(Constant.VEHICLE_PERTAIN_ENTERPRISE);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Highway);
			prt.setPassengerLower(36);
			prt.setPasserngerUpper(Integer.MAX_VALUE);
			break;
		}
		case 37: {// 营业货车--个人，企业，机关、事业单位无关
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Lorry);
			prt.setLoadLower(0);
			prt.setLoadUpper(2);
			break;
		}
		case 38: {
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Lorry);
			prt.setLoadLower(2);
			prt.setLoadUpper(5);
			break;
		}
		case 39: {
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Lorry);
			prt.setLoadLower(5);
			prt.setLoadUpper(10);
			break;
		}
		case 40: {
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Lorry);
			prt.setLoadLower(10);
			prt.setLoadUpper(Integer.MAX_VALUE);
			break;
		}
		case 41: {
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Lorry_Slow);
			break;
		}
		case 42: {// 特种车1
			prt.setUsedCode(null);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Special_1);
			break;
		}
		case 43: {// 特种车2
			prt.setUsedCode(null);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Special_2);
			break;
		}
		case 44: {// 特种车3
			prt.setUsedCode(null);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Special_3);
			break;
		}
		case 45: {// 特种车4
			prt.setUsedCode(null);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Special_4);
			break;
		}
		case 48: {// 摩托车
			prt.setUsedCode(null);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Motorcycle_Below_50);
			break;
		}
		case 49: {// 摩托车
			prt.setUsedCode(null);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Motorcycle_50_250);
			break;
		}
		case 50: {// 摩托车
			prt.setUsedCode(null);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Motorcycle_Above_250);
			break;
		}
		case 51: {// 拖拉机
			prt.setUsedCode(null);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Tractor_DualPurpose);
			break;
		}
		case 52: {// 拖拉机
			prt.setUsedCode(null);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Tractor_DualPurpose_Above147);
			break;
		}
		case 53: {// 拖拉机
			prt.setUsedCode(null);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Tractor_Transport);
			break;
		}
		case 54: {// 拖拉机
			prt.setUsedCode(null);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Tractor_Transport_Above147);
			break;
		}
		case 61: {
			prt.setSumassured(2000);
			prt.setAgeLower(0);
			prt.setAgeUpper(2);
			break;
		}
		case 62: {
			prt.setSumassured(5000);
			prt.setAgeLower(0);
			prt.setAgeUpper(2);
			break;
		}
		case 63: {
			prt.setSumassured(10000);
			prt.setAgeLower(0);
			prt.setAgeUpper(2);
			break;
		}
		case 64: {
			prt.setSumassured(20000);
			prt.setAgeLower(0);
			prt.setAgeUpper(2);
			break;
		}
		case 65: {
			prt.setSumassured(2000);
			prt.setAgeLower(2);
			prt.setAgeUpper(Integer.MAX_VALUE);
			break;
		}
		case 66: {
			prt.setSumassured(5000);
			prt.setAgeLower(2);
			prt.setAgeUpper(Integer.MAX_VALUE);
			break;
		}
		case 67: {
			prt.setSumassured(10000);
			prt.setAgeLower(2);
			prt.setAgeUpper(Integer.MAX_VALUE);
			break;
		}
		case 68: {
			prt.setSumassured(20000);
			prt.setAgeLower(2);
			prt.setAgeUpper(Integer.MAX_VALUE);
			break;
		}
		case 73: {//非营业客车自然损失险
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_UnBusiness);
			break;
		}
		case 74: {//非营业特种车自然损失险
			prt.setUsedCode(Constant.VEHICLE_USING_UNBUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Special_1);
			break;
		}
		case 75: {//营业出租自然损失险
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Hire);
			break;
		}
		case 76: {//营业租赁自然损失险
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Lease);
			break;
		}
		case 77: {//城市公交自然损失险
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Bus);
			break;
		}
		case 78: {//公路客运自然损失险
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_PassengerCar_Highway);
			break;
		}
		case 79: {//货车自然损失险
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Lorry);
			break;
		}
		case 80: {//营业特种车自然损失险
			prt.setUsedCode(Constant.VEHICLE_USING_BUSINESS);
			prt.setPertainCode(null);
			prt.setModelsCode(Constant.VEHICLE_MODEL_Special_1);
			break;
		}
		}
		return prt;
	}

	/**
	 * 导入A模板-初始化第三者责任险数据
	 * 
	 * @param colum
	 * @return
	 */
	public PremiumRateTemp initDSZA(HSSFCell cell, PremiumRateTemp prt) {
		logger.info("行号：{}||列号：{}", cell.getRowIndex(), cell.getColumnIndex());
		prt.setRiskCode(Constant.RISK_1);
		prt.setRateType(Constant.RT_BASE);
		switch (cell.getColumnIndex()) {
		case 3: {
			prt.setSumassured(5);
			break;
		}
		case 4: {
			prt.setSumassured(10);
			break;
		}
		case 5: {
			prt.setSumassured(15);
			break;
		}
		case 6: {
			prt.setSumassured(20);
			break;
		}
		case 7: {
			prt.setSumassured(30);
			break;
		}
		case 8: {
			prt.setSumassured(50);
			break;
		}
		case 9: {
			prt.setSumassured(100);
			break;
		}
		}
		if (cell.getColumnIndex() >= 3 && cell.getColumnIndex() <= 9) {
			prt.setPremium(cell.getNumericCellValue());
		}
		return prt;
	}

	/**
	 * 导入A模板车损险-摩托车、拖拉机等
	 * 
	 * @param colum
	 * @return
	 */
	public PremiumRateTemp initCSX_MOTOR(HSSFCell cell, PremiumRateTemp prt) {
		switch (cell.getColumnIndex()) {
		case 10: {// 取得 基础保费
			prt.setPremium(cell.getNumericCellValue());
			break;
		}
		case 14: {
			prt.setRate(cell.getNumericCellValue());
			break;
		}
		}
		return prt;
	}

	/**
	 * 导入A模板车损险
	 * 
	 * @param colum
	 * @return
	 */
	public PremiumRateTemp initCSXA(HSSFCell cell, PremiumRateTemp prt) {
		int rowNum = cell.getRowIndex();
		switch (cell.getColumnIndex()) {
		case 10: {// 取得 基础保费
			if (rowNum >= 5 && rowNum <= 20) {
				prt.setAgeLower(0);
				prt.setAgeUpper(1);
			} else {
				prt.setAgeLower(0);
				prt.setAgeUpper(2);
			}
			prt.setPremium(cell.getNumericCellValue());
			break;
		}
		case 11: {// 取得费率并保存记录
			prt.setRate(cell.getNumericCellValue());
			break;
		}
		case 12: {
			if (rowNum >= 5 && rowNum <= 20) {
				prt.setAgeLower(1);
				prt.setAgeUpper(2);
			} else {
				prt.setAgeLower(2);
				prt.setAgeUpper(3);
			}
			prt.setPremium(cell.getNumericCellValue());
			break;
		}
		case 13: {
			prt.setRate(cell.getNumericCellValue());
			break;
		}
		case 14: {
			if (rowNum >= 5 && rowNum <= 20) {
				prt.setAgeLower(2);
				prt.setAgeUpper(6);
			} else {
				prt.setAgeLower(3);
				prt.setAgeUpper(4);
			}
			prt.setPremium(cell.getNumericCellValue());
			break;
		}
		case 15: {
			prt.setRate(cell.getNumericCellValue());
			break;
		}
		case 16: {
			if (rowNum >= 5 && rowNum <= 20) {
				prt.setAgeLower(6);
				prt.setAgeUpper(Integer.MAX_VALUE);
			} else {
				prt.setAgeLower(4);
				prt.setAgeUpper(Integer.MAX_VALUE);
			}
			prt.setPremium(cell.getNumericCellValue());
			break;
		}
		case 17: {
			prt.setRate(cell.getNumericCellValue());
			break;
		}
		}
		return prt;
	}

	/**
	 * 导入A模板车上人员责任险
	 * 
	 * @param colum
	 * @return
	 */
	public PremiumRateTemp initRYXA(HSSFCell cell, boolean isMotor ,PremiumRateTemp prt) {
		prt.setRiskCode(Constant.RISK_3);
		if (!isMotor) {
			switch (cell.getColumnIndex()) {
			case 18: {// 取得 驾驶人费率
				prt.setRateType(Constant.RT_DRIVER);
				prt.setRate(cell.getNumericCellValue());
				break;
			}
			case 19: {// 取得 乘客费率
				prt.setRateType(Constant.RT_PASSENGER);
				prt.setRate(cell.getNumericCellValue());
				break;
			}
			}
		} else {
			if (cell.getColumnIndex() == 18) {
				prt.setRate(cell.getNumericCellValue());
			}
		}
		return prt;
	}

	/**
	 * 导入A模板盗抢险
	 * 
	 * @param colum
	 * @return
	 */
	public PremiumRateTemp initDQXA(HSSFCell cell,PremiumRateTemp prt) {
		switch (cell.getColumnIndex()) {
		case 20: {// 取得基础保费
			prt.setPremium(cell.getNumericCellValue());
			break;
		}
		case 21: {// 取得基础费率
			prt.setRate(cell.getNumericCellValue());
			break;
		}
		}
		return prt;
	}

	/**
	 * 导入A模板玻璃险
	 * 
	 * @param colum
	 * @return
	 */
	public PremiumRateTemp initBLXA(HSSFCell cell,PremiumRateTemp prt) {
		prt.setRiskCode(Constant.RISK_6);
		switch (cell.getColumnIndex()) {
		case 22: {// 取得国产玻璃费率
			prt.setRateType(Constant.RT_GLASS_LOCAL);
			prt.setRate(cell.getNumericCellValue());
			break;
		}
		case 23: {// 取得进口玻璃费率
			prt.setRateType(Constant.RT_GLASS_IMPORT);
			prt.setRate(cell.getNumericCellValue());
			break;
		}
		}
		return prt;
	}

	/**
	 * 导入A模板车身划痕险
	 * 
	 * @param colum
	 * @return
	 */
	public PremiumRateTemp initHHXA(HSSFCell cell,PremiumRateTemp prt) {
		prt.setRiskCode(Constant.RISK_8);
		prt.setRateType(Constant.RT_BASE);
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
	 * 导入A模板不计免赔率特约条款
	 * 
	 * @param colum
	 * @return
	 */
	public PremiumRateTemp initIOPA(HSSFCell cell,PremiumRateTemp prt) {
		prt.setRiskCode(Constant.RISK_5);
		prt.setRateType(Constant.RT_BASE);
		if (cell.getColumnIndex() == 9) {
			prt.setRate(cell.getNumericCellValue());
		}
		return prt;
	}

	/* 导出A模板自燃损失险费率 */
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
