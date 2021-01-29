package org.lkop.MINIC2C;

import org.lkop.MINIC2C.treecomponents.BaseTreeElement;

public abstract class ASTBaseVisitor<T> implements TreeVisitor {

    private ASTElement parent = null;

    @Override
    public T visit(ASTVisitableElement node) {
        return (T) node.accept(this);
    }

    public T visitChildren(ASTElement node) {
        ASTElement oldParent= parent;
        parent = node;
        T netResult = null;

        if (node.getChildren() != null) {
            for (BaseTreeElement child : node.getChildren()) {
                ASTVisitableElement element = (ASTVisitableElement) child;
                netResult = (T) element.accept(this);
            }
            parent = oldParent;
        }
        return netResult;
    }

//    public virtual T AggregateResult(T oldResult, T value) {
//        return value;
//    }
}

