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

import java.io.IOException;
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

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import th.co.cenos.model.Product;
import th.co.cenos.model.User;
import th.co.cenos.model.Warehouse;
import th.co.cenos.services.ProductService;
import th.co.cenos.web.WebSession;
//import th.co.cenos.web.WebSession;

/**
 * @function myStock
 * @package th.co.cenos.controller
 * @classname LoginController
 * @author Pasuwat Wang (CENS ONLINE SERVICES)
 * @created Nov 16, 2016 10:42:48 AM
 */
@Controller
public class ProductController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	ProductService productService;
	
	@RequestMapping(value = "/product", method = RequestMethod.GET)
	public ModelAndView showProductPage(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		model.setViewName("product");
		
		return model;
	}
	
	@RequestMapping(value = "/getProductList" , method = RequestMethod.GET)
	public ModelAndView getProductList(@RequestParam("srhKey") String srhKey , HttpServletRequest request) {
		if(srhKey == null || srhKey.length() <3 ){
			logger.debug("At least 3 digit product search key.");
			return null;
		}
		
		User user =WebSession.getLoginUser(request);
		Warehouse warehouse = WebSession.getDefaultWarehouse(request);
		
		if(user == null){
			logger.debug("No User Login");
			return null;
		}

		List<Product> productL = null;
		String jsonString = null;
		
		productL = productService.getProductList(user.getAdClientId(), warehouse.getWarehouseId(), srhKey);
		jsonString = toJsonString(productL);
		
		ModelAndView model = new ModelAndView();
		model.setViewName("json");
		model.addObject("JSON", jsonString);
		
		return model;
	}
	
	private String toJsonString(List<Product> productL){
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = null;
		try {
			jsonString = mapper.writeValueAsString(productL);
		} catch (Exception e) {
			logger.error("Cannot Parse Object To JSON");
			e.printStackTrace();
		} 
		
		return jsonString;
	}

}
