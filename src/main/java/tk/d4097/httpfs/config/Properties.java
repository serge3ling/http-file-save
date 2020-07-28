package tk.d4097.httpfs.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "http-file-save")
public class Properties {
  long gridFsSizeStart;
  List<String> allowedOrigins;

  public long getGridFsSizeStart() {
    return gridFsSizeStart;
  }

  public void setGridFsSizeStart(long gridFsSizeStart) {
    this.gridFsSizeStart = gridFsSizeStart;
  }

  public List<String> getAllowedOrigins() {
    return allowedOrigins;
  }

  public void setAllowedOrigins(List<String> allowedOrigins) {
    this.allowedOrigins = allowedOrigins;
  }
}
