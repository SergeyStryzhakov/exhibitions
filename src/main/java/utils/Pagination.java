package utils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Pagination {
    public static final int DEFAULT_PAGE = 1;
    public static final int DEFAULT_EXHIBITIONS_MAIN_PAGE = 6;
    public static final int DEFAULT_EXHIBITIONS_PER_PAGE = 5;
    public static final int DEFAULT_TICKETS_PER_PAGE = 5;
    public static final int DEFAULT_HALLS_PER_PAGE = 5;
    public static final int DEFAULT_THEMES_PER_PAGE = 5;
    public static final int DEFAULT_USERS_PER_PAGE = 5;
    private static int currentSize;

    public static int setPage(HttpServletRequest req) {
        String pageParameter = req.getParameter("page");
        int page = pageParameter != null ?
                Integer.parseInt(pageParameter) :
                DEFAULT_PAGE;
        req.setAttribute("page", page);
        return page;
    }

    public static int setItemsPerPage(HttpServletRequest req, String itemName) {
        int items = req.getParameter("itemsPerPage") == null ?
                (int) req.getSession().getAttribute(itemName) :
                Integer.parseInt(req.getParameter("itemsPerPage"));
        req.getSession().setAttribute(itemName, items);
        return items;
    }

    public static List<?> createListWithPagination(List<?> list,
                                                   int page, int itemsPerPage) {
        List<?> temp = list.stream()
                .skip((long) (page - 1) * itemsPerPage)
                .limit(itemsPerPage)
                .collect(Collectors.toList());
        currentSize = temp.size();
        return temp;
    }

    public static Map<String, Integer> setPagination(List<?> list,
                                                     int itemsPerPage) {
        Map<String, Integer> pagination = new HashMap<>();
        if (list == null || itemsPerPage <= 0) return pagination;
        int pageCount = (int) Math.ceil((double) list.size() / itemsPerPage);
        pagination.put("pageCount", pageCount);
        pagination.put("maxSize", list.size());
        pagination.put("currentSize", currentSize);
        return pagination;
    }
}
