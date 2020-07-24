package tk.d4097.httpfs.service.mongo;

import tk.d4097.httpfs.repository.FileRepository;
import org.bson.types.ObjectId;

public class FileDeleteBson {
  private final FileRepository fileRepository;

  public FileDeleteBson(FileRepository fileRepository) {
    this.fileRepository = fileRepository;
  }

  public void delete(ObjectId id) {
    fileRepository.deleteById(id);
  }
}
