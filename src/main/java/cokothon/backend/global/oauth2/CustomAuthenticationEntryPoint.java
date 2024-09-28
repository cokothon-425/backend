package cokothon.backend.global.oauth2;

import cokothon.backend.global.exception.ErrorResponseDTO;
import cokothon.backend.global.exception.GlobalErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        returnErrorResponse(response, authException);
    }

    private void returnErrorResponse(HttpServletResponse response, Exception e) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ErrorResponseDTO error = ErrorResponseDTO.create(GlobalErrorCode.DEFAULT, Instant.now());
        response.getWriter().write(objectMapper.writeValueAsString(error));
        System.out.println(error.getMessage());
    }
}