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
package th.co.censos.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import th.co.cenos.dao.imp.ProductDaoImp;

/**
 * @function myStock
 * @package th.co.censos.utils
 * @classname ADSequenceUtil
 * @author Pasuwat Wang (CENS ONLINE SERVICES)
 * @created Nov 24, 2016 10:09:14 AM
 */
@Component
public class ADSequenceUtil {
	private static DataSource dataSource;
	private static Logger logger;
	
	@Autowired
	DataSource _dataSource;
	
	@PostConstruct
    public void init() {
		ADSequenceUtil.dataSource = _dataSource;
		logger = LoggerFactory.getLogger(ADSequenceUtil.class);
    }
	
	public static synchronized int getNextId(String tableName ) throws Exception{
		int nexId = 0;
		String getIdSQL = "SELECT nextid(CAST(AD_Sequence_Id AS integer),'N') as nextId FROM AD_Sequence WHERE Name = ?";
		
		Connection conn = null;
		PreparedStatement ppstmt = null;
		ResultSet rset = null;
		
		try{
			conn = dataSource.getConnection();
			ppstmt = conn.prepareStatement(getIdSQL);
			ppstmt.setString(1, tableName);
			rset = ppstmt.executeQuery();
			
			if(rset.next()){
				nexId = rset.getInt("nextId");
			}
		}
		catch(Exception ex){
			logger.error("Cannot Get NextId!");
			ex.printStackTrace();
		}
		finally {
			try{
				if(rset != null)
					rset.close();
			}
			catch(Exception ex){
				logger.warn("DB Object cannot closed!");
			}
			
			try{
				if(ppstmt != null)
					ppstmt.close();
			}
			catch(Exception ex){
				logger.warn("DB Object cannot closed!");
			}
		}
		
		return nexId;
	}
	
	public static synchronized String getDocumentNo(String tableName , String documentBaseType , int adClientId , int adOrgId) throws Exception{
		String prefix = "";
		String suffix = "";
		String documentNo = "";
		StringBuffer getDocNoSQL = new StringBuffer("SELECT dt.IsDocNoControlled , dt.DocNoSequence_ID , seq.prefix,nextid(CAST(seq.AD_Sequence_Id AS integer),'N') as nextId ,seq.suffix \n");
		getDocNoSQL.append("FROM C_DocType dt LEFT JOIN AD_Sequence seq ON seq.AD_Sequence_Id = dt.DocNoSequence_ID \n")
			.append("WHERE dt.DocBaseType = 'MMI' AND dt.AD_Client_Id = ? AND dt.AD_Org_ID IN (0,?) \n")
			.append("ORDER BY dt.AD_Org_ID DESC ");
		
		Connection conn = null;
		PreparedStatement ppstmt = null;
		ResultSet rset = null;
		
		try{
			conn = dataSource.getConnection();
			ppstmt = conn.prepareStatement(getDocNoSQL.toString());
			ppstmt.setString(1, documentBaseType);
			ppstmt.setInt(2, adClientId);
			ppstmt.setInt(3, adOrgId);
			
			rset = ppstmt.executeQuery();
			
			if(rset.next()){
				boolean isDocNoControlled = "Y".equals(rset.getString("IsDocNoControlled"));
				if(isDocNoControlled){
					prefix = rset.getString("prefix");
					documentNo = rset.getString("nextId");
					suffix = rset.getString("suffix");
				}
				else{
					// Execute By Table
					String docNoSQL = "SELECT prefix, nextid(CAST(AD_Sequence_Id AS integer),'N') as nextId , suffix FROM AD_Sequence WHERE Name = ?";
					ppstmt = conn.prepareStatement(docNoSQL);
					ppstmt.setString(1, "DocumentNo_"+tableName);
					rset = ppstmt.executeQuery();
					if(rset.next()){
						prefix = rset.getString("prefix");
						documentNo = rset.getString("nextId");
						suffix = rset.getString("suffix");
					}
				}
			}
		}
		catch(Exception ex){
			logger.error("Cannot Get NextId!");
			ex.printStackTrace();
		}
		finally {
			try{
				if(rset != null)
					rset.close();
			}
			catch(Exception ex){
				logger.warn("DB Object cannot closed!");
			}
			
			try{
				if(ppstmt != null)
					ppstmt.close();
			}
			catch(Exception ex){
				logger.warn("DB Object cannot closed!");
			}
		}
		
		return prefix+documentNo+suffix;
	}
	
}
