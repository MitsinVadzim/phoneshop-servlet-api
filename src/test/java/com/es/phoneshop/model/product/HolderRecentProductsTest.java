package com.es.phoneshop.model.product;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

public class HolderRecentProductsTest {
    private HolderRecentProducts holderRecentProducts;
    private Currency usd = Currency.getInstance("USD");

    @Before
    public void setup(){
        holderRecentProducts = new HolderRecentProducts();
    }

    @Test
    public void testUpdateEmptyList(){
        Product testProduct = new Product(0L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        holderRecentProducts.updateList(testProduct);
        Assert.assertEquals(holderRecentProducts.getRecentProducts().get(0).getId(),testProduct.getId());
    }

    @Test
    public void testUpdateFullList(){
        Product expectedProduct = new Product(4L, "iphone6", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg");
        holderRecentProducts.updateList(new Product(0L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        holderRecentProducts.updateList(new Product(1L, "sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        holderRecentProducts.updateList(new Product(2L, "sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        holderRecentProducts.updateList(expectedProduct);
        Assert.assertEquals(holderRecentProducts.getRecentProducts().get(0).getId(),expectedProduct.getId());
    }
}
