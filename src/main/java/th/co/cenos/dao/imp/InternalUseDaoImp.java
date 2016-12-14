package th.co.cenos.dao.imp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import th.co.cenos.dao.AbstractDao;
import th.co.cenos.dao.InternalUseDao;
import th.co.cenos.model.ItemList;

/**
 * @function myStock
 * @package th.co.cenos.dao.imp
 * @classname InternalUseDaoImp.java
 * @author Pasuwat Wang (CENS ONLINE SERVICES)
 * @created Dec 5, 2016 4:20:26 PM
 */
@Repository
public class InternalUseDaoImp extends AbstractDao implements InternalUseDao {

	
	public List<ItemList> getReasonList(String adReferenceId) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement ppstmt = null;
		ResultSet rset = null;
		List<ItemList> itemL = null;
		
		StringBuffer reasonListSql = new StringBuffer("SELECT l.value ,l.NAME , l.description FROM AD_REF_LIST l WHERE l.AD_REFERENCE_ID = ? AND l.IsActive = 'Y' ORDER BY l.VALUE");
		try {
			conn = getConnection();
			ppstmt = conn.prepareStatement(reasonListSql.toString());

			int paramIdx = 1;
			ppstmt.setInt(paramIdx++, Integer.valueOf(adReferenceId));

			rset = ppstmt.executeQuery();
			while (rset.next()) {
				if (itemL == null)
					itemL = new ArrayList<ItemList>();

				ItemList option = new ItemList();
				option.setItemCode(rset.getString("value"));
				option.setItemName(rset.getString("name"));
				option.setDescription(rset.getString("description"));
				
				itemL.add(option);
			}
		} catch (Exception ex) {
			logger.error("Cannot Get Reason List!");
			ex.printStackTrace();
		} finally {
			dbObjClosed(rset);
			dbObjClosed(ppstmt);
		}
		return itemL;
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		logger = LoggerFactory.getLogger(ProductDaoImp.class);
	}

}
