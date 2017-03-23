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
import th.co.cenos.model.Warehouse;
import th.co.cenos.services.SecurityService;
import th.co.cenos.services.PIDocService;
import th.co.cenos.web.WebSession;

/**
 * @function myStock
 * @package th.co.cenos.controller
 * @classname LoginController
 * @author Pasuwat Wang (CENS ONLINE SERVICES)
 * @created Nov 16, 2016 10:42:48 AM
 */
@Controller
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	SecurityService securityService;
	
	@Autowired
	PIDocService stocktakingService;
	
	 @Value("${client.id}")
     private String adClientId;
	 
	 @Value("${session.timeout}")
     private String sessionTimeout;
	 
	 @Value("${login.role}")
     private String loginRole;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showLogin() {
		logger.debug("Login page");
		return "login";
	}
	
	private int getADClientId(){
		int ret = 0;
		if(StringUtils.isEmpty(adClientId))
			return ret;
		
		try{
			ret = Integer.valueOf(adClientId);
		}
		catch(Exception e){
			logger.debug(String.format("Cannot Parse AD Client ID %s", adClientId));
		}
		
		return ret ;
	}
	
	private int getSessionTimeout(){
		int ret = 1800;
		if(StringUtils.isEmpty(sessionTimeout))
			return ret;
		
		try{
			ret = Integer.valueOf(sessionTimeout);
		}
		catch(Exception e){
			logger.debug(String.format("Cannot Parse AD Client ID %s", sessionTimeout));
		}
		
		return ret ;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(@RequestParam("userId") String userId , @RequestParam("pwd") String pwd , HttpServletRequest request) {
		ModelAndView model = null;
		User user = new User();
		user.setUsername(userId);
		user.setPwd(pwd);
		
		if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(pwd) ){
			model = new ModelAndView("redirect:/login.jsp");
			model.addObject("error", "err.login.noUserPassword");
			return model;
		}
		
		logger.debug(String.format("Login Username : %s Password : %s", userId , pwd));
		user = securityService.authentication(getADClientId(),userId, pwd);
		
		if(user == null){
			model = new ModelAndView("redirect:/login.jsp");
			model.addObject("error", "err.login.failure");
			return model;
		}
		
		// Set User Session
		
		// set Role
		user.setRole(loginRole);
		
		request.getSession().setMaxInactiveInterval(getSessionTimeout()); 
		request.getSession().setAttribute(WebSession._LOGIN_USER, user);
		
		model = new ModelAndView();
		model.setViewName("default");
		model.addObject("warehouseL", securityService.getUserWarehouse(user));
		
		return model;
	}
	
	@RequestMapping(value = "/default", method = RequestMethod.GET)
	public ModelAndView defaultPage( HttpServletRequest request) {
		ModelAndView model = null;

		User user = WebSession.getLoginUser(request);
		
		if(user == null){
			model = new ModelAndView("redirect:/login.jsp");
			model.addObject("error", "err.login.failure");
			return model;
		}
		
		model = new ModelAndView();
		model.setViewName("default");
		model.addObject("warehouseL", securityService.getUserWarehouse(user));
		
		return model;
	}
	
	@RequestMapping(value = "/default", method = RequestMethod.POST)
	public ModelAndView defaultValue(@RequestParam("wh") String warehouseId , HttpServletRequest request) {
		ModelAndView model = null;
		Warehouse warehouse = null;
		
		logger.debug(String.format("Default Warehouse : %s ", warehouseId));
		
		if(StringUtils.isEmpty(warehouseId)){
			model = new ModelAndView();
			model.setViewName("default");
			model.addObject("error", "err.default.noWarehouseId");
			model.addObject("warehouseL", securityService.getUserWarehouse(WebSession.getLoginUser(request)));
			return model;
		}
		
		warehouse = securityService.getWarehouse(Integer.valueOf(warehouseId));
		
		if(warehouse == null){
			model = new ModelAndView();
			model.setViewName("default");
			model.addObject("error", "err.default.noWarehouseFound");
			model.addObject("warehouseL", securityService.getUserWarehouse(WebSession.getLoginUser(request)));
			return model;
		}
		
		// Set Warehouse into Session
		request.getSession().setAttribute(WebSession._DEFAULT_WAREHOUSE, warehouse);
		
		model = new ModelAndView();
		model.setViewName("redirect:/home");
		
		return model;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView showLogin(HttpServletRequest request) {
		ModelAndView model = null;
		model = new ModelAndView("redirect:/login.jsp");
		
		return model;
	}
	
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request) {
		ModelAndView model = null;
		model = new ModelAndView("redirect:/login.jsp");
		request.getSession().invalidate();
		
		return model;
	}
}
