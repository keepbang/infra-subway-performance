package nextstep.subway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import support.version.SubwayVersion;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    public static final String PREFIX_STATIC_RESOURCES = "/resources";

    @Autowired
    private SubwayVersion subwayVersion;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(PREFIX_STATIC_RESOURCES + "/" + subwayVersion.getVersion() +  "/css/**")
            .addResourceLocations("classpath:/static/css/")
            .setCachePeriod(60 * 60 * 24 * 365);

        registry.addResourceHandler(PREFIX_STATIC_RESOURCES + "/" + subwayVersion.getVersion() +  "/js/**")
            .addResourceLocations("classpath:/static/js/")
            .setCacheControl(CacheControl.noCache().cachePrivate());
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new ShallowEtagHeaderFilter());
        registration.addUrlPatterns(PREFIX_STATIC_RESOURCES + "/*");

        return registration;
    }
}