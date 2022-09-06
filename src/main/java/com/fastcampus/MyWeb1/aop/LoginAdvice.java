//package com.fastcampus.MyWeb1.aop;
//
//
//import com.fastcampus.MyWeb1.service.LoginService;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//@Aspect
//@Component
//public class LoginAdvice {
//    @Autowired
//    HttpSession session;
//    @Autowired
//    HttpServletRequest request;
//
//    @Autowired
//    LoginService loginService;
//
//    @Around("execution(String com.fastcampus.MyWeb1.controller.BoardController.*(..))")
//    public Object login(ProceedingJoinPoint pjp) throws Throwable {
////        Object[] objs = pjp.getArgs();
////        RedirectAttributes rattr;
////        for(int i = 0 ; i < objs.length ; i++){
////            if(objs[i] instanceof RedirectAttributes){
////                rattr = (RedirectAttributes) objs[i];
////            }
////        }
////        RedirectAttributes rattr = (RedirectAttributes) pjp.getArgs()[0];
////        System.out.println("pjp.getArgs() = " + Arrays.toString(pjp.getArgs()));
//        String toURL = request.getServletPath();
//        System.out.println("toURL = " + toURL);
//        if(session.getAttribute("id")==null){
////            rattr.addAttribute("toURL", toURL);
//            return "redirect:/login?toURL="+toURL;
//        }
//
//        Object obj = pjp.proceed();
//
//        return obj;
//    }
//
//
//}
