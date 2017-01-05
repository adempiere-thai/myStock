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
import th.co.cenos.dao.WarehouseLocatorDao;
import th.co.cenos.model.Locator;
import th.co.cenos.model.User;
import th.co.cenos.model.Warehouse;

/**
 * @function myStock
 * @package th.co.cenos.dao.imp
 * @classname UserDaoImp
 * @author Pasuwat Wang (CENS ONLINE SERVICES)
 * @created Nov 20, 2016 12:49:27 AM
 */
@Repository
public class WarehouseLocatorDaoImp extends AbstractDao implements
		WarehouseLocatorDao {

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		logger = LoggerFactory.getLogger(WarehouseLocatorDaoImp.class);
	}

	@Override
	public List<Warehouse> getUserAccessWarehouses(User user) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement ppstmt = null;
		ResultSet rset = null;
		List<Warehouse> warehouseL = null;
		StringBuffer userWHSql = new StringBuffer(
				"SELECT distinct wh.M_Warehouse_ID ,wh.name warehouseName , wh.AD_Client_ID , wh.AD_Org_Id ,o.Name as orgName  FROM M_Warehouse wh \n");
		userWHSql.append("INNER JOIN AD_Role_OrgAccess roa ON roa.AD_Org_ID = wh.AD_Org_ID \n")
				 .append("INNER JOIN AD_USER_ROLES ur ON roa.AD_Role_ID = ur.AD_Role_ID \n")
				 .append("INNER JOIN AD_Org o ON o.AD_Org_Id = wh.AD_Org_Id \n")
				 .append("WHERE ur.AD_User_ID = ? \n")
				 .append("AND roa.IsActive = 'Y' wh.IsActive = 'Y' \n")
				 .append("ORDER BY wh.M_Warehouse_ID ");

		try {
			conn = getConnection();
			ppstmt = conn.prepareStatement(userWHSql.toString());

			int paramIdx = 1;
			ppstmt.setInt(paramIdx++, user.getUserId());

			rset = ppstmt.executeQuery();
			while (rset.next()) {
				if(warehouseL == null)
					warehouseL = new ArrayList<Warehouse>();
				
				Warehouse warehouse = new Warehouse();
				warehouse.setAdOrgId(rset.getInt("AD_Org_Id"));
				warehouse.setWarehouseId(rset.getInt("M_Warehouse_Id"));
				warehouse.setWarehouseName(rset.getString("warehouseName"));
				warehouse.setOrgName(rset.getString("orgName"));
				
				warehouseL.add(warehouse);
			}
		} catch (Exception ex) {
			logger.error("Cannot get User Warehouse!");
			ex.printStackTrace();
		} finally {
			dbObjClosed(rset);
			dbObjClosed(ppstmt);
		}
		return warehouseL;
	}

	@Override
	public Warehouse getWarehouseById(int mWarehouseId) {
		Connection conn = null;
		PreparedStatement ppstmt = null;
		ResultSet rset = null;
		
		Warehouse warehouse = null;
		List<Locator> locatorL = null;
		StringBuffer whSql = new StringBuffer("SELECT  wh.AD_Org_Id ,wh.M_Warehouse_Id , wh.Name as WarehouseName , lo.M_Locator_Id , lo.Value , lo.isDefault \n");
		whSql.append("FROM M_Warehouse wh LEFT JOIN M_Locator lo ON wh.M_Warehouse_ID = lo.M_Warehouse_Id \n")
			.append("WHERE wh.M_Warehouse_Id =? AND lo.IsActive = 'Y' ORDER BY lo.PriorityNo Desc ");
		try {
			conn = getConnection();
			ppstmt = conn.prepareStatement(whSql.toString());
			int paramIdx = 1;
			ppstmt.setInt(paramIdx++, mWarehouseId);
			rset = ppstmt.executeQuery();
			while(rset.next()){
				if(warehouse == null){
					warehouse = new Warehouse();
					warehouse.setAdOrgId(rset.getInt("AD_Org_Id"));
					warehouse.setWarehouseId(rset.getInt("M_Warehouse_Id"));
					warehouse.setWarehouseName(rset.getString("WarehouseName"));
					// Intial List
					locatorL = new ArrayList<Locator>();
				}
				
				// Check Warehouse was set Locator
				if(rset.getInt("M_Locator_Id") >0 ){
					Locator locator = new Locator();
					locator.setLocatorId(rset.getInt("M_Locator_Id"));
					locator.setLocatorKey(rset.getString("Value"));
					locator.setDefaultLocator("Y".equals(rset.getString("isDefault")));
					
					locatorL.add(locator);
				}
			}
			
			if(warehouse != null){
				warehouse.setLocatorL(locatorL);
			}
		} catch (Exception ex) {
			logger.error(String.format("Cannot get Warehouse %s !",mWarehouseId));
			ex.printStackTrace();
		} finally {
			dbObjClosed(rset);
			dbObjClosed(ppstmt);
		}
		return warehouse;
	}
	

	@Override
	public Locator getLocatorById(int mLocatorId) {
		// TODO Auto-generated method stub
		return null;
	}

}
