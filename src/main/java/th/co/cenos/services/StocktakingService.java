package th.co.cenos.services;

import th.co.cenos.model.Warehouse;

/**
 * @function myStock
 * @package th.co.cenos.services
 * @classname StocktakingService.java
 * @author Pasuwat Wang (CENS ONLINE SERVICES)
 * @created Nov 23, 2016 10:06:01 PM
 */
public interface StocktakingService {
	public int getOpenStocktaking(Warehouse warehouse);
	
}
