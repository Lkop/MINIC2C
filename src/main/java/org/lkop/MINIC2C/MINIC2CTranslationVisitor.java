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

        //Creating empty containers
        new_node.addChild(new CodeContainer(CodeNodeType.CB_CODEREPOSITORY, "Preprocessor", CodeFile.CC_FILE_PREPROCESSOR));
        new_node.addPreprocessorCode("geiaaaa");

        new_node.addChild(new CodeContainer(CodeNodeType.CB_CODEREPOSITORY, "All_globals", CodeFile.CC_FILE_GLOBALS));

        parents.push(new_node);
        parents_ctx.push(CodeFile.CC_FILE_FUNCTIONDEFINITION);
        for (ASTElement elem : node.getChildrenInContext(CCompileUnit.CT_COMPILEUNIT_STATEMENTS)) {
            super.visit(elem);
        }
        parents_ctx.pop();
        parents.pop();

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
    public Integer visitCIf(CIf node) {
        CodeContainer parent = parents.peek();

        CodeIfStatement new_node = new CodeIfStatement(parents_ctx.peek());
        parent.addChild(new_node);

        parents.push(new_node);
        parents_ctx.push(CodeIfStatement.CB_IF_CONDITION);
        for (ASTElement elem : node.getChildrenInContext(CIf.CT_IF_EXPRESSION)) {
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
        for (ASTElement elem : node.getChildrenInContext(CWhile.CT_WHILE_EXPRESSION)) {
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
    public Integer visitCAssignment(CAssignment node) {
        CodeRepository new_node = new CodeRepository(parents_ctx.peek());

        CodeContainer parent = parents.peek();
        parent.addChild(new_node);

        code_file.declareGlobalVariable("abc");

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

        parent.addCode(new_node);
        return 0;
    }

    @Override
    public Integer visitCGt(CGt node) {
        return super.visitCGt(node);
    }

    @Override
    public Integer visitCGte(CGte node) {
        return super.visitCGte(node);
    }

    @Override
    public Integer visitCLt(CLt node) {
        return super.visitCLt(node);
    }

    @Override
    public Integer visitCLte(CLte node) {
        return super.visitCLte(node);
    }

    @Override
    public Integer visitCEqual(CEqual node) {
        return super.visitCEqual(node);
    }

    @Override
    public Integer visitCNequal(CNequal node) {
        CodeRepository new_node = new CodeRepository(parents_ctx.peek());

        CodeContainer parent = parents.peek();
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
        return 0;
    }
}
