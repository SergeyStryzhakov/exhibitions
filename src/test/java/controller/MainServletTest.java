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

       @Test
    public void setLanguageMainServletTest() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        assertNull(request.getParameter("lang"));
        when(request.getParameter("lang")).thenReturn("en");
        assertEquals(request.getParameter("lang"), "en");
    }

}