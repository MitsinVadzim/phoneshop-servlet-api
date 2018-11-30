package com.es.phoneshop.web;

import com.es.phoneshop.listener.ProductDemodataServletContextListener;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletContextEvent;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ProductDemodataServletContextListenerTest {
    @Mock
    private ServletContextEvent servletContextEvent;
    private ProductDao dao = ArrayListProductDao.getInstance();
    private ProductDemodataServletContextListener listener = new ProductDemodataServletContextListener();

    @Before
    public void setup() {
    }

    @Test
    public void testContextInitialized() {
        listener.contextInitialized(servletContextEvent);
        assertEquals(12, dao.findProducts().size());
    }
}
