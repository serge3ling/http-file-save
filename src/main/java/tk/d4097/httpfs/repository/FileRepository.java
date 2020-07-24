package tk.d4097.httpfs.repository;

import tk.d4097.httpfs.model.FileModel;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.lang.NonNull;

public interface FileRepository extends MongoRepository<FileModel, ObjectId> {
  @NonNull
  @Query(
      value =
          "{"
              + " 'name':        {$regex: ?0, $options: 'i'},"
              + " 'contentType': {$regex: ?1, $options: 'i'},"
              + " 'extension':   {$regex: ?2, $options: 'i'},"
              + "}",
      fields = "{content: 0}")
  List<FileModel> findByNameAndContentTypeAndExtensionQuery(
      String name, String contentType, String extension);
}
