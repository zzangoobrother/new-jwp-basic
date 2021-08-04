package ormNext.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ormNext.service.QnaService;

import javax.inject.Inject;

@Controller
public class HomeController {

    @Inject
    private QnaService qnaService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String execute(Model model) throws Exception {
        model.addAttribute("questions", qnaService.findQuestions());
        System.out.println("home");
        return "home";
    }
}
