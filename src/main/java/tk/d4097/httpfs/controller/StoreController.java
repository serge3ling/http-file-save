package tk.d4097.httpfs.controller;

import tk.d4097.httpfs.model.FileModel;
import tk.d4097.httpfs.service.FileService;
import tk.d4097.httpfs.service.FileServiceException;
import tk.d4097.httpfs.service.FileDownloadWrap;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class StoreController {
  private final FileService fileService;

  @Autowired
  public StoreController(FileService fileService) {
    this.fileService = fileService;
  }

  @GetMapping(path = "/")
  public String htFindAllFiles(Model model) {
    model.addAttribute("docs", fileService.findAll());
    return "uploadForm";
  }

  @PostMapping(path = "/")
  public String handleFileUpload(
      @RequestParam("file") MultipartFile file,
      @RequestParam("description") String description,
      RedirectAttributes redirectAttributes) {
    fileService.insert(description, file);
    redirectAttributes.addFlashAttribute(
        "message", "You successfully uploaded file \"" + file.getOriginalFilename() + "\".");

    return "redirect:/";
  }

  @GetMapping(path = "/files/download/{id}",
      produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
  @ResponseBody
  public ResponseEntity<Resource> downloadFileById(@PathVariable ObjectId id) {
    FileDownloadWrap wrap = fileService.getDownloadWrap(id);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + wrap.getName() + "\"")
        .body(wrap.getResource());
  }

  @GetMapping(path = "/files/{id}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<FileModel> findFileById(@PathVariable ObjectId id) {
    return ResponseEntity.ok().body(fileService.findById(id).orElse(null));
  }

  @GetMapping(path = "/files",
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<List<FileModel>> findFilesByQuery(
      @RequestParam(defaultValue = "") String name,
      @RequestParam(defaultValue = "") String contentType,
      @RequestParam(defaultValue = "") String extension) {
    return ResponseEntity.ok().body(fileService.findByFieldsQuery(name, contentType, extension));
  }

  @PostMapping(path = "/files",
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<ObjectId> insertFile(
      @RequestParam("description") String description,
      @RequestParam("file") MultipartFile file) {
    return ResponseEntity.ok().body(fileService.insert(description, file));
  }

  @PutMapping(path = "/files/{id}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<ObjectId> saveFile(
      @PathVariable ObjectId id,
      @RequestParam("description") String description,
      @RequestParam("file") MultipartFile file) {
    return ResponseEntity.ok().body(fileService.save(id, description, file));
  }

  @DeleteMapping(path = "/files/{id}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<String> deleteFile(@PathVariable ObjectId id) {
    fileService.deleteById(id);
    return ResponseEntity.ok().body("{\"ok\":\"OK.\"}");
  }

  @PostMapping(path = "/files/remove")
  public String deleteFileFromPost(@RequestParam("id") ObjectId id, RedirectAttributes redirectAttributes) {
    fileService.deleteById(id);
    redirectAttributes.addFlashAttribute(
        "message", "You successfully removed file by id \"" + id + "\".");

    return "redirect:/";
  }

  @ExceptionHandler(FileServiceException.class)
  public ResponseEntity<Void> handleStorageException(FileServiceException exc) {
    return ResponseEntity.notFound().build();
  }
}
