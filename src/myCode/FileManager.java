package myCode;

import java.lang.IllegalArgumentException;
import java.lang.IllegalStateException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;
import java.nio.file.Path;
import professorCode.AbstractFileMonitor;
import professorCode.FileTextReader;
import professorCode.FileTextWriter;

public class FileManager extends AbstractFileMonitor implements FileTextReader, FileTextWriter {

    String filePath = "";

    public FileManager(String filePath) {
        super(filePath);
        this.filePath = filePath;
    }

    public void setFilePath(String path) throws IllegalArgumentException {
        if ((path == null) || (path.equals(""))) {
            throw new IllegalArgumentException("File Path can not be null or blank.");
        }

        this.filePath = path;
    };

    public String getFilePath() throws IllegalStateException {
        if ((this.filePath == null) || (this.filePath.equals(""))) {
            throw new IllegalStateException("File Path has not been set");
        } else {
            return this.filePath;
        }
    };

    public void setNullPath() {
        this.filePath = "";
    }

    public String readText(String path) throws IOException {
        Path filePath = Paths.get(path);
        return Files.readString(filePath);
    }

    public Set<String> getAllLines(String path) throws IOException {
        Set<String> result = new HashSet<String>();
        Path filePath = Paths.get(path);
        Stream<String> stream = Files.lines(filePath, StandardCharsets.UTF_8);
        stream.forEach(s -> result.add(s.trim()));
        stream.close();
        return result;
    }

    public String getLastLine(String path) throws IOException {
        ArrayList<String> result = new ArrayList<String>();
        Path filePath = Paths.get(path);
        Stream<String> stream = Files.lines(filePath, StandardCharsets.UTF_8);
        stream.forEach(s -> result.add(s));
        int size = result.size();
        stream.close();
        return result.get(size - 1);
    }

    public void writeToFile(String string, String path) throws IOException, IllegalArgumentException {
        if ((string.isBlank() || string.isEmpty())) {
            throw new IllegalArgumentException("Can not write an empty string");
        }
        String outString = string + "\n";
        FileOutputStream outputStream = new FileOutputStream(path, true);
        byte[] strToBytes = outString.getBytes();
        outputStream.write(strToBytes);
        outputStream.close();
    }
}