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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import th.co.cenos.model.Warehouse;

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
		StringBuffer stkSQL = new StringBuffer("SELECT stk.Ext_Stocktaking_Id , stkl.Ext_StocktakingLine_ID , stkl.Line ,  pd.M_Product_Id ,pd.VALUE as ProductKey,  pd.Name as ProductName \n");
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
	public int saveStocktakingLine(StocktakingLine line) {
		// TODO Auto-generated method stub
		int ret = 0;
		
		if(line.getStocktakingLineId() == 0){
			//ret = insertStocktakingLine(line);
		}
		else{
			//ret = updateStocktakingLine(line);
		}
		
		return ret;
	}

	
	@Override
	public int deleteStocktakingLine() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
