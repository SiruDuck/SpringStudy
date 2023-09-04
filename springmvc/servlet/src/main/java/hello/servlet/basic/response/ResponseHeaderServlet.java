package hello.servlet.basic.response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //리퀘스트 응답타입 200직접 적는거보단 ㅇㅇ 라이브러리에 있는거 쓰는게 좋음
        //[status-line]
        response.setStatus(HttpServletResponse.SC_OK);

        //[response-headers]
        //response.setHeader("Content-Type","text/plain;charset=utf-8");
        // 캐시를 완전 무효화
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma","no-cache");
        response.setHeader("my-header", "hello");

        //[Header 편의 메서드]
//        content(response);
//        cookie(response);
        redirect(response);
        PrintWriter writer = response.getWriter();
        writer.println("안녕하세요");
    }

    private void content(HttpServletResponse response){
        //Content-Type: text/plain;charset=utf-8
        //Content-Length:2
        //response.setHeader("Content-Type", "text/plain;charset=utf-8");
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        //response.setContentLength(2); //(생략시 자동 생성)
    }

    private void cookie(HttpServletResponse response){
        //Set-Cookie : myCookie=good;Max-Age=600;
        Cookie cookie = new Cookie("myCookie", "good");
        cookie.setMaxAge(600); //600초
        response.addCookie(cookie);
    }

    private void redirect(HttpServletResponse response) throws IOException{
        //Status Code 302
        //LocationL /basic/hello-form.html

//        response.setStatus(HttpServletResponse.SC_FOUND); //302
//        response.setHeader("Location", "/basic/hello-form.html");
        //위 코드 두줄로 리다이렉트 시키기 불편해서 아래 줄로 적용
        response.sendRedirect("/basic/hello-form.html");
    }
}
