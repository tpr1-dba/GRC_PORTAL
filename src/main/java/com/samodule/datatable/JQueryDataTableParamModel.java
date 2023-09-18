/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samodule.datatable;

import java.io.Serializable;

public class JQueryDataTableParamModel implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 7756647988010411751L;
	/// <summary>
    /// Request sequence number sent by DataTable, same value must be returned in response
    /// </summary>       
    public String sEcho;
    /// <summary>
    /// Text used for filtering
    /// </summary>
    public String sSearch;
    /// <summary>
    /// Number of records that should be shown in table
    /// </summary>
    public int iDisplayLength;
    /// <summary>
    /// First record that should be shown(used for paging)
    /// </summary>
    public int iDisplayStart;
    /// <summary>
    /// Number of columns in table
    /// </summary>
    public int iColumns;
    /// <summary>
    /// Number of columns that are used in sorting
    /// </summary>
    public int iSortingCols;
    /// <summary>
    /// Index of the column that is used for sorting
    /// </summary>
    public int iSortColumnIndex;
    /// <summary>
    /// Sorting direction "asc" or "desc"
    /// </summary>
    public String sSortDirection;
    /// <summary>
    /// Comma separated list of column names
    /// </summary>
    
    public int  iSortCol_0;
	
	public String sSortDir_0;
	
    public int iTotalRecords;
    public int iTotalDisplayRecords;
    public String sColumns;
    public String firstCriteria;
    public String requestNo;
    
	public String getsEcho() {
		return sEcho;
	}
	public void setsEcho(String sEcho) {
		this.sEcho = sEcho;
	}
	public String getsSearch() {
		return sSearch;
	}
	public void setsSearch(String sSearch) {
		this.sSearch = sSearch;
	}
	public int getiDisplayLength() {
		return iDisplayLength;
	}
	public void setiDisplayLength(int iDisplayLength) {
		this.iDisplayLength = iDisplayLength;
	}
	public int getiDisplayStart() {
		return iDisplayStart;
	}
	public void setiDisplayStart(int iDisplayStart) {
		this.iDisplayStart = iDisplayStart;
	}
	public int getiColumns() {
		return iColumns;
	}
	public void setiColumns(int iColumns) {
		this.iColumns = iColumns;
	}
	public int getiSortingCols() {
		return iSortingCols;
	}
	public void setiSortingCols(int iSortingCols) {
		this.iSortingCols = iSortingCols;
	}
	public int getiSortColumnIndex() {
		return iSortColumnIndex;
	}
	public void setiSortColumnIndex(int iSortColumnIndex) {
		this.iSortColumnIndex = iSortColumnIndex;
	}
	public String getsSortDirection() {
		return sSortDirection;
	}
	public void setsSortDirection(String sSortDirection) {
		this.sSortDirection = sSortDirection;
	}
	public int getiTotalRecords() {
		return iTotalRecords;
	}
	public void setiTotalRecords(int iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}
	public int getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}
	public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}
	public String getsColumns() {
		return sColumns;
	}
	public void setsColumns(String sColumns) {
		this.sColumns = sColumns;
	}
	public String getFirstCriteria() {
		return firstCriteria;
	}
	public void setFirstCriteria(String firstCriteria) {
		this.firstCriteria = firstCriteria;
	}
	public int getiSortCol_0() {
		return iSortCol_0;
	}
	public void setiSortCol_0(int iSortCol_0) {
		this.iSortColumnIndex= iSortCol_0;
		this.iSortCol_0 = iSortCol_0;
	}
	public String getsSortDir_0() {
		return sSortDir_0;
	}
	public void setsSortDir_0(String  sSortDir_0) {
		this.sSortDirection = sSortDir_0;
		this.sSortDir_0 = sSortDir_0;
	}
	public String getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	@Override
	public String toString() {
		return "JQueryDataTableParamModel [sEcho=" + sEcho + ", sSearch="
				+ sSearch + ", iDisplayLength=" + iDisplayLength
				+ ", iDisplayStart=" + iDisplayStart + ", iColumns=" + iColumns
				+ ", iSortingCols=" + iSortingCols + ", iSortColumnIndex="
				+ iSortColumnIndex + ", sSortDirection=" + sSortDirection
				+ ", iTotalRecords=" + iTotalRecords
				+ ", iTotalDisplayRecords=" + iTotalDisplayRecords
				+ ", sColumns=" + sColumns + ", firstCriteria=" + firstCriteria
				+ "]";
	}
    
    
}
