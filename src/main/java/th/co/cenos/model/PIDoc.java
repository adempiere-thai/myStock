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
 * @classname PIDoc
 * @author Pasuwat Wang (CENS ONLINE SERVICES)
 * @created Nov 24, 2016 12:09:55 PM
 */
public class PIDoc implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7205460459420718443L;
	
	private int piDocId ;
	private String piDocNo;
	private String docStatus;
	private int totalLines;

	/**
	 * @return the piDocId
	 */
	public int getPiDocId() {
		return piDocId;
	}
	/**
	 * @param piDocId the piDocId to set
	 */
	public void setPiDocId(int piDocId) {
		this.piDocId = piDocId;
	}
	/**
	 * @return the piDocNo
	 */
	public String getPiDocNo() {
		return piDocNo;
	}
	/**
	 * @param piDocNo the piDocNo to set
	 */
	public void setPiDocNo(String piDocNo) {
		this.piDocNo = piDocNo;
	}
	/**
	 * @return the docStatus
	 */
	public String getDocStatus() {
		return docStatus;
	}
	/**
	 * @param docStatus the docStatus to set
	 */
	public void setDocStatus(String docStatus) {
		this.docStatus = docStatus;
	}
	/**
	 * @return the totalLines
	 */
	public int getTotalLines() {
		return totalLines;
	}
	/**
	 * @param totalLines the totalLines to set
	 */
	public void setTotalLines(int totalLines) {
		this.totalLines = totalLines;
	}
}
