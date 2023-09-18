/**
 * MIT License
 *
 * Copyright (c) 2018 Aykut Akin
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.samodule.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "SAP_USER_ROLES")
public class Role implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
//	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="IMIS.HRM_REIM_ROLES_SEQ")
//	@SequenceGenerator(name="IMIS.HRM_REIM_ROLES_SEQ",sequenceName="IMIS.HRM_REIM_ROLES_SEQ",allocationSize=1) 
	@Column(name="ROLE_RECID")
	private long roleRecId;
	@Column(name="entity")
	private String entity;	
	@Column(name="EMP_CODE")	
	private String empCode;	
	@Column(name="ROLE_CODE")	
	private String roleCode;
	@Column(name="ROLE_NAME")
    private String roleName;
	@Column(name="CREATED_BY")
	private String createdBy;
	@Column(name="CREATED_ON")
	private Date createdOn;
	@Column(name="LAST_UPD_BY")
	private String lastUpdBy;
	@Column(name="LAST_UPD_ON")
	private Date lastUpdOn;
	
	

	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRolName(String roleName) {
		this.roleName = roleName;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public String getLastUpdBy() {
		return lastUpdBy;
	}
	public void setLastUpdBy(String lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}
	public Date getLastUpdOn() {
		return lastUpdOn;
	}
	public void setLastUpdOn(Date lastUpdOn) {
		this.lastUpdOn = lastUpdOn;
	}
	public long getRoleRecId() {
		return roleRecId;
	}
	public void setRoleRecId(long roleRecId) {
		this.roleRecId = roleRecId;
	}
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}