package com.sinosoft.framework.collection;

public abstract class KeyNotFoundEventListener<K, V>
{
  public abstract V findKeyValue(K paramK);
}