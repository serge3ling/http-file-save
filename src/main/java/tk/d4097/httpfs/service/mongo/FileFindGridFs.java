package tk.d4097.httpfs.service.mongo;

import com.mongodb.client.gridfs.model.GridFSFile;
import tk.d4097.httpfs.model.FileModel;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

public class FileFindGridFs {
  private final GridFsTemplate gridFsTemplate;

  public FileFindGridFs(GridFsTemplate gridFsTemplate) {
    this.gridFsTemplate = gridFsTemplate;
  }

  public Optional<FileModel> find(ObjectId id) {
    GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
    return Optional.ofNullable(new FileModel(id, file));
  }
}
