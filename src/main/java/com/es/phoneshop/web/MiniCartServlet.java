package com.es.phoneshop.web;

import com.es.phoneshop.mapping.ArrayListMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MiniCartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("mappingList", ArrayListMapping.getInstance().getMappingList());
        request.getRequestDispatcher("/WEB-INF/pages/minicartPage.jsp").forward(request, response);
    }
}
