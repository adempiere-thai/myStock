package th.co.cenos.service.imp;

import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;

import org.compiere.Adempiere;
import org.compiere.model.MDocType;
import org.compiere.model.MInventory;
import org.compiere.model.MInventoryLine;
import org.compiere.util.CLogMgt;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Login;
import org.compiere.util.Trx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.co.cenos.dao.InternalUseDao;
import th.co.cenos.model.InternalUse;
import th.co.cenos.model.InternalUseLine;
import th.co.cenos.model.ItemList;
import th.co.cenos.model.User;
import th.co.cenos.model.Warehouse;
import th.co.cenos.process.AdempiereProcess;
import th.co.cenos.services.InternalUseService;

/**
 * @function myStock
 * @package th.co.cenos.service.imp
 * @classname InternalUseServiceImp.java
 * @author Pasuwat Wang (CENS ONLINE SERVICES)
 * @created Dec 5, 2016 4:17:28 PM
 */
@Service
@Transactional
public class InternalUseServiceImp implements InternalUseService {

	@Autowired
	InternalUseDao internalUseDao ;
	
	@Autowired
	AdempiereProcess adempiereProcess ;
	
	@Override
	public List<ItemList> getReasonList(String adReferenceId) {
		// TODO Auto-generated method stub
		return internalUseDao.getReasonList(adReferenceId);
	}

	@Override
	public String saveInternalUse(InternalUse internalUse, User user , Warehouse warehouse) throws Exception {
		// TODO Auto-generated method stub
		return adempiereProcess.saveInternalUse(internalUse, user, warehouse);
	}
	
	
}
