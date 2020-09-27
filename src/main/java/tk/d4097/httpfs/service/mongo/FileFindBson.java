package tk.d4097.httpfs.service.mongo;

import java.util.Optional;
import org.bson.types.ObjectId;

public class FileFindBson {
  private final FileRepository fileRepository;

  public FileFindBson(FileRepository fileRepository) {
    this.fileRepository = fileRepository;
  }

  public Optional<MongoFileModel> find(ObjectId id) {
    return fileRepository.findById(id);
  }
}
