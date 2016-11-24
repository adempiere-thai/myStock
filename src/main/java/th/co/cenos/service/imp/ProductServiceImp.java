/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package th.co.cenos.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.co.cenos.dao.ProductAttributeDao;
import th.co.cenos.dao.ProductDao;
import th.co.cenos.model.AttributeSetInstance;
import th.co.cenos.model.Product;
import th.co.cenos.services.ProductService;

/**
 * @function myStock
 * @package th.co.cenos.service.imp
 * @classname ProductServiceImp
 * @author Pasuwat Wang (CENS ONLINE SERVICES)
 * @created Nov 23, 2016 3:17:03 PM
 */
@Service
@Transactional
public class ProductServiceImp implements ProductService {
	
	@Autowired
	ProductDao productDao;
	
	@Autowired
	ProductAttributeDao productAttributeDao;
	
	@Override
	public List<Product> getProductList(int adClientId, int mWarehouseId,
			String srhKey) {
		// TODO Auto-generated method stub
		return productDao.getProductList(adClientId, mWarehouseId, srhKey);
	}

	@Override
	public Product getProductById(int productId) {
		// TODO Auto-generated method stub
		return productDao.getProductById(productId);
	}

	@Override
	public AttributeSetInstance getAttributeSetInstanceById(
			int attributeSetInstanceId) {
		// TODO Auto-generated method stub
		return productAttributeDao.getAttributeSetInsanceById(attributeSetInstanceId);
	}

	/* (non-Javadoc)
	 * @see th.co.cenos.services.ProductService#getProductByKey(java.lang.String)
	 */
	@Override
	public Product getProductByKey(String srhKey) {
		// TODO Auto-generated method stub
		return productDao.getProductByKey(srhKey);
	}

}
