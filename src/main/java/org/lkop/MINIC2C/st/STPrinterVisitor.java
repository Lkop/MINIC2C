package org.lkop.MINIC2C.st;

import org.antlr.v4.runtime.tree.TerminalNode;
import org.lkop.MINIC2C.lexerparsergenerated.MINICLexer;
import org.lkop.MINIC2C.lexerparsergenerated.MINICParser;
import org.lkop.MINIC2C.lexerparsergenerated.MINICParserBaseVisitor;
import org.lkop.MINIC2C.utils.GIFCreator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Stack;


public class STPrinterVisitor extends MINICParserBaseVisitor {

    private String filename;
    private PrintWriter writer;
    private Stack<String> parent_label = new Stack<>();
    private int serial_ounter = 0;

    public STPrinterVisitor(String filename) throws FileNotFoundException {
        this.filename = filename;
        writer = new PrintWriter("output/"+this.filename+".dot");
    }

    @Override
    public Integer visitCompileUnit(MINICParser.CompileUnitContext ctx) {
        String label = "CompileUnit"+"_" + serial_ounter++;
        writer.println("digraph G{");
        parent_label.push(label);
        super.visitCompileUnit(ctx);
        parent_label.pop();
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
    public Integer visitFunctionDefinition(MINICParser.FunctionDefinitionContext ctx) {
        String label = "Function_Definition"+"_" + serial_ounter++;
        writer.println("\""+parent_label.peek()+"\"->\""+label+"\";");
        parent_label.push(label);
        super.visitFunctionDefinition(ctx);
        parent_label.pop();
        return 0;
    }

    @Override
    public Integer visitStatement_ExpressionStatement(MINICParser.Statement_ExpressionStatementContext ctx) {
        String label = "Statement_ExpressionStatement"+"_" + serial_ounter++;
        writer.println("\""+parent_label.peek()+"\"->\""+label+"\";");
        parent_label.push(label);
        super.visitStatement_ExpressionStatement(ctx);
        parent_label.pop();
        return 0;
    }

    @Override
    public Integer visitStatement_IfStatement(MINICParser.Statement_IfStatementContext ctx) {
        String label = "IfStatement"+"_" + serial_ounter++;
        writer.println("\""+parent_label.peek()+"\"->\""+label+"\";");
        parent_label.push(label);
        super.visitStatement_IfStatement(ctx);
        parent_label.pop();
        return 0;
    }

    @Override
    public Integer visitStatement_Whilestatement(MINICParser.Statement_WhilestatementContext ctx) {
        String label = "Statement_Whilestatement"+"_" + serial_ounter++;
        writer.println("\""+parent_label.peek()+"\"->\""+label+"\";");
        parent_label.push(label);
        super.visitStatement_Whilestatement(ctx);
        parent_label.pop();
        return 0;
    }

    @Override
    public Integer visitStatement_CompoundStatement(MINICParser.Statement_CompoundStatementContext ctx) {
        String label = "Statement_CompoundStatement"+"_" + serial_ounter++;
        writer.println("\""+parent_label.peek()+"\"->\""+label+"\";");
        parent_label.push(label);
        super.visitStatement_CompoundStatement(ctx);
        parent_label.pop();
        return 0;
    }

    @Override
    public Integer visitStatement_ReturnStatement(MINICParser.Statement_ReturnStatementContext ctx) {
        String label = "Statement_ReturnStatement"+"_" + serial_ounter++;
        writer.println("\""+parent_label.peek()+"\"->\""+label+"\";");
        parent_label.push(label);
        super.visitStatement_ReturnStatement(ctx);
        parent_label.pop();
        return 0;
    }

    @Override
    public Integer visitStatement_BreakStatement(MINICParser.Statement_BreakStatementContext ctx) {
        String label = "Statement_BreakStatement"+"_" + serial_ounter++;
        writer.println("\""+parent_label.peek()+"\"->\""+label+"\";");
        parent_label.push(label);
        super.visitStatement_BreakStatement(ctx);
        parent_label.pop();
        return 0;
    }

    @Override
    public Integer visitIfstatement(MINICParser.IfstatementContext ctx) {
        String label = "If_Statement"+"_" + serial_ounter++;
        writer.println("\""+parent_label.peek()+"\"->\""+label+"\";");
        parent_label.push(label);
        super.visitIfstatement(ctx);
        parent_label.pop();
        return 0;
    }

    @Override
    public Integer visitWhilestatement(MINICParser.WhilestatementContext ctx) {
        String label = "While_Statement"+"_" + serial_ounter++;
        writer.println("\""+parent_label.peek()+"\"->\""+label+"\";");
        parent_label.push(label);
        super.visitWhilestatement(ctx);
        parent_label.pop();
        return 0;
    }

    @Override
    public Integer visitCompoundStatement(MINICParser.CompoundStatementContext ctx) {
        String label = "Compound_Statement"+"_" + serial_ounter++;
        writer.println("\""+parent_label.peek()+"\"->\""+label+"\";");
        parent_label.push(label);
        super.visitCompoundStatement(ctx);
        parent_label.pop();
        return 0;
    }

    @Override
    public Integer visitStatementList(MINICParser.StatementListContext ctx) {
        String label = "Statement_List"+"_" + serial_ounter++;
        writer.println("\""+parent_label.peek()+"\"->\""+label+"\";");
        parent_label.push(label);
        super.visitStatementList(ctx);
        parent_label.pop();
        return 0;
    }

    @Override
    public Integer visitExpr_FunctionCall(MINICParser.Expr_FunctionCallContext ctx) {
        String label = "Function_Call"+"_" + serial_ounter++;
        writer.println("\""+parent_label.peek()+"\"->\""+label+"\";");
        parent_label.push(label);
        super.visitExpr_FunctionCall(ctx);
        parent_label.pop();
        return 0;
    }

    @Override
    public Integer visitExpr_DIVMULT(MINICParser.Expr_DIVMULTContext ctx) {
        String label = "";
        switch (ctx.op.getType()) {
            case MINICLexer.DIV:
                label = "Division" + "_" + serial_ounter++;
                break;
            case MINICLexer.MULT:
                label = "Multiplication" + "_" + serial_ounter++;
                break;
        }
        writer.println("\""+parent_label.peek()+"\"->\""+label+"\";");
        parent_label.push(label);
        super.visitExpr_DIVMULT(ctx);
        parent_label.pop();
        return 0;
    }

    @Override
    public Integer visitExpr_PLUSMINUS(MINICParser.Expr_PLUSMINUSContext ctx) {
        String label = "";
        switch (ctx.op.getType()) {
            case MINICLexer.PLUS:
                label = "Addition" + "_" + serial_ounter++;
                break;
            case MINICLexer.MINUS:
                label = "Subtraction" + "_" + serial_ounter++;
                break;
        }
        writer.println("\""+parent_label.peek()+"\"->\""+label+"\";");
        parent_label.push(label);
        super.visitExpr_PLUSMINUS(ctx);
        parent_label.pop();
        return 0;
    }

    @Override
    public Integer visitExpr_UNARYPLUS(MINICParser.Expr_UNARYPLUSContext ctx) {
        String label = "UNARYPLUS"+"_" + serial_ounter++;
        writer.println("\""+parent_label.peek()+"\"->\""+label+"\";");
        parent_label.push(label);
        super.visitExpr_UNARYPLUS(ctx);
        parent_label.pop();
        return 0;
    }

    @Override
    public Integer visitExpr_UNARYMINUS(MINICParser.Expr_UNARYMINUSContext ctx) {
        String label = "UNARYMINUS"+"_" + serial_ounter++;
        writer.println("\""+parent_label.peek()+"\"->\""+label+"\";");
        parent_label.push(label);
        super.visitExpr_UNARYMINUS(ctx);
        parent_label.pop();
        return 0;
    }

    @Override
    public Integer visitExpr_PARENTHESIS(MINICParser.Expr_PARENTHESISContext ctx) {
        String label = "PARENTHESIS"+"_" + serial_ounter++;
        writer.println("\""+parent_label.peek()+"\"->\""+label+"\";");
        parent_label.push(label);
        super.visitExpr_PARENTHESIS(ctx);
        parent_label.pop();
        return 0;
    }

    @Override
    public Integer visitExpr_Assignment(MINICParser.Expr_AssignmentContext ctx) {
        String label = "Assignment"+"_" + serial_ounter++;
        writer.println("\""+parent_label.peek()+"\"->\""+label+"\";");
        parent_label.push(label);
        super.visitExpr_Assignment(ctx);
        parent_label.pop();
        return 0;
    }

    @Override
    public Integer visitExpr_NOT(MINICParser.Expr_NOTContext ctx) {
        String label = "NOT"+"_" + serial_ounter++;
        writer.println("\""+parent_label.peek()+"\"->\""+label+"\";");
        parent_label.push(label);
        super.visitExpr_NOT(ctx);
        parent_label.pop();
        return 0;
    }

    @Override
    public Integer visitExpr_AND(MINICParser.Expr_ANDContext ctx) {
        String label = "AND"+"_" + serial_ounter++;
        writer.println("\""+parent_label.peek()+"\"->\""+label+"\";");
        parent_label.push(label);
        super.visitExpr_AND(ctx);
        parent_label.pop();
        return 0;
    }

    @Override
    public Integer visitExpr_OR(MINICParser.Expr_ORContext ctx) {
        String label = "OR"+"_" + serial_ounter++;
        writer.println("\""+parent_label.peek()+"\"->\""+label+"\";");
        parent_label.push(label);
        super.visitExpr_OR(ctx);
        parent_label.pop();
        return 0;
    }

    @Override
    public Integer visitExpr_COMPARISON(MINICParser.Expr_COMPARISONContext ctx) {
        String label = "";
        switch (ctx.op.getType()) {
            case MINICLexer.LTE:
                label = "LTE" + "_" + serial_ounter++;
                break;
            case MINICLexer.LT:
                label = "LT" + "_" + serial_ounter++;
                break;
            case MINICLexer.GTE:
                label = "GTE" + "_" + serial_ounter++;
                break;
            case MINICLexer.GT:
                label = "GT" + "_" + serial_ounter++;
                break;
            case MINICLexer.EQUAL:
                label = "EQUAL" + "_" + serial_ounter++;
                break;
            case MINICLexer.NEQUAL:
                label = "NEQUAL" + "_" + serial_ounter++;
                break;
        }
        writer.println("\""+parent_label.peek()+"\"->\""+label+"\";");
        parent_label.push(label);
        super.visitExpr_COMPARISON(ctx);
        parent_label.pop();
        return 0;
    }

    @Override
    public Integer visitTerminal(TerminalNode node) {
        String label = "";
        switch (node.getSymbol().getType()) {
            case MINICLexer.NUMBER:
                label = "NUMBER"+"_"+serial_ounter+++"_("+node.getSymbol().getText()+")";
                writer.println("\""+parent_label.peek()+"\"->\""+label+"\";");
                break;
            case MINICLexer.IDENTIFIER:
                label = "IDENTIFIER"+"_"+serial_ounter+++"_("+node.getSymbol().getText()+")";
                writer.println("\""+parent_label.peek()+"\"->\""+label+"\";");
                break;
        }
        super.visitTerminal(node);
        return 0;
    }
}
