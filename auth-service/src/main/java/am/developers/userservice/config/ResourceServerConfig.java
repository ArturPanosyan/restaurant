package am.developers.userservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/public/**").permitAll()  // �������� ��������
                .antMatchers("/api/**").authenticated() // ��������, ��������� ��������������
                .and()
                .oauth2ResourceServer()
                .jwt(); // ������������� JWT ��� ��������������
    }
}