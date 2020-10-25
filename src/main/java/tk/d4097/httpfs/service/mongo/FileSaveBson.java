package tk.d4097.httpfs.service.mongo;

import tk.d4097.httpfs.service.FileServiceException;
import java.io.IOException;
import org.bson.types.ObjectId;
import org.springframework.web.multipart.MultipartFile;

public class FileSaveBson {
  private final FileRepository fileRepository;

  public FileSaveBson(FileRepository fileRepository) {
    this.fileRepository = fileRepository;
  }

  public String save(ObjectId id, String description, MultipartFile file) {
    String retVal = null;
    try {
      MongoFileModel fileModel = new MongoFileModel(id.toHexString(), description, file);
      fileRepository.save(fileModel);
      retVal = fileModel.getId();
    } catch (IOException e) {
      throw new FileServiceException("Failed to store file " + file.getOriginalFilename(), e);
    }
    return retVal;
  }
}
