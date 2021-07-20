package next.Controller;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.JdbcQuestionDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    private JdbcQuestionDao jdbcQuestionDao = JdbcQuestionDao.getInstance();

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        return jspView("home.jsp")
                .addObject("questions", jdbcQuestionDao.findAll());
    }
}
