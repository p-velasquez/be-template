package cl.template.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@RestController
@RequestMapping("/api/locale")
public class LocaleController {

    @PostMapping("/change")
    public ResponseEntity<String> changeLocale(HttpServletRequest request,
                                               @RequestParam("lang") String lang) {
        Locale locale = new Locale(lang);
        LocaleContextHolder.setLocale(locale);

        HttpSession session = request.getSession();
        session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);

        return ResponseEntity.ok("Locale changed to " + lang);
    }
}
