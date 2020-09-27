package tk.d4097.httpfs.service.mongo;

import com.mongodb.client.gridfs.model.GridFSFile;
import tk.d4097.httpfs.service.FileDownloadWrap;
import tk.d4097.httpfs.service.FileService;
import tk.d4097.httpfs.service.FileServiceException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MongoFileService implements FileService {
  @NonNull private final FileRepository fileRepository;

  @NonNull private final GridFsTemplate gridFsTemplate;

  @NonNull private final GridFsOperations gridFsOperations;

  @NonNull private final tk.d4097.httpfs.config.Properties properties;

  private final FileFindBson fileFindBson;
  private final FileFindGridFs fileFindGridFs;
  private final FileDeleteBson fileDeleteBson;
  private final FileDeleteGridFs fileDeleteGridFs;
  private final FileSaveBson fileSaveBson;
  private final FileSaveGridFs fileSaveGridFs;

  @Autowired
  public MongoFileService(
      FileRepository fileRepository,
      GridFsTemplate gridFsTemplate,
      GridFsOperations gridFsOperations,
      tk.d4097.httpfs.config.Properties properties) {
    this.fileRepository = fileRepository;
    this.gridFsTemplate = gridFsTemplate;
    this.gridFsOperations = gridFsOperations;
    this.properties = properties;
    this.fileFindBson = new FileFindBson(fileRepository);
    this.fileFindGridFs = new FileFindGridFs(gridFsTemplate);
    this.fileDeleteBson = new FileDeleteBson(fileRepository);
    this.fileDeleteGridFs = new FileDeleteGridFs(gridFsTemplate);
    this.fileSaveBson = new FileSaveBson(fileRepository);
    this.fileSaveGridFs = new FileSaveGridFs(gridFsTemplate);
  }

  @Override
  public String insert(String description, MultipartFile file) {
    String retVal = null;
    try {
      if (file.getSize() < properties.getGridFsSizeStart()) {
        MongoFileModel fileModel = new MongoFileModel(new ObjectId(), description, file);
        fileRepository.insert(fileModel);
        retVal = fileModel.getId().toString();
      } else {
        retVal = fileSaveGridFs.save(description, file).toString();
      }
    } catch (IOException e) {
      throw new FileServiceException("Failed to store file " + file.getOriginalFilename(), e);
    }
    return retVal;
  }

  @Override
  public String save(String id, String description, MultipartFile file) {
    ObjectId argObjectId  = new ObjectId(id);
    String retVal = null;
    if (file.getSize() < properties.getGridFsSizeStart()) {
      fileDeleteGridFs.delete(argObjectId);
      retVal = fileSaveBson.save(argObjectId, description, file).toString();
    } else {
      fileDeleteBson.delete(argObjectId);
      retVal = fileSaveGridFs.save(description, file).toString(); // id != retVal
    }
    return retVal;
  }

  private List<MongoFileModel> findInGridFs(Query query) {
    List<GridFSFile> files = new ArrayList<>();
    gridFsTemplate.find(query).into(files);
    return files.stream()
        .map(item -> new MongoFileModel(item.getObjectId(), item))
        .collect(Collectors.toList());
  }

  @Override
  public List<MongoFileModel> findAll() {
    List<MongoFileModel> files = fileRepository.findAll();
    files.addAll(findInGridFs(new Query()));
    return files;
  }

  @Override
  public List<MongoFileModel> findByFieldsQuery(String name, String contentType, String extension) {
    Query query = new Query();
    query.addCriteria(Criteria.where("filename").regex(name));
    query.addCriteria(Criteria.where("metadata._contentType").regex(contentType));
    query.addCriteria(Criteria.where("metadata.extension").regex(extension));

    List<MongoFileModel> files =
        fileRepository.findByNameAndContentTypeAndExtensionQuery(name, contentType, extension);
    files.addAll(findInGridFs(query));
    return files;
  }

  @Override
  public Optional findById(String id) {
    ObjectId objectId = new ObjectId(id);
    Optional<MongoFileModel> opt = fileFindBson.find(objectId);

    if (!opt.isPresent()) {
      opt = fileFindGridFs.find(objectId);
    }

    return opt;
  }

  @Override
  public void deleteById(String id) {
    fileDeleteBson.delete(new ObjectId(id));
    fileDeleteGridFs.delete(new ObjectId(id));
  }

  @Override
  public FileDownloadWrap getDownloadWrap(String id) {
    GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
    Optional<MongoFileModel> opt = findById(id);
    String name = "unknown.file";
    Resource resource = new ByteArrayResource(new byte[0]);

    // file is null if not on GridFS
    if (file == null) {
      if (opt.isPresent()) {
        resource = new ByteArrayResource(opt.get().getContent().getData());
        name = opt.get().getName();
      }
    } else {
      try {
        resource = new InputStreamResource(gridFsOperations.getResource(file).getInputStream());
        name = opt.isPresent() ? opt.get().getName() : name;
      } catch (IOException e) {
        throw new FileServiceException("Could not get stream.", e);
      }
    }

    return new FileDownloadWrap(name, resource);
  }
}
