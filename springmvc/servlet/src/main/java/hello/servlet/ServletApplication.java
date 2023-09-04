package hello.servlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan	//서블릿 자동 등록
@SpringBootApplication
public class ServletApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServletApplication.class, args);
	}

//	@Bean
//	ViewResolver internalResourceViewResolver(){
//		return new InternalResourceViewResolver("/WEB-INF/views/",".jsp")
//	} 이 정보를 스프링 부트가 자동으로 해주기때문에 안해두됨!!    //application.properties에 경로를 지정해줘야함

}
