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

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.co.cenos.dao.UserDao;
import th.co.cenos.dao.WarehouseLocatorDao;
import th.co.cenos.model.Locator;
import th.co.cenos.model.Menu;
import th.co.cenos.model.User;
import th.co.cenos.model.Warehouse;
import th.co.cenos.services.SecurityService;

/**
 * @function myStock
 * @package th.co.cenos.service.imp
 * @classname SecurityServiceImp
 * @author Pasuwat Wang (CENS ONLINE SERVICES)
 * @created Nov 20, 2016 12:41:09 AM
 */
@Service
@Transactional
public class SecurityServiceImp implements SecurityService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	UserDao userDao;
	
	@Autowired
	WarehouseLocatorDao warehouseLocatorDao;

	@Override
	public User authentication(int adClientId, String username, String password) {
		// TODO Auto-generated method stub
		return userDao.login(adClientId, username, password);
	}

	@Override
	public List<String> getAuthorizeMenu(int roleId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Warehouse> getUserWarehouse(User user) {
		// TODO Auto-generated method stub
		return warehouseLocatorDao.getUserAccessWarehouses(user);
	}

	@Override
	public Warehouse getWarehouse(int mWarehouseId) {
		// TODO Auto-generated method stub
		return warehouseLocatorDao.getWarehouseById(mWarehouseId);
	}
	
	@Override
	public Locator getLocator(int mLocatorId) {
		// TODO Auto-generated method stub
		return warehouseLocatorDao.getLocatorById(mLocatorId);
	}


	@Override
	public List<Menu> getMenu(User user) {
		List<Menu> menuL = new ArrayList<Menu>();
		Menu menu1 = new Menu();
		menu1.setMenuName("menu.checkStock");
		menu1.setUrl("product");
		
		Menu menu2 = new Menu();
		menu2.setMenuName("menu.stocktaking");
		menu2.setUrl("stocktaking");
		
		Menu menu3 = new Menu();
		menu3.setMenuName("menu.internalUse");
		menu3.setUrl("internaluse");
		
		menuL.add(menu1);
		menuL.add(menu2);
		menuL.add(menu3);
		
		return menuL;
	}

}
