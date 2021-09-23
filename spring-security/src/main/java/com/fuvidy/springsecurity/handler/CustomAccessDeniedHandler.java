package com.fuvidy.springsecurity.handler;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private Log logger = LogFactory.getLog(CustomAccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        boolean isAjax = ControllerTools.isAjaxRequest(httpServletRequest);
        logger.info("CustomAccessDeniedHandler Handel");
        if (!httpServletResponse.isCommitted()) {
            if (isAjax) {
                String msg = accessDeniedException.getMessage();
                logger.info("accessDeniedException.message:" + msg);
                String accessDenyMsg = "{\"code\":\"403\",\"msg\":\"没有权限\"}";
                ControllerTools.print(httpServletResponse, accessDenyMsg);
            } else {
                httpServletRequest.setAttribute(WebAttributes.ACCESS_DENIED_403, accessDeniedException);
                httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
                RequestDispatcher dispatcher = httpServletRequest.getRequestDispatcher("/403");
                dispatcher.forward(httpServletRequest, httpServletResponse);
            }
        }
    }

    public static class ControllerTools {
        public static boolean isAjaxRequest(HttpServletRequest request) {
            return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
        }

        public static void print(HttpServletResponse response, String msg) throws IOException {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(msg);
            writer.flush();
            writer.close();
        }
    }
}
