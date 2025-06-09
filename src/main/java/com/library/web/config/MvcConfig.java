package com.library.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // 이 클래스가 Spring 설정임을 나타냅니다.
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        // LocalDateTime 객체를 JSP에서 포맷팅할 수 있도록 DateTimeFormatterRegistrar를 등록
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        // ISO 형식 사용 여부. 필요에 따라 true로 설정할 수 있습니다.
        // false로 두면 pattern="yyyy-MM-dd HH:mm" 같은 명시적 패턴을 따릅니다.
        
        registrar.registerFormatters(registry);
    }
}