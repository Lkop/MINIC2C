package org.lkop.MINIC2C;

import org.lkop.MINIC2C.treecomponents.BaseVisitor;


public abstract class ASTVisitor<T> extends BaseVisitor<T> {

    public T visitCCompileUnit(CCompileUnit node) {
        return super.visitChildren(node);
    }

    public T visitCIf(CIf node) {
        return super.visitChildren(node);
    }

    public T visitCAssignment(CAssignment node) {
        return super.visitChildren(node);
    }

    public T visitCNUMBER(CNUMBER node) {
        return null;
    }

    public T visitCIDENTIFIER(CIDENTIFIER node) {
        return null;
    }
}
