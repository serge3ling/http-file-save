package tk.d4097.httpfs.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "http-file-save")
public class Properties {
  long gridFsSizeStart;

  public long getGridFsSizeStart() {
    return gridFsSizeStart;
  }

  public void setGridFsSizeStart(long gridFsSizeStart) {
    this.gridFsSizeStart = gridFsSizeStart;
  }
}
