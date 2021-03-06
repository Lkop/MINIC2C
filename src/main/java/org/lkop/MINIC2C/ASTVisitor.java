package org.lkop.MINIC2C;

import org.lkop.MINIC2C.treecomponents.BaseVisitor;


public abstract class ASTVisitor<T> extends BaseVisitor<T> {

    public T visitCCompileUnit(CCompileUnit node) {
        return super.visitChildren(node);
    }

    public T visitCFunctionDefinition(CFunctionDefinition node) {
        return super.visitChildren(node);
    }

    public T visitCExpressionStatement(CExpressionStatement node) {
        return super.visitChildren(node);
    }

    public T visitCReturnStatement(CReturnStatement node) {
        return super.visitChildren(node);
    }

    public T visitCBreakStatement(CBreakStatement node) {
        return super.visitChildren(node);
    }

    public T visitCIf(CIf node) {
        return super.visitChildren(node);
    }

    public T visitCWhile(CWhile node) {
        return super.visitChildren(node);
    }

    public T visitCDoWhile(CDoWhile node) {
        return super.visitChildren(node);
    }

    public T visitCForLoop(CForLoop node) {
        return super.visitChildren(node);
    }

    public T visitCCompound(CCompound node) {
        return super.visitChildren(node);
    }

    public T visitCCondition(CCondition node) {
        return super.visitChildren(node);
    }

    public T visitCFunctionCall(CFunctionCall node) {
        return super.visitChildren(node);
    }

    public T visitCDivision(CDivision node) {
        return super.visitChildren(node);
    }

    public T visitCMultiplication(CMultiplication node) {
        return super.visitChildren(node);
    }

    public T visitCAddition(CAddition node) {
        return super.visitChildren(node);
    }

    public T visitCSubtraction(CSubtraction node) {
        return super.visitChildren(node);
    }

    public T visitCUnaryPlus(CUnaryPlus node) {
        return super.visitChildren(node);
    }

    public T visitCUnaryMinus(CUnaryMinus node) {
        return super.visitChildren(node);
    }

    public T visitCAssignment(CAssignment node) {
        return super.visitChildren(node);
    }

    public T visitCArrayElementAssignment(CArrayElementAssignment node) {
        return super.visitChildren(node);
    }

    public T visitCNot(CNot node) {
        return super.visitChildren(node);
    }

    public T visitCAnd(CAnd node) {
        return super.visitChildren(node);
    }

    public T visitCOr(COr node) {
        return super.visitChildren(node);
    }

    public T visitCGt(CGt node) {
        return super.visitChildren(node);
    }

    public T visitCGte(CGte node) {
        return super.visitChildren(node);
    }

    public T visitCLt(CLt node) {
        return super.visitChildren(node);
    }

    public T visitCLte(CLte node) {
        return super.visitChildren(node);
    }

    public T visitCEqual(CEqual node) {
        return super.visitChildren(node);
    }

    public T visitCNequal(CNequal node) {
        return super.visitChildren(node);
    }

    public T visitCDeclarationArray(CDeclarationArray node) {
        return super.visitChildren(node);
    }

    public T visitCNUMBER(CNUMBER node) {
        return null;
    }

    public T visitCIDENTIFIER(CIDENTIFIER node) {
        return null;
    }
}
