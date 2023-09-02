package com.dice.weatherApp.filter;

import com.dice.weatherApp.service.CredentialService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Component
@WebFilter("/api/weather/*")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WeatherFilter implements Filter {

    final
    CredentialService clientService;
    final AntPathMatcher antPathMatcher;

    private static final Logger logger = LoggerFactory.getLogger(WeatherFilter.class);
    private final List<String> excludedUrls = new ArrayList<>();


    public WeatherFilter(CredentialService clientService) {
        excludedUrls.add("/api/weather/get-new-credentials");
        antPathMatcher = new AntPathMatcher();
        this.clientService = clientService;
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String requestUri = new UrlPathHelper().getRequestUri(httpRequest);
        boolean isExcluded = excludedUrls.stream()
                .anyMatch(pattern -> antPathMatcher.match(pattern, requestUri));



        String clientId = httpRequest.getHeader("client-id");
        String clientSecret = httpRequest.getHeader("client-secret");

        try {
            if (isExcluded ||
                    (clientId != null && clientSecret != null &&
                    clientService.validateAuthentication(clientId, clientSecret))) {
                chain.doFilter(request, response);
            } else {
                ((HttpServletResponse) response).sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid credentials");
            }
        } catch (NoSuchAlgorithmException e) {
            logger.debug("NoSuchAlgorithmException", e);
            ((HttpServletResponse) response).sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Error");
        }


    }
}
