package com.Me_and_U.project;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 프로젝트 외부 경로 접근 설정 
// 상품 이미지를 외부 폴더에 저장하고 출력하기 위해 필요
@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/**")
		.addResourceLocations("file:///usr/local/project/me_and_u_images/",
				"file:///C:/springWorkspace/upload/");
	
		registry.addResourceHandler("/audio/**")
		.addResourceLocations("file:///C:/springWorkspace/upload/");
	}
}
