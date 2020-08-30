package tk.d4097.httpfs.service;

import org.springframework.core.io.Resource;

public class FileDownloadWrap {
  private final String name;
  private final Resource resource;

  public FileDownloadWrap(String name, Resource resource) {
    this.name = name;
    this.resource = resource;
  }

  public String getName() {
    return name;
  }

  public Resource getResource() {
    return resource;
  }
}
