package weddingplanner.login.component.security.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import weddingplanner.application.component.MD5Encoder;
import weddingplanner.login.component.security.*;

import javax.sql.DataSource;

/**
 * Create by Daniel Drzazga on 16.10.2020
 **/

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private AuthSuccessHandler authSuccessHandler;
    private AuthFailureHandler authFailureHandler;

    private DataSource dataSource;
    private MD5Encoder md5Encoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable().
                headers().frameOptions().sameOrigin()
                .and()
                    .authorizeRequests()
                    .antMatchers("/admin/users/new")
                    .permitAll()
                    .antMatchers("/")
                    .authenticated()
                .and()
                    .formLogin()
                    .successHandler(authSuccessHandler)
                    .failureHandler(authFailureHandler)
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .permitAll()
                .and().
                    exceptionHandling()
                .and()
                    .logout()
                    .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");
    }


    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .jdbcAuthentication()
                .usersByUsernameQuery("SELECT email, password, active FROM users WHERE email=?")
                .authoritiesByUsernameQuery("SELECT u.email, r.role_name FROM users_roles ur LEFT JOIN users u on u.id=ur.user_id LEFT JOIN roles r on r.id=ur.role_id WHERE email=?")
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder());

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return md5Encoder.getMD5Hash(charSequence.toString());
            }

            @Override
            public boolean matches(CharSequence charSequence, String encodedPassword) {
                return md5Encoder.getMD5Hash((charSequence.toString())).equals(encodedPassword);
            }
        };
    }


}
