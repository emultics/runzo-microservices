package com.runzo.common.logger;

import com.runzo.common.constant.CommonConstant;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.UUID;

public class CorrelationIdFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String correlationId = request.getHeader(CommonConstant.CORRELATION_ID_HEADER);
        if(correlationId==null || correlationId.isEmpty()){
            correlationId = getRunZoCoId();
        }

        MDC.put(CommonConstant.CORRELATION_ID, correlationId);
        response.setHeader(CommonConstant.CORRELATION_ID_HEADER, correlationId);

        try{
            filterChain.doFilter(request, response);
        }finally {
            MDC.remove(CommonConstant.CORRELATION_ID);
        }

    }

    private String getRunZoCoId(){
        UUID uuid = UUID.randomUUID();
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);
        byteBuffer.putLong(uuid.getMostSignificantBits());
        byteBuffer.putLong(uuid.getLeastSignificantBits());
        return Base64.getUrlEncoder().withoutPadding().encodeToString(byteBuffer.array());

    }
}
