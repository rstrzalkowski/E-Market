package pl.marketapi.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.marketapi.domain.dto.ErrorObject;
import pl.marketapi.domain.entity.User;
import pl.marketapi.repository.UserRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;
    private final List<AntPathRequestMatcher> excludedMatchers;

    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    UserRepository userRepository;


    public JwtFilter(ObjectMapper objectMapper, List<AntPathRequestMatcher> excludedMatchers) {
        this.objectMapper = objectMapper;
        this.excludedMatchers = excludedMatchers;

        this.excludedMatchers.add(new AntPathRequestMatcher("/login"));
        this.excludedMatchers.add(new AntPathRequestMatcher("/register"));
        this.excludedMatchers.add(new AntPathRequestMatcher("/products"));
        this.excludedMatchers.add(new AntPathRequestMatcher("/products/search/*"));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwt = getToken(request);

        try {
            if (jwt != null && jwtProvider.validateToken(jwt)) {
                Claims claims = jwtProvider.parseJWT(jwt).getBody();

                Optional<User> optionalUser = userRepository.findByEmail(claims.getSubject());

                if (optionalUser.isPresent() && optionalUser.get().isEnabled()) {
                    User user = optionalUser.get();
                    String role = "ROLE_" + user.getRole();

                    SecurityContextHolder.getContext()
                            .setAuthentication(new UsernamePasswordAuthenticationToken(
                                    claims.getSubject(),
                                    null,
                                    Collections.singleton(new SimpleGrantedAuthority(role))));

                    filterChain.doFilter(request, response);

                }
            }
        } catch (Throwable ex) {
            handleFilterException(response, "Authorization failed");
        }

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {


        return excludedMatchers.stream().anyMatch(antPathRequestMatcher -> antPathRequestMatcher.matches(request));

    }

    private String getToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.replace("Bearer ", "");
        }

        return null;
    }

    private void handleFilterException(HttpServletResponse response, String message) throws IOException {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        errorObject.setMessage(message);

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(objectMapper.writeValueAsString(errorObject));
    }
}
