package cn.com.sinosoft.service.impl;

import cn.com.sinosoft.dao.InsuredHealthDao;
import cn.com.sinosoft.entity.HealthyInfo;
import cn.com.sinosoft.entity.InsuredHealth;
import cn.com.sinosoft.service.InsuredHealthService;
import cn.com.sinosoft.util.JdbcTemplateData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InsuredHealthServiceImpl extends BaseServiceImpl<InsuredHealth, String> implements InsuredHealthService {
	@Resource
	private InsuredHealthDao insuredHealthDao;
	private JdbcTemplateData jtd = new JdbcTemplateData();

	@Resource
	public void setInsuredHealthDao(InsuredHealthDao insuredHealthDao) {
		super.setBaseDao(insuredHealthDao);
	}

	@Override
	public List<InsuredHealth> createShowInformation(List<HealthyInfo> healthList) {
		List<InsuredHealth> showList = new ArrayList<InsuredHealth>();
		if (healthList != null && healthList.size() > 0) {
			for (HealthyInfo healthyInfo : healthList) {
				showList.add(createByHealthyInfo(healthyInfo));
			}
			return showList;
		}
		return null;
	}

	private InsuredHealth createByHealthyInfo(HealthyInfo healthyInfo) {
		InsuredHealth ih = new InsuredHealth();
		String flag = getHealthyInfoFlag(healthyInfo.getId());
		Map<String, Object> m = getHealthyInfoByProductId(healthyInfo.getId());
		ih.setHealthyInfoId(healthyInfo.getId());
		ih.setProductId(healthyInfo.getProductId());
		ih.setInsuranceCompany(healthyInfo.getInsuranceCompany());
		ih.setShowOrder(healthyInfo.getShowOrder());
		ih.setShowInfo(healthyInfo.getShowInfo());
		ih.setUIInfo(healthyInfo.getUIInfo());
		ih.setShowDistrict(healthyInfo.getShowDistrict());
		ih.setIsMustInput(healthyInfo.getIsMustInput());
		ih.setIsDisplay(healthyInfo.getIsDisplay());
		ih.setDataType(healthyInfo.getDataType());
		ih.setShowInfoType(healthyInfo.getShowInfoType());
		ih.setShowInfoCode(healthyInfo.getShowInfoCode());
		ih.setBackUp1(healthyInfo.getBackUp1());
		ih.setValueTypeFlag(flag);
		ih.setHealthyInfo(m.get("healthys").toString());
		ih.setHealthyInfoNum(Integer.parseInt(m.get("healthyInfoNum").toString()));
		return ih;
	}

	private String getHealthyInfoFlag(String uiinfoid) {
		String flag = "N";
		try {
			String sql = "select Remark from ZDFEUIInfoValue where uiinfoid= ? ";
			String sqltemp[] = new String[] { uiinfoid };    
			List<Map> healthFlag = jtd.obtainData(sql, sqltemp);   
			if (healthFlag != null && healthFlag.size() > 0) {
				for (int i = 0; i < healthFlag.size(); i++) {
					if (healthFlag.get(i) != null && healthFlag.get(i).get("Remark") != null) {
						flag = healthFlag.get(i).get("Remark").toString();
						if ("Y".equals(flag)) {
							return flag;
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return flag;
	}

	private Map<String, Object> getHealthyInfoByProductId(String uiinfoid) {
		Map<String, Object> m = new HashMap<String, Object>();
		StringBuffer healthys = new StringBuffer();
		int healthyInfoNum = 0;
		String healthy = "";
		try {
			String sql = "select HealthyInfo from ZDHealthyInfo where uiinfoid= ? ";
			String sqltemp[] = new String[] { uiinfoid };
			List<Map> HealthyInfo = jtd.obtainData(sql , sqltemp);
			if (HealthyInfo != null && HealthyInfo.size() > 0) {
				Map map = HealthyInfo.get(0);
				if (map != null && map.get("HealthyInfo") != null) {
					healthy = map.get("HealthyInfo").toString();
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		if (healthy != null && !"".equals(healthy)) {
			String[] healthyString = healthy.split("#");
			if (healthyString != null && healthyString.length > 0) {
				for (int i = 0; i < healthyString.length - 1; i++) {
					healthys.append(healthyString[i]);
					healthys.append("<input type=\"text\" name=\"" + uiinfoid + "c" + i + "\" id=\"" + uiinfoid + "c" + i + "\" />");
				}
				healthyInfoNum = healthyString.length - 1;
			}
			healthys.append(healthyString[healthyString.length - 1]);
		}
		m.put("healthys", healthys.toString());
		m.put("healthyInfoNum", healthyInfoNum);
		return m;
	}

}
