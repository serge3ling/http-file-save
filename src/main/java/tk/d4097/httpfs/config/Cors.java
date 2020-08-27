package tk.d4097.httpfs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Cors implements WebMvcConfigurer {
  @NonNull private final Properties properties;

  public Cors(Properties properties) {
    this.properties = properties;
  }

  private String[] originsForConfigurer() {
    return properties.getAllowedOrigins().toArray(new String[0]);
  }

  @NonNull
  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        //String[] origins = properties.getAllowedOrigins().toArray(new String[] {});
        registry.addMapping("/**").allowedOrigins(originsForConfigurer());
      }
    };
  }
}
