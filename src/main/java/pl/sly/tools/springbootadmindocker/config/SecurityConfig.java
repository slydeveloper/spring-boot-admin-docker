package pl.sly.tools.springbootadmindocker.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import pl.sly.tools.springbootadmindocker.config.condition.SpringBootAdminSecureConditional;

@Conditional(SpringBootAdminSecureConditional.class)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String REDIRECT_TO = "redirectTo";
    private static final String ASSETS = "/assets/**";
    private static final String LOGIN = "/login";
    private static final String LOGOUT = "/logout";
    private final String adminContextPath;

    public SecurityConfig(AdminServerProperties adminServerProperties) {
        this.adminContextPath = adminServerProperties.getContextPath();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        SavedRequestAwareAuthenticationSuccessHandler successHandler
                = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter(REDIRECT_TO);

        http
                .authorizeRequests()
                    .antMatchers(adminContextPath + ASSETS).permitAll()
                    .antMatchers(adminContextPath + LOGIN).permitAll()
                    .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage(adminContextPath + LOGIN)
                    .successHandler(successHandler)
                .and()
                    .logout()
                    .logoutUrl(adminContextPath + LOGOUT)
                .and()
                    .httpBasic()
                .and()
                    .csrf()
                    .disable();
    }
}