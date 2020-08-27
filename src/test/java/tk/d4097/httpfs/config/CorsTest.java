package tk.d4097.httpfs.config;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CorsTest {
  @Test
  void corsConfigurer__OriginsPassed() {
    Properties properties = new Properties();
    List<String> origins = new ArrayList<>();
    String originVal = "origin";
    origins.add(originVal);
    origins.add("another");
    properties.setAllowedOrigins(origins);
    Cors cors = new Cors(properties);
    WebMvcConfigurer configurer = cors.corsConfigurer();

    CorsRegistry registry = Mockito.mock(CorsRegistry.class);
    CorsRegistration registration = Mockito.mock(CorsRegistration.class);
    ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<String[]> methodsCaptor = ArgumentCaptor.forClass(String[].class);
    Mockito.when(registry.addMapping(Mockito.anyString())).thenReturn(registration);
    Mockito.when(registration.allowedOrigins(Mockito.any())).thenReturn(registration);

    configurer.addCorsMappings(registry);

    Mockito.verify(registry, Mockito.times(1)).addMapping(stringCaptor.capture());
    Mockito.verify(registration, Mockito.times(1)).allowedOrigins(methodsCaptor.capture());

    assertEquals("/**", stringCaptor.getAllValues().get(0));
    assertEquals(originVal, methodsCaptor.getAllValues().get(0)); // Mockito's bug, values are strings, not arrays.
  }
}