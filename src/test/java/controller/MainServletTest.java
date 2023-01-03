package controller;

import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MainServletTest {
    @Test
    public void setLanguageMainServletTest() {
        HttpServletRequest request = mock(HttpServletRequest.class);
                assertNull(request.getParameter("lang"));
                        when(request.getParameter("lang")).thenReturn("en");
        assertEquals(request.getParameter("lang"), "en");
    }

}