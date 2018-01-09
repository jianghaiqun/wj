package com.sinosoft.cms.webservice;

import org.codehaus.xfire.XFire;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.ServiceFactory;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;
import org.codehaus.xfire.service.invoker.ObjectInvoker;
import org.codehaus.xfire.transport.http.XFireServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;

/**
 * xfire webservice 发布的servlet 参考web.xml中的配置
 * @author lanjun 
 * 2009-5-5
 */
public class XFireConfigServlet extends XFireServlet {
	private static final Logger logger = LoggerFactory.getLogger(XFireConfigServlet.class);

	public XFire createXFire() throws ServletException {
		XFire xfire = super.createXFire();
		ServiceFactory factory = new ObjectServiceFactory(xfire.getTransportManager(), null);

		Service service = factory.create(CmsService.class);
		service.setProperty(ObjectInvoker.SERVICE_IMPL_CLASS, CmsServiceImpl.class);
		xfire.getServiceRegistry().register(service);
		
		//用户同步
		Service userService = factory.create(UserOperator.class);
		userService.setProperty(ObjectInvoker.SERVICE_IMPL_CLASS, UserOperatorImpl.class);
		xfire.getServiceRegistry().register(userService);
		
		
		Service productservice = factory.create(ProductService.class);
		productservice.setProperty(ObjectInvoker.SERVICE_IMPL_CLASS, ProductServiceImpl.class);
		xfire.getServiceRegistry().register(productservice);

		if (xfire == null || xfire.getServiceRegistry() == null || xfire.getServiceRegistry().getServices() == null || xfire.getServiceRegistry().getServices().size() == 0) {
			xfire = super.createXFire();
		}

		logger.info("发布webservice");

		return xfire;
	}
}
