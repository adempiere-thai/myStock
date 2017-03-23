package th.co.cenos.services;

import java.math.BigDecimal;

import th.co.cenos.model.PIDoc;
import th.co.cenos.model.PIDocLine;
import th.co.cenos.model.User;
import th.co.cenos.model.Warehouse;

/**
 * @function myStock
 * @package th.co.cenos.services
 * @classname PIDocService.java
 * @author Pasuwat Wang (CENS ONLINE SERVICES)
 * @created Nov 23, 2016 10:06:01 PM
 */
public interface PIDocService {

	public PIDoc findPIDocByDocNo(int adClientId , int adOrgId ,String documentNo );
	
	public PIDocLine findPIDocLine(PIDocLine line);
	
	public int updatePIDocLine(PIDocLine line);
}
