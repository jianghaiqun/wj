package com.sinosoft.platform.meta;

public class SystemModelTemplateType
  implements IMetaModelTemplateType
{
  public static final String ID = "System";

  public String getID()
  {
    return "System";
  }

  public String getName() {
    return "默认展现模板";
  }
}