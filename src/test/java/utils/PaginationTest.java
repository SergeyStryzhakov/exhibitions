package utils;

import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PaginationTest {

    @Test
    public void setPage() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        assertNull(request.getParameter("page"));
        when(request.getParameter("page")).thenReturn("1");
        assertEquals(Pagination.setPage(request), 1);

    }

    @Test
    public void setItemsPerPage() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        assertNull(request.getParameter("itemsPerPage"));
    }

    @Test
    public void createListWithPaginationTest() {
        List<Integer> testList = IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList());
        List<Integer> actualList = IntStream.rangeClosed(4, 6).boxed().collect(Collectors.toList());
        assertEquals(Pagination.createListWithPagination(testList, 2, 3), actualList);
    }

    @Test
    public void setPaginationTest() {
        List<Integer> testList = IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList());
        Map<String, Integer> testPaginationMap = new HashMap<>();
                assertEquals(Pagination.setPagination(null, 1), testPaginationMap);
        assertEquals(Pagination.setPagination(testList, 0), testPaginationMap);
        testPaginationMap.put("pageCount", 4);
        testPaginationMap.put("maxSize", 10);
        testPaginationMap.put("currentSize", 3);
        assertEquals(Pagination.setPagination(testList,3), testPaginationMap);
    }
}