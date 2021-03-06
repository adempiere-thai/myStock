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

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import th.co.cenos.dao.AbstractDao;
import th.co.cenos.dao.UserDao;
import th.co.cenos.model.User;

/**
 * @function myStock
 * @package th.co.cenos.dao.imp
 * @classname UserDaoImp
 * @author Pasuwat Wang (CENS ONLINE SERVICES)
 * @created Nov 20, 2016 12:49:27 AM
 */
@Repository
public class UserDaoImp extends AbstractDao implements UserDao {
	
	public User login(int adClientId, String username, String password) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement ppstmt = null;
		ResultSet rset = null;
		User user = null;
		StringBuffer loginSQL = new StringBuffer("SELECT * FROM AD_USER WHERE AD_Client_ID = ? AND name = ? AND Password = ? AND IsActive = 'Y' "); 
		
		try{
			conn = getConnection();
			ppstmt =  conn.prepareStatement(loginSQL.toString());
			
			int paramIdx = 1;
			ppstmt.setInt(paramIdx++, adClientId);
			ppstmt.setString(paramIdx++, username);
			ppstmt.setString(paramIdx++, password);
			
			rset = ppstmt.executeQuery();
			if(rset.next()){
				user =  new User();
				user.setAdClientId(adClientId);
				user.setUserId(rset.getInt("AD_User_Id"));
				user.setUsername(username);
				user.setPwd(password);
			}
		}
		catch(Exception ex){
			logger.error("Login Error");
			ex.printStackTrace();
		}
		finally{
			dbObjClosed(rset);
			dbObjClosed(ppstmt);
		}
		
		return user;
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		logger = LoggerFactory.getLogger(UserDaoImp.class);
	}

}
