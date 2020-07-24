package tk.d4097.httpfs.service;

public class FileExt {
  private final String filename;

  public FileExt(String filename) {
    this.filename = filename;
  }

  public String get() {
    String outcome = "";
    boolean goOn = (filename != null);

    if (goOn) {
      goOn = !filename.isEmpty();
    }

    int slashX = -1;
    if (goOn) {
      int linX = filename.lastIndexOf('\\');
      int winX = filename.lastIndexOf('/');
      slashX = Math.max(linX, winX);
      goOn = (slashX < (filename.length() - 1));
    }

    String name = "";
    int dotX = -1;
    if (goOn) {
      name = filename.substring(slashX + 1);
      dotX = name.lastIndexOf('.');
      goOn = (dotX >= 0) && (dotX < (name.length() - 1));
    }

    if (goOn) {
      outcome = name.substring(dotX + 1);
    }

    return outcome;
  }
}
