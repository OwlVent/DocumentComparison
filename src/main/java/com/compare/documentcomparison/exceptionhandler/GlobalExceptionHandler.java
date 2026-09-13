package com.compare.documentcomparison.exceptionhandler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception ex, Model model) {
        // 1. Проверяем, не спрятана ли наша кастомная ошибка внутри ex
        Throwable cause = ex;
        while (cause != null) {
            if (cause instanceof BusinessException) {
                BusinessException businessEx = (BusinessException) cause;
                model.addAttribute("errorMessage", businessEx.getMessage());
                return "error-page";
            }
            cause = cause.getCause(); // Переходим к следующему вложенному исключению
        }

        // 2. Если это реально неизвестная ошибка (например, базы данных или сети)
        model.addAttribute("errorMessage", ErrorType.UNKNOWN_ERROR.getMessage());
        return "error-page";
    }
}
