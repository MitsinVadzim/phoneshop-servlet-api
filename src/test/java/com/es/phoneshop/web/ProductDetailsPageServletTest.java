package com.es.phoneshop.web;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsPageServletTest {

    private static final long PRODUCT_ID = 1L;
    private static final String URI = "http://localhost:8080/products/" + PRODUCT_ID;
    private static final String MALFORMED_URI = "http://localhost:8080/products/notValidId";

    @InjectMocks
    private ProductDetailsPageServlet servlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private ProductDao productDao;

    @Mock
    private Product product;

    @Before
    public void setup() {
        when(productDao.getProduct(PRODUCT_ID)).thenReturn(product);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

//    @Test
//    public void doGet() throws ServletException, IOException {
//        servlet.init();
//        when(request.getRequestURI()).thenReturn(URI);
//
//
//        servlet.doGet(request, response);
//
//        verify(request).setAttribute("product", product);
//        verify(requestDispatcher).forward(request, response);
//    }


    @Test(expected = NumberFormatException.class)
    public void doGetWhenProductIdIsMalformed() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn(MALFORMED_URI);

        servlet.doGet(request, response);
    }
}
