package unifesp.seg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.TreeVisitor;

public class FolderInputProcessor implements IInputProcessor{
    
    @Override
    public void processInput(String filename) throws FileNotFoundException {
        System.out.println("processing directory: " + filename);
        List<File> files = DirExplorer.finder(filename);
        for (File f : files) {
            try {
                this.extract(f);
            } catch (FileNotFoundException e) {
                System.out.println("WARN: File not found, skipping file: " + f.getAbsolutePath());
                try {
                    FileWriters.errorsFileWriter.write(f.getAbsolutePath() + System.lineSeparator());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } catch (ParseProblemException e) {
                System.out.println("WARN: parse problem exception, skippig file: " + f.getAbsolutePath());
                try {
                    FileWriters.errorsFileWriter.write(f.getAbsolutePath() + System.lineSeparator());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            } catch (Exception e) {
                System.out.println("WARN: unknown error, skippig file: " + f.getAbsolutePath());
                try {
                    FileWriters.errorsFileWriter.write(f.getAbsolutePath() + System.lineSeparator());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
        }
    }
    
    private void extract(final File file) throws FileNotFoundException {
    	System.out.println("Extracting from " + file.getName());
        JavaExtractor.FILE_COUNTER++;
        CompilationUnit cu = StaticJavaParser.parse(file);
        TreeVisitor visitor = new CustomVisitor(file);
        visitor.visitPreOrder(cu);
    }

}
