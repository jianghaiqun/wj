package cn.com.sinosoft.entity;

import java.io.Serializable;
import java.util.List;

import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMRiskFactorList;

public class OrderRiskAppFactor implements Serializable {
	private static final long serialVersionUID = 2095698083750727358L;
	/**
	 * 产品编码
	 */
	private String productCode;
	/**
	 * 投保要素类别名称
	 */
	private String factorTypeName;
	/**
	 * 投保要素编码
	 */
	private String appFactorCode;
	/**
	 * 投保要素类型
	 */
	private String factorType;
	/**
	 * 投保要素值列表
	 */
	private List<FEMRiskFactorList> factorValue;
	/**
	 * 投保要素是否参与保费计算
	 */
	private String isPremCalFacotor;
	/**
	 * 投保要素值类型
	 */
	private String dataType;

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getFactorType() {
		return factorType;
	}

	public void setFactorType(String factorType) {
		this.factorType = factorType;
	}

	public List<FEMRiskFactorList> getFactorValue() {
		return factorValue;
	}

	public void setFactorValue(List<FEMRiskFactorList> factorValue) {
		this.factorValue = factorValue;
	}

	public String getIsPremCalFacotor() {
		return isPremCalFacotor;
	}

	public void setIsPremCalFacotor(String isPremCalFacotor) {
		this.isPremCalFacotor = isPremCalFacotor;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getAppFactorCode() {
		return appFactorCode;
	}

	public void setAppFactorCode(String appFactorCode) {
		this.appFactorCode = appFactorCode;
	}

	public String getFactorTypeName() {
		return factorTypeName;
	}

	public void setFactorTypeName(String factorTypeName) {
		this.factorTypeName = factorTypeName;
	}

}
