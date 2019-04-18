package unifesp.seg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;


public class JavaExtractor {
	
    public String outputDirPath;
    public String identifiersFileName;
    public String errorsFileName;
    public static String prefix;
    public static String fileIdPrefix;
    public static long FILE_COUNTER;
    public static long METHOD_COUNTER;
    private IInputProcessor inputProcessor;

    public JavaExtractor(String inputFilePath) {
        this.identifiersFileName = "identifiers.file";
        this.errorsFileName = "errors.file";
        JavaExtractor.prefix = this.getBaseName(inputFilePath);
        JavaExtractor.fileIdPrefix = "1";
        this.outputDirPath = JavaExtractor.prefix + "_extraction_output";
        File outDir = new File(this.outputDirPath);
        if (!outDir.exists()) {
            outDir.mkdirs();
        }
    }

    private String getBaseName(String path) {
        File inputFile = new File(path);
        String fileName = inputFile.getName();
        int pos = fileName.lastIndexOf(".");
        if (pos > 0) {
            fileName = fileName.substring(0, pos);
        }
        return fileName;
    }

    private void initializeWriters() throws IOException {
        FileWriters.identifiersFileWriter = Util.openFile(this.outputDirPath + File.separator + this.identifiersFileName, false);
        FileWriters.errorsFileWriter = Util.openFile(this.outputDirPath + File.pathSeparator + this.errorsFileName, false);
    }

    private void closeWriters() {
        Util.closeOutputFile(FileWriters.identifiersFileWriter);
    }

    private void handleInput(String inputMode, String filename) {
        BufferedReader br;
        try {
            this.initializeWriters();
            this.inputProcessor = new FolderInputProcessor();

            br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"));
            String line = null;
            while ((line = br.readLine()) != null && line.trim().length() > 0) {
                line = line.trim();
                this.inputProcessor.processInput(line);
            }
            try {
                br.close();
            } catch (IOException e) {
                System.out.println("WARN, couldn't close inputfile: " + filename);
            }

            this.closeWriters();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


	public static void main(String[] args) throws FileNotFoundException {
        if (args.length > 0) {
            //System.out.println(args);
            String filename = args[0];
            String inputMode = args[1];
            JavaExtractor javaExtractor = new JavaExtractor(filename);
            System.out.println("File name: " + filename);
            javaExtractor.handleInput(inputMode, filename);
        } else {
            System.out.println("FATAL: please specify the file with list of directories!");
        }
        System.out.println("done!");
    }
}
