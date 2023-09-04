package hello.springmvc.basic.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyJsonController {

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}",messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);

        log.info("username={}, age={}",helloData.getUsername(),helloData.getAge());
        response.getWriter().write("ok");
    }

    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {

        log.info("messageBody={}",messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        // 위 코드가 Http 메시지컨버터 역할

        log.info("username={}, age={}",helloData.getUsername(),helloData.getAge());
        return "ok";
    }

    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody HelloData helloData) throws IOException {
        // @RequestBody 생략 불가능 -> 생략하면 ModelAttribute가 되버림
        //@ModelAttribute @RequestParam 해당 생략시 다음과 같은 규칙 적용
        //String int Integr 같은 단순 타입 @RequestParam
        //나머지 ModelAttribute (argument resolver로 지정해둔 타입 외)
        log.info("username={}, age={}",helloData.getUsername(),helloData.getAge());
        return "ok";
    }
    //HTTP 요청시에 content-type이 application/json인지 꼭 확인해야함

    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(HttpEntity<HelloData> httpEntity) throws IOException {
        HelloData data = httpEntity.getBody();
        log.info("httpEntity={}, age={}",data.getUsername(),data.getAge());
        return "ok";
    }

    @ResponseBody
    @PostMapping("/request-body-json-v5")
    public HelloData requestBodyJsonV5(@RequestBody HelloData data) throws IOException {
        log.info("httpEntity={}, age={}",data.getUsername(),data.getAge());
        //HTTP 컨버터가 @ResponseBody 있으면 들어올때도 적용 나갈때도 적용
        //다시 Json으로 변환
        return data;
    }

    //@RequestBody  JSON요청 -> HTTP 메시지 컨버터 -> 객체
    //@ResponseBody 객체 -> HTTP 메시지 컨버터 -> JSON 응답


}
