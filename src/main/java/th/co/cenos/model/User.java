/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package th.co.cenos.model;

import java.io.Serializable;
import java.util.List;

/**
 * @function myStock
 * @package th.co.cenos.model
 * @classname User
 * @author Pasuwat Wang (CENS ONLINE SERVICES)
 * @created Nov 16, 2016 9:52:09 AM
 */
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2124957273604300791L;
	
	private int adClientId;
	private int userId ;
	private String username ;
	private String pwd;
	private String role;
	
	private List<Warehouse> warehouseL;
	
	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
	/**
	 * @return the adClientId
	 */
	public int getAdClientId() {
		return adClientId;
	}
	/**
	 * @param adClientId the adClientId to set
	 */
	public void setAdClientId(int adClientId) {
		this.adClientId = adClientId;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the pwd
	 */
	public String getPwd() {
		return pwd;
	}
	/**
	 * @param pwd the pwd to set
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	/**
	 * @return the warehouseL
	 */
	public List<Warehouse> getWarehouseL() {
		return warehouseL;
	}
	/**
	 * @param warehouseL the warehouseL to set
	 */
	public void setWarehouseL(List<Warehouse> warehouseL) {
		this.warehouseL = warehouseL;
	}
	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}
	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

}
