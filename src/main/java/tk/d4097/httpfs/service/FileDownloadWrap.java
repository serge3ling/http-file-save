package tk.d4097.httpfs.service;

import lombok.Getter;
import org.springframework.core.io.Resource;

@Getter
public class FileDownloadWrap {
  private final String name;
  private final Resource resource;

  public FileDownloadWrap(String name, Resource resource) {
    this.name = name;
    this.resource = resource;
  }
}
