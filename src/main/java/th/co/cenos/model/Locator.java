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

/**
 * @function myStock
 * @package th.co.cenos.model
 * @classname Locator
 * @author Pasuwat Wang (CENS ONLINE SERVICES)
 * @created Nov 21, 2016 11:37:15 AM
 */
public class Locator implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2647231016198765055L;
	
	private int locatorId ;
	
	private String locatorKey;
	private boolean defaultLocator;
	
	
	public int getLocatorId() {
		return locatorId;
	}
	public void setLocatorId(int locatorId) {
		this.locatorId = locatorId;
	}
	public String getLocatorKey() {
		return locatorKey;
	}
	public void setLocatorKey(String locatorKey) {
		this.locatorKey = locatorKey;
	}

	public boolean isDefaultLocator() {
		return defaultLocator;
	}
	
	public void setDefaultLocator(boolean defaultLocator) {
		this.defaultLocator = defaultLocator;
	}
	
}
