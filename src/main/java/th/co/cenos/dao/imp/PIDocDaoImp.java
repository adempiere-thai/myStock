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
package th.co.cenos.dao.imp;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import th.co.cenos.dao.AbstractDao;
import th.co.cenos.dao.PIDocDao;
import th.co.cenos.model.AttributeSetInstance;
import th.co.cenos.model.Locator;
import th.co.cenos.model.Product;
import th.co.cenos.model.PIDoc;
import th.co.cenos.model.PIDocLine;
import th.co.cenos.model.User;
import th.co.cenos.model.Warehouse;
import th.co.cenos.utils.ADSequenceUtil;

/**
 * @function myStock
 * @package th.co.cenos.dao.imp
 * @classname StocktakingDaoImp
 * @author Pasuwat Wang (CENS ONLINE SERVICES)
 * @created Nov 24, 2016 11:35:49 AM
 */
@Repository
public class PIDocDaoImp extends AbstractDao implements PIDocDao {
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		logger = LoggerFactory.getLogger(PIDocDaoImp.class);
	}

	@Override
	public PIDoc findPIDocByDocNo(int adClientId, int adOrgId, String documentNo) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement ppstmt = null;
		ResultSet rset = null;
		PIDoc piDoc = null;

		StringBuffer sql = new StringBuffer(
				"SELECT iv.M_Inventory_Id , iv.DocumentNo , iv.DocStatus , (SELECT COUNT(1) FROM M_InventoryLine ivl WHERE iv.M_Inventory_Id = ivl.M_Inventory_Id) as total_lines FROM M_Inventory iv \n");
		sql.append("WHERE iv.AD_Client_Id = ? AND iv.AD_Org_ID = ? AND iv.DocumentNo = ? ");

		try {
			conn = getConnection();
			ppstmt = conn.prepareStatement(sql.toString());
			int paramIdx = 1;
			ppstmt.setInt(paramIdx++, adClientId);
			ppstmt.setInt(paramIdx++, adOrgId);
			ppstmt.setString(paramIdx++, documentNo);

			rset = ppstmt.executeQuery();
			if (rset.next()) {
				piDoc = new PIDoc();
				piDoc.setPiDocId(rset.getInt("M_Inventory_Id"));
				piDoc.setPiDocNo(rset.getString("DocumentNo"));
				piDoc.setDocStatus(rset.getString("DocStatus"));
				piDoc.setTotalLines(rset.getInt("total_lines"));
			}
		} catch (Exception ex) {
			logger.error(String.format("Exception Error In PIDocDao.findPIDocByDocNo [%s]!", documentNo));
			ex.printStackTrace();
		} finally {
			dbObjClosed(rset);
			dbObjClosed(ppstmt);
		}
		return piDoc;
	}

	@Override
	public PIDocLine findPIDocLine(PIDocLine line) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement ppstmt = null;
		ResultSet rset = null;
		PIDocLine piDocLine = line;

		StringBuffer sql = new StringBuffer("SELECT ivl.* FROM M_InventoryLine ivl  \n");
		sql.append("WHERE ivl.AD_Client_Id = ? \n AND ivl.M_Product_Id = ? \n");
		sql.append("AND ivl.m_attributesetinstance_id = ? \n AND ivl.M_Locator_Id = ? AND ivl.m_inventory_id = ? ");

		try {
			conn = getConnection();
			ppstmt = conn.prepareStatement(sql.toString());
			int paramIdx = 1;
			ppstmt.setInt(paramIdx++, line.getAdClientId());
			ppstmt.setInt(paramIdx++, line.getProduct().getProductId());
			ppstmt.setInt(paramIdx++, line.getAsi().getAttributeSetInstanceId());
			ppstmt.setInt(paramIdx++, line.getLocatorId());
			ppstmt.setInt(paramIdx++, line.getPiDocId());

			rset = ppstmt.executeQuery();
			if(rset.next()){
				piDocLine.setPiDocLineId(rset.getInt("M_InventoryLine_Id"));
			}
		} catch (Exception ex) {
			//logger.error(String.format("Exception Error In PIDocDao.findPIDocByDocNo [%s]!", documentNo));
			ex.printStackTrace();
		} finally {
			dbObjClosed(rset);
			dbObjClosed(ppstmt);
		}
		return piDocLine;
	}

	@Override
	public int updatePIDocLine(PIDocLine line) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement ppstmt = null;
		int ret = 0;
		PIDocLine piDocLine = line;

		StringBuffer sql = new StringBuffer("UPDATE M_InventoryLine SET QtyCount = ? WHERE M_InventoryLine_Id = ? ");

		try {
			conn = getConnection();
			ppstmt = conn.prepareStatement(sql.toString());
			int paramIdx = 1;
			ppstmt.setBigDecimal(paramIdx++, piDocLine.getCountQty());
			ppstmt.setInt(paramIdx++, piDocLine.getPiDocLineId());
			
			ret = ppstmt.executeUpdate();
		} catch (Exception ex) {
			//logger.error(String.format("Exception Error In PIDocDao.findPIDocByDocNo [%s]!", documentNo));
			ex.printStackTrace();
		} finally {
			dbObjClosed(ppstmt);
		}
		
		return ret;
	}

}
