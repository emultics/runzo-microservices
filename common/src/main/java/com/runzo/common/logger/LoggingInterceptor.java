package com.runzo.common.logger;

import com.runzo.common.constant.CommonConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private final AppLogger logger = AppLogger.getInstance(LoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String correlationId = request.getHeader(CommonConstant.CORRELATION_ID_HEADER);
        logger.info("Incoming request: "+ request.getMethod()+ " "+ request.getRequestURI()+ " | runzo-cor-Id: "+ correlationId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        String correlationId = response.getHeader(CommonConstant.CORRELATION_ID_HEADER);
        logger.info("Response Status: " + response.getStatus() + " for " + request.getRequestURI() + " | runzo-cor-Id: " + correlationId);
        if (ex != null) {
            logger.error("Exception: ", ex);
        }
    }


}
