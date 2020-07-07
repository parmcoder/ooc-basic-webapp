package ooc.webapp.possawat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(value = NoHandlerFoundException.class)
    public Object handleStaticResourceNotFound(final NoHandlerFoundException ex,
                                               HttpServletRequest req,
                                               RedirectAttributes redirectAttributes) {
        if (req.getRequestURI().startsWith("/api")){
            System.out.println(req.toString());
        return this.getApiResourceNotFoundBody(ex, req);
        }else {
            redirectAttributes.addFlashAttribute("errorMessage", "Nothing here, sorry!");
            return "redirect:/login";
        }
    }

    private ResponseEntity<String> getApiResourceNotFoundBody(NoHandlerFoundException ex, HttpServletRequest req) {
        return new ResponseEntity<>("Not Found!", HttpStatus.NOT_FOUND);
    }
}