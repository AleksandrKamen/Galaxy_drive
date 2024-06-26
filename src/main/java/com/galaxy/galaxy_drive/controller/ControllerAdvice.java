package com.galaxy.galaxy_drive.controller;

import com.galaxy.galaxy_drive.exception.minio.*;
import com.galaxy.galaxy_drive.exception.user.UserAlreadyExistsException;
import com.galaxy.galaxy_drive.exception.user.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Slf4j
@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {


    @ExceptionHandler(UserAlreadyExistsException.class)
    public String handleUserAlreadyExistsException(UserAlreadyExistsException exception,
                                                   RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errors", List.of(exception.getMessage()));
        return "redirect:registration";
    }

    @ExceptionHandler({MinioCopyException.class, MinioDownloadException.class, MinioRemoveException.class,
            MinioUploadException.class, MinioDuplicateNameException.class, MemoryLlimitException.class, IncorrectNameException.class, MinioObjectSizeException.class})
    public String handleMinioException(Exception exception,
                                       RedirectAttributes redirectAttributes,
                                       HttpServletRequest request) {
        redirectAttributes.addFlashAttribute("errors", exception.getMessage());
        return "redirect:" + request.getHeader("referer");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDeniedException() {
        return "errors_page/error403";
    }

    @ExceptionHandler(FolderNotFoundException.class)
    public String handleFolderNotFoundException() {
        return "errors_page/error404";
    }

    @ExceptionHandler({MinioCreateException.class, UserNotFoundException.class, Exception.class})
    public String handleException(Exception e) {
        log.error("Server error:" + e.getMessage());
        return "errors_page/error500";
    }
}
