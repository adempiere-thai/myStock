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
package th.co.cenos.web;

import javax.servlet.http.HttpServletRequest;

import th.co.cenos.model.InternalUse;
import th.co.cenos.model.User;
import th.co.cenos.model.Warehouse;
import th.co.cenos.model.Stocktaking;

/**
 * @function myStock
 * @package th.co.cenos.web
 * @classname WebSession
 * @author Pasuwat Wang (CENS ONLINE SERVICES)
 * @created Nov 16, 2016 9:49:58 AM
 */
public class WebSession {
	public static final String _LOGIN_USER = "LOGIN_USER";
	public static final String _DEFAULT_WAREHOUSE = "DEFAULT_WAREHOUSE";
	public static final String _STOCKTAKING_DOCUMENT ="STOCKTAKING";
	public static final String _IS_OPEN_STOCKTAKING ="IS_OPEN_STOCKTAKING";
	public static final String _INTERNALUSE_DOCUMENT = "INTERNAL_USE";
	
	public static User getLoginUser(HttpServletRequest request){
		User user = null;
		
		if(request.getSession().getAttribute(_LOGIN_USER)!=null)
			user = (User)request.getSession().getAttribute(_LOGIN_USER) ;
		
		return user;
	}
	
	public static Stocktaking getOpenedStocktaking(HttpServletRequest request){
		Stocktaking stocktaking = null;
		
		if(request.getSession().getAttribute(_STOCKTAKING_DOCUMENT)!=null)
			stocktaking = (Stocktaking)request.getSession().getAttribute(_STOCKTAKING_DOCUMENT) ;
		
		return stocktaking;
	}
	
	public static Warehouse getDefaultWarehouse(HttpServletRequest request){
		Warehouse warehouse = null;
		
		if(request.getSession().getAttribute(_DEFAULT_WAREHOUSE)!=null)
			warehouse = (Warehouse)request.getSession().getAttribute(_DEFAULT_WAREHOUSE) ;
		
		return warehouse;
	}
	
	public static InternalUse getInternalUse(HttpServletRequest request){
		InternalUse internalUse = null;
		
		if(request.getSession().getAttribute(_INTERNALUSE_DOCUMENT)!=null)
			internalUse = (InternalUse)request.getSession().getAttribute(_INTERNALUSE_DOCUMENT) ;
		
		return internalUse;
	}
}
