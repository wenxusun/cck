package com.lazy.cck.auth.security.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
public class RequestLogFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!(request instanceof ContentCachingRequestWrapper)) {
            request = new ContentCachingRequestWrapper(request);
        }
        if (!(response instanceof ContentCachingResponseWrapper)) {
            response = new ContentCachingResponseWrapper(response);
        }
        long ts = System.currentTimeMillis();
        try {
            filterChain.doFilter(request, response);
        } finally {
            LogInfo info = new LogInfo();
            info.setPath(request.getMethod() + " " + request.getRequestURI());
            info.setJwt(request.getHeader("Authorization"));
            info.setRequestType(request.getContentType());
            info.setQueryStr(request.getQueryString());
            info.setResponseStatus(response.getStatus());
            info.setResponseType(response.getContentType());

            ServletInputStream stream = request.getInputStream();
            if (stream.isFinished()) {
                info.setRequestBody(new String(((ContentCachingRequestWrapper) request).getContentAsByteArray(), request.getCharacterEncoding()));
            } else {
                info.setRequestBody(IOUtils.toString(request.getInputStream(), request.getCharacterEncoding()));
            }
            if (response.getContentType() != null && response.getContentType().startsWith(MediaType.APPLICATION_JSON_VALUE)) {
                try {
                    JsonNode result = new ObjectMapper().readTree(((ContentCachingResponseWrapper) response).getContentAsByteArray());
                    info.setResponseCode(result.path("code").asInt());
                } catch (Exception e) {
                }
            }
            info.setMs(System.currentTimeMillis() - ts);
            log.info(info.toString());
            ((ContentCachingResponseWrapper) response).copyBodyToResponse();
        }
    }

    @Data
    private static class LogInfo {

        private String path;

        private String jwt;

        private Integer user;

        private String queryStr;

        private String requestType;

        private String requestBody;

        private Integer responseStatus;

        private String responseType;

        private Integer responseCode;

        private Long ms;

        private String requestId;
    }
}
