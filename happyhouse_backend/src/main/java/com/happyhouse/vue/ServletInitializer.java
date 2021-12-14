package com.happyhouse.vue;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/*
 * SpringBootServletInitializer 클래스는 WebApplicationInitializer 인터페이스의 구현체 결과적으로
 * SpringBootServletInitializer 상속 한다는 것은 Spring Boot 애플리케이션 동작이 가능 하도록 웹 애플리케이션 컨텍스트(IoC 방식으로 Bean을
 * 관리하는 컨테이너) 구성 한다는 의미
 */
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        // TODO Auto-generated method stub
        return application.sources(LastHappyHouseApplication.class);
    }

}
