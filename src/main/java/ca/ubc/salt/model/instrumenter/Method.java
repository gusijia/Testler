package ca.ubc.salt.model.instrumenter;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.VariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;

import ca.ubc.salt.model.state.ReadVariableVisitor;

public class Method
{
    MethodDeclaration methodDec = null;
    String className;

    public Method(MethodDeclaration methodDec, String className)
    {
	this.methodDec = methodDec;
	this.className = className;
    }

    public void instrumentTestMethod(ASTRewrite rewriter, Document document, List<String> loadedClassVars, String fileName,
	    boolean start)
	    throws JavaModelException, IllegalArgumentException, MalformedTreeException, BadLocationException
    {
	
	addTimeOut(rewriter);
	
	int randomNumber = (int) (Math.random() * (Integer.MAX_VALUE - 1));
	Block block = methodDec.getBody();
	if (block == null)
	    return;
	ListRewrite listRewrite = rewriter.getListRewrite(block, Block.STATEMENTS_PROPERTY);

	Block header = (Block) TestClassInstrumenter.generateInstrumentationHeader(randomNumber, fileName,
		methodDec.getName().toString());
	List<Statement> stmts = header.statements();
	if (start)
	    for (int i = stmts.size() - 1; i >= 0; i--)
		listRewrite.insertFirst(stmts.get(i), null);
	else
	    for (int i = 0; i < stmts.size(); i++)
		listRewrite.insertLast(stmts.get(i), null);

	// Block footer =
	// (Block)TestClassInstrumenter.generateFooterBlock(randomNumber);
	// stmts = footer.statements();
	// for (int i = stmts.size() - 1; i >= 0; i--)
	// listRewrite.insertLast(stmts.get(i), null);
	// listRewrite.insertLast(footer, null);

	InstrumenterVisitor visitor = new InstrumenterVisitor(rewriter, randomNumber, methodDec.getName().toString());
	this.methodDec.accept(visitor);

	// apply the text edits to the compilation unit

	// edits.apply(document);
	//
	// // this is the code for adding statements
	// System.out.println(document.get());

    }

    public void instrumentProductionMethod(ASTRewrite rewriter, Document document, List<String> loadedClassVars,
	    boolean start)
	    throws JavaModelException, IllegalArgumentException, MalformedTreeException, BadLocationException
    {
	Block block = methodDec.getBody();
	if (block == null)
	    return;
	ListRewrite listRewrite = rewriter.getListRewrite(block, Block.STATEMENTS_PROPERTY);

	List params = this.methodDec.parameters();

	List<String> paramStrs = new LinkedList<String>();
	if (!Modifier.isStatic(methodDec.getModifiers()) && !methodDec.isConstructor())
	    paramStrs.add("this");
	for (Object paramObj : params)
	{
	    if (paramObj instanceof VariableDeclaration)
	    {
		VariableDeclaration param = (VariableDeclaration) paramObj;
		String name = param.getName().toString();
		paramStrs.add(name);
	    }
	}
	AST ast = methodDec.getAST();

	Block header = (Block) ASTNode.copySubtree(ast,
		ProductionClassInstrumenter.generateInstrumentationHeader(className + "." + methodDec.getName().toString(), paramStrs));
	List<Statement> stmts = header.statements();


	TryStatement trystmt = ast.newTryStatement();
	// Block newBlock = ast.newBlock();
	Block blk = (Block) ASTNode.copySubtree(ast, block);
	
	trystmt.setBody(blk);
	Block footer = (Block) ASTNode.copySubtree(ast, ProductionClassInstrumenter.generateFooterBlock());
	trystmt.setFinally(footer);

	removeAllFromBlock(block, listRewrite);
	listRewrite.insertFirst(trystmt, null);
	if (!start && block.statements().size() > 0)
	{
	    ASTNode firstStmt = (ASTNode) blk.statements().remove(0);
	    listRewrite.insertFirst(firstStmt, null);
	}

	ListRewrite tryList = rewriter.getListRewrite(trystmt.getBody(), Block.STATEMENTS_PROPERTY);
	
	for (int i = stmts.size() - 1; i >= 0; i--)
	    tryList.insertFirst(stmts.get(i), null);
    }

    private void removeAllFromBlock(Block block, ListRewrite listRewrite)
    {
	for (Object obj : block.statements())
	    listRewrite.remove((ASTNode) obj, null);
    }

    public void populateReadVars(Document document, List<String> loadedClassVars, Map<String, Set<SimpleName>> readVars)
    {
	ReadVariableVisitor visitor = new ReadVariableVisitor(className + "." + methodDec.getName().toString());
	visitor.setReadVars(readVars);
	this.methodDec.accept(visitor);
	// System.out.println(visitor.getReadVars());
    }

    public MethodDeclaration getMethodDec()
    {
	return methodDec;
    }

    public void setMethodDec(MethodDeclaration methodDec)
    {
	this.methodDec = methodDec;
    }
    
    public void addTimeOut(ASTRewrite rewrite)
    {

//	if (method.methodDec.getName().toString().toLowerCase().contains("test"))
//	    return true;
	
	ListRewrite listRewrite = rewrite.getListRewrite(this.methodDec, MethodDeclaration.MODIFIERS2_PROPERTY);
	List<NormalAnnotation> modifs = this.methodDec.modifiers();
	AST ast = this.methodDec.getAST();
	for (Object obj : modifs)
	{
	    if (obj instanceof MarkerAnnotation)
	    {
		MarkerAnnotation ma = (MarkerAnnotation) obj;
		if (ma.getTypeName().getFullyQualifiedName().contains("Test"))
		{
		    listRewrite.remove(ma, null);
//		    ma = (MarkerAnnotation)ASTNode.copySubtree(method.methodDec.getAST(), ma);
		    NormalAnnotation na = ast.newNormalAnnotation();
		    Name name = ast.newName(ma.getTypeName().getFullyQualifiedName());
		    na.setTypeName(name);
		    MemberValuePair pair = ast.newMemberValuePair();
		    pair.setName(ast.newSimpleName("timeout"));
		    pair.setValue(ast.newNumberLiteral("600000"));
		    na.values().add(pair);
		    listRewrite.insertFirst(na, null);
		}
	    }
	}

    }
    
    

}
