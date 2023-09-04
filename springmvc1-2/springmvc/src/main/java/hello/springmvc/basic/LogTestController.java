package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LogTestController {
    //private final Logger log = LoggerFactory.getLogger(getClass()); // getClass() = LogTestController.class

    // LogLevel : TRACE > DEBUG > INFO > WARN > ERROR
    // 개발서버는 debug 운영서버는 info
    @RequestMapping("/log-test")
    public String logTest(){
        String name = "Spring";

        // log.info("info log=" +name);  <- 더하기 연산을 하기때문에 메모리낭비ㅇㅇ +로 사용X
        // log.trace("trace log={} ",name); <- 이렇게 사용해야 의미없는 연산이 일어나지 않음

        log.trace("trace log={} ",name); //
        log.debug("debug log={} ",name); // debug할때 보는거
        log.info("info log={} ",name); // 중요한 정보
        log.warn("warn log={} ",name); // 경고
        log.error("error log={} ",name); // 에러

        return "ok";
    }
    //@RestController를 사용하면 문자를 반환하면 웹페이지에 Http body에 바로 입력
    //@Controller는 반환값이 String 이면 뷰 이름으로 인식된다. 그래서 뷰를찾고 뷰가 렌더링됨
}
