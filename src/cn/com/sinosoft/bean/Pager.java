package cn.com.sinosoft.bean;

import java.util.List;

public class Pager
{
  public static final Integer MAX_PAGE_SIZE = Integer.valueOf(500);

  private Integer pageNumber = Integer.valueOf(1);
  private Integer pageSize = Integer.valueOf(20);
  private Integer totalCount = Integer.valueOf(0);
  private Integer pageCount = Integer.valueOf(0);
  private String property;
  private String keyword;
  private String orderBy = "createDate";
  private OrderType orderType = OrderType.desc;
  private List<QueryBuilder> properties;
  private List list;
  private List<OrderBys> orderBys = null;

  public Integer getPageNumber() {
    return this.pageNumber;
  }

  public void setPageNumber(Integer pageNumber) {
    if (pageNumber.intValue() < 1) {
      pageNumber = Integer.valueOf(1);
    }
    this.pageNumber = pageNumber;
  }

  public Integer getPageSize() {
    return this.pageSize;
  }

  public void setPageSize(Integer pageSize) {
    if (pageSize.intValue() < 1)
      pageSize = Integer.valueOf(1);
    else if (pageSize.intValue() > MAX_PAGE_SIZE.intValue()) {
      pageSize = MAX_PAGE_SIZE;
    }
    this.pageSize = pageSize;
  }

  public Integer getTotalCount() {
    return this.totalCount;
  }

  public void setTotalCount(Integer totalCount) {
    this.totalCount = totalCount;
  }

  public Integer getPageCount() {
    this.pageCount = Integer.valueOf(this.totalCount.intValue() / this.pageSize.intValue());
    if (this.totalCount.intValue() % this.pageSize.intValue() > 0) {
      this.pageCount = Integer.valueOf(this.pageCount.intValue() + 1);
    }
    return this.pageCount;
  }

  public void setPageCount(Integer pageCount) {
    this.pageCount = pageCount;
  }

  public String getProperty() {
    return this.property;
  }

  public void setProperty(String property) {
    this.property = property;
  }

  public String getKeyword() {
    return this.keyword;
  }

  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }

  public String getOrderBy() {
    return this.orderBy;
  }

  public void setOrderBy(String orderBy) {
    this.orderBy = orderBy;
  }

  public OrderType getOrderType() {
    return this.orderType;
  }

  public void setOrderType(OrderType orderType) {
    this.orderType = orderType;
  }

  public List<QueryBuilder> getProperties() {
    return this.properties;
  }

  public void setProperties(List<QueryBuilder> properties) {
    this.properties = properties;
  }

  public List getList() {
    return this.list;
  }

  public void setList(List list) {
    this.list = list;
  }

  public List<OrderBys> getOrderBys() {
    return this.orderBys;
  }

  public void setOrderBys(List<OrderBys> orderBys) {
    this.orderBys = orderBys;
  }

  public static enum OrderType
  {
    asc, desc;
  }
}