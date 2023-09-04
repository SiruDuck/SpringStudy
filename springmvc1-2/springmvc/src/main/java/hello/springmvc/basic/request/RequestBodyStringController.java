package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyStringController {

    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        //byte코드를 바꿀땐 어떤 인코딩으로 할 지 지정해줘야함 지정안하면 기본 인코딩으로 지정
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}",messageBody);

        response.getWriter().write("ok");


    }

    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {
        //byte코드를 바꿀땐 어떤 인코딩으로 할 지 지정해줘야함 지정안하면 기본 인코딩으로 지정
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}",messageBody);

        responseWriter.write("ok");
    }

    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException {
        //Http message 컨버터
        String messageBody = httpEntity.getBody();
        //HttpHeaders headers = httpEntity.getHeaders();
        //메시지 바디 정보를 직접 조회
        //요청 파라미터를 조회하는 기능과 관계 없음 @RequesetParam X,  @ModelAttribute X
        //view 조회 X
        log.info("messageBody={}",messageBody);
        return new HttpEntity<>("ok");
    }


    @ResponseBody
    @PostMapping("/request-body-string-v4")
    public String requestBodyString4(@RequestBody String messageBody) throws IOException {
        
        //헤더 정보가 필요하다면 HttpEntity or @RequestHeader 사용
        //바디를 조회하는 기능은 @RequestParam @ModelAttribute와 관계 x
        log.info("messageBody={}",messageBody);

        //요청 파라미터를 조회하는 기능 @RequestParam, @ModelAttribute
        //HTTP Message Body를 직접 조회하는 기능 @RequestBody
        //@ResponseBody를 사용하면 응답결과(201) HTTP MessageBody에 직접 담아서 전달할 수 있다. view 사용x
        return "ok";
    }

}
