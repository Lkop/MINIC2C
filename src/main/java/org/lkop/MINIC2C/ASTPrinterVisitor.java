package org.lkop.MINIC2C;

import org.lkop.MINIC2C.utils.GIFCreator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Stack;

public class ASTPrinterVisitor extends ASTVisitor<Integer>{

    private String filename;
    private PrintWriter writer;
    private int serial_counter = 0;
    private Stack<Integer> pos = new Stack<>();

    public ASTPrinterVisitor(String filename) throws FileNotFoundException {
        this.filename = filename;
        writer = new PrintWriter("output/"+this.filename+".dot");
    }

    private void extractSubgraphs(ASTElement node, int context, String[] context_names) {
        writer.println("\tsubgraph cluster" + serial_counter++ + "{");
        writer.println("\t\tnode [style=filled,color=white];");
        writer.println("\t\tstyle=filled;");
        writer.println("\t\tcolor=lightgrey;");

        writer.print("\t\t");
        for (ASTElement elem : node.getChildrenInContext(context, pos.peek())) {
            int i = pos.peek();
            pos.pop();
            pos.push(++i);
            writer.print(elem.getName()+"_"+elem.getSerialId()+"; ");
        }

        writer.println("\n\t\tlabel=" + context_names[context] + ";");
        writer.println("\t}");
    }

    @Override
    public Integer visitCCompileUnit(CCompileUnit node) {
        System.out.println("ASTVisitableElement -> CCompileUnit");

        writer.println("digraph G {");
        pos.push(0);
        extractSubgraphs(node, CCompileUnit.CT_COMPILEUNIT_STATEMENTS, CCompileUnit.context_names);
        pos.pop();

        pos.push(0);
        extractSubgraphs(node, CCompileUnit.CT_COMPILEUNIT_FUNDEFS, CCompileUnit.context_names);
        pos.pop();

        super.visitCCompileUnit(node);

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
    public Integer visitCIf(CIf node) {
        System.out.println("ASTVisitableElement -> CIf");

        pos.push(0);
        extractSubgraphs(node, CIf.CT_IF_EXPRESSION, CIf.context_names);
        pos.pop();

        pos.push(0);
        extractSubgraphs(node, CIf.CT_IF_STATEMENT, CIf.context_names);
        pos.pop();

        writer.println("\""+node.getParent(0).getGraphvizName()+"\"->\""+node.getGraphvizName()+"\";");

        super.visitCIf(node);
        return 0;
    }

    @Override
    public Integer visitCAssignment(CAssignment node) {
        System.out.println("ASTVisitableElement -> CAssignment");

        pos.push(0);
        extractSubgraphs(node, CAssignment.CT_LEFT, CAssignment.context_names);
        pos.pop();

        pos.push(0);
        extractSubgraphs(node, CAssignment.CT_RIGHT, CAssignment.context_names);
        pos.pop();

        writer.println("\""+node.getParent(0).getGraphvizName()+"\"->\""+node.getGraphvizName()+"\";");
        super.visitCAssignment(node);
        return 0;
    }

    @Override
    public Integer visitCIDENTIFIER(CIDENTIFIER node) {
        System.out.println("ASTVisitableElement -> CIDENTIFIER");

        writer.println("\""+node.getParent(0).getGraphvizName()+"\"->\""+node.getGraphvizName()+"\";");
        super.visitCIDENTIFIER(node);
        return 0;
    }

    @Override
    public Integer visitCNUMBER(CNUMBER node) {
        System.out.println("ASTVisitableElement -> CNUMBER");

        writer.println("\""+node.getParent(0).getGraphvizName()+"\"->\""+node.getGraphvizName()+"\";");
        super.visitCNUMBER(node);
        return 0;
    }
}
