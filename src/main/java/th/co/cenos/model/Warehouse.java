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
 * @classname Warehouse
 * @author Pasuwat Wang (CENS ONLINE SERVICES)
 * @created Nov 16, 2016 3:52:36 PM
 */
public class Warehouse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1936356748911194167L;
	
	private int adOrgId;
	private String orgName;
	private int warehouseId ;
	private String warehouseName;
	
	private List<Locator> locatorL;
	/**
	 * @return the adOrgId
	 */
	public int getAdOrgId() {
		return adOrgId;
	}
	/**
	 * @param adOrgId the adOrgId to set
	 */
	public void setAdOrgId(int adOrgId) {
		this.adOrgId = adOrgId;
	}
	/**
	 * @return the warehouseId
	 */
	public int getWarehouseId() {
		return warehouseId;
	}
	/**
	 * @param warehouseId the warehouseId to set
	 */
	public void setWarehouseId(int warehouseId) {
		this.warehouseId = warehouseId;
	}
	/**
	 * @return the warehouseName
	 */
	public String getWarehouseName() {
		return warehouseName;
	}
	/**
	 * @param warehouseName the warehouseName to set
	 */
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	/**
	 * @return the locatorL
	 */
	public List<Locator> getLocatorL() {
		return locatorL;
	}
	/**
	 * @param locatorL the locatorL to set
	 */
	public void setLocatorL(List<Locator> locatorL) {
		this.locatorL = locatorL;
	}
	/**
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}
	/**
	 * @param orgName the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	 
}
