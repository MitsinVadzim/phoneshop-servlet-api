package com.es.phoneshop.listener;

import com.es.phoneshop.model.product.ArrayListProductDao;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ProductDemodataServletContextListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("ServletContextListener destroyed");
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        ArrayListProductDao productDao = ArrayListProductDao.getInstance();
        servletContext.setAttribute("products", productDao.findProducts("", ""));
        System.out.println("ServletContextListener started");
    }
}
