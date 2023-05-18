package com.example.nextboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("NEXT API")
                .description("NEXT Post System API")
                .version("0.1")
                .build();
    }

//    Docket: Springfox 프레임워크의 api 들에 대한 기본 인터페이스 설정을 위한 빌더
//          Swagger 의 기본 설정을 위한 Bean 으로 사용

//    userDefaultResponseMessages(): Swagger 에 미리 정의되어 있는 기본 응답 메시지를 사용할 지에 대한 여부를 설정하는 메서드

//    apiInfo(): Swagger API 문서에 대한 설명을 표기하는 메서드

//    select(): 선택된 api 에 대한 빌더를 초기화

//    apis(): Swagger API 문서로 만들기 API 메서드들이 작성되어 있는 패키지 (Controller 를 포함하고 있는 패키지) 의 경로를 지정

//    paths(): 특정 Path 를 지정하여 apis() 에 지정된 경로 중에서 원하는 경로의 api 만 문서화

//    build(): ApiSelectorBuilder 를 빌드한다. Docket 타입을 반환
}
