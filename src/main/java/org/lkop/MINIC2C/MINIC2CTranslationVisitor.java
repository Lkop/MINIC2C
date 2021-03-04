package org.lkop.MINIC2C;

import java.util.Stack;

public class MINIC2CTranslationVisitor extends ASTVisitor<Integer>{

    private CodeFile code_file;
    private Stack<CodeContainer> parents = new Stack<>();
    private Stack<Integer> parents_ctx = new Stack<>();

    public CodeFile getRoot() {
        return code_file;
    }

    @Override
    public Integer visitCCompileUnit(CCompileUnit node) {
        CodeFile new_node = new CodeFile(-1);
        code_file = new_node;

        new_node.addPreprocessorCode("#include <stdio.h>");
        new_node.addPreprocessorCode("#include <stdlib.h>");

        new_node.declareGlobalVariable("testvariable");

        CodeMainFunctionDefinition fd = new CodeMainFunctionDefinition(CodeFile.CC_FILE_FUNCTIONDEFINITION);
        new_node.addChild(fd);

        parents.push(fd.getMainBody());
        parents_ctx.push(CodeCompoundStatement.CB_COMPOUND_BODY);
        for (ASTElement elem : node.getChildrenInContext(CCompileUnit.CT_COMPILEUNIT_STATEMENTS)) {
            super.visit(elem);
        }
        parents_ctx.pop();
        parents.pop();

        return 0;
    }

    @Override
    public Integer visitCFunctionDefinition(CFuntionDefinition node) {
        CodeContainer parent = parents.peek();

        CodeFunctionDefinition new_node = new CodeFunctionDefinition(parents_ctx.peek());
        parent.addChild(new_node);

        parents.push(new_node);
        parents_ctx.push(CodeFunctionDefinition.CC_FUNCTIONDEFINITION_HEADER);
        for (ASTElement elem : node.getChildrenInContext(CFuntionDefinition.CT_NAME)) {
            super.visit(elem);
        }
        parents_ctx.pop();
        parents.pop();

        parents.push(new_node);
        parents_ctx.push(CodeFunctionDefinition.CC_FUNCTIONDEFINITION_BODY);
        for (ASTElement elem : node.getChildrenInContext(CFuntionDefinition.CT_BODY)) {
            super.visit(elem);
        }
        parents_ctx.pop();
        parents.pop();

        parent.addCode(new_node);
        return 0;
    }

    @Override
    public Integer visitCExpressionStatement(CExpressionStatement node) {
        CodeContainer parent = parents.peek();

        CodeExpressionStatement new_node = new CodeExpressionStatement(parents_ctx.peek());
        parent.addChild(new_node);

        parents.push(new_node);
        parents_ctx.push(CodeExpressionStatement.CB_EXPRESSION_BODY);
        for (ASTElement elem : node.getChildrenInContext(CExpressionStatement.CT_EXRESSION)) {
            super.visit(elem);
        }
        parents_ctx.pop();
        parents.pop();

        new_node.addCode(";\n");

        parent.addCode(new_node);
        return 0;
    }

    @Override
    public Integer visitCReturnStatement(CReturnStatement node) {
        return super.visitCReturnStatement(node);
    }

    @Override
    public Integer visitCBreakStatement(CBreakStatement node) {
        return super.visitCBreakStatement(node);
    }

    @Override
    public Integer visitCIf(CIf node) {
        CodeContainer parent = parents.peek();

        CodeIfStatement new_node = new CodeIfStatement(parents_ctx.peek());
        parent.addChild(new_node);

        parents.push(new_node);
        parents_ctx.push(CodeIfStatement.CB_IF_CONDITION);
        for (ASTElement elem : node.getChildrenInContext(CIf.CT_IF_CONDITION)) {
            super.visit(elem);
        }
        parents_ctx.pop();
        parents.pop();

        parents.push(new_node);
        parents_ctx.push(CodeIfStatement.CB_IF_BODY);
        for (ASTElement elem : node.getChildrenInContext(CIf.CT_IF_STATEMENT)) {
            super.visit(elem);
        }
        parents_ctx.pop();
        parents.pop();

        parent.addCode(new_node);
        return 0;
    }

    @Override
    public Integer visitCWhile(CWhile node) {
        CodeContainer parent = parents.peek();

        CodeWhileStatement new_node = new CodeWhileStatement(parents_ctx.peek());
        parent.addChild(new_node);

        parents.push(new_node);
        parents_ctx.push(CodeWhileStatement.CB_WHILE_CONDITION);
        for (ASTElement elem : node.getChildrenInContext(CWhile.CT_WHILE_CONDITION)) {
            super.visit(elem);
        }
        parents_ctx.pop();
        parents.pop();

        parents.push(new_node);
        parents_ctx.push(CodeWhileStatement.CB_WHILE_BODY);
        for (ASTElement elem : node.getChildrenInContext(CWhile.CT_WHILE_STATEMENT)) {
            super.visit(elem);
        }
        parents_ctx.pop();
        parents.pop();

        parent.addCode(new_node);
        return 0;
    }

    @Override
    public Integer visitCCompound(CCompound node) {
        CodeContainer parent = parents.peek();

        CodeCompoundStatement new_node = new CodeCompoundStatement(parents_ctx.peek());
        parent.addChild(new_node);

        parents.push(new_node);
        parents_ctx.push(CodeCompoundStatement.CB_COMPOUND_BODY);
        for (ASTElement elem : node.getChildrenInContext(CCompound.CT_COMPOUND_STATEMENTSLIST)) {
            super.visit(elem);
        }
        parents_ctx.pop();
        parents.pop();

        parent.addCode(new_node);
        return 0;
    }

    @Override
    public Integer visitCCondition(CCondition node) {
        CodeContainer parent = parents.peek();

        CodeCondition new_node = new CodeCondition(parents_ctx.peek());
        parent.addChild(new_node);

        parents.push(new_node);
        parents_ctx.push(CodeCondition.CB_CONDITION);
        for (ASTElement elem : node.getChildrenInContext(CCondition.CT_CONDITION_EXPRESSION)) {
            super.visit(elem);
        }
        parents_ctx.pop();
        parents.pop();

        parent.addCode(new_node);
        return 0;
    }

    @Override
    public Integer visitCFunctionCall(CFunctionCall node) {
        CodeContainer parent = parents.peek();

        if(parent instanceof CodeRepository) {
            parent.addCode("(");
            for (ASTElement elem : node.getChildrenInContext(CFunctionCall.CT_ARGS)) {
                super.visit(elem);
            }
            parent.addCode(")");
        }else{
            CodeRepository new_node = new CodeRepository(parents_ctx.peek());
            parent.addChild(new_node);

            new_node.addCode("(");

            parents.push(new_node);
            for (ASTElement elem : node.getChildrenInContext(CFunctionCall.CT_ARGS)) {
                super.visit(elem);
            }
            parents.pop();

            new_node.addCode(")");
        }
        return 0;
    }

    @Override
    public Integer visitCDivision(CDivision node) {
        CodeContainer parent = parents.peek();

        if(parent instanceof CodeRepository) {
            for (ASTElement elem : node.getChildrenInContext(CDivision.CT_LEFT)) {
                super.visit(elem);
            }
            parent.addCode("/");
            for (ASTElement elem : node.getChildrenInContext(CDivision.CT_RIGHT)) {
                super.visit(elem);
            }
        }else{
            CodeRepository new_node = new CodeRepository(parents_ctx.peek());
            parent.addChild(new_node);

            parents.push(new_node);
            for (ASTElement elem : node.getChildrenInContext(CDivision.CT_LEFT)) {
                super.visit(elem);
            }
            parents.pop();

            new_node.addCode("/");

            parents.push(new_node);
            for (ASTElement elem : node.getChildrenInContext(CDivision.CT_RIGHT)) {
                super.visit(elem);
            }
            parents.pop();
        }
        return 0;
    }

    @Override
    public Integer visitCMultiplication(CMultiplication node) {
        CodeContainer parent = parents.peek();

        if(parent instanceof CodeRepository) {
            for (ASTElement elem : node.getChildrenInContext(CMultiplication.CT_LEFT)) {
                super.visit(elem);
            }
            parent.addCode("*");
            for (ASTElement elem : node.getChildrenInContext(CMultiplication.CT_RIGHT)) {
                super.visit(elem);
            }
        }else{
            CodeRepository new_node = new CodeRepository(parents_ctx.peek());
            parent.addChild(new_node);

            parents.push(new_node);
            for (ASTElement elem : node.getChildrenInContext(CMultiplication.CT_LEFT)) {
                super.visit(elem);
            }
            parents.pop();

            new_node.addCode("*");

            parents.push(new_node);
            for (ASTElement elem : node.getChildrenInContext(CMultiplication.CT_RIGHT)) {
                super.visit(elem);
            }
            parents.pop();
        }
        return 0;
    }

    @Override
    public Integer visitCAddition(CAddition node) {
        CodeContainer parent = parents.peek();

        if(parent instanceof CodeRepository) {
            for (ASTElement elem : node.getChildrenInContext(CAddition.CT_LEFT)) {
                super.visit(elem);
            }
            parent.addCode("+");
            for (ASTElement elem : node.getChildrenInContext(CAddition.CT_RIGHT)) {
                super.visit(elem);
            }
        }else{
            CodeRepository new_node = new CodeRepository(parents_ctx.peek());
            parent.addChild(new_node);

            parents.push(new_node);
            for (ASTElement elem : node.getChildrenInContext(CAddition.CT_LEFT)) {
                super.visit(elem);
            }
            parents.pop();

            new_node.addCode("+");

            parents.push(new_node);
            for (ASTElement elem : node.getChildrenInContext(CAddition.CT_RIGHT)) {
                super.visit(elem);
            }
            parents.pop();
        }
        return 0;
    }

    @Override
    public Integer visitCSubtraction(CSubtraction node) {
        CodeContainer parent = parents.peek();

        if(parent instanceof CodeRepository) {
            for (ASTElement elem : node.getChildrenInContext(CSubtraction.CT_LEFT)) {
                super.visit(elem);
            }
            parent.addCode("-");
            for (ASTElement elem : node.getChildrenInContext(CSubtraction.CT_RIGHT)) {
                super.visit(elem);
            }
        }else{
            CodeRepository new_node = new CodeRepository(parents_ctx.peek());
            parent.addChild(new_node);

            parents.push(new_node);
            for (ASTElement elem : node.getChildrenInContext(CSubtraction.CT_LEFT)) {
                super.visit(elem);
            }
            parents.pop();

            new_node.addCode("-");

            parents.push(new_node);
            for (ASTElement elem : node.getChildrenInContext(CSubtraction.CT_RIGHT)) {
                super.visit(elem);
            }
            parents.pop();
        }
        return 0;
    }

    @Override
    public Integer visitCUnaryPlus(CUnaryPlus node) {
        CodeContainer parent = parents.peek();

        if(parent instanceof CodeRepository) {
            parent.addCode("+");
            for (ASTElement elem : node.getChildrenInContext(CUnaryPlus.CT_RIGHT)) {
                super.visit(elem);
            }
        }else{
            CodeRepository new_node = new CodeRepository(parents_ctx.peek());
            parent.addChild(new_node);

            new_node.addCode("+");

            parents.push(new_node);
            for (ASTElement elem : node.getChildrenInContext(CUnaryPlus.CT_RIGHT)) {
                super.visit(elem);
            }
            parents.pop();
        }
        return 0;
    }

    @Override
    public Integer visitCUnaryMinus(CUnaryMinus node) {
        CodeContainer parent = parents.peek();

        if(parent instanceof CodeRepository) {
            parent.addCode("-");
            for (ASTElement elem : node.getChildrenInContext(CUnaryMinus.CT_RIGHT)) {
                super.visit(elem);
            }
        }else{
            CodeRepository new_node = new CodeRepository(parents_ctx.peek());
            parent.addChild(new_node);

            new_node.addCode("-");

            parents.push(new_node);
            for (ASTElement elem : node.getChildrenInContext(CUnaryMinus.CT_RIGHT)) {
                super.visit(elem);
            }
            parents.pop();
        }
        return 0;
    }

    @Override
    public Integer visitCAssignment(CAssignment node) {
        CodeContainer parent = parents.peek();

        if(parent instanceof CodeRepository) {
            for (ASTElement elem : node.getChildrenInContext(CAssignment.CT_LEFT)) {
                super.visit(elem);
            }
            parent.addCode("=");
            for (ASTElement elem : node.getChildrenInContext(CAssignment.CT_RIGHT)) {
                super.visit(elem);
            }
        }else {
            CodeRepository new_node = new CodeRepository(parents_ctx.peek());
            parent.addChild(new_node);

            parents.push(new_node);
            for (ASTElement elem : node.getChildrenInContext(CAssignment.CT_LEFT)) {
                super.visit(elem);
            }
            parents.pop();

            new_node.addCode("=");

            parents.push(new_node);
            for (ASTElement elem : node.getChildrenInContext(CAssignment.CT_RIGHT)) {
                super.visit(elem);
            }
            parents.pop();
        }
        return 0;
    }

    @Override
    public Integer visitCNot(CNot node) {
        CodeContainer parent = parents.peek();

        if(parent instanceof CodeRepository) {
            parent.addCode("!");
            for (ASTElement elem : node.getChildrenInContext(CNot.CT_RIGHT)) {
                super.visit(elem);
            }
        }else{
            CodeRepository new_node = new CodeRepository(parents_ctx.peek());
            parent.addChild(new_node);

            new_node.addCode("!");

            parents.push(new_node);
            for (ASTElement elem : node.getChildrenInContext(CNot.CT_RIGHT)) {
                super.visit(elem);
            }
            parents.pop();
        }
        return 0;
    }

    @Override
    public Integer visitCAnd(CAnd node) {
        CodeContainer parent = parents.peek();

        if(parent instanceof CodeRepository) {
            parent.addCode("&&");
            for (ASTElement elem : node.getChildrenInContext(CAnd.CT_RIGHT)) {
                super.visit(elem);
            }
        }else{
            CodeRepository new_node = new CodeRepository(parents_ctx.peek());
            parent.addChild(new_node);

            new_node.addCode("&&");

            parents.push(new_node);
            for (ASTElement elem : node.getChildrenInContext(CAnd.CT_RIGHT)) {
                super.visit(elem);
            }
            parents.pop();
        }
        return 0;
    }

    @Override
    public Integer visitCOr(COr node) {
        CodeContainer parent = parents.peek();

        if(parent instanceof CodeRepository) {
            parent.addCode("||");
            for (ASTElement elem : node.getChildrenInContext(COr.CT_RIGHT)) {
                super.visit(elem);
            }
        }else{
            CodeRepository new_node = new CodeRepository(parents_ctx.peek());
            parent.addChild(new_node);

            new_node.addCode("||");

            parents.push(new_node);
            for (ASTElement elem : node.getChildrenInContext(COr.CT_RIGHT)) {
                super.visit(elem);
            }
            parents.pop();
        }
        return 0;
    }

    @Override
    public Integer visitCGt(CGt node) {
        CodeContainer parent = parents.peek();

        if(parent instanceof CodeRepository) {
            for (ASTElement elem : node.getChildrenInContext(CGt.CT_LEFT)) {
                super.visit(elem);
            }
            parent.addCode(">");
            for (ASTElement elem : node.getChildrenInContext(CGt.CT_RIGHT)) {
                super.visit(elem);
            }
        }else{
            CodeRepository new_node = new CodeRepository(parents_ctx.peek());
            parent.addChild(new_node);

            parents.push(new_node);
            for (ASTElement elem : node.getChildrenInContext(CGt.CT_LEFT)) {
                super.visit(elem);
            }
            parents.pop();

            new_node.addCode(">");

            parents.push(new_node);
            for (ASTElement elem : node.getChildrenInContext(CGt.CT_RIGHT)) {
                super.visit(elem);
            }
            parents.pop();
        }
        return 0;
    }

    @Override
    public Integer visitCGte(CGte node) {
        CodeContainer parent = parents.peek();

        if(parent instanceof CodeRepository) {
            for (ASTElement elem : node.getChildrenInContext(CGte.CT_LEFT)) {
                super.visit(elem);
            }
            parent.addCode(">=");
            for (ASTElement elem : node.getChildrenInContext(CGte.CT_RIGHT)) {
                super.visit(elem);
            }
        }else{
            CodeRepository new_node = new CodeRepository(parents_ctx.peek());
            parent.addChild(new_node);

            parents.push(new_node);
            for (ASTElement elem : node.getChildrenInContext(CGte.CT_LEFT)) {
                super.visit(elem);
            }
            parents.pop();

            new_node.addCode(">=");

            parents.push(new_node);
            for (ASTElement elem : node.getChildrenInContext(CGte.CT_RIGHT)) {
                super.visit(elem);
            }
            parents.pop();
        }
        return 0;
    }

    @Override
    public Integer visitCLt(CLt node) {
        CodeContainer parent = parents.peek();

        if(parent instanceof CodeRepository) {
            for (ASTElement elem : node.getChildrenInContext(CLt.CT_LEFT)) {
                super.visit(elem);
            }
            parent.addCode("<");
            for (ASTElement elem : node.getChildrenInContext(CLt.CT_RIGHT)) {
                super.visit(elem);
            }
        }else{
            CodeRepository new_node = new CodeRepository(parents_ctx.peek());
            parent.addChild(new_node);

            parents.push(new_node);
            for (ASTElement elem : node.getChildrenInContext(CLt.CT_LEFT)) {
                super.visit(elem);
            }
            parents.pop();

            new_node.addCode("<");

            parents.push(new_node);
            for (ASTElement elem : node.getChildrenInContext(CLt.CT_RIGHT)) {
                super.visit(elem);
            }
            parents.pop();
        }
        return 0;
    }

    @Override
    public Integer visitCLte(CLte node) {
        CodeContainer parent = parents.peek();

        if(parent instanceof CodeRepository) {
            for (ASTElement elem : node.getChildrenInContext(CLte.CT_LEFT)) {
                super.visit(elem);
            }
            parent.addCode("<=");
            for (ASTElement elem : node.getChildrenInContext(CLte.CT_RIGHT)) {
                super.visit(elem);
            }
        }else{
            CodeRepository new_node = new CodeRepository(parents_ctx.peek());
            parent.addChild(new_node);

            parents.push(new_node);
            for (ASTElement elem : node.getChildrenInContext(CLte.CT_LEFT)) {
                super.visit(elem);
            }
            parents.pop();

            new_node.addCode("<=");

            parents.push(new_node);
            for (ASTElement elem : node.getChildrenInContext(CLte.CT_RIGHT)) {
                super.visit(elem);
            }
            parents.pop();
        }
        return 0;
    }

    @Override
    public Integer visitCEqual(CEqual node) {
        CodeContainer parent = parents.peek();

        if(parent instanceof CodeRepository) {
            for (ASTElement elem : node.getChildrenInContext(CEqual.CT_LEFT)) {
                super.visit(elem);
            }
            parent.addCode("==");
            for (ASTElement elem : node.getChildrenInContext(CEqual.CT_RIGHT)) {
                super.visit(elem);
            }
        }else{
            CodeRepository new_node = new CodeRepository(parents_ctx.peek());
            parent.addChild(new_node);

            parents.push(new_node);
            for (ASTElement elem : node.getChildrenInContext(CEqual.CT_LEFT)) {
                super.visit(elem);
            }
            parents.pop();

            new_node.addCode("==");

            parents.push(new_node);
            for (ASTElement elem : node.getChildrenInContext(CEqual.CT_RIGHT)) {
                super.visit(elem);
            }
            parents.pop();
        }
        return 0;
    }

    @Override
    public Integer visitCNequal(CNequal node) {
        CodeContainer parent = parents.peek();

        if(parent instanceof CodeRepository) {
            for (ASTElement elem : node.getChildrenInContext(CNequal.CT_LEFT)) {
                super.visit(elem);
            }
            parent.addCode("!=");
            for (ASTElement elem : node.getChildrenInContext(CNequal.CT_RIGHT)) {
                super.visit(elem);
            }
        }else{
            CodeRepository new_node = new CodeRepository(parents_ctx.peek());
            parent.addChild(new_node);

            parents.push(new_node);
            for (ASTElement elem : node.getChildrenInContext(CNequal.CT_LEFT)) {
                super.visit(elem);
            }
            parents.pop();

            new_node.addCode("!=");

            parents.push(new_node);
            for (ASTElement elem : node.getChildrenInContext(CNequal.CT_RIGHT)) {
                super.visit(elem);
            }
            parents.pop();
        }
        return 0;
    }

    @Override
    public Integer visitCNUMBER(CNUMBER node) {
        CodeContainer parent = parents.peek();
        parent.addCode(node.getValue());
        return 0;
    }

    @Override
    public Integer visitCIDENTIFIER(CIDENTIFIER node) {
        CodeContainer parent = parents.peek();
        parent.addCode(node.getValue());
        code_file.declareGlobalVariable(node.getValue());
        return 0;
    }
}
