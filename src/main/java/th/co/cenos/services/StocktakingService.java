package th.co.cenos.services;

import th.co.cenos.model.Stocktaking;
import th.co.cenos.model.StocktakingLine;
import th.co.cenos.model.User;
import th.co.cenos.model.Warehouse;

/**
 * @function myStock
 * @package th.co.cenos.services
 * @classname StocktakingService.java
 * @author Pasuwat Wang (CENS ONLINE SERVICES)
 * @created Nov 23, 2016 10:06:01 PM
 */
public interface StocktakingService {
	public Stocktaking getOpenStocktaking(Warehouse warehouse);
	
	public int saveStocktakingLine(StocktakingLine line , User user);
}
