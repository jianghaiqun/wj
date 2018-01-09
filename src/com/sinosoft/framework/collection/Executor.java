package com.sinosoft.framework.collection;

public abstract class Executor
{
  protected Object param;

  public Executor(Object param)
  {
    this.param = param;
  }

  public abstract boolean execute();
}