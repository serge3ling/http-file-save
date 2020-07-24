package tk.d4097.httpfs.service.mongo;

import tk.d4097.httpfs.model.FileModel;
import tk.d4097.httpfs.repository.FileRepository;
import java.util.Optional;
import org.bson.types.ObjectId;

public class FileFindBson {
  private final FileRepository fileRepository;

  public FileFindBson(FileRepository fileRepository) {
    this.fileRepository = fileRepository;
  }

  public Optional<FileModel> find(ObjectId id) {
    return fileRepository.findById(id);
  }
}
