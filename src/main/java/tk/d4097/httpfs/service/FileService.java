package tk.d4097.httpfs.service;

import java.util.List;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
  String insert(String description, MultipartFile file);

  String save(String id, String description, MultipartFile file);

  List findAll();

  List findByFieldsQuery(String name, String contentType, String extension);

  Optional findById(String id);

  void deleteById(String id);

  FileDownloadWrap getDownloadWrap(String id);
}
