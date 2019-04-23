package unifesp.seg;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
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
	  
	  // for comments: JavadocComment; LineComment 
	  
	  
	  if (node instanceof MethodDeclaration) {
		  MethodDeclaration m = (MethodDeclaration) node;
		  try {
			FileWriters.methodIdentifiersFW.append(m.getNameAsString());
			FileWriters.methodIdentifiersFW.append("\n");
		  } catch (IOException e) {
			System.out.println("Failed to append method name to method identifiers file.");
			e.printStackTrace();
		  }
		  
		  List<VariableDeclarationExpr> nl = m.getNodesByType(VariableDeclarationExpr.class);
		  Iterator<VariableDeclarationExpr> i = nl.iterator();
			while(i.hasNext()) {
				VariableDeclarationExpr vde = i.next();
				System.out.println(vde.getVariable(0).getNameAsString());
			}
		  
	  }
	  if (node instanceof FieldDeclaration) {
		  FieldDeclaration f = (FieldDeclaration) node;
		  try {
			NodeList<VariableDeclarator> nl = f.getVariables();
			Iterator<VariableDeclarator> i = nl.iterator();
			while(i.hasNext()) {
			  VariableDeclarator vd = i.next();
			  FileWriters.fieldIdentifiersFW.append(vd.getNameAsString());
			  FileWriters.fieldIdentifiersFW.append("\n");
			}
		  } catch (IOException e) {
			System.out.println("Failed to append field name to variable identifiers file.");
			e.printStackTrace();
		  }
	  }
	  if(node instanceof VariableDeclarator) {
		  VariableDeclarator v = (VariableDeclarator) node;
		  try {
			  FileWriters.varIdentifiersFW.append(v.getNameAsString());
			  FileWriters.varIdentifiersFW.append("\n");
		  } catch (IOException e) {
			System.out.println("Failed to append variable name to variable identifiers file.");
			e.printStackTrace();
		  }
	  }
  }

}
