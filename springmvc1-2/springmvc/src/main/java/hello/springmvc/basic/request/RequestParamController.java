package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Controller
public class RequestParamController {

    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        log.info("username={}, age={}", username,age);

        response.getWriter().write("ok");
        //void type이면서 response.getWriter로 하면 값쓴게 body에 출력
    }

    @ResponseBody   //String 이 body에 출력됨 == RestController
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String memberName,
            @RequestParam("age") int memberAge){

        log.info("username={}, age={}", memberName,memberAge);
        return "ok";
    }

    @ResponseBody   //String 이 body에 출력됨 == RestController
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            @RequestParam String username,
            @RequestParam int age){

        log.info("username={}, age={}", username,age);
        return "ok";
    }

    @ResponseBody   //String 이 body에 출력됨 == RestController
    @RequestMapping("/request-param-v4")
    public String requestParamV4( String username, int age ){
        // String int Integer 등의 단순 타입이면 @RequestParam 도 생략 가능
        log.info("username={}, age={}", username,age);
        return "ok";
    }

    @ResponseBody   //String 이 body에 출력됨 == RestController
    @RequestMapping("/request-param-required")
    public String requestParamRequired(
            @RequestParam (required = true) String username,
            @RequestParam (required = false) Integer age ){
        //int 에는 null을 넣을 수 없음
        //Integer 은 객체형이기때문에 null o
        //required 가 true면 꼭 있어야됨 , false면 없어두 됨

        //** 파라미터 이름만 있고 값이 없는경우  -> 빈 문자로 통과 " " 꼭 null로 넣어줘야함
        log.info("username={}, age={}", username,age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
            @RequestParam (defaultValue = "guest") String username,
            @RequestParam (defaultValue = "-1") int age ){

        //defaultValue는 빈 문자의 경우에도 디폴트값이 적용됨
        log.info("username={}, age={}", username,age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Objects> paramMap){ //파라미터 값을 다 받고싶을때
        //파라미터를 Map이나 MultiValueMap으로 조회할 수 있다.
        //파라미터의 값이 1개면 'Map' 그렇지 않다면 MultiValueMap 보통 파라미터는 하나의 값만 사용
        log.info("username={}, age={}", paramMap.get("username"),paramMap.get("age"));
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData){
        //HelloData 객체 생성
        // 요청 파라미터의 이름으로 객체의 프로퍼티를 찾음 setter를 호출해서 값을 입력한다.
        log.info("username={}, age={}", helloData.getUsername(),helloData.getAge());
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData){
        // String int Integer 같은 단순 타입 = @RequestParam
        // 나머지 @ModelAttribute (argument resolver로 지정해둔 타입 외) (HttpServletResponse같은게 argument ...)
        log.info("username={}, age={}", helloData.getUsername(),helloData.getAge());
        return "ok";
    }
}
