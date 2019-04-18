package unifesp.seg;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.ast.visitor.TreeVisitor;

public class CustomVisitor extends TreeVisitor {
  File file;
  String fileContent;
  Path path;
  SourceCodeLineCounter sourceCodeLineCounter;
  boolean APPLY_SLOC_FILTER = false;
  int MIN_SLOC_ALLOWED = 0;

  public CustomVisitor(File file) {
    super();
    this.file = file;
    this.path = file.toPath();
    sourceCodeLineCounter = new SourceCodeLineCounter();
  }

  public CustomVisitor(String content, Path path) {
    super();
    this.fileContent = content;
    this.path = path;
    sourceCodeLineCounter = new SourceCodeLineCounter();
  }
  
  @Override
	public void process(Node node) {
       // extract identifiers and save to file (?)
	}

}
