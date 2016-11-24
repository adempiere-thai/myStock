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
package th.co.cenos.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.co.cenos.dao.StocktakingDao;
import th.co.cenos.model.Stocktaking;
import th.co.cenos.model.StocktakingLine;
import th.co.cenos.model.Warehouse;
import th.co.cenos.services.StocktakingService;

/**
 * @function myStock
 * @package th.co.cenos.service.imp
 * @classname StocktakingServiceImp
 * @author Pasuwat Wang (CENS ONLINE SERVICES)
 * @created Nov 24, 2016 3:10:43 PM
 */
@Service
@Transactional
public class StocktakingServiceImp implements StocktakingService {

	@Autowired
	StocktakingDao stocktakingDao;
	
	@Override
	public Stocktaking getOpenStocktaking(Warehouse warehouse) {
		// TODO Auto-generated method stub
		return stocktakingDao.getOpenStocktaking(warehouse);
	}

	@Override
	public StocktakingLine saveStocktakingLine(StocktakingLine line) {
		// TODO Auto-generated method stub
		return null;
	}

}
