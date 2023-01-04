package controller;

import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MainServletTest {
    public static final String PATH = "/main.jsp";

    @Test
    public void whenDoGetMainServletThenReturnMainPage() throws ServletException, IOException {
//        MainServlet mainServlet = new MainServlet();
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        HttpServletResponse response = mock(HttpServletResponse.class);
//        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
//        when(request.getRequestDispatcher(PATH)).thenReturn(dispatcher);
//        mainServlet.doGet(request, response);
//        verify(request, times(1)).getRequestDispatcher(PATH);
//        //verify(request, never()).getSession();
//        verify(dispatcher).forward(request, response);
    }

    @Test
    public void setLanguageMainServletTest() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        assertNull(request.getParameter("lang"));
        when(request.getParameter("lang")).thenReturn("en");
        assertEquals(request.getParameter("lang"), "en");
    }

}