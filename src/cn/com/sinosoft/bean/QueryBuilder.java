package cn.com.sinosoft.bean;

public class QueryBuilder
{
  private String property;
  private String sign;
  private String value;

  public String getProperty()
  {
    return this.property;
  }
  public void setProperty(String property) {
    this.property = property;
  }
  public String getSign() {
    return this.sign;
  }
  public void setSign(String sign) {
    this.sign = sign;
  }
  public String getValue() {
    return this.value;
  }
  public void setValue(String value) {
    this.value = value;
  }
}