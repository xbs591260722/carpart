package com.xiupeilian.carpart.session;

import com.xiupeilian.carpart.model.Menu;
import com.xiupeilian.carpart.model.SysUser;
import com.xiupeilian.carpart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Description: session拦截、权限控制
 * @Author: Tu Xu
 * @CreateDate: 2019/8/21 13:59
 * @Version: 1.0
 **/
public class SessionInterceptor implements HandlerInterceptor {


    @Autowired
    private UserService userService;




    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws  Exception{

        String path=request.getRequestURI();
        if(path.contains("login")){
            return true;
        }else {
            HttpSession session=request.getSession(false);
            if (null==session){

                response.sendRedirect(request.getContextPath()
                +"/login/toLogin");
                return false;
            }else {
                if(null==session.getAttribute("user")){
                    response.sendRedirect(request.getContextPath()
                            +"/login/toLogin");
                    return false;
                }else {

                    //意味着用户登录过  session校验完毕
                    //权限过滤权限拦截
                    SysUser user = (SysUser) session.getAttribute("user");

                    List<Menu> menuList = userService.findMenusById(user.getId());

                    boolean check=true;
                    for (Menu menu:menuList){

                        if (path.contains(menu.getMenuKey())){

                            check=true;
                        }
                    }
                    if (check){
                        return true;

                    }else {
                        response.sendRedirect(request.getContextPath()+"/login/noauth");
                        return false;
                    }

                }
            }
        }
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
           request.setAttribute("ctx",request.getContextPath());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
