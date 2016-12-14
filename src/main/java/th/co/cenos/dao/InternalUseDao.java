package th.co.cenos.dao;

import java.util.List;

import th.co.cenos.model.ItemList;

/**
 * @function myStock
 * @package th.co.cenos.dao
 * @classname InternalUseDao.java
 * @author Pasuwat Wang (CENS ONLINE SERVICES)
 * @created Dec 5, 2016 4:18:50 PM
 */
public interface InternalUseDao {
	public List<ItemList> getReasonList(String adReferenceId);
}
