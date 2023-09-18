package com.samodule.datatable;

import java.util.Collections;
import java.util.List;

import com.samodule.vo.DomainObject;



public class DataTablesResultSet<T extends DomainObject>  implements WebResultSet<T>
{
  private final Integer sEcho;
  private final Long iTotalRecords;
  private final Long iTotalDisplayRecords;
  private final List<T> aaData;
 
  public DataTablesResultSet(JQueryDataTableParamModel pc, ResultSet<T> rs)
  {
    this.sEcho = Integer.parseInt(pc.sEcho);
    this.aaData = rs.getRows();
    this.iTotalRecords = rs.getTotalRecords();
    this.iTotalDisplayRecords = (long) pc.iTotalDisplayRecords;
  }
 
  public Integer getsEcho() {
    return sEcho;
  }
 
  public Long getiTotalRecords() {
    return iTotalRecords;
  }
 
  public Long getiTotalDisplayRecords() {
    return iTotalDisplayRecords;
  }
 
  public List<T> getAaData() {
    return Collections.unmodifiableList(aaData);
  }
}
