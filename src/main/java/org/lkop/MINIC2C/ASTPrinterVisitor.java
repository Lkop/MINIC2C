package org.lkop.MINIC2C;

import org.lkop.MINIC2C.utils.GIFCreator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Stack;

public class ASTPrinterVisitor extends ASTVisitor<Integer> {

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
        extractSubgraphs(node, CCompileUnit.CT_COMPILEUNIT_FUNCTIONDEFINITIONS, CCompileUnit.context_names);
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
    public Integer visitCFunctionDefinition(CFuntionDefinition node) {
        return super.visitCFunctionDefinition(node);
    }

    @Override
    public Integer visitCExpressionStatement(CExpressionStatement node) {
        System.out.println("ASTVisitableElement -> CExpressionStatement");

        pos.push(0);
        extractSubgraphs(node, CExpressionStatement.CT_EXRESSION, CExpressionStatement.context_names);
        pos.pop();

        writer.println("\""+node.getParent(0).getGraphvizName()+"\"->\""+node.getGraphvizName()+"\";");

        super.visitCExpressionStatement(node);
        return 0;
    }

    @Override
    public Integer visitCReturnStatement(CReturnStatement node) {
        System.out.println("ASTVisitableElement -> CReturnStatement");

        pos.push(0);
        extractSubgraphs(node, CReturnStatement.CT_EXRESSION, CReturnStatement.context_names);
        pos.pop();

        writer.println("\""+node.getParent(0).getGraphvizName()+"\"->\""+node.getGraphvizName()+"\";");

        super.visitCReturnStatement(node);
        return 0;
    }

    @Override
    public Integer visitCBreakStatement(CBreakStatement node) {
        return super.visitCBreakStatement(node);
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
    public Integer visitCWhile(CWhile node) {
        System.out.println("ASTVisitableElement -> CWhile");

        pos.push(0);
        extractSubgraphs(node, CWhile.CT_WHILE_EXPRESSION, CWhile.context_names);
        pos.pop();

        pos.push(0);
        extractSubgraphs(node, CWhile.CT_WHILE_STATEMENT, CWhile.context_names);
        pos.pop();

        writer.println("\""+node.getParent(0).getGraphvizName()+"\"->\""+node.getGraphvizName()+"\";");

        super.visitCWhile(node);
        return 0;
    }

    @Override
    public Integer visitCCompound(CCompound node) {
        System.out.println("ASTVisitableElement -> CCompound");

        pos.push(0);
        extractSubgraphs(node, CCompound.CT_COMPOUND_STATEMENTSLIST, CCompound.context_names);
        pos.pop();

        writer.println("\""+node.getParent(0).getGraphvizName()+"\"->\""+node.getGraphvizName()+"\";");

        super.visitCCompound(node);
        return 0;
    }

    @Override
    public Integer visitCFunctionCall(CFunctionCall node) {
        System.out.println("ASTVisitableElement -> CFunctionCall");

        pos.push(0);
        extractSubgraphs(node, CFunctionCall.CT_ARGS, CFunctionCall.context_names);
        pos.pop();

        writer.println("\""+node.getParent(0).getGraphvizName()+"\"->\""+node.getGraphvizName()+"\";");

        super.visitCFunctionCall(node);
        return 0;
    }

    @Override
    public Integer visitCDivision(CDivision node) {
        System.out.println("ASTVisitableElement -> CDivision");

        pos.push(0);
        extractSubgraphs(node, CDivision.CT_LEFT, CDivision.context_names);
        pos.pop();

        pos.push(0);
        extractSubgraphs(node, CDivision.CT_RIGHT, CDivision.context_names);
        pos.pop();

        writer.println("\""+node.getParent(0).getGraphvizName()+"\"->\""+node.getGraphvizName()+"\";");
        super.visitCDivision(node);
        return 0;
    }

    @Override
    public Integer visitCMultiplication(CMultiplication node) {
        System.out.println("ASTVisitableElement -> CMultiplication");

        pos.push(0);
        extractSubgraphs(node, CMultiplication.CT_LEFT, CMultiplication.context_names);
        pos.pop();

        pos.push(0);
        extractSubgraphs(node, CMultiplication.CT_RIGHT, CMultiplication.context_names);
        pos.pop();

        writer.println("\""+node.getParent(0).getGraphvizName()+"\"->\""+node.getGraphvizName()+"\";");
        super.visitCMultiplication(node);
        return 0;
    }

    @Override
    public Integer visitCAddition(CAddition node) {
        System.out.println("ASTVisitableElement -> CAddition");

        pos.push(0);
        extractSubgraphs(node, CAddition.CT_LEFT, CAddition.context_names);
        pos.pop();

        pos.push(0);
        extractSubgraphs(node, CAddition.CT_RIGHT, CAddition.context_names);
        pos.pop();

        writer.println("\""+node.getParent(0).getGraphvizName()+"\"->\""+node.getGraphvizName()+"\";");
        super.visitCAddition(node);
        return 0;
    }

    @Override
    public Integer visitCSubtraction(CSubtraction node) {
        System.out.println("ASTVisitableElement -> CSubtraction");

        pos.push(0);
        extractSubgraphs(node, CSubtraction.CT_LEFT, CSubtraction.context_names);
        pos.pop();

        pos.push(0);
        extractSubgraphs(node, CSubtraction.CT_RIGHT, CSubtraction.context_names);
        pos.pop();

        writer.println("\""+node.getParent(0).getGraphvizName()+"\"->\""+node.getGraphvizName()+"\";");
        super.visitCSubtraction(node);
        return 0;
    }

    @Override
    public Integer visitCUnaryPlus(CUnaryPlus node) {
        System.out.println("ASTVisitableElement -> CUnaryPlus");

        pos.push(0);
        extractSubgraphs(node, CUnaryPlus.CT_RIGHT, CUnaryPlus.context_names);
        pos.pop();

        writer.println("\""+node.getParent(0).getGraphvizName()+"\"->\""+node.getGraphvizName()+"\";");
        super.visitCUnaryPlus(node);
        return 0;
    }

    @Override
    public Integer visitCUnaryMinus(CUnaryMinus node) {
        System.out.println("ASTVisitableElement -> CUnaryMinus");

        pos.push(0);
        extractSubgraphs(node, CUnaryMinus.CT_RIGHT, CUnaryMinus.context_names);
        pos.pop();

        writer.println("\""+node.getParent(0).getGraphvizName()+"\"->\""+node.getGraphvizName()+"\";");
        super.visitCUnaryMinus(node);
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
    public Integer visitCNot(CNot node) {
        System.out.println("ASTVisitableElement -> CNot");

        pos.push(0);
        extractSubgraphs(node, CNot.CT_RIGHT, CNot.context_names);
        pos.pop();

        writer.println("\""+node.getParent(0).getGraphvizName()+"\"->\""+node.getGraphvizName()+"\";");
        super.visitCNot(node);
        return 0;
    }

    @Override
    public Integer visitCAnd(CAnd node) {
        System.out.println("ASTVisitableElement -> CGt");

        pos.push(0);
        extractSubgraphs(node, CAnd.CT_LEFT, CAnd.context_names);
        pos.pop();

        pos.push(0);
        extractSubgraphs(node, CAnd.CT_RIGHT, CAnd.context_names);
        pos.pop();

        writer.println("\""+node.getParent(0).getGraphvizName()+"\"->\""+node.getGraphvizName()+"\";");
        super.visitCAnd(node);
        return 0;
    }

    @Override
    public Integer visitCOr(COr node) {
        System.out.println("ASTVisitableElement -> CGt");

        pos.push(0);
        extractSubgraphs(node, COr.CT_LEFT, CGte.context_names);
        pos.pop();

        pos.push(0);
        extractSubgraphs(node, COr.CT_RIGHT, CGte.context_names);
        pos.pop();

        writer.println("\""+node.getParent(0).getGraphvizName()+"\"->\""+node.getGraphvizName()+"\";");
        super.visitCOr(node);
        return 0;
    }

    @Override
    public Integer visitCGt(CGt node) {
        System.out.println("ASTVisitableElement -> CGt");

        pos.push(0);
        extractSubgraphs(node, CGt.CT_LEFT, CGte.context_names);
        pos.pop();

        pos.push(0);
        extractSubgraphs(node, CGt.CT_RIGHT, CGte.context_names);
        pos.pop();

        writer.println("\""+node.getParent(0).getGraphvizName()+"\"->\""+node.getGraphvizName()+"\";");
        super.visitCGt(node);
        return 0;
    }

    @Override
    public Integer visitCGte(CGte node) {
        System.out.println("ASTVisitableElement -> CGte");

        pos.push(0);
        extractSubgraphs(node, CGte.CT_LEFT, CGte.context_names);
        pos.pop();

        pos.push(0);
        extractSubgraphs(node, CGte.CT_RIGHT, CGte.context_names);
        pos.pop();

        writer.println("\""+node.getParent(0).getGraphvizName()+"\"->\""+node.getGraphvizName()+"\";");
        super.visitCGte(node);
        return 0;
    }

    @Override
    public Integer visitCLt(CLt node) {
        System.out.println("ASTVisitableElement -> CLt");

        pos.push(0);
        extractSubgraphs(node, CLt.CT_LEFT, CLte.context_names);
        pos.pop();

        pos.push(0);
        extractSubgraphs(node, CLt.CT_RIGHT, CLte.context_names);
        pos.pop();

        writer.println("\""+node.getParent(0).getGraphvizName()+"\"->\""+node.getGraphvizName()+"\";");
        super.visitCLt(node);
        return 0;
    }

    @Override
    public Integer visitCLte(CLte node) {
        System.out.println("ASTVisitableElement -> CLte");

        pos.push(0);
        extractSubgraphs(node, CLte.CT_LEFT, CLte.context_names);
        pos.pop();

        pos.push(0);
        extractSubgraphs(node, CLte.CT_RIGHT, CLte.context_names);
        pos.pop();

        writer.println("\""+node.getParent(0).getGraphvizName()+"\"->\""+node.getGraphvizName()+"\";");
        super.visitCLte(node);
        return 0;
    }

    @Override
    public Integer visitCEqual(CEqual node) {
        System.out.println("ASTVisitableElement -> CEqual");

        pos.push(0);
        extractSubgraphs(node, CEqual.CT_LEFT, CEqual.context_names);
        pos.pop();

        pos.push(0);
        extractSubgraphs(node, CEqual.CT_RIGHT, CEqual.context_names);
        pos.pop();

        writer.println("\""+node.getParent(0).getGraphvizName()+"\"->\""+node.getGraphvizName()+"\";");
        super.visitCEqual(node);
        return 0;
    }

    @Override
    public Integer visitCNequal(CNequal node) {
        System.out.println("ASTVisitableElement -> CNequal");

        pos.push(0);
        extractSubgraphs(node, CNequal.CT_LEFT, CNequal.context_names);
        pos.pop();

        pos.push(0);
        extractSubgraphs(node, CNequal.CT_RIGHT, CNequal.context_names);
        pos.pop();

        writer.println("\""+node.getParent(0).getGraphvizName()+"\"->\""+node.getGraphvizName()+"\";");
        super.visitCNequal(node);
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
