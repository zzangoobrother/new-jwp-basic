package core.di.factory.example;

import core.annotation.Controller;
import core.annotation.Inject;

@Controller
public class MyUserController {
    @Inject
    private MyQnaService qnaService;

    private MyUserService myUserService;

    @Inject
    public void setMyUserService(MyUserService myUserService) {
        this.myUserService = myUserService;
    }

    public MyUserService getMyUserService() {
        return myUserService;
    }
}
