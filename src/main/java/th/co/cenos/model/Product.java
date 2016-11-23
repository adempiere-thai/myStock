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
 * @classname User
 * @author Pasuwat Wang (CENS ONLINE SERVICES)
 * @created Nov 16, 2016 9:52:09 AM
 */
public class Product implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1658083259767193902L;

	private int productId ;
	private String productSrhKey;
	private String productName;
	
	private BigDecimal availableQty;
	private BigDecimal orderQty;
	private BigDecimal reservedQty;
	private BigDecimal onhandQty;

	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	
	public String getProductSrhKey() {
		return productSrhKey;
	}
	public void setProductSrhKey(String productSrhKey) {
		this.productSrhKey = productSrhKey;
	}
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public BigDecimal getAvailableQty() {
		return availableQty;
	}
	public void setAvailableQty(BigDecimal availableQty) {
		this.availableQty = availableQty;
	}
	public BigDecimal getOrderQty() {
		return orderQty;
	}
	public void setOrderQty(BigDecimal orderQty) {
		this.orderQty = orderQty;
	}
	public BigDecimal getReservedQty() {
		return reservedQty;
	}
	public void setReservedQty(BigDecimal reservedQty) {
		this.reservedQty = reservedQty;
	}
	public BigDecimal getOnhandQty() {
		return onhandQty;
	}
	public void setOnhandQty(BigDecimal onhandQty) {
		this.onhandQty = onhandQty;
	}
}
