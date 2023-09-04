package hello.servlet.web.springmvc.old;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component("/springmvc/old-controller")
public class OldController implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("OldController.handleRequest");
        return new ModelAndView("new-form");    //논리 정의 이름만 넣은거임
        //view를 못찾기때문에 오류페이지가 뜸
        //application.properties에 경로를 지정해줘야함
        //권장하지는 않지만 설정없이 다음과 같이 전체 경로를 주어도 동작하기는 한다. return new ModelAndView("WEB-INF/views/new-form.jsp");
        //BeanNameViewResolver 이라는 뷰를 찾아야하는데 없기때문에 InternalResourceViewResolver가 호출
    }
}
