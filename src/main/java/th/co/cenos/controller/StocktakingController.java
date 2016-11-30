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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import th.co.cenos.model.AttributeSetInstance;
import th.co.cenos.model.Locator;
import th.co.cenos.model.Product;
import th.co.cenos.model.Stocktaking;
import th.co.cenos.model.StocktakingLine;
import th.co.cenos.model.User;
import th.co.cenos.model.Warehouse;
import th.co.cenos.services.ProductService;
import th.co.cenos.services.SecurityService;
import th.co.cenos.services.StocktakingService;
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
	
	private Logger logger = LoggerFactory.getLogger(StocktakingController.class);
	
	@Autowired
	ProductService productService;
	
	@Autowired
	SecurityService securityService;
	
	@Autowired
	StocktakingService stocktakingService; 
	
	@RequestMapping(value = "/stocktaking", method = RequestMethod.GET)
	public ModelAndView showStocktakingPage(HttpServletRequest request) {
		Warehouse warehouse = WebSession.getDefaultWarehouse(request);
		List<Locator> locatorL = warehouse.getLocatorL();
		
		ModelAndView model = new ModelAndView();
		model.setViewName("stocktaking");
		model.addObject("locatorL", locatorL);
		
		return model;
	}
	
	@RequestMapping(value = "/stocktaking/detail", method = RequestMethod.GET)
	public ModelAndView showStocktakingDetail(@RequestParam("locator") String locatorId,HttpServletRequest request) {
		ModelAndView model = null;
		Stocktaking stocktaking = WebSession.getOpenedStocktaking(request);
		int i_locator_id = 0;
		
		try{
			if(StringUtils.isEmpty(locatorId)){
				model = new ModelAndView("redirect:/stocktaking");
				model.addObject("error", "err.stocktaking.locatorId");
				return model;
			}
			
			i_locator_id = Integer.valueOf(locatorId);
		}catch(Exception ex){
			// Cannot Parse Locator
			model = new ModelAndView("redirect:/stocktaking");
			model.addObject("error", "err.stocktaking.parsing");
			return model;
		}
		
		Locator locator = getLocator(i_locator_id , WebSession.getDefaultWarehouse(request));
		if(locator == null){
			// Cannot Find Locator
			model = new ModelAndView("redirect:/stocktaking");
			model.addObject("error", "err.stocktaking.locator");
			return model;
		}
		
		model = new ModelAndView();
		model.setViewName("stocktaking-detail");
		
		List<StocktakingLine> detailL = new ArrayList<StocktakingLine>();
		for(StocktakingLine line : stocktaking.getLineL()){
			if(line.getLocator().getLocatorId() == locator.getLocatorId())
				detailL.add(line);
		}
		
		model.addObject("detailL", detailL);
		model.addObject("locator", locator);

		return model;
	}

	/**
	 * @param locatorId
	 * @param defaultWarehouse
	 * @return
	 */
	private Locator getLocator(int locatorId, Warehouse defaultWarehouse) {
		List<Locator> locatorL = defaultWarehouse.getLocatorL();
		Locator ret = null;
		for(Locator locator : locatorL){
			if(locator.getLocatorId() == locatorId){
				ret = locator;
				break;
			}
		}
		
		return ret;
	}

	@RequestMapping(value = "/stocktaking/detail/new", method = RequestMethod.GET)
	public ModelAndView showInputPage(@RequestParam("locator") String locatorId,HttpServletRequest request) {
		ModelAndView model = null;
		int i_locator_id = 0;
		
		try{
			if(StringUtils.isEmpty(locatorId)){
				model = new ModelAndView("redirect:/stocktaking");
				model.addObject("error", "err.stocktaking.locatorId");
				return model;
			}
			
			i_locator_id = Integer.valueOf(locatorId);
		}catch(Exception ex){
			// Cannot Parse Locator
			model = new ModelAndView("redirect:/stocktaking");
			model.addObject("error", "err.stocktaking.parsing");
			return model;
		}
		
		Locator locator = getLocator(i_locator_id , WebSession.getDefaultWarehouse(request));
		if(locator == null){
			// Cannot Find Locator
			model = new ModelAndView("redirect:/stocktaking");
			model.addObject("error", "err.stocktaking.locator");
			return model;
		}
		
		StocktakingLine stkLine = new StocktakingLine();
		
		model = new ModelAndView();
		model.setViewName("stocktaking-new");
		model.addObject("locator", locator);
		model.addObject("stkLine", stkLine);
		
		return model;
	}

	@RequestMapping(value = "/stocktaking/detail/add", method = RequestMethod.POST)
	public ModelAndView addNewLine(@RequestParam("locatorId") String locatorId,
									@RequestParam("pdCode") String pdCode,
									@RequestParam("asiId") String asiId,
									@RequestParam("countQty") String countQty,
									HttpServletRequest request) 
	{
		ModelAndView model = new ModelAndView();
		int i_locatorId = 0;
		int i_asiId = 0;
		BigDecimal bd_countQty = BigDecimal.ZERO;
		StocktakingLine line = new StocktakingLine();
		Product product = null;
		AttributeSetInstance asi = null;
		
		User user = WebSession.getLoginUser(request);
		Warehouse warehouse = WebSession.getDefaultWarehouse(request);
		Stocktaking stocktaking = WebSession.getOpenedStocktaking(request);
		
		// Start Validation 
		/* 1 Check Input Data Type*/
		// 1.1 Locator Id
		try{
			i_locatorId = Integer.valueOf(locatorId);
		}
		catch(Exception ex){
			model = new ModelAndView("redirect:/stocktaking");
			model.addObject("error", "err.stocktaking.locator");
			return model;
		}
		
		Locator locator = getLocator(i_locatorId , WebSession.getDefaultWarehouse(request));
		
		// 1.2 asiId
		if(!StringUtils.isEmpty(asiId)){
			try{
				i_asiId = Integer.valueOf(asiId);
			}
			catch(Exception ex){
				model.setViewName("stocktaking-new");
				model.addObject("stkLine", line);
				model.addObject("locator", locator);
				model.addObject("error", "err.stocktaking.parsing");
				model.addObject("errParams", "ASI Id" );
				return model;
			}
		}
		
		// 1.3 count qty
		if(!StringUtils.isEmpty(countQty)){
			try{
				bd_countQty = new BigDecimal(countQty);
			}
			catch(Exception ex){
				model.setViewName("stocktaking-new");
				model.addObject("stkLine", line);
				model.addObject("locator", locator);
				model.addObject("error", "err.stocktaking.parsing");
				model.addObject("errParams", "Count Qty" );
				return model;
			}
		}
		
		// 2. Check Required Field
		// 2.1 Required Product
		if(StringUtils.isEmpty(pdCode)){
			model.setViewName("stocktaking-new");
			model.addObject("stkLine", line);
			model.addObject("locator", locator);
			model.addObject("error", "err.field.required");
			model.addObject("errParams", "Product" );
			return model;
		}
		
		// 2.2 Required Count Qty
		if(StringUtils.isEmpty(countQty)){
			model.setViewName("stocktaking-new");
			model.addObject("stkLine", line);
			model.addObject("locator", locator);
			model.addObject("error", "err.field.required");
			model.addObject("errParams", "Count Qty" );
			return model;
		}
		
		// 3. Check value is existing in the DB
		// 3.1 Product
		product = productService.getProductByKey(user.getAdClientId(), pdCode);
		if(product == null){
			model.setViewName("stocktaking-new");
			model.addObject("stkLine", line);
			model.addObject("locator", locator);
			model.addObject("error", "err.cannot.find.db");
			model.addObject("errParams", "Product" );
			return model;
		}
		
		// 3.2 ASI
		if(i_asiId > 0){
			asi = productService.getAttributeSetInstanceById(i_asiId);
			
			if(asi == null){
				model.setViewName("stocktaking-new");
				model.addObject("stkLine", line);
				model.addObject("locator", locator);
				model.addObject("error", "err.cannot.find.db");
				model.addObject("errParams", "ASI ID" );
				return model;
			}
			
			// Check asi is product asi
			if(!productService.isProductASI(product, asi)){
				model.setViewName("stocktaking-new");
				model.addObject("stkLine", line);
				model.addObject("locator", locator);
				model.addObject("error", "err.not.product.asi");
				model.addObject("errParams", product.getProductSrhKey()+":"+product.getProductName());
				return model;
			}
		}
		// End Validation
	
		line.setAdOrgId(warehouse.getAdOrgId());
		line.setProduct(product);
		line.setAsi(asi);
		line.setCountQty(bd_countQty);
		line.setLocator(locator);
		line.setStocktakingId(stocktaking.getStocktakingId());
		line.setLineNo(getNextLineNo(stocktaking));
		
		int ret = stocktakingService.saveStocktakingLine(line, user);
		// Handle Cannot Save Stocktaking Line
		if(ret <= 0){
			model.setViewName("stocktaking-new");
			model.addObject("stkLine", line);
			model.addObject("locator", locator);
			model.addObject("error", "err.stocktaking.cannotSave");
			return model;
		}
		
		// Save Success 
		// Reload Stocktaking in Session
		stocktaking = stocktakingService.getOpenStocktaking(warehouse);
		request.getSession().setAttribute(WebSession._STOCKTAKING_DOCUMENT, stocktaking);
		
		model = new ModelAndView();
		model.setViewName("redirect:/stocktaking/detail?locator="+locator.getLocatorId());
		
		return model;
	}
	
	private int getNextLineNo(Stocktaking stocktaking){
		int ret = 0;
		List<StocktakingLine> lineL = stocktaking.getLineL();
		for(StocktakingLine line : lineL){
			if(line.getLineNo() > ret)
				ret = line.getLineNo();
		}
		
		ret +=10;
				
		return ret;
	}
	
	@RequestMapping(value = "/stocktaking/detail/edit", method = RequestMethod.POST)
	public ModelAndView editLine(@RequestParam("editLineId") String lineId,
			@RequestParam("editQty") String qty,
			@RequestParam("editLocatorId") String locatorId,
			HttpServletRequest request)
	{
		// Validate Qty
		ModelAndView model = new ModelAndView();
		model.setViewName("stocktaking-detail");
		Warehouse warehouse = WebSession.getDefaultWarehouse(request);
		Stocktaking stocktaking = WebSession.getOpenedStocktaking(request);
		User user = WebSession.getLoginUser(request);
		BigDecimal bd_countQty = BigDecimal.ZERO;
		Locator locator = null;
		int i_lineId = 0;
		int i_locatorId = 0;
		boolean isError = false;
		
		// Validate
		// 1. Check Input Qty Data Type
		if(!StringUtils.isEmpty(qty)){
			try{
				bd_countQty = new BigDecimal(qty);
				i_lineId = Integer.valueOf(lineId);
				i_locatorId = Integer.valueOf(locatorId);
			}
			catch(Exception ex){
				model.addObject("error", "err.stocktaking.parsing");
				model.addObject("errParams", " Count Qty" );
				isError = true;
			}
		}
		
		// 2. Check Required
		if(StringUtils.isEmpty(qty)){
			model.addObject("error", "err.field.required");
			model.addObject("errParams", "Count Qty" );
			isError = true;
		}
		
		if(!isError){
			int ret = stocktakingService.updayeQty(i_lineId,bd_countQty , user);
			
			if(ret > 0){
				stocktaking = stocktakingService.getOpenStocktaking(warehouse);
				request.getSession().setAttribute(WebSession._STOCKTAKING_DOCUMENT, stocktaking);
			}
			else{
				model.addObject("error", "err.cannot.update.qty");
			}
		}
		
		locator = getLocator(i_locatorId, warehouse);
		
		List<StocktakingLine> detailL = new ArrayList<StocktakingLine>();
		for(StocktakingLine line : stocktaking.getLineL()){
			if(line.getLocator().getLocatorId() == locator.getLocatorId())
				detailL.add(line);
		}
		
		model.addObject("detailL", detailL);
		model.addObject("locator", locator);

		return model;		
	}
	
	@RequestMapping(value = "/stocktaking/detail/delete", method = RequestMethod.POST)
	public ModelAndView deleteLine(@RequestParam("delLineId") String lineId,
			@RequestParam("delLocatorId") String locatorId,
			HttpServletRequest request)
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("stocktaking-detail");
		Warehouse warehouse = WebSession.getDefaultWarehouse(request);
		Stocktaking stocktaking = WebSession.getOpenedStocktaking(request);
		Locator locator = null;

		int i_lineId = Integer.valueOf(lineId);
		int i_locatorId = Integer.valueOf(locatorId);
		
		int ret = stocktakingService.deleteStocktakingLine(i_lineId);
 
		if(ret > 0){
			stocktaking = stocktakingService.getOpenStocktaking(warehouse);
			request.getSession().setAttribute(WebSession._STOCKTAKING_DOCUMENT, stocktaking);
		}
		else{
			model.addObject("error", "err.cannot.delete");
			model.addObject("errParams", "Stocktaking Line" );
		}
		
		locator = getLocator(i_locatorId, warehouse);
		
		List<StocktakingLine> detailL = new ArrayList<StocktakingLine>();
		for(StocktakingLine line : stocktaking.getLineL()){
			if(line.getLocator().getLocatorId() == locator.getLocatorId())
				detailL.add(line);
		}
		
		
		model.addObject("detailL", detailL);
		model.addObject("locator", locator);

		return model;		
	}
}
