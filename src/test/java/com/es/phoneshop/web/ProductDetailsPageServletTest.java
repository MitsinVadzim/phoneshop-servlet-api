//package com.es.phoneshop.web;
//
//import com.es.phoneshop.interfaces.IDao;
//import com.es.phoneshop.model.product.ArrayListProductDao;
//import com.es.phoneshop.model.product.Product;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.util.Currency;
//
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@RunWith(MockitoJUnitRunner.class)
//public class ProductDetailsPageServletTest {
//
//    private static final Long PRODUCT_ID = 0L;
//    private static final String URI = "http://localhost:8080/products/" + PRODUCT_ID;
//    private static final String MALFORMED_URI = "http://localhost:8080/products/notValidId";
//
//    @InjectMocks
//    private ProductDetailsPageServlet servlet;
//
//    @Mock
//    private HttpServletRequest request;
//
//    @Mock
//    private HttpServletResponse response;
//
//    @Mock
//    private RequestDispatcher requestDispatcher;
//
//
//    private ArrayListProductDao productDao = ArrayListProductDao.getInstance();
//
//    @Mock
//    private Product product;
//
//    @Before
//    public void setup() {
//        //when(servlet.getIdFromURI(request)).thenReturn(PRODUCT_ID);
//        //when(productDao.getElement(PRODUCT_ID)).thenReturn(product);
//        Currency usd = Currency.getInstance("USD");
//        productDao.save(new Product(0L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
//        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
//    }
//
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
//
//
////    @Test
////    public void doGetWhenProductIdIsMalformed() throws ServletException, IOException {
////        when(request.getRequestURI()).thenReturn(URI);
////
////        servlet.doGet(request, response);
////    }
//}
