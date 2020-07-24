package tk.d4097.httpfs.service.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import tk.d4097.httpfs.service.FileExt;
import tk.d4097.httpfs.service.FileServiceException;
import java.io.IOException;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.web.multipart.MultipartFile;

public class FileSaveGridFs {
  private final GridFsTemplate gridFsTemplate;

  public FileSaveGridFs(GridFsTemplate gridFsTemplate) {
    this.gridFsTemplate = gridFsTemplate;
  }

  public ObjectId save(String description, MultipartFile file) {
    ObjectId retVal = null;
    try {
      DBObject metaData = new BasicDBObject();
      metaData.put("description", description);
      metaData.put("extension", new FileExt(file.getOriginalFilename()).get());
      retVal =
          gridFsTemplate.store(
              file.getInputStream(), file.getOriginalFilename(), file.getContentType(), metaData);
    } catch (IOException e) {
      throw new FileServiceException("Failed to store file " + file.getOriginalFilename(), e);
    }
    return retVal;
  }
}
