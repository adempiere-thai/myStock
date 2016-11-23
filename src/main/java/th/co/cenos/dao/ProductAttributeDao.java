package th.co.cenos.dao;

import th.co.cenos.model.AttributeSetInstance;

/**
 * @function myStock
 * @package th.co.cenos.dao
 * @classname ProductAttributeDao.java
 * @author Pasuwat Wang (CENS ONLINE SERVICES)
 * @created Nov 23, 2016 10:37:38 PM
 */
public interface ProductAttributeDao {
	public AttributeSetInstance getAttributeSetInsanceById(int attributeSetInsanceId);
}
