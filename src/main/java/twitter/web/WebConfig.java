package twitter.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.thymeleaf.extras.springsecurity3.dialect.SpringSecurityDialect;
import org.thymeleaf.spring3.SpringTemplateEngine;
import org.thymeleaf.spring3.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import java.io.IOException;

@Configuration
@EnableWebMvc
@ComponentScan("twitter.web")
public class WebConfig extends WebMvcConfigurerAdapter {

    @Bean
    public ViewResolver viewResolver(SpringTemplateEngine templateEngine) {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine);
        return viewResolver;
    }
    @Bean
    public SpringTemplateEngine templateEngine(TemplateResolver templateResolver) {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        templateEngine.addDialect(new SpringSecurityDialect());//SPRING SECURITY ENABLING
        return templateEngine;
    }
    @Bean
    public TemplateResolver templateResolver() {
        TemplateResolver templateResolver = new ServletContextTemplateResolver();
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        return templateResolver;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }


    //configuring of params in TwitterWebInitializer because it doesn't have a constructor and setters
//    @Bean
//    public MultipartResolver multipartResolver() throws IOException {
//        return new StandardServletMultipartResolver();
//    }

    //alternative to StandardServletMultipartResolver - it doesn't need Servlet 3.0;
    // tmp directory is optional there (by default using tmp loc of Servlet container);
    // max multipart request size can not configuring
//    @Bean
//    public MultipartResolver multipartResolver() throws IOException {
//        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
//        multipartResolver.setUploadTempDir(new FileSystemResource("/tmp/twitter/uploads"));
//        multipartResolver.setMaxUploadSize(2097152);
//        multipartResolver.setMaxInMemorySize(0);
//        return multipartResolver;
//    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //TODO CHECK favicon.ICO WITHOUT THIS METHOD
        registry.addResourceHandler("/resources/").addResourceLocations("/resources/**");
    }

    //    @Controller
//    static class FavIconController {
//        @RequestMapping("resources/images/favicon.ico")
//        String favIcon() {
//            return "forward:/resources/images/favicon.ico";
//        }
//    }

    //    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        // TODO Auto-generated method stub
//        super.addResourceHandlers(registry);
//    }

//    @Bean
//    public MessageSource messageSource(){
//        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//        messageSource.setBasename("messages");
//        ////Reload MSG without recompiling
////        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
////        messageSource.setBasename("messages");
////        messageSource.setCacheSeconds(10);
//        return messageSource;
//    }
}
