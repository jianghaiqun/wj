package com.sinosoft.cms.webservice;

import com.sinosoft.cms.api.UserAPI;
import com.sinosoft.framework.utility.Errorx;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用户接口
 * @author lanjun
 *
 */
public class UserOperatorImpl implements UserOperator{
	private static final Logger logger = LoggerFactory.getLogger(UserOperatorImpl.class);

	public UserOperationResponse doUserOperation(UserOperationRequest request) {
		String operationType = request.getOperationType();
		String userCode = request.getUserCode();
	    String userName = request.getUserName();
	    String orgCode  = request.getOrgCode();
	    String orgName = request.getOrgName();
		UserOperationResponse response  = new UserOperationResponse();
		int errorFlag = 0;
		int errorCode = 0;
		String  errorMsg  = "";
		if(CREATE.equals(operationType)){
			Mapx params=new Mapx();
			params.put("Username", userCode);
			params.put("RealName", userName);
			params.put("Password","123456");
			params.put("Email", "");
			params.put("BranchCode", orgCode);
			if (StringUtil.isEmpty(orgCode)) {
				params.put("BranchCode", "0001");
			}
			params.put("Status", "N");
			UserAPI u=new UserAPI();
			u.setParams(params);
			if(u.insert()!=-1){
				logger.info("新建用户成功：{}", userCode);
			}else{
				errorMsg = "新建用户失败，"+Errorx.getMessages()[0];
				logger.info(errorMsg);
				errorFlag = -1;
				errorCode = 1;
			}
		}else if(UPDATE.equals(operationType)){
			Mapx params = new Mapx();
			params.put("Username", ""+userCode);
			params.put("RealName", ""+userName);
			params.put("BranchCode", ""+orgCode);
			UserAPI u = new UserAPI();
			u.setParams(params);
			
			if(u.update()){
				logger.info("成功修改用户：{}", userCode);
			}else{
				errorMsg = "修改用户失败："+userCode;
				logger.info(errorMsg);
				errorFlag = -1;
				errorCode = 2;
			}
		}else if(SUSPEND.equals(operationType)){
			Mapx params = new Mapx();
			params.put("Username", ""+userCode);
			params.put("Status", "S");
			params.put("OperationType", "suspend");
			UserAPI u = new UserAPI();
			u.setParams(params);
			
			if(u.update()){
				logger.info("成功暂停用户：{}", userCode);
			}else{
				errorMsg = "暂停失败，"+Errorx.getMessages()[0];
				logger.info(errorMsg);
				errorFlag = -1;
				errorCode = 3;
			}
		}else if(RESTORE.equals(operationType)){
			Mapx params = new Mapx();
			params.put("Username", userCode);
			params.put("Status", "N");
			params.put("OperationType", "restore");
			UserAPI u = new UserAPI();
			u.setParams(params);
			
			if(u.update()){
				logger.info("成功恢复用户："+userCode);
			}else{
				errorMsg = "恢复用户失败，"+Errorx.getMessages()[0];
				logger.info(errorMsg);
				errorFlag = -1;
				errorCode = 4;
			}
		}else{
			errorFlag = -1;
			errorCode = 5;
			errorMsg = "不支持的操作类型:"+operationType;
		}
		response.setErrorCode(errorCode);
		response.setErrorFlag(errorFlag);
		response.setErrorMessage(errorMsg);
		return response;
	}
	
}
