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
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
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
                .antMatchers(HttpMethod.GET, "/api/users/forgotPassword", "/api/users/forgotPassword/reset", "/api/ratings/{id}", "/api/items/{itemId}", "/api/advertisements", "/api/users/activate", "/api/image/{name}").permitAll()
                .antMatchers(HttpMethod.POST, "/api", "/api/users/auth", "/api/users", "/api/users/contactUs").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/users/forgotPassword/change").permitAll()

                .antMatchers(HttpMethod.GET, "/api/users", "/api/items/my/{id}", "/api/items/active", "/api/items/archived", "api/cars", "api/cars/{id}", "/api/preference").hasAnyAuthority("USER")
                .antMatchers(HttpMethod.POST, "/api/ratings", "/api/preference", "api/items", "/api/cars", "/api/users/avatar", "/api/cars/picture").hasAnyAuthority("USER")
                .antMatchers(HttpMethod.PUT, "/api/users/password/change", "/api/users", "/api/items/change", "/api/items/change/active/{id}", "/api/items/change/archived/{id}", "/api/cars/mainPicture", "/api/cars", "/api/users/phone", "/api/cars/picture").hasAnyAuthority("USER")
                .antMatchers(HttpMethod.DELETE, "/api/users", "/api/items/{id}", "/api/cars/{id}", "/api/cars/picture{id}").hasAnyAuthority("USER")

                .antMatchers(HttpMethod.GET, "/api/users/find/{name}/{surname}").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/advertisements/", "/api/users/find").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/advertisements/{id}", "/api/users/{id}").hasAnyAuthority("ADMIN")
                .anyRequest().permitAll();
        //mnacac zaprosner@ pakelu hamar petq e stugvi  permitAll em tvel swageri hamar

        // .anyRequest().hasAnyAuthority();

        // Custom JWT based security filter
        http
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

        // disable page caching
        http.headers().cacheControl();
        // for http://localhost:8080/h2-console . you need change scope in runtime
        // http.headers().frameOptions().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
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
