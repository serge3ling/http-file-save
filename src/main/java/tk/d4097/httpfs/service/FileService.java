package tk.d4097.httpfs.service;

import tk.d4097.httpfs.model.FileModel;

import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
  ObjectId insert(String description, MultipartFile file);

  ObjectId save(ObjectId id, String description, MultipartFile file);

  List<FileModel> findAll();

  List<FileModel> findByFieldsQuery(String name, String contentType, String extension);

  Optional<FileModel> findById(ObjectId id);

  void deleteById(ObjectId id);

  FileDownloadWrap getDownloadWrap(ObjectId id);
}
