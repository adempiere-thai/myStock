package th.co.cenos.process;

import java.sql.Timestamp;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.Adempiere;
import org.compiere.model.MClient;
import org.compiere.model.MDocType;
import org.compiere.model.MInventory;
import org.compiere.model.MInventoryLine;
import org.compiere.model.MRefList;
import org.compiere.model.MSystem;
import org.compiere.process.DocAction;
import org.compiere.util.CLogMgt;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Login;
import org.compiere.util.Trx;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import th.co.cenos.model.InternalUse;
import th.co.cenos.model.InternalUseLine;
import th.co.cenos.model.User;
import th.co.cenos.model.Warehouse;

/**
 * @function myStock
 * @package th.co.cenos.process
 * @classname AdempiereProcess.java
 * @author Pasuwat Wang (CENS ONLINE SERVICES)
 * @created Dec 12, 2016 10:32:52 PM
 */
@Component
public class AdempiereProcess {
	
	@Value("${internal.charge.id}")
    private int chargeId;
	
	@Value("${ad.role.id}")
    private int roleId;
	
	@Value("${adempiere.home}")
    private String adempiereHome;
	
	@Value("${ad.reference.id}")
    private int adReferenceId; // Reference Id for Reason List
	
	Properties m_ctx = null;
	
	public AdempiereProcess(){
		m_ctx = new Properties();
	}
	
	public String saveInternalUse(InternalUse internalUse, User user , Warehouse warehouse) throws Exception {
		// TODO Auto-generated method stub
		
		loginADempiere(user, warehouse);
		
		MDocType[] docTypes = MDocType.getOfDocBaseType(Env.getCtx(), "MMI");
		MDocType mmiDoctype = null;
		
		for(MDocType type : docTypes){
			mmiDoctype = type;
		}
		
		MRefList reason = MRefList.get(Env.getCtx(), adReferenceId, internalUse.getReason(), null);
		
		MInventory inventory = new MInventory(Env.getCtx(),0,null);
		inventory.setAD_Org_ID(warehouse.getAdOrgId());
		inventory.setDescription(reason.getName());
		inventory.setC_DocType_ID(mmiDoctype.getC_DocType_ID());
		inventory.setM_Warehouse_ID(warehouse.getWarehouseId());
		
		if (!inventory.save())
		{
			throw new Exception("err.cannot.save.intentory.header");
		}
		
		for(InternalUseLine line : internalUse.getLineL()){
			MInventoryLine iLine = new MInventoryLine(Env.getCtx(),0,inventory.get_TrxName());
			
			iLine.setAD_Org_ID(warehouse.getAdOrgId());
			iLine.setM_Inventory_ID(inventory.getM_Inventory_ID());
			iLine.setLine(line.getLineNo());
			iLine.setM_Product_ID(line.getProduct().getProductId());
			iLine.setM_Locator_ID(line.getLocator().getLocatorId());
			
			if(line.getAsi() != null)
				iLine.setM_AttributeSetInstance_ID(line.getAsi().getAttributeSetInstanceId());
			else
				iLine.setM_AttributeSetInstance_ID(0);
			
			iLine.setQtyInternalUse(line.getUsedQty());
			iLine.setC_Charge_ID(chargeId);
			
			if(!iLine.save(inventory.get_TrxName())){
				throw new Exception("err.cannot.save.intentory.line");
			}
			
		}
		
		inventory.processIt(DocAction.ACTION_Complete);
		inventory.saveEx(inventory.get_TrxName());
		
		Env.logout();
		
		return inventory.getDocumentNo(); 
		
	}

	private void loginADempiere(User user ,Warehouse wh ){
		Ini.setAdempiereHome(adempiereHome);
		CLogMgt.setLoggerLevel(Level.INFO, null);
		
		Adempiere.startup(false);
		MClient client = MClient.get(m_ctx, user.getAdClientId());
		MSystem system = MSystem.get(m_ctx);
		
		Env.setContext(Env.getCtx(), "#AD_Client_ID", user.getAdClientId());
		Env.setContext(Env.getCtx(), "#AD_User_ID", user.getUserId());
		Env.setContext(Env.getCtx(), "#AD_Role_ID", roleId);
		Env.setContext(Env.getCtx(), "#AD_Org_ID", wh.getAdOrgId());
		Env.setContext(Env.getCtx(), "#M_Warehouse_ID", wh.getWarehouseId());

	}
	
}
