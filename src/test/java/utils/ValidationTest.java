package utils;

import entity.User;
import exception.LoginException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ValidationTest {
    private final User testUser = mock(User.class);

    @Test(expected = LoginException.class)
    public void isAdminTest() throws LoginException {

        assertTrue(Validation.isAdmin(null));
        when(testUser.getRole()).thenReturn(User.Role.ROLE_ADMIN);
        assertTrue(Validation.isAdmin(testUser));
        when(testUser.getRole()).thenReturn(User.Role.ROLE_USER);
        assertFalse(Validation.isAdmin(testUser));
    }

    @Test(expected = LoginException.class)
    public void isAuthUserTest() throws LoginException {
        assertTrue(Validation.isAuthUser(null));
        when(testUser.getRole()).thenReturn(User.Role.ROLE_USER);
        assertTrue(Validation.isAuthUser(testUser));
        when(testUser.getRole()).thenReturn(User.Role.ROLE_ADMIN);
        assertFalse(Validation.isAuthUser(testUser));
    }
}