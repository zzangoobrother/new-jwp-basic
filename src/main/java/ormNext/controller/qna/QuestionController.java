package ormNext.controller.qna;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ormCore.web.argumentresolver.LoginUser;
import ormNext.CannotOperateException;
import ormNext.model.Question;
import ormNext.model.User;
import ormNext.service.QnaService;

import javax.inject.Inject;

@Controller
@RequestMapping("/qna")
public class QuestionController {
    private QnaService qnaService;

    @Inject
    public QuestionController(QnaService qnaService) {
        this.qnaService = qnaService;
    }

    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public String show(@PathVariable Long questionId, Model model) throws Exception {
        model.addAttribute("question", qnaService.findById(questionId));
        return "/qna/show";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String createForm(@LoginUser User loginUser, Model model) throws Exception {
        model.addAttribute("question", new Question());
        return "/qna/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@LoginUser User loginUser, Question question) throws Exception {
        qnaService.create(question, loginUser);
        return "redirect:/";
    }

    @RequestMapping(value = "/updateForm", method = RequestMethod.GET)
    public String updateForm(@LoginUser User loginUser, @PathVariable Long questionId, Model model) throws Exception {
        Question question = qnaService.findById(questionId);
        if (!question.isSameWriter(loginUser)) {
            throw new IllegalStateException("다른 사용자가 쓴 글을 수정할 수 없습니다.");
        }
        model.addAttribute("question", question);
        return "/qna/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public String update(@LoginUser User loginUser, @PathVariable Long questionId, Question updateQuestion) throws Exception {
        qnaService.update(updateQuestion, loginUser);
        return "redirect:/";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public String delete(@LoginUser User loginUser, @PathVariable Long questionId, RedirectAttributes attributes) throws Exception {
        try {
            qnaService.deleteQuestion(questionId, loginUser);
            return "redirect:/";
        } catch (CannotOperateException e) {
            attributes.addAttribute("errorMessage", e.getMessage());
            return String.format("redirect:/qna/show?questionId=%d", questionId);
        }
    }
}
