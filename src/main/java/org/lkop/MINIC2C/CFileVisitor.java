package org.lkop.MINIC2C;

import org.lkop.MINIC2C.utils.GIFCreator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Stack;

public class CFileVisitor extends CodeVisitor{

    private String filename;
    private PrintWriter writer;
    private int serial_counter = 0;
    private Stack<Integer> pos = new Stack<>();

    public CFileVisitor(String filename) throws FileNotFoundException {
        this.filename = filename;
        writer = new PrintWriter("output/"+this.filename+".dot");
    }

    private void extractSubgraphs(CodeContainer node, int context, String[] context_names) {
        writer.println("\tsubgraph cluster" + serial_counter++ + "{");
        writer.println("\t\tnode [style=filled,color=white];");
        writer.println("\t\tstyle=filled;");
        writer.println("\t\tcolor=lightgrey;");

        writer.print("\t\t");
        for (CodeContainer elem : node.getChildrenInContext(context, pos.peek())) {
            int i = pos.peek();
            pos.pop();
            pos.push(++i);
            writer.print(elem.getName()+"_"+elem.getSerialId()+"; ");
        }

        writer.println("\n\t\tlabel=" + context_names[context] + ";");
        writer.println("\t}");
    }

    @Override
    public Integer visitCodeFile(CodeFile node) {
        System.out.println("CodeVisitableElement -> CodeFile");

        writer.println("digraph G {");

        pos.push(0);
        extractSubgraphs(node, CodeFile.CC_FILE_PREPROCESSOR, CodeFile.context_names);
        writer.println("\""+node.getGraphvizName()+"\"->\""+node.getChildrenInContext(CodeFile.CC_FILE_PREPROCESSOR).get(0).getGraphvizName()+"\";");
        pos.pop();

        pos.push(0);
        extractSubgraphs(node, CodeFile.CC_FILE_GLOBALS, CodeFile.context_names);
        writer.println("\""+node.getGraphvizName()+"\"->\""+node.getChildrenInContext(CodeFile.CC_FILE_GLOBALS).get(0).getGraphvizName()+"\";");
        pos.pop();

        pos.push(0);
        extractSubgraphs(node, CodeFile.CC_FILE_FUNDEF, CodeFile.context_names);
        pos.pop();
        writer.println("\""+node.getGraphvizName()+"\"->\""+node.getChildrenInContext(CodeFile.CC_FILE_FUNDEF).get(0).getGraphvizName()+"\";");

        super.visitCodeFile(node);

        writer.println("}");
        writer.close();

        try {
            new GIFCreator(filename);
        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public Integer visitCodeIfStatement(CodeIfStatement node) {
        System.out.println("CodeVisitableElement -> CodeIfStatement");

        pos.push(0);
        extractSubgraphs(node, CodeIfStatement.CB_IF_STATEMENT_CONDITION, CodeIfStatement.context_names);
        pos.pop();

        pos.push(0);
        extractSubgraphs(node, CodeIfStatement.CB_IF_STATEMENT_BODY, CodeIfStatement.context_names);
        pos.pop();

        writer.println("\""+node.getParent(0).getGraphvizName()+"\"->\""+node.getGraphvizName()+"\";");

        super.visitCodeIfStatement(node);
        return 0;
    }
}
