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
 * @classname AttributeSetInstance
 * @author Pasuwat Wang (CENS ONLINE SERVICES)
 * @created Nov 16, 2016 9:52:09 AM
 */
public class AttributeSetInstance implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4835292487296607099L;

	private int attributeSetInstanceId;
	private String description;
	private String lotNo;
	private String attributeSet;
	
	public int getAttributeSetInstanceId() {
		return attributeSetInstanceId;
	}
	public void setAttributeSetInstanceId(int attributeSetInstanceId) {
		this.attributeSetInstanceId = attributeSetInstanceId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLotNo() {
		return lotNo;
	}
	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}
	public String getAttributeSet() {
		return attributeSet;
	}
	public void setAttributeSet(String attributeSet) {
		this.attributeSet = attributeSet;
	}

}
