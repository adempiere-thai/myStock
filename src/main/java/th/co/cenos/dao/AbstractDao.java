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
package th.co.cenos.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import th.co.cenos.exceptions.NoDatasourceException;

/**
 * @function myStock
 * @package th.co.cenos.dao
 * @classname AbstractDao
 * @author Pasuwat Wang (CENS ONLINE SERVICES)
 * @created Nov 20, 2016 4:28:44 PM
 */
public abstract class AbstractDao {
	
	protected Logger logger; 
	
	public AbstractDao(){
		init();
	}
	
	@Autowired
	DataSource dataSource;
	
	protected Connection getConnection() throws NoDatasourceException , SQLException{
		if(dataSource == null)
			throw new NoDatasourceException("No Datasource");
		
		return dataSource.getConnection();
	}
	
	protected abstract void init();
	
	protected void dbObjClosed(Object o) {
		if(o == null)
			return;
		
		try{
			if( o instanceof ResultSet ){
				ResultSet rs = (ResultSet)o;
				rs.close();
			}
		}
		catch(Exception ex){
			logger.warn("DB Object cannot closed!");
		}
	}

}
