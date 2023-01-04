package controller;

import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ErrorHandlerTest {
    public static final String PATH = "/WEB-INF/jsp/error.jsp";

    @Test
   public void whenCallDoGetServletReturnErrorPage() throws ServletException, IOException {
        ErrorHandler servlet = new ErrorHandler();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(request.getRequestDispatcher(PATH)).thenReturn(dispatcher);
        servlet.doGet(request, response);
        verify(request, times(1)).getRequestDispatcher(PATH);
        verify(request, never()).getSession();
        verify(dispatcher).forward(request, response);

    }
}