package cl.template.config;

import cl.template.enums.UserResponseCode;
import org.springframework.context.MessageSource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
public class LanguageConfig {

    private final MessageSource messageSource;

    public LanguageConfig(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        UserResponseCode.setMessageSource(messageSource);
    }
}
