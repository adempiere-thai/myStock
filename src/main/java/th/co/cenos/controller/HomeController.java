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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import th.co.cenos.model.User;
import th.co.cenos.model.Menu;
import th.co.cenos.services.SecurityService;
import th.co.cenos.web.WebSession;

/**
 * @function myStock
 * @package th.co.cenos.controller
 * @classname HomeController
 * @author Pasuwat Wang (CENS ONLINE SERVICES)
 * @created Nov 16, 2016 10:42:48 AM
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	SecurityService securityService;
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView showHomePage(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		List<Menu> menuL = securityService.getMenu(WebSession.getLoginUser(request));
		
		model.setViewName("home");
		model.addObject("menuL",menuL);
		model.addObject("warehouse", WebSession.getDefaultWarehouse(request));
		
		return model;
	}

}
