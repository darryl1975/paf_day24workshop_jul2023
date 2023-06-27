package sg.edu.nus.iss.paf_day24workshop_jul2023.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgumentException(IllegalArgumentException ex,
            HttpServletRequest request, HttpServletResponse response) {
        ApiError errMsg = new ApiError(response.getStatus(), new Date(), ex.getMessage());

        return new ResponseEntity<ApiError>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception ex,
            HttpServletRequest request, HttpServletResponse response) {
        ApiError errMsg = new ApiError(response.getStatus(), new Date(), ex.getMessage() + " " + request.getRequestURI());

        return new ResponseEntity<ApiError>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
