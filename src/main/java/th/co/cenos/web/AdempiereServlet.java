package th.co.cenos.web;

import org.compiere.Adempiere;
import org.compiere.util.Ini;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * @function myStock
 * @package th.co.cenos.web
 * @classname AdempiereServlet.java
 * @author Pasuwat Wang (CENS ONLINE SERVICES)
 * @created Mar 29, 2017 9:41:30 AM
 */
public class AdempiereServlet extends DispatcherServlet {
	
	@Override
	protected WebApplicationContext initWebApplicationContext() {
		System.out.println(getServletContext().getRealPath("/"));
		String adempierePropertyFile = getInitParameter("adempierePropFile");
		String propertyFile = getServletContext().getRealPath("/")+adempierePropertyFile;
		System.setProperty("PropertyFile",propertyFile);
		
		boolean started = Adempiere.startup(false);
        if(!started)
        {
        	System.out.println("Cannot Start Adempiere");
        }
        
		Ini.setProperty(Ini.P_ADEMPIERESYS, false);
		
		// TODO Auto-generated method stub
		return super.initWebApplicationContext();
	}

}
