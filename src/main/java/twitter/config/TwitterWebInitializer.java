package twitter.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import twitter.web.WebConfig;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

//Alternative to web.xml - needs Servlet 3.0+
// creates DispatcherServlet (using getServletCC (WebConfig) for creating Spring app ctx)
// and ContextLoaderListener (using getRootCC -> 2nd app ctx )

// in project ThymeleafExample - 2 xmls for Spring resolving of JSP files in WEB-INF dir
// + xmls for LOG4J config
public class TwitterWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] { RootConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { WebConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }


    // 1 max filesize and 2 request size by default - unlimited
    // 3rd param - max filesize without loading to tmp directory ( 0 means ALL files go to tmp)
    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setMultipartConfig(
                new MultipartConfigElement("/tmp/twitter/uploads", 2097152, 4194304, 0));//2MB 4MB
    }
//    ServletRegistration.Dynamic supports configuring of:
// multipart-requests by setMultipartConfig(), load priorities by setLoadOnStartup(), init params by setInitParameter()
}
