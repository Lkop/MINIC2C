package org.lkop.MINIC2C;

import org.lkop.MINIC2C.utils.GIFCreator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Stack;

public class CFileVisitor extends CodeVisitor<Integer>{

    private String filename;
    private PrintWriter dot_writer, c_writer;
    private int serial_counter = 0;
    private Stack<Integer> pos = new Stack<>();

    public CFileVisitor(String filename) throws FileNotFoundException {
        this.filename = filename;
        dot_writer = new PrintWriter("output/"+this.filename+".dot");
        c_writer = new PrintWriter("output/"+this.filename+".c");
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

    private void addToFile(CodeContainer node, int context) {
        for (CodeContainer elem : node.getChildrenInContext(context, pos.peek())) {
            int i = pos.peek();
            pos.pop();
            pos.push(++i);

            c_writer.print(elem.code);
        }
    }

    @Override
    public Integer visitCodeFile(CodeFile node) {
        System.out.println("CodeVisitableElement -> CodeFile");

        dot_writer.println("digraph G {");

        pos.push(0);
        extractSubgraphs(node, CodeFile.CC_FILE_PREPROCESSOR, CodeFile.context_names);
        pos.pop();
        dot_writer.println("\""+node.getGraphvizName()+"\"->\""+node.getChildrenInContext(CodeFile.CC_FILE_PREPROCESSOR).get(0).getGraphvizName()+"\";");

        pos.push(0);
        extractSubgraphs(node, CodeFile.CC_FILE_GLOBALS, CodeFile.context_names);
        pos.pop();
        dot_writer.println("\""+node.getGraphvizName()+"\"->\""+node.getChildrenInContext(CodeFile.CC_FILE_GLOBALS).get(0).getGraphvizName()+"\";");

        pos.push(0);
        extractSubgraphs(node, CodeFile.CC_FILE_FUNCTIONDEFINITION, CodeFile.context_names);
        pos.pop();

        super.visitCodeFile(node);

        dot_writer.println("}");
        dot_writer.close();
        c_writer.close();

        try {
            new GIFCreator(filename);
        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public Integer visitCodeExpressionStatement(CodeExpressionStatement node) {
        System.out.println("CodeVisitableElement -> CodeExpressionStatement");

        //Graphviz
        pos.push(0);
        extractSubgraphs(node, CodeExpressionStatement.CB_EXPRESSION_BODY, CodeExpressionStatement.context_names);
        pos.pop();
        dot_writer.println("\""+node.getParent(0).getGraphvizName()+"\"->\""+node.getGraphvizName()+"\";");

        //dot_writer.println("\""+node.getGraphvizName()+"\"->\""+node.getChildrenInContext(CodeExpressionStatement.CB_EXPRESSION_BODY).get(0).getGraphvizName()+"\";");


        super.visitCodeExpressionStatement(node);
        return 0;
    }

    @Override
    public Integer visitCodeCompoundStatement(CodeCompoundStatement node) {
        System.out.println("CodeVisitableElement -> CodeCompoundStatement");

        //Graphviz
        pos.push(0);
        extractSubgraphs(node, CodeCompoundStatement.CB_COMPOUND_BODY, CodeCompoundStatement.context_names);
        pos.pop();
        dot_writer.println("\""+node.getParent(0).getGraphvizName()+"\"->\""+node.getGraphvizName()+"\";");

        super.visitCodeCompoundStatement(node);
        return 0;
    }

    @Override
    public Integer visitCodeWhileStatement(CodeWhileStatement node) {
        System.out.println("CodeVisitableElement -> CodeWhileStatement");

        //Graphviz
        pos.push(0);
        extractSubgraphs(node, CodeWhileStatement.CB_WHILE_CONDITION, CodeWhileStatement.context_names);
        pos.pop();

        pos.push(0);
        extractSubgraphs(node, CodeWhileStatement.CB_WHILE_BODY, CodeWhileStatement.context_names);
        pos.pop();

        dot_writer.println("\""+node.getParent(0).getGraphvizName()+"\"->\""+node.getGraphvizName()+"\";");

        //Adding to file
//        c_writer.print("if(");
//        pos.push(0);
//        addToFile(node, CodeIfStatement.CB_IF_CONDITION);
//        pos.pop();
//
//        c_writer.println(") {");
//
//        pos.push(0);
//        addToFile(node, CodeIfStatement.CB_IF_BODY);
//        pos.pop();
//
//        c_writer.println("}");

        super.visitCodeWhileStatement(node);
        return 0;
    }

    @Override
    public Integer visitCodeIfStatement(CodeIfStatement node) {
        System.out.println("CodeVisitableElement -> CodeIfStatement");

        //Graphviz
        pos.push(0);
        extractSubgraphs(node, CodeIfStatement.CB_IF_CONDITION, CodeIfStatement.context_names);
        pos.pop();

        pos.push(0);
        extractSubgraphs(node, CodeIfStatement.CB_IF_BODY, CodeIfStatement.context_names);
        pos.pop();

        dot_writer.println("\""+node.getParent(0).getGraphvizName()+"\"->\""+node.getGraphvizName()+"\";");

//        //Adding to file
//        c_writer.print("if(");
//        pos.push(0);
//        addToFile(node, CodeIfStatement.CB_IF_CONDITION);
//        pos.pop();
//
//        c_writer.println(") {");
//
//        pos.push(0);
//        addToFile(node, CodeIfStatement.CB_IF_BODY);
//        pos.pop();
//
//        c_writer.println("}");

        super.visitCodeIfStatement(node);
        return 0;
    }

    @Override
    public Integer visitCodeRepository(CodeRepository node) {
        System.out.println("CodeVisitableElement -> CodeRepository");

        //Graphviz
        dot_writer.println("\""+node.getParent(0).getGraphvizName()+"\"->\""+node.getGraphvizName()+"\";");

        super.visitCodeRepository(node);
        return 0;
    }
}
