package am.greenlight.greenlight.config;


import am.greenlight.greenlight.security.JwtAuthenticationEntryPoint;
import am.greenlight.greenlight.security.JwtAuthenticationTokenFilter;
import am.greenlight.greenlight.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,  "/user/forgotPassword", "/user/forgotPassword/reset", "/rating/{id}", "/image", "/item/{itemId}", "/advertisements").permitAll()
                .antMatchers(HttpMethod.POST, "/","user/auth", "/user", "/user/activate").permitAll()
                .antMatchers(HttpMethod.PUT, "/user/forgotPassword/reset").permitAll()

                .antMatchers(HttpMethod.GET, "/user" , "/item/active", "/item/archived", "/cars", "/car/{id}").hasAnyAuthority("USER")
                .antMatchers(HttpMethod.POST, "/rating", "/preference","/item", "/car", "/user/avatar").hasAnyAuthority("USER")
                .antMatchers(HttpMethod.PUT, "/user/password/change", "/user/about",   "/user", "/item/change", "/car/Image", "/car").hasAnyAuthority("USER")
                .antMatchers(HttpMethod.DELETE, "/user", "/item/{id}").hasAnyAuthority("USER")

                .antMatchers(HttpMethod.GET, "/user/find").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/advertisement","/user/find").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/advertisement/{id}", "/user/{id}").hasAnyAuthority("ADMIN")
                .anyRequest().authenticated();

        // Custom JWT based security filter
        http
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

        // disable page caching
        http.headers().cacheControl();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.
                userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }

}
