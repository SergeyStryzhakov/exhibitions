package controller.exhibition;

import dto.ExhibitionDto;
import entity.ExhibitionState;
import entity.Hall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ExhibitionService;
import service.HallService;
import service.ThemeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/exhibitions/create")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class CreateExhibition extends HttpServlet {
    private static final String UPLOAD_DIRECTORY = "assets\\img";
    public static Logger LOGGER = LoggerFactory.getLogger(CreateExhibition.class);
    private ExhibitionService exhibitionService;
    private ThemeService themeService;
    private HallService hallService;

    @Override
    public void init() throws ServletException {
        exhibitionService = (ExhibitionService) getServletContext()
                .getAttribute("exhibitionService");
        themeService = (ThemeService) getServletContext()
                .getAttribute("themeService");
        hallService = (HallService) getServletContext()
                .getAttribute("hallService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("themes", themeService.getThemes());
        req.setAttribute("halls", hallService.getHalls());
        req.setAttribute("states", ExhibitionState.values());
        LOGGER.info("Create exhibition started (GET request)");
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/exhibitions/create_exhibition.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        LOGGER.info("Create exhibition started (POST request)");
        int exId = processRequest(req);
        String uploadImagePath = getServletContext().getRealPath("")
                + UPLOAD_DIRECTORY + File.separator + exId;
        uploadImage(req, uploadImagePath);
        resp.sendRedirect(req.getContextPath() + "/exhibitions/show");
    }

    private List<Hall> processHallList(String[] halls) {
        List<Hall> hallList = new ArrayList<>();
        for (String hall : halls) {
            int hallId = Integer.parseInt(hall);
            hallList.add(hallService.getHallById(hallId));
        }
        return hallList;
    }

    private void uploadImage(HttpServletRequest req, String uploadPath)
            throws ServletException, IOException {
        String fileName = req.getPart("image")
                .getSubmittedFileName();
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();
        for (Part part : req.getParts()) {
            part.write(uploadPath + File.separator + fileName);
        }
        LOGGER.info("Image uploaded successfully => " +
                uploadPath + File.separator + fileName);
    }

    private int processRequest(HttpServletRequest req)
            throws ServletException, IOException {
        ExhibitionDto exhibitionDto = new ExhibitionDto.Builder()
                .title(req.getParameter("title"))
                .description(req.getParameter("description"))
                .theme(themeService.getThemeById(
                        Integer.parseInt(req.getParameter("theme"))))
                .halls(processHallList(
                        req.getParameterValues("halls")))
                .startDate(req.getParameter("start-date"))
                .finishDate(req.getParameter("finish-date"))
                .openTime(req.getParameter("open-time"))
                .closeTime(req.getParameter("close-time"))
                .price(Integer.parseInt(req.getParameter("price")))
                .image(req.getPart("image").getSubmittedFileName())
                .state(ExhibitionState.valueOf(req.getParameter("state")))
                .build();
        return exhibitionService.createExhibition(exhibitionDto);
    }
}
