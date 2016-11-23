package th.co.cenos.dao.imp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.slf4j.LoggerFactory;

import th.co.cenos.dao.AbstractDao;
import th.co.cenos.dao.ProductAttributeDao;
import th.co.cenos.model.AttributeSetInstance;
import th.co.cenos.model.Product;

/**
 * @function myStock
 * @package th.co.cenos.dao.imp
 * @classname ProductAttributeDao.java
 * @author Pasuwat Wang (CENS ONLINE SERVICES)
 * @created Nov 23, 2016 10:40:23 PM
 */
public class ProductAttributeDaoImp extends AbstractDao implements ProductAttributeDao {

	@Override
	public AttributeSetInstance getAttributeSetInsanceById(int attributeSetInsanceId) {
		Connection conn = null;
		PreparedStatement ppstmt = null;
		ResultSet rset = null;
		AttributeSetInstance attributeSetInsance = null;
		StringBuffer attributeSetInstanceSQL = new StringBuffer(
				"SELECT asi.M_AttributeSetInstance_ID , asi.Description , asi.lot , ats.Name AttributeSet \n");
		attributeSetInstanceSQL.append("FROM M_AttributeSetInstance asi LEFT JOIN M_AttributeSet ats ON asi.M_AttributeSet_ID = ats.M_AttributeSet_ID \n")
			.append("WHERE asi.M_AttributeSetInstance_ID = ? ");

		try {
			conn = getConnection();
			ppstmt = conn.prepareStatement(attributeSetInstanceSQL.toString());

			int paramIdx = 1;
			ppstmt.setInt(paramIdx++, attributeSetInsanceId);

			rset = ppstmt.executeQuery();
			if (rset.next()) {
				attributeSetInsance = new AttributeSetInstance();
				attributeSetInsance.setAttributeSetInstanceId(rset.getInt("M_AttributeSetInstance_ID"));
				attributeSetInsance.setDescription(rset.getString("Description"));
				attributeSetInsance.setLotNo(rset.getString("lot"));
				attributeSetInsance.setAttributeSet(rset.getString("AttributeSet"));
			}
		} catch (Exception ex) {
			logger.error("Error AttributeSetInsance");
			ex.printStackTrace();
		} finally {
			dbObjClosed(rset);
			dbObjClosed(ppstmt);
		}

		return attributeSetInsance;
	}

	protected void init() {
		// TODO Auto-generated method stub
		logger = LoggerFactory.getLogger(ProductDaoImp.class);
	}

}
