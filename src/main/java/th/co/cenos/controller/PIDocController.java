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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import th.co.cenos.model.AttributeSetInstance;
import th.co.cenos.model.Locator;
import th.co.cenos.model.Product;
import th.co.cenos.model.PIDoc;
import th.co.cenos.model.PIDocLine;
import th.co.cenos.model.User;
import th.co.cenos.model.Warehouse;
import th.co.cenos.services.ProductService;
import th.co.cenos.services.SecurityService;
import th.co.cenos.services.PIDocService;
import th.co.cenos.web.WebSession;

/**
 * @function myStock
 * @package th.co.cenos.controller
 * @classname StocktakingController
 * @author Pasuwat Wang (CENS ONLINE SERVICES)
 * @created Nov 16, 2016 10:42:48 AM
 */
@Controller
public class PIDocController {
	
	private Logger logger = LoggerFactory.getLogger(PIDocController.class);
	
	@Autowired
	PIDocService piDocService; 
	
	@Value("${client.id}")
    private Integer adClientId;
	
	/*@Autowired
	PIDocValidator piDocValidator;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(piDocValidator);
	}
	*/
	
	@RequestMapping(value = "/stocktaking", method = RequestMethod.GET)
	public ModelAndView stocktakingPage(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		model.setViewName("stocktaking");
		
		PIDoc piDoc = new PIDoc();
		model.addObject("piDocFrm", piDoc);
		
		return model;
	}
	
	@RequestMapping(value = "/stocktaking", method = RequestMethod.POST)
	public ModelAndView submitPIDocNo(@ModelAttribute("piDocFrm") PIDoc piDocReq ,HttpServletRequest request) {
		logger.debug("submitPIDocNo : ", piDocReq.getPiDocNo());
		ModelAndView model = new ModelAndView();
		boolean isError = false;
		PIDoc piDoc = null;
		
		Warehouse wh = WebSession.getDefaultWarehouse(request);
		
		// Validate
		// Check Required Field
		if(StringUtils.isEmpty(piDocReq.getPiDocNo())){
			model.addObject("error", "err.field.required");
			model.addObject("errParams", "Document No.");
			isError = true;
		}
		piDoc = piDocService.findPIDocByDocNo(adClientId, wh.getAdOrgId(), piDocReq.getPiDocNo());
		// 1. Document does not exist
		if(!isError && piDoc == null){
			model.addObject("error", "err.piDoc.not.exist");
			model.addObject("errParams", piDocReq.getPiDocNo());
			isError = true;
		}
		// 2. Document is not in draft or prepare
		if(!isError && (!"DR".equals(piDoc.getDocStatus()) && !"PR".equals(piDoc.getDocStatus()))){
			model.addObject("error", "err.piDoc.is.not.draft");
			model.addObject("errParams", piDocReq.getPiDocNo());
			isError = true;
		}
		// 3. No document line.
		if(!isError && (piDoc.getTotalLines() <= 0)){
			model.addObject("error", "err.piDoc.no.lines");
			model.addObject("errParams", piDocReq.getPiDocNo());
			isError = true;
		}
		
		
		if(isError){
			model.addObject("piDocFrm", piDocReq);
			model.setViewName("stocktaking");
		}
		else{
			Warehouse warehouse = WebSession.getDefaultWarehouse(request);
			List<Locator> locatorL = warehouse.getLocatorL();
			Map<Integer , String> locatorOpt = new HashMap<Integer , String>();
			Locator defLocator = null;
			
			for(Locator locator : locatorL){
				locatorOpt.put(locator.getLocatorId(), locator.getLocatorKey());
				if(locator.isDefaultLocator())
					defLocator =locator; 
			}
			
			PIDocLine piDocLine = new PIDocLine();
			piDocLine.setPiDocId(piDoc.getPiDocId());
			piDocLine.setLocator(defLocator);
			piDocLine.setLocatorId(defLocator.getLocatorId());
			
			model.addObject("locatorL", locatorOpt);
			model.addObject("piDocLineFrm", piDocLine);
			model.setViewName("stocktaking-input");
		}
		
		return model;
	}
	
	@RequestMapping(value = "/stocktaking/detail", method = RequestMethod.GET)
	public ModelAndView redirectToStocktaking(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("redirect:/stocktaking");
		return model;
	}
	
	@RequestMapping(value = "/stocktaking/detail", method = RequestMethod.POST)
	public ModelAndView submitPIDocLine(@ModelAttribute("piDocLineFrm") PIDocLine piDocLineReq ,HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		boolean isError = false;
		PIDocLine piDocLine = null;
		piDocLineReq.setAdClientId(adClientId);
		
		if(piDocLineReq.getCountQty() == null){
			model.addObject("error", "err.field.required");
			model.addObject("errParams","Count Qty");
			isError = true;
		}
		
		if(!isError && StringUtils.isEmpty(piDocLineReq.getPdCode())){
			model.addObject("error", "err.field.required");
			model.addObject("errParams","Item Code");
			isError = true;
		}
		
		if(!isError && 
			(StringUtils.isEmpty(piDocLineReq.getAsi().getDescription()) 
				&& piDocLineReq.getAsi().getAttributeSetInstanceId() > 0 ))
		{
			model.addObject("error", "err.cannot.find.db");
			model.addObject("errParams","ASI");
			isError = true;
		}
		
		if(!isError && piDocLineReq.getCountQty().signum() <= 0){
			model.addObject("error", "err.piDocLine.countQty.negative");
			isError = true;
		}
		
		piDocLine = piDocService.findPIDocLine(piDocLineReq);
		if(!isError && piDocLine.getPiDocLineId() <= 0){
			model.addObject("error", "err.cannot.foud.piDocLine");
			isError = true;
		}
		
		if(!isError){
			int ret = piDocService.updatePIDocLine(piDocLine);
			if( ret <= 0){
				model.addObject("error", "err.cannot.update.piDocLine");
				isError = true;
			}
		}
		
		// Load Locator List
		Warehouse warehouse = WebSession.getDefaultWarehouse(request);
		List<Locator> locatorL = warehouse.getLocatorL();
		Map<Integer , String> locatorOpt = new HashMap<Integer , String>();
		
		Locator defLocator = null;
		
		for(Locator locator : locatorL){
			locatorOpt.put(locator.getLocatorId(), locator.getLocatorKey());
			if(locator.isDefaultLocator())
				defLocator =locator; 
		}
		
		// End Load Locator List
		
		if(isError){
			model.addObject("locatorL", locatorOpt);
			model.addObject("piDocLineFrm", piDocLine);
			model.setViewName("stocktaking-input");
		}
		else{
			String action = request.getParameter("action");
			
			if("save".equals(action)){
				model.setViewName("redirect:/home");
			}
			else{
				PIDocLine line = new PIDocLine();
				line.setPiDocId(piDocLineReq.getPiDocId());
				line.setLocator(defLocator);
				line.setLocatorId(defLocator.getLocatorId());
				
				model.addObject("locatorL", locatorOpt);
				model.addObject("piDocLineFrm",line );
				model.setViewName("stocktaking-input");
			}
		}
		
		return model;
	}
	
	/*
	@RequestMapping(value = "/stocktaking/detail", method = RequestMethod.GET)
	public ModelAndView showStocktakingDetail(@RequestParam("locator") String locatorId,HttpServletRequest request) {
		ModelAndView model = null;
		PIDoc stocktaking = WebSession.getOpenedStocktaking(request);
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
			model.addObject("error", "err.parsing");
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
		
		List<PIDocLine> detailL = new ArrayList<PIDocLine>();
		for(PIDocLine line : stocktaking.getLineL()){
			if(line.getLocator().getLocatorId() == locator.getLocatorId())
				detailL.add(line);
		}
		
		model.addObject("detailL", detailL);
		model.addObject("locator", locator);

		return model;
	}

	*//**
	 * @param locatorId
	 * @param defaultWarehouse
	 * @return
	 *//*
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
			model.addObject("error", "err.parsing");
			return model;
		}
		
		Locator locator = getLocator(i_locator_id , WebSession.getDefaultWarehouse(request));
		if(locator == null){
			// Cannot Find Locator
			model = new ModelAndView("redirect:/stocktaking");
			model.addObject("error", "err.stocktaking.locator");
			return model;
		}
		
		PIDocLine stkLine = new PIDocLine();
		
		model = new ModelAndView();
		model.setViewName("stocktaking-new");
		model.addObject("locator", locator);
		model.addObject("stkLine", stkLine);
		
		return model;
	}
*/
	
	/*@RequestMapping(value = "/stocktaking/detail/add", method = RequestMethod.POST)
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
		PIDocLine line = new PIDocLine();
		Product product = null;
		AttributeSetInstance asi = null;
		
		User user = WebSession.getLoginUser(request);
		Warehouse warehouse = WebSession.getDefaultWarehouse(request);
		PIDoc stocktaking = WebSession.getOpenedStocktaking(request);
		
		// Start Validation 
		 1 Check Input Data Type
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
				model.addObject("error", "err.parsing");
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
				model.addObject("error", "err.parsing");
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
		
		int ret = piDocService.saveStocktakingLine(line, user);
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
		stocktaking = piDocService.getOpenStocktaking(warehouse);
		request.getSession().setAttribute(WebSession._STOCKTAKING_DOCUMENT, stocktaking);
		
		model = new ModelAndView();
		model.setViewName("redirect:/stocktaking/detail?locator="+locator.getLocatorId());
		
		return model;
	}
	
	private int getNextLineNo(PIDoc stocktaking){
		int ret = 0;
		List<PIDocLine> lineL = stocktaking.getLineL();
		for(PIDocLine line : lineL){
			if(line.getLineNo() > ret)
				ret = line.getLineNo();
		}
		
		ret +=10;
				
		return ret;
	}*/
	
	/*@RequestMapping(value = "/stocktaking/detail/edit", method = RequestMethod.POST)
	public ModelAndView editLine(@RequestParam("editLineId") String lineId,
			@RequestParam("editQty") String qty,
			@RequestParam("editLocatorId") String locatorId,
			HttpServletRequest request)
	{
		// Validate Qty
		ModelAndView model = new ModelAndView();
		model.setViewName("stocktaking-detail");
		Warehouse warehouse = WebSession.getDefaultWarehouse(request);
		PIDoc stocktaking = WebSession.getOpenedStocktaking(request);
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
				model.addObject("error", "err.parsing");
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
			int ret = piDocService.updayeQty(i_lineId,bd_countQty , user);
			
			if(ret > 0){
				stocktaking = piDocService.getOpenStocktaking(warehouse);
				request.getSession().setAttribute(WebSession._STOCKTAKING_DOCUMENT, stocktaking);
			}
			else{
				model.addObject("error", "err.cannot.update.qty");
			}
		}
		
		locator = getLocator(i_locatorId, warehouse);
		
		List<PIDocLine> detailL = new ArrayList<PIDocLine>();
		for(PIDocLine line : stocktaking.getLineL()){
			if(line.getLocator().getLocatorId() == locator.getLocatorId())
				detailL.add(line);
		}
		
		model.addObject("detailL", detailL);
		model.addObject("locator", locator);

		return model;		
	}*/
	
	/*@RequestMapping(value = "/stocktaking/detail/delete", method = RequestMethod.POST)
	public ModelAndView deleteLine(@RequestParam("delLineId") String lineId,
			@RequestParam("delLocatorId") String locatorId,
			HttpServletRequest request)
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("stocktaking-detail");
		Warehouse warehouse = WebSession.getDefaultWarehouse(request);
		PIDoc stocktaking = WebSession.getOpenedStocktaking(request);
		Locator locator = null;

		int i_lineId = Integer.valueOf(lineId);
		int i_locatorId = Integer.valueOf(locatorId);
		
		int ret = piDocService.deleteStocktakingLine(i_lineId);
 
		if(ret > 0){
			stocktaking = piDocService.getOpenStocktaking(warehouse);
			request.getSession().setAttribute(WebSession._STOCKTAKING_DOCUMENT, stocktaking);
		}
		else{
			model.addObject("error", "err.cannot.delete");
			model.addObject("errParams", "Stocktaking Line" );
		}
		
		locator = getLocator(i_locatorId, warehouse);
		
		List<PIDocLine> detailL = new ArrayList<PIDocLine>();
		for(PIDocLine line : stocktaking.getLineL()){
			if(line.getLocator().getLocatorId() == locator.getLocatorId())
				detailL.add(line);
		}
		
		
		model.addObject("detailL", detailL);
		model.addObject("locator", locator);

		return model;		
	}*/
}
