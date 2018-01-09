package com.sinosoft.framework;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Vector;

public class SSRS {
  private static final Logger logger = LoggerFactory.getLogger(SSRS.class);

  @SuppressWarnings("rawtypes")
  private Vector RData = new Vector();
  private int MaxCol = 0;
  private int MaxRow = 0;
  private int MaxNumber = 0;
  
  public SSRS() {
  }

  public SSRS(int n){
    MaxCol = n;
  }

  @SuppressWarnings("unchecked")
  public void SetText(String strValue)
  {
    try
    {
      RData.addElement(strValue);
      MaxNumber = RData.size();
      if ((MaxNumber % MaxCol) == 0)
      {
        MaxRow = MaxNumber / MaxCol;
      }
      else
      {
        MaxRow = MaxNumber / MaxCol + 1;
      }
    }
    catch(Exception ex)
    {
      logger.error(ex.getMessage(), ex);
    }

  }

  public String GetText (int cRow,int cCol)
  {
    String Result="";
    int Number = (cRow-1)*MaxCol+cCol-1;
    if (Number <= MaxNumber)
    {
      Result = (String)RData.get(Number);
    }
    else
    {
    }
    return Result;
  }

  public int getMaxCol()
  {
    return MaxCol;
  }

  public int getMaxRow()
  {
    return MaxRow;
  }

  public int getMaxNumber()
  {
    return MaxNumber;
  }

  public void setMaxCol(int aMaxCol)
  {
    MaxCol = aMaxCol;
  }

  public void setMaxRow(int aMaxRow)
  {
    MaxRow = aMaxRow;
  }

  public void setMaxNumber(int aMaxNumber)
  {
    MaxNumber = aMaxNumber;
  }

  public void Clear()
  {
    RData.clear();
    MaxRow = 0;
    MaxCol = 0;
    MaxNumber = 0;
  }
}