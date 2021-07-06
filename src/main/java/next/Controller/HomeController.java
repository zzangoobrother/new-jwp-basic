package next.Controller;

import core.mvc.Controller;
import next.dao.QuestionDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        QuestionDao questionDao = new QuestionDao();
        request.setAttribute("questions", questionDao.findAll());
        System.out.println(request.getAttribute("questions"));
        return "home.jsp";
    }
}
