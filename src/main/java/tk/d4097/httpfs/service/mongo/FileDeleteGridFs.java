package tk.d4097.httpfs.service.mongo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

public class FileDeleteGridFs {
  GridFsTemplate gridFsTemplate;

  public FileDeleteGridFs(GridFsTemplate gridFsTemplate) {
    this.gridFsTemplate = gridFsTemplate;
  }

  public void delete(ObjectId id) {
    gridFsTemplate.delete(new Query(Criteria.where("_id").is(id)));
  }
}
