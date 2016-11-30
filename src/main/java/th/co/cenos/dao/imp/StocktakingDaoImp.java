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
import th.co.cenos.dao.StocktakingDao;
import th.co.cenos.model.AttributeSetInstance;
import th.co.cenos.model.Locator;
import th.co.cenos.model.Product;
import th.co.cenos.model.Stocktaking;
import th.co.cenos.model.StocktakingLine;
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
public class StocktakingDaoImp  extends AbstractDao implements StocktakingDao {
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		logger = LoggerFactory.getLogger(StocktakingDaoImp.class);
	}
	
	@Override
	public Stocktaking getOpenStocktaking(Warehouse warehouse) {
		Connection conn = null;
		PreparedStatement ppstmt = null;
		ResultSet rset = null;
		
		Stocktaking stocktaking = null;
		List<StocktakingLine> stkLineL = null;
		StringBuffer stkSQL = new StringBuffer("SELECT stk.Ext_Stocktaking_Id , stkl.Ext_StocktakingLine_ID , stkl.Line ,  pd.M_Product_Id ,pd.VALUE as ProductKey,  pd.Name as ProductName , stkl.AD_Org_Id \n");
		stkSQL.append("\t\t, asi.M_AttributeSetInstance_Id , asi.Description , asi.Lot , lo.M_Locator_id , lo.VALUE locatorKey, stkl.qtycount \n")
			.append("FROM Ext_Stocktaking stk LEFT JOIN Ext_StocktakingLine stkl ON stk.Ext_Stocktaking_Id = stkl.Ext_Stocktaking_Id \n ")
			.append("LEFT JOIN M_Product pd ON stkl.M_Product_Id = pd.M_Product_Id  \n")
			.append("LEFT JOIN M_AttributeSetInstance asi ON stkl.M_AttributeSetInstance_Id = asi.M_AttributeSetInstance_Id \n")
			.append("LEFT JOIN M_Locator lo ON stkl.M_Locator_Id = lo.M_Locator_id \n")
			.append("WHERE stk.DocStatus IN ('DR','IP') AND stk.M_Warehouse_Id = ? ORDER BY stkl.Line");
		try {
			conn = getConnection();
			ppstmt = conn.prepareStatement(stkSQL.toString());
			int paramIdx = 1;
			ppstmt.setInt(paramIdx++, warehouse.getWarehouseId());
			rset = ppstmt.executeQuery();
			while(rset.next()){
				if(stocktaking == null){
					stocktaking = new Stocktaking();
					stocktaking.setStocktakingId(rset.getInt("Ext_Stocktaking_Id"));
					
					// Intial List
					stkLineL = new ArrayList<StocktakingLine>();
				}
				
				// Check Warehouse was set Locator
				if(rset.getInt("Ext_StocktakingLine_ID") >0 ){
					StocktakingLine line = new StocktakingLine();
					line.setStocktakingLineId(rset.getInt("Ext_StocktakingLine_ID"));
					line.setLineNo(rset.getInt("Line"));
					line.setStocktakingId(stocktaking.getStocktakingId());
					
					// Set Product
					Product product = new Product();
					product.setProductId(rset.getInt("M_Product_Id"));
					product.setProductSrhKey(rset.getString("ProductKey"));
					product.setProductName(rset.getString("ProductName"));
					
					line.setProduct(product);
					
					// Set ASI
					if(rset.getInt("M_AttributeSetInstance_Id") > 0){
						AttributeSetInstance asi = new AttributeSetInstance();
						asi.setAttributeSetInstanceId(rset.getInt("M_AttributeSetInstance_Id"));
						asi.setDescription(rset.getString("Description"));
						asi.setLotNo(rset.getString("Lot"));
						
						line.setAsi(asi);
					}
						
					// Set Locator
					Locator locator = new Locator();
					locator.setLocatorId(rset.getInt("M_Locator_id"));
					locator.setLocatorKey(rset.getString("locatorKey"));
					line.setLocator(locator);
					
					line.setCountQty(rset.getBigDecimal("qtycount"));
					
					stkLineL.add(line);
				}
			}
			
			if(stocktaking != null){
				stocktaking.setLineL(stkLineL);
			}
		} catch (Exception ex) {
			logger.error(String.format("Cannot get Stocktaking in Warehouse %s !",warehouse.getWarehouseName()));
			ex.printStackTrace();
		} finally {
			dbObjClosed(rset);
			dbObjClosed(ppstmt);
		}
		return stocktaking;
	}

	@Override
	public int saveStocktakingLine(StocktakingLine line , User user) {
		// TODO Auto-generated method stub
		int ret = 0;
		
		if(line.getStocktakingLineId() == 0){
			ret = insertStocktakingLine(line,user);
		}
		else{
			//ret = updateStocktakingLine(line);
		}
		
		return ret;
	}

	
	/**
	 * @param line
	 * @param user 
	 * @return
	 */
	private int insertStocktakingLine(StocktakingLine line, User user) {
		// TODO Auto-generated method stub
		int ret = 0;
		Connection conn = null;
		PreparedStatement ppstmt = null;
		ResultSet rset = null;
		
		StringBuffer insertLineSQL = new StringBuffer("INSERT INTO ext_stocktakingline(ext_stocktakingline_id, ext_stocktaking_id, ad_client_id, ad_org_id , \n");
		insertLineSQL.append("\t\tcreatedby , updatedby , line , m_locator_id, m_product_id, m_attributesetinstance_id, qtycount) \n")
			.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ? ) ");
		
		try {
			conn = getConnection();
			ppstmt = conn.prepareStatement(insertLineSQL.toString());
			int paramIdx = 1;
			// Set Insert Value
			// ext_stocktakingline_id, ext_stocktaking_id, ad_client_id, ad_org_id ,
			// createdby , updatedby, line , m_locator_id, m_product_id, m_attributesetinstance_id, qtycount
			
			int nextId = ADSequenceUtil.getNextId("Ext_StocktakingLine");
			
			ppstmt.setInt(paramIdx++, nextId);
			ppstmt.setInt(paramIdx++, line.getStocktakingId());
			ppstmt.setInt(paramIdx++, user.getAdClientId());
			ppstmt.setInt(paramIdx++, line.getAdOrgId());
			
			ppstmt.setInt(paramIdx++, user.getUserId());
			ppstmt.setInt(paramIdx++, user.getUserId());
			ppstmt.setInt(paramIdx++, line.getLineNo());
			ppstmt.setInt(paramIdx++, line.getLocator().getLocatorId());
			ppstmt.setInt(paramIdx++, line.getProduct().getProductId());
			if(line.getAsi() != null && line.getAsi().getAttributeSetInstanceId() >0)
				ppstmt.setInt(paramIdx++, line.getAsi().getAttributeSetInstanceId());
			else
				ppstmt.setNull(paramIdx++,Types.INTEGER );
			
			ppstmt.setBigDecimal(paramIdx++, line.getCountQty());
			ret = ppstmt.executeUpdate();
		} catch (Exception ex) {
			logger.error(String.format("Cannot Save Stocktaking Line !"));
			ex.printStackTrace();
		} finally {
			dbObjClosed(rset);
			dbObjClosed(ppstmt);
		}
		
		
		return ret;
	}

	@Override
	public int deleteStocktakingLine(int lineId) {
		int ret = 0;
		Connection conn = null;
		PreparedStatement ppstmt = null;
		
		StringBuffer updateSQL = new StringBuffer("DELETE FROM ext_stocktakingline WHERE ext_stocktakingline_id = ? \n");
		
		try {
			conn = getConnection();
			ppstmt = conn.prepareStatement(updateSQL.toString());
			int paramIdx = 1;

			ppstmt.setInt(paramIdx++, lineId);
			
			ret = ppstmt.executeUpdate();
		} catch (Exception ex) {
			logger.error(String.format("Cannot Remove Stocktaking Line !"));
			ex.printStackTrace();
		} finally {
			dbObjClosed(ppstmt);
		}
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see th.co.cenos.dao.StocktakingDao#updateQty(int, java.math.BigDecimal, th.co.cenos.model.User)
	 */
	@Override
	public int updateQty(int i_lineId, BigDecimal bd_countQty, User user) {
		// TODO Auto-generated method stub
		int ret = 0;
		Connection conn = null;
		PreparedStatement ppstmt = null;
		
		StringBuffer updateSQL = new StringBuffer("UPDATE ext_stocktakingline SET qtycount = ? ,updatedby = ? ,updated = now() WHERE ext_stocktakingline_id = ? \n");
		
		try {
			conn = getConnection();
			ppstmt = conn.prepareStatement(updateSQL.toString());
			int paramIdx = 1;
						
			ppstmt.setBigDecimal(paramIdx++, bd_countQty);
			ppstmt.setInt(paramIdx++, user.getUserId());
			ppstmt.setInt(paramIdx++, i_lineId);
			
			ret = ppstmt.executeUpdate();
		} catch (Exception ex) {
			logger.error(String.format("Cannot Update Stocktaking Line !"));
			ex.printStackTrace();
		} finally {
			dbObjClosed(ppstmt);
		}
		
		return ret;
	}
	
	
}
