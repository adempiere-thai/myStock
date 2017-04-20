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
import th.co.cenos.dao.ProductDao;
import th.co.cenos.model.AttributeSetInstance;
import th.co.cenos.model.Product;

/**
 * @function myStock
 * @package th.co.cenos.dao.imp
 * @classname UserDaoImp
 * @author Pasuwat Wang (CENS ONLINE SERVICES)
 * @created Nov 20, 2016 12:49:27 AM
 */
@Repository
public class ProductDaoImp extends AbstractDao implements ProductDao {

	protected void init() {
		// TODO Auto-generated method stub
		logger = LoggerFactory.getLogger(ProductDaoImp.class);
	}

	public List<Product> getProductList(int adClientId, int mWarehouseId,
			String srhKey) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement ppstmt = null;
		ResultSet rset = null;
		List<Product> productL = null;
		StringBuffer fndProductSql = new StringBuffer(
				"SELECT stock.* FROM(  \n");
		fndProductSql
				.append("\t\tSELECT stk.m_product_id , stk.value product_value ,  stk.product_name , stk.IsStocked , stk.locator_code ,SUM(stk.qtyavailable) as qtyavailable, SUM(stk.qtyonhand) as qtyonhand, SUM(stk.qtyreserved) as qtyreserved, SUM(stk.qtyordered) as qtyordered \n")
				.append("\t\tFROM ext_product_stock_v stk \n")
				.append("\t\tWHERE stk.AD_Client_Id = ? AND stk.M_Warehouse_id = ? \n")
				.append("\t\tAND (lower(stk.product_name) like lower(?) or lower(stk.value) like lower(?) ) \n")
				.append("\t\tGROUP BY stk.m_product_id , stk.value ,  stk.product_name, stk.IsStocked,stk.locator_code ) stock \n")
				.append("WHERE ( stock.qtyavailable <> 0 OR stock.qtyonhand <> 0 OR stock.qtyreserved <> 0 OR stock.qtyordered <> 0 ) \n")
				.append("ORDER BY stock.product_value , stock.IsStocked DESC , stock.qtyavailable DESC ");

		try {
			conn = getConnection();
			ppstmt = conn.prepareStatement(fndProductSql.toString());

			int paramIdx = 1;
			ppstmt.setInt(paramIdx++, adClientId);
			ppstmt.setInt(paramIdx++, mWarehouseId);
			ppstmt.setString(paramIdx++, "%" + srhKey + "%");
			ppstmt.setString(paramIdx++, "%" + srhKey + "%");

			rset = ppstmt.executeQuery();
			while (rset.next()) {
				if (productL == null)
					productL = new ArrayList<Product>();

				Product product = new Product();
				product.setProductId(rset.getInt("m_product_id"));
				product.setProductName(rset.getString("product_name"));
				product.setProductSrhKey(rset.getString("product_value"));
				product.setLocatorCode(rset.getString("locator_code"));
				product.setAvailableQty(rset.getBigDecimal("qtyavailable"));
				product.setOnhandQty(rset.getBigDecimal("qtyonhand"));
				product.setOrderQty(rset.getBigDecimal("qtyordered"));
				product.setReservedQty(rset.getBigDecimal("qtyreserved"));

				productL.add(product);
			}
		} catch (Exception ex) {
			logger.error("Cannot Get Product List!");
			ex.printStackTrace();
		} finally {
			dbObjClosed(rset);
			dbObjClosed(ppstmt);
		}
		return productL;
	}

	public Product getProductById(int productId) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement ppstmt = null;
		ResultSet rset = null;
		Product product = null;
		StringBuffer productSQL = new StringBuffer(
				"SELECT * FROM M_Product pd WHERE pd.M_Product_Id = ? ");

		try {
			conn = getConnection();
			ppstmt = conn.prepareStatement(productSQL.toString());

			int paramIdx = 1;
			ppstmt.setInt(paramIdx++, productId);

			rset = ppstmt.executeQuery();
			if (rset.next()) {
				product = new Product();
				product.setProductId(rset.getInt("M_Product_ID"));
				product.setProductSrhKey(rset.getString("value"));
				product.setProductName(rset.getString("Name"));
			}
		} catch (Exception ex) {
			logger.error("Login Error");
			ex.printStackTrace();
		} finally {
			dbObjClosed(rset);
			dbObjClosed(ppstmt);
		}

		return product;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see th.co.cenos.dao.ProductDao#getProductByKey(java.lang.String)
	 */
	@Override
	public Product getProductByKey(int adClientId , String srhKey) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement ppstmt = null;
		ResultSet rset = null;
		Product product = null;
		StringBuffer productSQL = new StringBuffer(
				"SELECT * FROM M_Product pd WHERE pd.AD_Client_ID = ? AND UPPER(pd.value) = UPPER(?) ");

		try {
			conn = getConnection();
			ppstmt = conn.prepareStatement(productSQL.toString());

			int paramIdx = 1;
			ppstmt.setInt(paramIdx++, adClientId);
			ppstmt.setString(paramIdx++, srhKey);

			rset = ppstmt.executeQuery();
			if (rset.next()) {
				product = new Product();
				product.setProductId(rset.getInt("M_Product_ID"));
				product.setProductSrhKey(rset.getString("value"));
				product.setProductName(rset.getString("Name"));
			}
		} catch (Exception ex) {
			logger.error("Login Error");
			ex.printStackTrace();
		} finally {
			dbObjClosed(rset);
			dbObjClosed(ppstmt);
		}

		return product;
	}

	/* (non-Javadoc)
	 * @see th.co.cenos.dao.ProductDao#isProductASI(th.co.cenos.model.Product, th.co.cenos.model.AttributeSetInstance)
	 */
	@Override
	public boolean isProductASI(Product product, AttributeSetInstance asi) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement ppstmt = null;
		ResultSet rset = null;
		
		boolean ret = false;
		
		StringBuffer productSQL = new StringBuffer(
			"SELECT * FROM M_Storage st WHERE st.M_Product_Id = ? AND st.M_AttributeSetInstance_Id = ? ");

		try {
			conn = getConnection();
			ppstmt = conn.prepareStatement(productSQL.toString());

			int paramIdx = 1;
			ppstmt.setInt(paramIdx++, product.getProductId());
			ppstmt.setInt(paramIdx++, asi.getAttributeSetInstanceId());

			rset = ppstmt.executeQuery();
			if (rset.next()) {
				ret = true;
			}
		} catch (Exception ex) {
			logger.error("Login Error");
			ex.printStackTrace();
		} finally {
			dbObjClosed(rset);
			dbObjClosed(ppstmt);
		}
		
		return ret;
	}

}
