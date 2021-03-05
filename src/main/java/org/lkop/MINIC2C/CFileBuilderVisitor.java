package org.lkop.MINIC2C;

import org.lkop.MINIC2C.utils.GIFCreator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Stack;

public class CFileBuilderVisitor extends CodeVisitor<Integer>{

    private String filename;
    private PrintWriter c_writer;
    private int nesting_lvl=0;
    private int tmp_nesting_lvl=0;

    public CFileBuilderVisitor(String filename) throws FileNotFoundException {
        this.filename = filename;
        c_writer = new PrintWriter("output/"+this.filename+".c");
    }

    private String addTabs(int level){
        String tabs = "";
        for(int i=0; i<level; i++){
            tabs += "\t";
        }
        return tabs;
    }

    @Override
    public Integer visitCodeFile(CodeFile node) {
        System.out.println("CodeVisitableElement -> CodeFile");

        for (CodeContainer elem : node.getChildrenInContext(CodeFile.CC_FILE_PREPROCESSOR)) {
            super.visit(elem);
        }

        for (CodeContainer elem : node.getChildrenInContext(CodeFile.CC_FILE_GLOBALS)) {
            super.visit(elem);
        }

        for (CodeContainer elem : node.getChildrenInContext(CodeFile.CC_FILE_FUNCTIONDEFINITION)) {
            super.visit(elem);
        }

        c_writer.close();

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

        for (CodeContainer elem : node.getChildrenInContext(CodeFunctionDefinition.CC_FUNCTIONDEFINITION_HEADER)) {
            super.visit(elem);
        }

        for (CodeContainer elem : node.getChildrenInContext(CodeFunctionDefinition.CC_FUNCTIONDEFINITION_BODY)) {
            super.visit(elem);
        }

        return 0;
    }

    @Override
    public Integer visitCodeExpressionStatement(CodeExpressionStatement node) {
        System.out.println("CodeVisitableElement -> CodeExpressionStatement");

        for (CodeContainer elem : node.getChildrenInContext(CodeExpressionStatement.CB_EXPRESSION_BODY)) {
            super.visit(elem);
        }
        c_writer.println(";");

        return 0;
    }

    @Override
    public Integer visitCodeReturnStatement(CodeReturnStatement node) {
        System.out.println("CodeVisitableElement -> CodeReturnStatement");

        c_writer.print(addTabs(nesting_lvl)+"return ");
        tmp_nesting_lvl = nesting_lvl;
        nesting_lvl = 0;
        for (CodeContainer elem : node.getChildrenInContext(CodeReturnStatement.CB_EXPRESSION_BODY)) {
            super.visit(elem);
        }
        nesting_lvl = tmp_nesting_lvl;
        c_writer.println(";");

        return 0;
    }

    @Override
    public Integer visitCodeIfStatement(CodeIfStatement node) {
        System.out.println("CodeVisitableElement -> CodeIfStatement");

        c_writer.print(addTabs(nesting_lvl)+"if(");
        for (CodeContainer elem : node.getChildrenInContext(CodeIfStatement.CB_IF_CONDITION)) {
            super.visit(elem);
        }
        c_writer.print(")");

        for (CodeContainer elem : node.getChildrenInContext(CodeIfStatement.CB_IF_BODY)) {
            super.visit(elem);
        }

        return 0;
    }

    @Override
    public Integer visitCodeWhileStatement(CodeWhileStatement node) {
        System.out.println("CodeVisitableElement -> CodeWhileStatement");

        c_writer.print(addTabs(nesting_lvl)+"while(");
        for (CodeContainer elem : node.getChildrenInContext(CodeWhileStatement.CB_WHILE_CONDITION)) {
            super.visit(elem);
        }
        c_writer.print(")");

        for (CodeContainer elem : node.getChildrenInContext(CodeWhileStatement.CB_WHILE_BODY)) {
            super.visit(elem);
        }

        return 0;
    }

    @Override
    public Integer visitCodeCondition(CodeCondition node) {
        System.out.println("CodeVisitableElement -> CodeCondition");

        tmp_nesting_lvl = nesting_lvl;
        nesting_lvl = 0;
        for (CodeContainer elem : node.getChildrenInContext(CodeCondition.CB_CONDITION)) {
            super.visit(elem);
        }
        nesting_lvl = tmp_nesting_lvl;

        return 0;
    }

    @Override
    public Integer visitCodeCompoundStatement(CodeCompoundStatement node) {
        System.out.println("CodeVisitableElement -> CodeCompoundStatement");

        c_writer.println("{");
        nesting_lvl++;
        for (CodeContainer elem : node.getChildrenInContext(CodeCompoundStatement.CB_COMPOUND_BODY)) {
            super.visit(elem);
        }
        nesting_lvl--;
        c_writer.println(addTabs(nesting_lvl)+"}");

        return 0;
    }

    @Override
    public Integer visitCodeRepository(CodeRepository node) {
        c_writer.print(addTabs(nesting_lvl)+node.code);
        return 0;
    }
}
