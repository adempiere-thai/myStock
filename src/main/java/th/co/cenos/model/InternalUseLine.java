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
import java.math.BigDecimal;

/**
 * @function myStock
 * @package th.co.cenos.model
 * @classname InternalUseLine
 * @author Pasuwat Wang (CENS ONLINE SERVICES)
 * @created Nov 24, 2016 12:11:23 PM
 */
public class InternalUseLine implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6650152365930604439L;
	
	
	public InternalUseLine(){
		this.asi = new AttributeSetInstance();
		this.product = new Product();
	}
	
	private int adOrgId ;
	private int internalUseLineId;
	private int internalUseId;
	private int lineNo;
	private Product product;
	private AttributeSetInstance asi;
	private Locator locator;
	private BigDecimal usedQty;
	
	
	public int getLineNo() {
		return lineNo;
	}
	public void setLineNo(int lineNo) {
		this.lineNo = lineNo;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public AttributeSetInstance getAsi() {
		return asi;
	}
	public void setAsi(AttributeSetInstance asi) {
		this.asi = asi;
	}
	public Locator getLocator() {
		return locator;
	}
	public void setLocator(Locator locator) {
		this.locator = locator;
	}
	public BigDecimal getUsedQty() {
		return usedQty;
	}
	public void setUsedQty(BigDecimal usedQty) {
		this.usedQty = usedQty;
	}
	public int getAdOrgId() {
		return adOrgId;
	}
	public void setAdOrgId(int adOrgId) {
		this.adOrgId = adOrgId;
	}
	public int getInternalUseLineId() {
		return internalUseLineId;
	}
	public void setInternalUseLineId(int internalUseLineId) {
		this.internalUseLineId = internalUseLineId;
	}
	public int getInternalUseId() {
		return internalUseId;
	}
	public void setInternalUseId(int internalUseId) {
		this.internalUseId = internalUseId;
	}
	
}
