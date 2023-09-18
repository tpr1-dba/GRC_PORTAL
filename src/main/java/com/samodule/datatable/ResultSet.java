package com.samodule.datatable;

import java.util.Collections;
import java.util.List;

import com.samodule.vo.DomainObject;



public final class ResultSet<T extends DomainObject>{
  private final List<T> rows;
  private final Long totalDisplayRecords;
  private final Long totalRecords;
 
  public ResultSet(List<T> rows, Long totalRecords, Long totalDisplayRecords)
  {
    this.rows = rows;
    this.totalRecords = totalRecords;
    this.totalDisplayRecords = totalDisplayRecords;
  }
 
  public List<T> getRows()
  {
    return Collections.unmodifiableList(rows);
  }
 
  public Long getTotalDisplayRecords()
  {
    return totalDisplayRecords;
  }
 
  public Long getTotalRecords()
  {
    return totalRecords;
  }
}
