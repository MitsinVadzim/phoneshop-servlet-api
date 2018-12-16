package com.es.phoneshop.service;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.cart.CartItem;
import com.es.phoneshop.interfaces.ICartService;
import com.es.phoneshop.interfaces.IDao;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;

public class HttpSessionCartService implements ICartService {
    private static HttpSessionCartService instance;
    private final IDao<Product, Long> productList = ArrayListProductDao.getInstance();

    private HttpSessionCartService() {
    }

    public synchronized static HttpSessionCartService getInstance() {
        if (instance == null) {
            synchronized (HttpSessionCartService.class) {
                if (instance == null) {
                    instance = new HttpSessionCartService();
                }
            }
        }
        return instance;
    }

    @Override
    public int getCartItemQuantityById(Long productId, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            int index = getIndexCartItemById(productId, cart.getCartItemList());
            if (index >= 0) {
                return cart.getCartItemList().get(index).getQuantity();
            }
        }
        return 0;
    }

    @Override
    public boolean add(Long productId, int quantity, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (ArrayListProductDao.getInstance().getElement(productId).getStock() < quantity) {
            return false;
        }
        if (cart != null) {
            int index = getIndexCartItemById(productId, cart.getCartItemList());
            if (index >= 0) {
                cart.updateCartItemQuantity(index, quantity);
            } else {
                cart.addToCart(new CartItem(quantity, productList.getElement(productId)));
            }
        } else {
            cart = new Cart();
            cart.addToCart(new CartItem(quantity, productList.getElement(productId)));
            session.setAttribute("cart", cart);
        }
        recalculateCart(session);
        return true;
    }

    @Override
    public void recalculateCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            BigDecimal totalPrice = cart.getCartItemList().stream()
                    .map(x -> x.getProduct().getPrice().multiply(BigDecimal.valueOf(x.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            cart.setTotalPrice(totalPrice);
        }
    }

    @Override
    public void delete(Long productId, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            int index = getIndexCartItemById(productId, cart.getCartItemList());
            if (index >= 0) {
                cart.deleteByIndex(index);
                recalculateCart(session);
                //session.setAttribute("cart", cart);
            }
        }
    }

    private int getIndexCartItemById(Long id, List<CartItem> cartItemList) {
        return IntStream.range(0, cartItemList.size())
                .filter(i -> id.equals(cartItemList.get(i).getProduct().getId())).findFirst().orElse(-1);
    }
}
