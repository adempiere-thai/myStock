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
package th.co.cenos.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import th.co.cenos.model.Locator;
import th.co.cenos.model.Stocktaking;
import th.co.cenos.model.Warehouse;
import th.co.cenos.web.WebSession;

/**
 * @function myStock
 * @package th.co.cenos.controller
 * @classname LoginController
 * @author Pasuwat Wang (CENS ONLINE SERVICES)
 * @created Nov 16, 2016 10:42:48 AM
 */
@Controller
public class StocktakingController {
	
	private static final Logger logger = LoggerFactory.getLogger(StocktakingController.class);
	
	@RequestMapping(value = "/stocktaking", method = RequestMethod.GET)
	public ModelAndView showProductPage(HttpServletRequest request) {
		Warehouse warehouse = WebSession.getDefaultWarehouse(request);
		List<Locator> locatorL = warehouse.getLocatorL();
		
		ModelAndView model = new ModelAndView();
		model.setViewName("stocktaking");
		model.addObject("locatorL", locatorL);
		
		return model;
	}
	
	@RequestMapping(value = "/stocktaking/detail", method = RequestMethod.GET)
	public ModelAndView showStocktakingDetail(HttpServletRequest request) {
		Stocktaking stocktaking = WebSession.getOpenedStocktaking(request);
		
		ModelAndView model = new ModelAndView();
		model.setViewName("stocktaking-detail");
		model.addObject("stocktaking", stocktaking);

		return model;
	}
}
