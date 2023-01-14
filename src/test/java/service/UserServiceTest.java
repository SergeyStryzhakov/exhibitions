package service;

import dao.UserDAO;
import entity.User;
import exception.DBException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

public class UserServiceTest {
    @Mock
    private UserDAO dao;
    private UserService service;
    private User firstUser;
    private User secondUser;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new UserService(dao);
        firstUser = new User.Builder()
                .id(1)
                .login("firstUser")
                .pass("1234")
                .firstName("Ivan")
                .lastName("Ivanov")
                .email("ivanov@gmail.com")
                .balance(10.0)
                .role(User.Role.ROLE_USER)
                .build();
        secondUser = new User.Builder()
                .id(2)
                .login("secondUser")
                .pass("1234")
                .firstName("Nick")
                .lastName("Abrams")
                .email("abrams@gmail.com")
                .balance(0.0)
                .role(User.Role.ROLE_ADMIN)
                .build();
    }

    @Test
    public void createUser_Should_Return_User() throws DBException {
        given(dao.create(firstUser)).willReturn(firstUser);
        assertNotNull(service.createUser(firstUser));
        assertEquals(service.createUser(firstUser), firstUser);
    }

    @Test
    public void getUsers_Should_Return_ListUsers() throws DBException {
        List<User> testListUsers = Arrays.asList(firstUser, secondUser);
        given(dao.findAll()).willReturn(Arrays.asList(firstUser, secondUser));
        assertEquals(service.getUsers(), testListUsers);
    }

    @Test
    public void getUsers_Should_Return_EmptyList() throws DBException {
        given(dao.findAll()).willReturn(new ArrayList<>());
        assertEquals(service.getUsers(), Collections.emptyList());
    }

    @Test
    public void getUserById_Should_Return_UserId() throws DBException {
        given(dao.findById(2)).willReturn(secondUser);
        assertEquals(service.getUserById(2), secondUser);
    }

    @Test
    public void getUserById_Should_Return_Null() throws DBException {
        given(dao.findById(3)).willReturn(null);
        assertNull(service.getUserById(3));
    }

    @Test
    public void updateUser_Should_Return_NumberOfUpdatedRows() throws DBException {
        firstUser.setRole(User.Role.ROLE_ADMIN);
        given(dao.update(firstUser)).willReturn(1);
        assertEquals(service.updateUser(firstUser), 1);
        assertNotEquals(service.updateUser(firstUser), 2);
    }

    @Test
    public void deleteUser_Should_Return_True() throws DBException {
        given(dao.delete(1)).willReturn(true);
        assertTrue(service.deleteUser(1));
        assertFalse(service.deleteUser(5));
    }

    @Test
    public void getUserByLogin_Should_Return_User() throws DBException {
        given(dao.findByLogin("firstUser")).willReturn(firstUser);
        assertEquals(service.getUserByLogin("firstUser"), firstUser);
        assertNotEquals(service.getUserByLogin("firstUser"), secondUser);
    }
}