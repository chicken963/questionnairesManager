package ru.andreychuk.questionnaireManager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login.html");
//        registry.addViewController("/registration").setViewName("registration.html");
        registry.addViewController("/view").setViewName("view.html");
        registry.addViewController("/").setViewName("view.html");
        registry.addViewController("/create").setViewName("create.html");
        registry.addViewController("/edit").setViewName("edit.html");
        registry.addViewController("/check").setViewName("check.html");
        registry.addViewController("/users").setViewName("users.html");
    }
}
