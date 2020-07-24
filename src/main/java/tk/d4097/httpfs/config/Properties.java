package tk.d4097.httpfs.config;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "http-file-save")
public class Properties {
  List<String> allowedOrigins;
  long gridFsSizeStart;

  public List<String> getAllowedOrigins() {
    return allowedOrigins;
  }

  public void setAllowedOrigins(List<String> allowedOrigins) {
    this.allowedOrigins = allowedOrigins;
  }

  public long getGridFsSizeStart() {
    return gridFsSizeStart;
  }

  public void setGridFsSizeStart(long gridFsSizeStart) {
    this.gridFsSizeStart = gridFsSizeStart;
  }
}
