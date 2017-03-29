package th.co.cenos.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import th.co.cenos.model.AttributeSetInstance;
import th.co.cenos.model.InternalUse;
import th.co.cenos.model.InternalUseLine;
import th.co.cenos.model.ItemList;
import th.co.cenos.model.Locator;
import th.co.cenos.model.Product;
import th.co.cenos.model.PIDoc;
import th.co.cenos.model.PIDocLine;
import th.co.cenos.model.User;
import th.co.cenos.model.Warehouse;
import th.co.cenos.services.InternalUseService;
import th.co.cenos.services.ProductService;
import th.co.cenos.web.WebSession;

/**
 * @function myStock
 * @package th.co.cenos.controller
 * @classname InternalUseController.java
 * @author Pasuwat Wang (CENS ONLINE SERVICES)
 * @created Nov 30, 2016 10:50:45 PM
 */
@Controller
public class InternalUseController {

	private Logger logger = LoggerFactory.getLogger(InternalUseController.class);	
	 
	@Autowired
	InternalUseService internalUseService;
	 
	@Autowired
	ProductService productService;
	 
	@RequestMapping(value = "/internaluse", method = RequestMethod.GET)
	public ModelAndView showInternalUsePage(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		model.setViewName("internaluse");
		
		return model;
	}
	
	@RequestMapping(value = "/internaluse", method = RequestMethod.POST)
	public ModelAndView selectReason(@RequestParam("reason") String reason ,HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		InternalUse internalUse = null;
		if(StringUtils.isEmpty(reason)){
			model.setViewName("redirect:/internaluse");
			model.addObject("error", "err.field.required");
			model.addObject("errParams", "Reason" );
			return model;
		}
		
		internalUse = WebSession.getInternalUse(request);
		if(internalUse == null)
			internalUse = new InternalUse();
		
		internalUse.setReason(reason);
		request.getSession().setAttribute(WebSession._INTERNALUSE_DOCUMENT, internalUse);
		
		model.addObject("detailL", internalUse.getLineL());
		model.setViewName("redirect:internaluse/detail");
		
		return model;
	}
	
	@RequestMapping(value = "internaluse/detail", method = RequestMethod.GET)
	public ModelAndView showInternalUseDetail(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		
		InternalUse internalUse = WebSession.getInternalUse(request);
		if(internalUse == null){
			model.setViewName("redirect:/internaluse");
			return model;
		}
		
		model.addObject("detailL", internalUse.getLineL());
		model.setViewName("internaluse-detail");
		return model;
	}
	
	@RequestMapping(value = "internaluse/detail/new", method = RequestMethod.GET)
	public ModelAndView showInternalUseNew(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();	
		Warehouse warehouse = WebSession.getDefaultWarehouse(request);
		List<Locator> locatorL = warehouse.getLocatorL();
		model.addObject("locatorL", locatorL);
		model.setViewName("internaluse-new");
		return model;
	}
	
	@RequestMapping(value = "/internaluse/detail/new", method = RequestMethod.POST)
	public ModelAndView saveLine(
			@RequestParam("locator") String locatorId ,
			@RequestParam("pdCode") String pdCode,
			@RequestParam("asiId") String asiId ,
			@RequestParam("usedQty") String usedQty ,
			HttpServletRequest request) {
		
		ModelAndView model = new ModelAndView();
		int i_locatorId = 0;
		int i_asiId = 0;
		BigDecimal bd_useQty = BigDecimal.ZERO;
		InternalUseLine line = new InternalUseLine();
		Product product = null;
		AttributeSetInstance asi = null;	
		
		User user = WebSession.getLoginUser(request);
		Warehouse warehouse = WebSession.getDefaultWarehouse(request);
		List<Locator> locatorL = warehouse.getLocatorL();
		InternalUse internalUse = WebSession.getInternalUse(request);
		
		// Start Validation 
		/* 1 Check Input Data Type*/
		// 1.1 Locator Id
		try{
			i_locatorId = Integer.valueOf(locatorId);
		}
		catch(Exception ex){
			model = new ModelAndView("redirect:/internaluse/detail");
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
				model.setViewName("internaluse-new");
				model.addObject("line", line);
				model.addObject("locatorL", locatorL);
				model.addObject("error", "err.parsing");
				model.addObject("errParams", "ASI Id" );
				return model;
			}
		}
		
		// 1.3 use qty
		if(!StringUtils.isEmpty(usedQty)){
			try{
				bd_useQty = new BigDecimal(usedQty);
			}
			catch(Exception ex){
				model.setViewName("internaluse-new");
				model.addObject("line", line);
				model.addObject("locatorL", locatorL);
				model.addObject("error", "err.parsing");
				model.addObject("errParams", "Count Qty" );
				return model;
			}
		}
		
		// 2. Check Required Field
		// 2.1 Required Product
		if(StringUtils.isEmpty(pdCode)){
			model.setViewName("internaluse-new");
			model.addObject("line", line);
			model.addObject("locatorL", locatorL);
			model.addObject("error", "err.field.required");
			model.addObject("errParams", "Product" );
			return model;
		}
		
		// 2.2 Required Count Qty
		if(StringUtils.isEmpty(usedQty)){
			model.setViewName("internaluse-new");
			model.addObject("line", line);
			model.addObject("locatorL", locatorL);
			model.addObject("error", "err.field.required");
			model.addObject("errParams", "Count Qty" );
			return model;
		}
		
		// 3. Check value is existing in the DB
		// 3.1 Product
		product = productService.getProductByKey(user.getAdClientId(), pdCode);
		if(product == null){
			model.setViewName("internaluse-new");
			model.addObject("line", line);
			model.addObject("locatorL", locatorL);
			model.addObject("error", "err.cannot.find.db");
			model.addObject("errParams", "Product" );
			return model;
		}
		
		// 3.2 ASI
		if(i_asiId > 0){
			asi = productService.getAttributeSetInstanceById(i_asiId);
			
			if(asi == null){
				model.setViewName("internaluse-new");
				model.addObject("line", line);
				model.addObject("locatorL", locatorL);
				model.addObject("error", "err.cannot.find.db");
				model.addObject("errParams", "ASI ID" );
				return model;
			}
			
			// Check asi is product asi
			if(!productService.isProductASI(product, asi)){
				model.setViewName("internaluse-new");
				model.addObject("line", line);
				model.addObject("locatorL", locatorL);
				model.addObject("error", "err.not.product.asi");
				model.addObject("errParams", product.getProductSrhKey()+":"+product.getProductName());
				return model;
			}
		}
		// End Validation
		
		line.setAdOrgId(warehouse.getAdOrgId());
		line.setProduct(product);
		line.setAsi(asi);
		line.setUsedQty(bd_useQty);
		line.setLocator(locator);
		line.setLineNo(getNextLineNo(internalUse));
		
		internalUse.addLine(line);
		
		request.getSession().setAttribute(WebSession._INTERNALUSE_DOCUMENT, internalUse);
		
		model = new ModelAndView();
		model.setViewName("redirect:/internaluse/detail");
		
		return model;
	}
	
	@RequestMapping(value = "/internaluse/detail/edit", method = RequestMethod.POST)
	public ModelAndView editLine(@RequestParam("editLineNo") String lineNo,
			@RequestParam("editQty") String qty,
			HttpServletRequest request)
	{
		// Validate Qty
		ModelAndView model = new ModelAndView();
		model.setViewName("redirect:/internaluse/detail");
		InternalUse internalUse = WebSession.getInternalUse(request);
		BigDecimal bd_usedQty = BigDecimal.ZERO;
		int i_lineNo = 0;
		boolean isError = false;
		
		// Validate
		// 1. Check Input Qty Data Type
		if(!StringUtils.isEmpty(qty)){
			try{
				bd_usedQty = new BigDecimal(qty);
				i_lineNo = Integer.valueOf(lineNo);
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
			List<InternalUseLine> lineL = internalUse.getLineL();
			
			List<InternalUseLine> newLineL = new ArrayList<InternalUseLine>();
			
			for(InternalUseLine line : lineL){
				if(line.getLineNo() == i_lineNo){
					line.setUsedQty(bd_usedQty);
				}
				
				newLineL.add(line);
			}
			
			internalUse.setLineL(newLineL);
		}
		
		model.addObject("detailL", internalUse.getLineL());
		
		request.getSession().setAttribute(WebSession._INTERNALUSE_DOCUMENT, internalUse);

		return model;		
	}
	
	
	@RequestMapping(value = "/internaluse/detail/delete", method = RequestMethod.POST)
	public ModelAndView delLine(@RequestParam("delLineNo") String lineNo,
			HttpServletRequest request)
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("redirect:/internaluse/detail");
		InternalUse internalUse = WebSession.getInternalUse(request);
		int i_lineNo = Integer.valueOf(lineNo);
		List<InternalUseLine> lineL = internalUse.getLineL();
		List<InternalUseLine> newLineL = new ArrayList<InternalUseLine>();
		int new_lineNo = 0;
		
		for(InternalUseLine line : lineL){
			if(line.getLineNo() != i_lineNo){
				new_lineNo = new_lineNo+10;
				line.setLineNo(new_lineNo);
				newLineL.add(line);
			}
		}

		internalUse.setLineL(newLineL);
		
		model.addObject("detailL", internalUse.getLineL());
		request.getSession().setAttribute(WebSession._INTERNALUSE_DOCUMENT, internalUse);

		return model;		
	}
	
	
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
	
	private int getNextLineNo(InternalUse internalUse){
		int ret = 0;
		List<InternalUseLine> lineL = internalUse.getLineL();
		for(InternalUseLine line : lineL){
			if(line.getLineNo() > ret)
				ret = line.getLineNo();
		}
		
		ret +=10;
				
		return ret;
	}
	
	@RequestMapping(value = "/internaluse/save", method = RequestMethod.GET)
	public ModelAndView saveInternalUse(HttpServletRequest request)
	{
		ModelAndView model = new ModelAndView();
		
		User user = WebSession.getLoginUser(request);
		Warehouse warehouse = WebSession.getDefaultWarehouse(request);
		InternalUse internalUse = WebSession.getInternalUse(request);
		String action = request.getParameter("action");
		String jsonString = "";
		
		List<InternalUseLine> lineL = internalUse.getLineL();
		
		try{
			String documentNo = internalUseService.saveInternalUse(internalUse, user ,warehouse ,action);
			jsonString = toJsonString(documentNo);
		}
		catch(Exception ex){
			model.setViewName("json");
			jsonString = toJsonString(ex.getMessage());
			model.addObject("JSON", jsonString);
			return model;
		}
		
		request.getSession().removeAttribute(WebSession._INTERNALUSE_DOCUMENT);
		
		model.setViewName("json");
		model.addObject("JSON", jsonString);
		
		return model;		
	}
	
	private String toJsonString(Object object){
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = null;
		try {
			jsonString = mapper.writeValueAsString(object);
		} catch (Exception e) {
			logger.error("Cannot Parse Object To JSON");
			e.printStackTrace();
		} 
		
		return jsonString;
	}
}
