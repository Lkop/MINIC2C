package org.lkop.MINIC2C;

import org.lkop.MINIC2C.utils.GIFCreator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Stack;

public class CFilePrinterVisitor extends CodeVisitor<Integer>{

    private String filename;
    private PrintWriter dot_writer;
    private int serial_counter = 0;
    private Stack<Integer> pos = new Stack<>();

    public CFilePrinterVisitor(String filename) throws FileNotFoundException {
        this.filename = filename;
        dot_writer = new PrintWriter("output/"+this.filename+".dot");
    }

    private void extractSubgraphs(CodeContainer node, int context, String[] context_names) {
        dot_writer.println("\tsubgraph cluster" + serial_counter++ + "{");
        dot_writer.println("\t\tnode [style=filled,color=white];");
        dot_writer.println("\t\tstyle=filled;");
        dot_writer.println("\t\tcolor=lightgrey;");

        dot_writer.print("\t\t");
        for (CodeContainer elem : node.getChildrenInContext(context, pos.peek())) {
            int i = pos.peek();
            pos.pop();
            pos.push(++i);
            dot_writer.print(elem.getName()+"_"+elem.getSerialId()+"; ");
        }

        dot_writer.println("\n\t\tlabel=" + context_names[context] + ";");
        dot_writer.println("\t}");
    }

    @Override
    public Integer visitCodeFile(CodeFile node) {
        System.out.println("CodeVisitableElement -> CodeFile");

        dot_writer.println("digraph G {");

        pos.push(0);
        extractSubgraphs(node, CodeFile.CC_FILE_PREPROCESSOR, CodeFile.context_names);
        pos.pop();

        pos.push(0);
        extractSubgraphs(node, CodeFile.CC_FILE_GLOBALS, CodeFile.context_names);
        pos.pop();

        pos.push(0);
        extractSubgraphs(node, CodeFile.CC_FILE_FUNCTIONSTANDARD, CodeFile.context_names);
        pos.pop();

        pos.push(0);
        extractSubgraphs(node, CodeFile.CC_FILE_FUNCTIONDEFINITIONS, CodeFile.context_names);
        pos.pop();

        super.visitCodeFile(node);

        dot_writer.println("}");
        dot_writer.close();

        try {
            new GIFCreator(filename);
        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public Integer visitCodeFunctionDefinition(CodeFunctionDefinition node) {
        System.out.println("CodeVisitableElement -> CodeFunctionDefinition");

        pos.push(0);
        extractSubgraphs(node, CodeFunctionDefinition.CC_FUNCTIONDEFINITION_HEADER, CodeFunctionDefinition.context_names);
        pos.pop();

        pos.push(0);
        extractSubgraphs(node, CodeFunctionDefinition.CC_FUNCTIONDEFINITION_BODY, CodeFunctionDefinition.context_names);
        pos.pop();

        dot_writer.println("\""+node.getParent(0).getGraphvizName()+"\"->\""+node.getGraphvizName()+"\";");

        super.visitCodeFunctionDefinition(node);
        return 0;
    }

    @Override
    public Integer visitCodeExpressionStatement(CodeExpressionStatement node) {
        System.out.println("CodeVisitableElement -> CodeExpressionStatement");

        pos.push(0);
        extractSubgraphs(node, CodeExpressionStatement.CB_EXPRESSION_BODY, CodeExpressionStatement.context_names);
        pos.pop();

        dot_writer.println("\""+node.getParent(0).getGraphvizName()+"\"->\""+node.getGraphvizName()+"\";");

        super.visitCodeExpressionStatement(node);
        return 0;
    }

    @Override
    public Integer visitCodeReturnStatement(CodeReturnStatement node) {
        System.out.println("CodeVisitableElement -> CodeReturnStatement");

        pos.push(0);
        extractSubgraphs(node, CodeReturnStatement.CB_EXPRESSION_BODY, CodeReturnStatement.context_names);
        pos.pop();

        dot_writer.println("\""+node.getParent(0).getGraphvizName()+"\"->\""+node.getGraphvizName()+"\";");

        super.visitCodeReturnStatement(node);
        return 0;
    }

    @Override
    public Integer visitCodeIfStatement(CodeIfStatement node) {
        System.out.println("CodeVisitableElement -> CodeIfStatement");

        pos.push(0);
        extractSubgraphs(node, CodeIfStatement.CB_IF_CONDITION, CodeIfStatement.context_names);
        pos.pop();

        pos.push(0);
        extractSubgraphs(node, CodeIfStatement.CB_IF_BODY, CodeIfStatement.context_names);
        pos.pop();

        dot_writer.println("\""+node.getParent(0).getGraphvizName()+"\"->\""+node.getGraphvizName()+"\";");

        super.visitCodeIfStatement(node);
        return 0;
    }

    @Override
    public Integer visitCodeWhileStatement(CodeWhileStatement node) {
        System.out.println("CodeVisitableElement -> CodeWhileStatement");

        pos.push(0);
        extractSubgraphs(node, CodeWhileStatement.CB_WHILE_CONDITION, CodeWhileStatement.context_names);
        pos.pop();

        pos.push(0);
        extractSubgraphs(node, CodeWhileStatement.CB_WHILE_BODY, CodeWhileStatement.context_names);
        pos.pop();

        dot_writer.println("\""+node.getParent(0).getGraphvizName()+"\"->\""+node.getGraphvizName()+"\";");

        super.visitCodeWhileStatement(node);
        return 0;
    }

    @Override
    public Integer visitCodeCompoundStatement(CodeCompoundStatement node) {
        System.out.println("CodeVisitableElement -> CodeCompoundStatement");

        pos.push(0);
        extractSubgraphs(node, CodeCompoundStatement.CB_COMPOUND_BODY, CodeCompoundStatement.context_names);
        pos.pop();

        dot_writer.println("\""+node.getParent(0).getGraphvizName()+"\"->\""+node.getGraphvizName()+"\";");

        super.visitCodeCompoundStatement(node);
        return 0;
    }

    @Override
    public Integer visitCodeCondition(CodeCondition node) {
        System.out.println("CodeVisitableElement -> CodeCondition");

        pos.push(0);
        extractSubgraphs(node, CodeCondition.CB_CONDITION, CodeCondition.context_names);
        pos.pop();

        dot_writer.println("\""+node.getParent(0).getGraphvizName()+"\"->\""+node.getGraphvizName()+"\";");

        super.visitCodeCondition(node);
        return 0;
    }

    @Override
    public Integer visitCodeRepository(CodeRepository node) {
        System.out.println("CodeVisitableElement -> CodeRepository");

        dot_writer.println("\""+node.getParent(0).getGraphvizName()+"\"->\""+node.getGraphvizName()+"\";");

        super.visitCodeRepository(node);
        return 0;
    }
}
