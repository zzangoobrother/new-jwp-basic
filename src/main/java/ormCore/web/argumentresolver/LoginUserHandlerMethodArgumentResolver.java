package ormCore.web.argumentresolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import ormNext.model.User;
import ormNext.service.SessionComponent;

public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private SessionComponent sessionComponent;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        LoginUser loginUserAnnotation = methodParameter.getParameterAnnotation(LoginUser.class);
        User loginUser = sessionComponent.findUser(nativeWebRequest);
        if (loginUserAnnotation.required() && loginUser.isGuestUser()) {
            throw new LoginRequiredException();
        }
        return loginUser;
    }
}
