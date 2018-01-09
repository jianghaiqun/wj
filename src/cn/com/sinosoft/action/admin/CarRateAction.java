package cn.com.sinosoft.action.admin;

import cn.com.sinosoft.bean.QueryBuilder;
import cn.com.sinosoft.entity.City;
import cn.com.sinosoft.entity.PremiumRate;
import cn.com.sinosoft.entity.PremiumRateTemp;
import cn.com.sinosoft.service.CityService;
import cn.com.sinosoft.service.RateService;
import cn.com.sinosoft.service.RateTempService;
import cn.com.sinosoft.util.Constant;
import cn.com.sinosoft.util.ResolvingTempleateA;
import cn.com.sinosoft.util.ResolvingTempleateB;
import cn.com.sinosoft.util.ResolvingTempleateC;
import cn.com.sinosoft.util.ResolvingTempleateD;
import cn.com.sinosoft.util.ResolvingTempleateJQX;
import cn.com.sinosoft.util.Tool;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LiuXin
 * 
 */
@ParentPackage("admin")
public class CarRateAction extends BaseAdminAction{
	private static final long serialVersionUID = 2891171155232296695L;
	private ResolvingTempleateA tempA = new ResolvingTempleateA();
	private ResolvingTempleateB tempB = new ResolvingTempleateB();
	private ResolvingTempleateC tempC = new ResolvingTempleateC();
	private ResolvingTempleateD tempD = new ResolvingTempleateD();
	private ResolvingTempleateJQX tempJQX = new ResolvingTempleateJQX();
	private List<City> citys = new ArrayList<City>();
	private String regionCode;// 行驶区域编码
	private String rateModelType;// 模板类型编码
	private String stringFlag="";
	private File importRateFile;
	@Resource
	private RateService rateService;
	@Resource
	private RateTempService rateTempService;
	@Resource
	private CityService cityService;
	public String commercialRate(){
		citys = cityService.selectCity();
		return "commercial";
	}
	/**
	 * 
	 * 导入商险费率
	 */
	public String importRate() {
		logger.info("==费率导入==");
		if(importRateByModelType()){
			if(saveTempToRate()){
				stringFlag="保存成功";
			}else{
				deleteTempAndRate();
				stringFlag="导入失败";
			}
		}else{
			String sql = "delete from PREMIUMRATETEMP";
			rateTempService.deleteRateTempBySql(sql);
		}
		citys = cityService.selectCity();
		return "commercial";
	}
	/**
	 * 
	 * 导入交强险费率
	 */
	public String importJQXRate() {
		return "jqxrate";
	}
	public String saveJQXRate(){
		logger.info("==交强险费率导入==");
		if(importRateByModelType()){
			if(saveTempToRate()){
				stringFlag="保存成功";
			}else{
				deleteTempAndRate();
				stringFlag="导入失败";
			}
		}else{
			String sql = "delete PREMIUMRATETEMP";
			rateTempService.deleteRateTempBySql(sql);
		}
		return "jqxrate";
	}
	public boolean importRateByModelType() {
		try {
			// 取得并解析文件头部
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(
					importRateFile));
			HSSFSheet sheet = workbook.getSheetAt(0);// 取头一个sheet
			if ("JQX".equals(rateModelType)) {// 交强险费率导入
				if(!findJQXRateIsExist()){
					stringFlag="删除原始记录出错";
					return false;
				}
				if("SUCCESS".equals(resolvingTempJQX(sheet))){
					return true;
				}
			} else {
				if(!findRateIsExist()){
					stringFlag="删除原始记录出错";
					return false;
				}
				// 循环处理数据
				if ("A".equals(rateModelType)) {
					logger.info("导入A模板====");
					if("SUCCESS".equals(resolvingTempA(sheet))){
						return true;
					}
				} else if ("B".equals(rateModelType)) {
					if("SUCCESS".equals(resolvingTempB(sheet))){
						logger.info("导入B模板====");
						return true;
					}
				} else if ("C".equals(rateModelType)) {
					if("SUCCESS".equals(resolvingTempC(sheet))){
						logger.info("导入C模板====");
						return true;
					}
				} else if ("D".equals(rateModelType)) {
					if("SUCCESS".equals(resolvingTempD(sheet))){
						logger.info("导入D模板====");
						return true;
					}
				}
			}
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return false;
	}
	/**
	 * 解析JQX模板
	 * 
	 * @param sheet
	 * @return
	 */
	private String resolvingTempJQX(HSSFSheet sheet) {
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			PremiumRateTemp prt = new PremiumRateTemp();
			HSSFRow row = sheet.getRow(i);
			if (row != null) {
				prt = tempJQX.resolving(row,prt);
				if (row.getRowNum() >= 2 && row.getRowNum() <= 36) {
					tempJQX.initJQX(row.getCell(3),prt);
					if(!rateTempService.savePrt(prt)){
						stringFlag = "第"+i+"行有错";
						return stringFlag;
					}
				}
			}
		}
		return "SUCCESS";
	}
	
	/**
	 * 解析A模板
	 * 
	 */
	private String resolvingTempA(HSSFSheet sheet) {
		for (int i = 0; i <= sheet.getLastRowNum(); i++) {
			PremiumRateTemp prt = new PremiumRateTemp();
			prt.setModelType(rateModelType);
			prt.setRegionCode(regionCode);
			HSSFRow row = sheet.getRow(i);
			if (row != null) {
				prt = tempA.resolving(row, prt);
				stringFlag=resolvingRowA(row, prt);
			}
		}
		return stringFlag;
	}
	/**
	 * 解析B模板
	 * 
	 * @param sheet
	 * @return
	 */
	private String resolvingTempB(HSSFSheet sheet) {
		for (int i = 0; i<= sheet.getLastRowNum(); i++) {
			PremiumRateTemp prt = new PremiumRateTemp();
			prt.setModelType(rateModelType);
			prt.setRegionCode(regionCode);
			HSSFRow row = sheet.getRow(i);
			if (row != null) {
				tempB.resolving(row,prt);
				stringFlag=this.resolvingRowB(row, prt);
			}
		}
		return stringFlag;
	}
	/**
	 * 解析C模板
	 * 
	 * @param sheet
	 * @return
	 */
	private String resolvingTempC(HSSFSheet sheet) {
		for (int i = 0; i <= sheet.getLastRowNum(); i++) {
			PremiumRateTemp prt = new PremiumRateTemp();
			prt.setModelType(rateModelType);
			prt.setRegionCode(regionCode);
			HSSFRow row = sheet.getRow(i);
			if (row != null) {
				tempC.resolving(row,prt);
				stringFlag=this.resolvingRowC(row, prt);
			}
		}
		return stringFlag;
	}
	/**
	 * 解析D模板
	 * 
	 * @param sheet
	 * @return
	 */
	private String resolvingTempD(HSSFSheet sheet) {
		for (int i = 0; i <= sheet.getLastRowNum(); i++) {
			PremiumRateTemp prt = new PremiumRateTemp();
			prt.setModelType(rateModelType);
			prt.setRegionCode(regionCode);
			HSSFRow row = sheet.getRow(i);
			if (row != null) {
				tempD.resolving(row,prt);
				stringFlag=this.resolvingRowD(row, prt);
			}
		}
		return stringFlag;
	}
	/**
	 * 解析模板A行数据
	 * 
	 */
	private String resolvingRowA(HSSFRow row, PremiumRateTemp prt1) {
		logger.info("ResolvingRow:{} and Num of Columns is:{}", row.getRowNum(), row.getLastCellNum());
		if ((row.getRowNum() >= 5 && row.getRowNum() <= 20)
				|| (row.getRowNum() >= 24 && row.getRowNum() <= 45)
				|| (row.getRowNum() >= 48 && row.getRowNum() <= 54)) {
			for (int i = 3; i < row.getLastCellNum(); i++) {
				PremiumRateTemp prt = getNewPremiumRateTemp(prt1);
				if (i <= 9) {// 第三者责任险
					tempA.initDSZA(row.getCell(i), prt);
					if(!rateTempService.savePrt(prt)){
						return "第"+row.getRowNum()+"行"+"第"+i+"列有错";
					}
				}
				if (i > 9 && i <= 17) {// 机动车损失险
					if (row.getRowNum() >= 48 && row.getRowNum() <= 54) {// 摩托车及拖拉机
						if (i == 14) {
							tempA.initCSX_MOTOR(row.getCell(10), prt);
							tempA.initCSX_MOTOR(row.getCell(i), prt);
							prt.setRiskCode(Constant.RISK_2);
							prt.setRateType(Constant.RT_BASE);
							if(!rateTempService.savePrt(prt)){
								return "第"+row.getRowNum()+"行"+"第"+i+"列有错";
							}
						}
					} else {
						if (i % 2 == 1) {// 奇数列则进行保存动作
							tempA.initCSXA(row.getCell(i), prt);
							tempA.initCSXA(row.getCell(i-1), prt);
							prt.setRiskCode(Constant.RISK_2);
							prt.setRateType(Constant.RT_BASE);
							if(!rateTempService.savePrt(prt)){
								return "第"+row.getRowNum()+"行"+"第"+i+"列有错";
							}
						}
					}
				}
				if (i == 18 || i == 19) {// 车上人员责任险
					if (row.getRowNum() >= 48 && row.getRowNum() <= 54) {// 摩托车及拖拉机
						if(i==18){
							tempA.initRYXA(row.getCell(i), true, prt);
							prt.setRateType(Constant.RT_DRIVER);
							if(!rateTempService.savePrt(prt)){
								return "第"+row.getRowNum()+"行"+"第"+i+"列有错";
							}
						}
						if (i == 19) {
							tempA.initRYXA(row.getCell(i-1), true, prt);
							prt.setRateType(Constant.RT_PASSENGER);
							if(!rateTempService.savePrt(prt)){
								return "第"+row.getRowNum()+"行"+"第"+i+"列有错";
							}
						}
					} else {
						if(i==18){
							tempA.initRYXA(row.getCell(i), false, prt);
							if(!rateTempService.savePrt(prt)){
								return "第"+row.getRowNum()+"行"+"第"+i+"列有错";
							}
						}
						if (i == 19) {
							tempA.initRYXA(row.getCell(i), false, prt);
							if(!rateTempService.savePrt(prt)){
								return "第"+row.getRowNum()+"行"+"第"+i+"列有错";
							}
						}
					}

				}
				if (i == 20 || i == 21) {// 盗抢险
					if (i == 21) {// 两列解析一个费率
						tempA.initDQXA(row.getCell(i-1), prt);
						tempA.initDQXA(row.getCell(i), prt);
						prt.setRateType(Constant.RT_BASE);
						prt.setRiskCode(Constant.RISK_4);
						if(!rateTempService.savePrt(prt)){
							return "第"+row.getRowNum()+"行"+"第"+i+"列有错";
						}
					}
				}
				if (i == 22 || i == 23) {// 玻璃单独破碎险
					if (!(row.getRowNum() >= 48 && row.getRowNum() <= 54)) {
						if (i == 22) {
							tempA.initBLXA(row.getCell(i), prt);
							if(!rateTempService.savePrt(prt)){
								return "第"+row.getRowNum()+"行"+"第"+i+"列有错";
							}
						}
						if (i == 23) {
							tempA.initBLXA(row.getCell(i), prt);
							if(!rateTempService.savePrt(prt)){
								return "第"+row.getRowNum()+"行"+"第"+i+"列有错";
							}
						}
					}
				}
			}
		} else if (row.getRowNum() >= 60 && row.getRowNum() <= 68) {// 既有车身划痕又有不计免赔
			for (int j = 3; j < row.getLastCellNum(); j++) {
				PremiumRateTemp prt = getNewPremiumRateTemp(prt1);
				if (row.getRowNum() > 60 && j <= 5) {
					tempA.initHHXA(row.getCell(j), prt);
					if(!rateTempService.savePrt(prt)){
						return "第"+row.getRowNum()+"行"+"第"+j+"列有错";
					}
				}
				if (j == 9) {
					if(row.getRowNum()==60){
						prt.setApplyRiskCode(Constant.RISK_1);
					}else if(row.getRowNum()==61){
						prt.setApplyRiskCode(Constant.RISK_2);
					}else if(row.getRowNum()==62){
						prt.setApplyRiskCode(Constant.RISK_3);
					}else if(row.getRowNum()==63){
						prt.setApplyRiskCode(Constant.RISK_8);
					}else if(row.getRowNum()==64){
						prt.setApplyRiskCode(Constant.RISK_4);
					}
					tempA.initIOPA(row.getCell(j), prt);
					if(!rateTempService.savePrt(prt)){
						return "第"+row.getRowNum()+"行"+"第"+j+"列有错";
					}
				}

			}
		} else if (row.getRowNum() >= 73 && row.getRowNum() <= 80) {// 自然损失
			for (int z = 3; z < row.getLastCellNum(); z++) {
				PremiumRateTemp prt = getNewPremiumRateTemp(prt1);
				if (z >= 3 && z <= 9) {
					tempA.initZRSSX(row.getCell(z), prt);
					if(!rateTempService.savePrt(prt)){
						return "第"+row.getRowNum()+"行"+"第"+z+"列有错";
					}
				}
			}
		}
		return "SUCCESS";
	}
	/**
	 * 解析模板B行数据
	 * 
	 * @param row
	 * @return
	 */
	private String resolvingRowB(HSSFRow row, PremiumRateTemp prt1) {
		logger.info("ResolvingRow:{} and Num of Columns is:{}", row.getRowNum(), row.getLastCellNum());
		if ((row.getRowNum() >= 5 && row.getRowNum() <= 20)
				|| (row.getRowNum() >= 24 && row.getRowNum() <= 45)
				|| (row.getRowNum() >= 49 && row.getRowNum() <= 55)||(row.getRowNum() >= 62 && row.getRowNum() <= 69)) {
			for (int i = 3; i < row.getLastCellNum(); i++) {
				PremiumRateTemp prt = getNewPremiumRateTemp(prt1);
				if (i <= 10) {// 机动车损失险
					if (row.getRowNum() >= 49 && row.getRowNum() <= 55) {// 摩托车、拖拉机
						if (i == 7) {
							tempB.initCSXB_MOTOR(row.getCell(3),prt);
							tempB.initCSXB_MOTOR(row.getCell(i),prt);
							prt.setRiskCode(Constant.RISK_2);
							prt.setRateType(Constant.RT_BASE);
							if(!rateTempService.savePrt(prt)){
								return "第"+row.getRowNum()+"行"+"第"+i+"列有错";
							}
						}
					} else {
						if (row.getRowNum() >= 5 && row.getRowNum() <= 45) {
							tempB.initCSXB(row.getCell(i),prt);
							if (i % 2 == 0) {// 偶数列则进行保存动作
								tempB.initCSXB(row.getCell(i-1),prt);
								tempB.initCSXB(row.getCell(i),prt);
								prt.setRiskCode(Constant.RISK_2);
								prt.setRateType(Constant.RT_BASE);
								if(!rateTempService.savePrt(prt)){
									return "第"+row.getRowNum()+"行"+"第"+i+"列有错";
								}
							}
						}
					}
				}
				if (i >= 11 && i <= 17) {// 第三者责任险
					tempB.initDSZB(row.getCell(i), prt);
					if(!rateTempService.savePrt(prt)){
						return "第"+row.getRowNum()+"行"+"第"+i+"列有错";
					}
				}
				if (i == 18 || i == 19) {// 盗抢险
					if(i==19){
						tempB.initDQXB(row.getCell(i), prt);
						tempB.initDQXB(row.getCell(i-1), prt);
						prt.setRateType(Constant.RT_BASE);
						prt.setRiskCode(Constant.RISK_4);
						if(!rateTempService.savePrt(prt)){
							return "第"+row.getRowNum()+"行"+"第"+i+"列有错";
						}
					}
				}
				if (i == 20 || i == 21) {// 车上人员责任险
					if (row.getRowNum() >= 49 && row.getRowNum() <= 55) {// 摩托车、拖拉机
						if(i==20){
							tempB.initRYXB_MOTOR(row.getCell(i), prt);
							prt.setRateType(Constant.RT_DRIVER);
						}else if(i==21){
							tempB.initRYXB_MOTOR(row.getCell(i-1), prt);
							prt.setRateType(Constant.RT_PASSENGER);
						}
						if(!rateTempService.savePrt(prt)){
							return "第"+row.getRowNum()+"行"+"第"+i+"列有错";
						}
					} else {
						tempB.initRYXB(row.getCell(i), prt);
						if(!rateTempService.savePrt(prt)){
							return "第"+row.getRowNum()+"行"+"第"+i+"列有错";
						}
					}
				}
				if (i == 22 || i == 23) {// 玻璃单独破碎险
					if (row.getRowNum() >= 5 && row.getRowNum() <= 48) {// 只有非摩托车、拖拉机
						tempB.initBLXB(row.getCell(i), prt);
						if(!rateTempService.savePrt(prt)){
							return "第"+row.getRowNum()+"行"+"第"+i+"列有错";
						}
					}
				}
				if(i>=3&&i<=9){
					if (row.getRowNum() >= 62 && row.getRowNum() <= 69) {
						tempB.initZRSSX(row.getCell(i), prt);
						if(!rateTempService.savePrt(prt)){
							return "第"+row.getRowNum()+"行"+"第"+i+"列有错";
						}
					}
				}

			}
		}
		return "SUCCESS";
	}
	/**
	 * 解析模板C行数据
	 * 
	 * @param row
	 * @return
	 */
	private String resolvingRowC(HSSFRow row, PremiumRateTemp prt1) {
		logger.info("ResolvingRow:{} and Num of Columns is:{}", row.getRowNum(), row.getLastCellNum());
		if ((row.getRowNum() >= 9 && row.getRowNum() <= 35)
				|| (row.getRowNum() >= 37 && row.getRowNum() <= 58)
				|| (row.getRowNum() >= 60 && row.getRowNum() <= 66)) {
			for (int i = 2; i < row.getLastCellNum(); i++) {
				PremiumRateTemp prt = getNewPremiumRateTemp(prt1);
				if (i <= 8) {// 第三者责任险
					tempC.initDSZ(row.getCell(i), prt);
					if(!rateTempService.savePrt(prt)){
						return "第"+row.getRowNum()+"行"+"第"+i+"列有错";
					}
				}
				if (i >= 9 && i <= 18) {// 机动车损失险
					if (row.getRowNum() >= 60 && row.getRowNum() <= 66) {// 摩托车、拖拉机
						if (i == 15) {
							prt.setRiskCode(Constant.RISK_2);
							prt.setRateType(Constant.RT_BASE);
							tempC.initCSX_Motor(row.getCell(9), prt);
							tempC.initCSX_Motor(row.getCell(i), prt);
							if(!rateTempService.savePrt(prt)){
								return "第"+row.getRowNum()+"行"+"第"+i+"列有错";
							}
						}
					} else {
						if (i % 2 == 0) {// 偶数列则进行保存动作
							tempC.initCSX(row.getCell(i-1), prt);
							tempC.initCSX(row.getCell(i), prt);
							prt.setRiskCode(Constant.RISK_2);
							prt.setRateType(Constant.RT_BASE);
							if(!rateTempService.savePrt(prt)){
								return "第"+row.getRowNum()+"行"+"第"+i+"列有错";
							}
						}
					}
				}
				if (i == 19 || i == 20) {// 车上人员责任险
					if (row.getRowNum() >= 60 && row.getRowNum() <= 66) {// 摩托车、拖拉机
						prt.setRiskCode(Constant.RISK_3);
						if (i == 19) {
							tempC.initRYX_Motor(row.getCell(19), prt);
							prt.setRateType(Constant.RT_DRIVER);
							if(!rateTempService.savePrt(prt)){
								return "第"+row.getRowNum()+"行"+"第"+i+"列有错";
							}
						}
						if (i == 20) {
							tempC.initRYX_Motor(row.getCell(19), prt);
							prt.setRateType(Constant.RT_PASSENGER);
							if(!rateTempService.savePrt(prt)){
								return "第"+row.getRowNum()+"行"+"第"+i+"列有错";
							}
						}
					} else {
						prt.setRiskCode(Constant.RISK_3);
						tempC.initRYX(row.getCell(i), prt);
						if(!rateTempService.savePrt(prt)){
							return "第"+row.getRowNum()+"行"+"第"+i+"列有错";
						}
					}
				}
				if (i == 21 || i == 22) {// 盗抢险
					if (i == 22) {// 两列解析一个费率
						tempC.initDQX(row.getCell(i-1), prt);
						tempC.initDQX(row.getCell(i), prt);
						prt.setRiskCode(Constant.RISK_4);
						prt.setRateType(Constant.RT_BASE);
						if(!rateTempService.savePrt(prt)){
							return "第"+row.getRowNum()+"行"+"第"+i+"列有错";
						}
					}
				}
				if (i == 23 || i == 24) {// 玻璃单独破碎险
					if (row.getRowNum() <= 58) {// 非摩托车、拖拉机
						tempC.initBLX(row.getCell(i), prt);
						if(!rateTempService.savePrt(prt)){
							return "第"+row.getRowNum()+"行"+"第"+i+"列有错";
						}
					}
				}
			}
		}else if((row.getRowNum() >= 76 && row.getRowNum() <=83)){
			for(int j=3;j<=row.getLastCellNum();j++){
				PremiumRateTemp prt = getNewPremiumRateTemp(prt1);
				if(j>=3&&j<=9){
					tempC.initZRSSX(row.getCell(j), prt);
					if(!rateTempService.savePrt(prt)){
						return "第"+row.getRowNum()+"行"+"第"+j+"列有错";
					}
				}
			}
		}
		return "SUCCESS";
	}
	/**
	 * 解析模板D行数据
	 * 
	 * @param row
	 * @return
	 */
	private String resolvingRowD(HSSFRow row, PremiumRateTemp prt1) {
		logger.info("ResolvingRow:{} and Num of Columns is:{}", row.getRowNum(), row.getLastCellNum());
		if (row.getRowNum() >= 6 && row.getRowNum() <= 47) {
			for (int i = 3; i < row.getLastCellNum(); i++) {
				PremiumRateTemp prt = getNewPremiumRateTemp(prt1);
				if (i >= 4 && i <= 13) {// 车碰车车辆损失险
					if (i % 2 == 1) {
						this.tempD.initCRASHD(row.getCell(i-1),prt);
						this.tempD.initCRASHD(row.getCell(i),prt);
						prt.setRiskCode(Constant.RISK_CRASH);
						prt.setRateType(Constant.RT_BASE);
						if(!rateTempService.savePrt(prt)){
							return "第"+row.getRowNum()+"行"+"第"+i+"列有错";
						}
					}
				}
				if (i >= 14 && i <= 23) {// 车辆损失综合险
					if (i % 2 == 1) {
						this.tempD.initCOMPREHENSIVED(row.getCell(i-1),prt);
						this.tempD.initCOMPREHENSIVED(row.getCell(i),prt);
						prt.setRiskCode(Constant.RISK_comprehensive);
						prt.setRateType(Constant.RT_BASE);
						if(!rateTempService.savePrt(prt)){
							return "第"+row.getRowNum()+"行"+"第"+i+"列有错";
						}
					}
				}
			}
		} else if (row.getRowNum() >= 55 && row.getRowNum() <= 96) {
			for (int i = 4; i < row.getLastCellNum(); i++) {
				PremiumRateTemp prt = getNewPremiumRateTemp(prt1);
				if (i <= 10) {// 第三者责任险
					tempD.initDSZD(row.getCell(i),prt);
					if(!rateTempService.savePrt(prt)){
						return "第"+row.getRowNum()+"行"+"第"+i+"列有错";
					}
				}
				if (i >= 11 && i <= 16) {// 机动车损失险(一切险)
					if (row.getRowNum() < 90 || row.getRowNum() > 92) {// 摩托车
						tempD.initCSXD(row.getCell(i),prt);
						if (i != 11) {// 除第11列外都要进行保存动作
							if(!rateTempService.savePrt(prt)){
								return "第"+row.getRowNum()+"行"+"第"+i+"列有错";
							}
						}
					}
				}
				if (i == 17 || i == 18) {// 车上人员责任险
					if (row.getRowNum() <= 92) {// 除了拖拉机
						tempD.initRYXD(row.getCell(i),prt);
						if(!rateTempService.savePrt(prt)){
							return "第"+row.getRowNum()+"行"+"第"+i+"列有错";
						}
					}
				}
				if (i == 19) {// 盗抢险
					tempD.initDQXD(row.getCell(i),prt);
					if(!rateTempService.savePrt(prt)){
						return "第"+row.getRowNum()+"行"+"第"+i+"列有错";
					}
				}

				if (i == 20 || i == 21) {// 玻璃单独破碎险
					if (row.getRowNum() <= 89) {
						tempD.initBLXD(row.getCell(i),prt);
						if(!rateTempService.savePrt(prt)){
							return "第"+row.getRowNum()+"行"+"第"+i+"列有错";
						}
					}
				}
			}
		} else if (row.getRowNum() >= 99 && row.getRowNum() <= 100) {// 既有车身划痕又有不计免赔

		} else if(row.getRowNum() >= 108 && row.getRowNum() <= 115){
			for(int j=3;j<=row.getLastCellNum();j++){
				PremiumRateTemp prt = getNewPremiumRateTemp(prt1);
				if(j>=3&&j<=9){
					tempD.initZRSSX(row.getCell(j),prt);
					if(!rateTempService.savePrt(prt)){
						return "第"+row.getRowNum()+"行"+"第"+j+"列有错";
					}
				}
			}
		}
		return "SUCCESS";
	}

	private PremiumRateTemp getNewPremiumRateTemp(PremiumRateTemp prt){
		PremiumRateTemp p = new PremiumRateTemp();
		p.setCreateDate(Tool.getCurrentTime());
		p.setModelType(prt.getModelType());
		p.setRegionCode(prt.getRegionCode());
		p.setUsedCode(prt.getUsedCode());
		p.setPertainCode(prt.getPertainCode());
		p.setModelsCode(prt.getModelsCode());
		p.setPassengerLower(prt.getPassengerLower());
		p.setPasserngerUpper(prt.getPasserngerUpper());
		p.setLoadLower(prt.getLoadLower());
		p.setLoadUpper(prt.getLoadUpper());
		p.setAgeLower(prt.getAgeLower());
		p.setAgeUpper(prt.getAgeUpper());
		p.setSumassured(prt.getSumassured());
		return p;
	}
	/**
	 * 导入失败时删除以保存数据
	 */
	private void deleteTempAndRate() {
		String sql ="delete from PREMIUMRATE where MODELTYPE="+rateModelType+" and REGION_CODE="+regionCode;
		rateService.deleteRateBySql(sql);
		rateTempService.deleteRateTempBySql("delete from PREMIUMRATETEMP");
		
	}

	/**
	 * 将临时表数据导入到正式表中
	 */
	private boolean saveTempToRate() {
		String sql = "insert into PREMIUMRATE select * from PREMIUMRATETEMP ";
		boolean flag1 = rateService.saveRateBySql(sql);
		boolean flag2 = rateTempService.deleteRateTempBySql("delete from PREMIUMRATETEMP");
		if(flag1&&flag2){
			return true;
		}
		return false;
	}
	/**
	 * 查询费率表中是否已存在将要录入公司的费率
	 * 根据公司和行驶区域判断
	 */
	private boolean findRateIsExist() {
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>();
		qbs.add(createQB("modelType","=",rateModelType));
		qbs.add(createQB("regionCode","=",regionCode));
		List<PremiumRate> list = rateService.findByQBs(qbs, "id", "desc");
		if(list!=null&&list.size()>0){
			String sql ="delete from PREMIUMRATE where MODELTYPE="+rateModelType+" and REGION_CODE="+regionCode;
			return rateService.deleteRateBySql(sql);
		}
		return true;
	}
	/**
	 * 
	 * 查询数据库中是否已有交强险费率
	 */
	private boolean findJQXRateIsExist(){
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>();
		qbs.add(createQB("riskCode","=",Constant.RISK_JQX));
		List<PremiumRate> list = rateService.findByQBs(qbs, "id", "desc");
		if(list!=null&&list.size()>0){
			String sql ="delete from PREMIUMRATE where RISK_CODE="+Constant.RISK_JQX;
			return rateService.deleteRateBySql(sql);
		}
		return true;
	}
	private QueryBuilder createQB(String property,String sign ,String value){
		QueryBuilder qb = new QueryBuilder();
		qb.setProperty(property);
		qb.setSign(sign);
		qb.setValue(value);
		return qb;
	}

	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public String getRateModelType() {
		return rateModelType;
	}
	public void setRateModelType(String rateModelType) {
		this.rateModelType = rateModelType;
	}

	public File getImportRateFile() {
		return importRateFile;
	}

	public void setImportRateFile(File importRateFile) {
		this.importRateFile = importRateFile;
	}
	public String getStringFlag() {
		return stringFlag;
	}
	public void setStringFlag(String stringFlag) {
		this.stringFlag = stringFlag;
	}
	public List<City> getCitys() {
		return citys;
	}
	public void setCitys(List<City> citys) {
		this.citys = citys;
	}

}
