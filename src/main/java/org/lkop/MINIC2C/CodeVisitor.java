package org.lkop.MINIC2C;

import org.lkop.MINIC2C.treecomponents.BaseVisitor;

public class CodeVisitor<T> extends BaseVisitor<T> {

    public T visitCodeFile(CodeFile node) {
        return super.visitChildren(node);
    }

    public T visitCodeIfStatement(CodeIfStatement node) {
        return super.visitChildren(node);
    }
}
