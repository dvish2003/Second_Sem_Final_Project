package lk.ijse.back_end_prerental.advicer;

import lk.ijse.back_end_prerental.dto.ResponseDTO;
import lk.ijse.back_end_prerental.util.ResponseUtil;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppWideExeptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseDTO ExeptionHandler(Exception e){
        return new ResponseDTO(500, "Internal Server Error", e.getMessage());
    }
}
