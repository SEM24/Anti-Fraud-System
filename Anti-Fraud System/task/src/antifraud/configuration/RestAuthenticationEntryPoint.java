package antifraud.configuration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    /**
     * This method is called whenever an exception is thrown due to an unauthenticated user trying to access a resource
     * that requires authentication.
     *
     * @param request  that resulted in an <code>AuthenticationException</code>
     * @param response  so that the user agent can begin authentication
     * @param authException that caused the invocation
     * @throws IOException  if an input/output exception happens
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }
}
