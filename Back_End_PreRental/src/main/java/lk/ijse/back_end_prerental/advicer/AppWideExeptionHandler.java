package lk.ijse.back_end_prerental.advicer;

import lk.ijse.back_end_prerental.util.ResponseUtil;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppWideExeptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseUtil ExeptionHandler(Exception e){
        return new ResponseUtil(500, "Internal Server Error", e.getMessage());
    }
}
