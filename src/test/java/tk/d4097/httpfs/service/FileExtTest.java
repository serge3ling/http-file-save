package tk.d4097.httpfs.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class FileExtTest {
  @ParameterizedTest
  @CsvSource({
    "file.txt, txt",
    ".gitignore, gitignore",
    "C:\\pagefile.sys, sys",
    "/vmlinuz.old, old",
    "C:\\Program Files\\file.txt, txt",
    "/home/user/file.txt, txt"
  })
  public void get__AllAfterDot(String filename, String expected) {
    String ext = new FileExt(filename).get();
    Assertions.assertEquals(expected, ext);
  }

  @ParameterizedTest
  @ValueSource(strings = {"", "/vmlinuz", "C:\\file.", "file", "C:\\Users\\temp.2\\file"})
  public void get__EmptyExt(String filename) {
    String ext = new FileExt(filename).get();
    Assertions.assertTrue(ext.isEmpty());
  }
}
