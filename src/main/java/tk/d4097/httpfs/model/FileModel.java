package tk.d4097.httpfs.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.mongodb.client.gridfs.model.GridFSFile;
import tk.d4097.httpfs.service.FileExt;
import java.io.IOException;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

@Document(collection = "file")
public class FileModel {
  @JsonSerialize(using = ToStringSerializer.class)
  @Id
  private ObjectId id;

  private String name;
  private String contentType;
  private String extension;
  private long size;
  private String description;
  private Binary content;

  public FileModel() {
  }

  public FileModel(ObjectId id, String description, MultipartFile file) throws IOException {
    this(
        id,
        file.getOriginalFilename(),
        file.getContentType(),
        new FileExt(file.getOriginalFilename()).get(),
        file.getSize(),
        description,
        new Binary(BsonBinarySubType.BINARY, file.getBytes()));
  }

  public FileModel(ObjectId id, String name, String contentType, String extension,
                   long size, String description, Binary content) {
    this.id = id;
    this.name = name;
    this.contentType = contentType;
    this.extension = extension;
    this.size = size;
    this.description = description;
    this.content = content;
  }

  private static String fetchMetadataString(GridFSFile file, String key, String valueDefault) {
    String string = valueDefault;
    if (file.getMetadata() != null) {
      string = file.getMetadata().getString(key);
    }
    return string;
  }

  public FileModel(ObjectId id, GridFSFile file) {
    this(
        id,
        file.getFilename(),
        fetchMetadataString(file, "_contentType", "application/octet-stream"),
        new FileExt(file.getFilename()).get(),
        file.getLength(),
        fetchMetadataString(file, "description", file.getFilename()),
        null);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override
  public String toString() {
    return "[id: " + id + ", name: \"" + name + "\", description: \"" + description + "\"]";
  }

  public ObjectId getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getContentType() {
    return contentType;
  }

  public String getExtension() {
    return extension;
  }

  public long getSize() {
    return size;
  }

  public String getDescription() {
    return description;
  }

  public Binary getContent() {
    return content;
  }
}
