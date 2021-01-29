package org.lkop.MINIC2C;

import java.util.Stack;

public class MINIC2CTranslationVisitor extends ASTVisitor{

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
        parents_ctx.push(CodeFile.CC_FILE_FUNDEF);
        for (ASTElement elem : node.getChildrenInContext(CCompileUnit.CT_COMPILEUNIT_STATEMENTS)) {
            super.visit((ASTVisitableElement)elem);
        }
        parents_ctx.pop();
        parents.pop();

        return 0;
    }

    @Override
    public Integer visitCIf(CIf node) {
        CodeContainer parent = parents.peek();

        CodeIfStatement new_node = new CodeIfStatement(parents_ctx.peek());
        parent.addChild(new_node);

        new_node.addCode("if (");
        parents.push(new_node);
        parents_ctx.push(CodeIfStatement.CB_IF_STATEMENT_CONDITION);
        for (ASTElement elem : node.getChildrenInContext(CIf.CT_IF_EXPRESSION)) {
            super.visit((ASTVisitableElement)elem);
        }
        parents_ctx.pop();
        parents.pop();

        new_node.addCode(") {\n");

        parents.push(new_node);
        parents_ctx.push(CodeIfStatement.CB_IF_STATEMENT_BODY);
        for (ASTElement elem : node.getChildrenInContext(CIf.CT_IF_STATEMENT)) {
            super.visit((ASTVisitableElement)elem);
        }
        parents_ctx.pop();
        parents.pop();

        new_node.addCode("}\n");

        parent.addCode(new_node);
        return 0;
    }

    @Override
    public Integer visitCAssignment(CAssignment node) {
        CodeAssignment new_node = new CodeAssignment(parents_ctx.peek());

        CodeContainer parent = parents.peek();
        parent.addChild(new_node);

        code_file.declareGlobalVariable("abc");

        parents.push(new_node);
        for (ASTElement elem : node.getChildrenInContext(CAssignment.CT_LEFT)) {
            super.visit((ASTVisitableElement)elem);
        }
        parents.pop();

        new_node.addCode("=");

        parents.push(new_node);
        for (ASTElement elem : node.getChildrenInContext(CAssignment.CT_RIGHT)) {
            super.visit((ASTVisitableElement)elem);
        }
        parents.pop();

        parent.addCode(new_node);
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
