package controller.exhibition;

import dto.ExhibitionDto;
import entity.Exhibition;
import entity.ExhibitionState;
import entity.Hall;
import exception.DBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ExhibitionService;
import service.HallService;
import service.ThemeService;
import utils.Utils;

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

@WebServlet("/exhibitions/edit")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class EditExhibition extends HttpServlet {
    public static final Logger LOGGER = LoggerFactory.getLogger(EditExhibition.class);
    private static final String UPLOAD_DIRECTORY = "assets\\img";
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
        try {
            int exhibitionId = Integer.parseInt(req.getParameter("exid"));
            req.setAttribute("ex", exhibitionService.getExhibitionById(exhibitionId));
            req.setAttribute("themes", themeService.getThemes());
            req.setAttribute("halls", hallService.getHalls());
            req.setAttribute("states", ExhibitionState.values());
        } catch (DBException e) {
            LOGGER.error(e.getMessage());
            Utils.setErrorMessage(req, resp, e.getMessage());
        }
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/exhibitions/edit_exhibition.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            int exhibitionId = Integer.parseInt(req.getParameter("exid"));
            String uploadImagePath = getServletContext().getRealPath("")
                    + UPLOAD_DIRECTORY + File.separator + exhibitionId;
            processRequest(req, exhibitionId, uploadImagePath);
            resp.sendRedirect(req.getContextPath() + "/exhibitions/show");
        } catch (DBException e) {
            LOGGER.error(e.getMessage());
            Utils.setErrorMessage(req, resp, e.getMessage());
        }
    }

    private void processRequest(HttpServletRequest req, int exId, String uploadImagePath)
            throws ServletException, IOException, DBException {
        ExhibitionDto editEx = exhibitionService.getExhibitionById(exId);
        editEx.setTitle(req.getParameter("title"));
        editEx.setDescription(req.getParameter("description"));
        editEx.setTheme(themeService.getThemeById(
                Integer.parseInt(req.getParameter("theme"))));
        editEx.setHalls(processHallList(
                req.getParameterValues("halls")));
        editEx.setStartDate(req.getParameter("start-date"));
        editEx.setFinishDate(req.getParameter("finish-date"));
        editEx.setOpenTime(req.getParameter("open-time"));
        editEx.setCloseTime(req.getParameter("close-time"));
        editEx.setPrice(Integer.parseInt(req.getParameter("price")));
        String fileName = uploadImage(req, uploadImagePath);
        editEx.setImage(fileName == null ? editEx.getImage() : fileName);
        editEx.setState(ExhibitionState.valueOf(req.getParameter("state")));
        exhibitionService.updateExhibition(editEx);
    }

    private List<Hall> processHallList(String[] halls) throws DBException {
        List<Hall> hallList = new ArrayList<>();
        for (String hall : halls) {
            int hallId = Integer.parseInt(hall);
            hallList.add(hallService.getHallById(hallId));
        }
        return hallList;
    }

    private String uploadImage(HttpServletRequest req, String uploadPath)
            throws ServletException, IOException {
        String fileName = req.getPart("image")
                .getSubmittedFileName();
        if (fileName.isEmpty()) return null;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();
        for (Part part : req.getParts()) {
            part.write(uploadPath + File.separator + fileName);
        }
        LOGGER.info("Image uploaded successfully => " +
                uploadPath + File.separator + fileName);
        return fileName;
    }
}
