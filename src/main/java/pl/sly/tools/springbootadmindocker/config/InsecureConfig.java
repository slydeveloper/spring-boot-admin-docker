package pl.sly.tools.springbootadmindocker.config;

import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import pl.sly.tools.springbootadmindocker.config.condition.SpringBootAdminInsecureConditional;

@Conditional(SpringBootAdminInsecureConditional.class)
@Configuration
public class InsecureConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .anyRequest()
                    .permitAll()
                .and()
                    .csrf()
                    .disable();
    }
}
