package org.lkop.MINIC2C.treecomponents;

import java.util.List;


public abstract class BaseVisitor<T> implements TreeVisitor {

    private BaseTreeElement parent = null;

    @Override
    public T visit(VisitableBaseTreeElement node) {
        return (T) node.accept(this);
    }

    public T visitChildren(BaseTreeElement node) {
        BaseTreeElement oldParent= parent;
        parent = node;
        T netResult = null;

        if (node.getChildren() != null) {
            for (List<BaseTreeElement> lchild : node.getChildren()) {
                for (BaseTreeElement child : lchild) {
                    VisitableBaseTreeElement element = (VisitableBaseTreeElement) child;
                    netResult = (T) element.accept(this);
                }
            }
            parent = oldParent;
        }
        return netResult;
    }

//    public virtual T AggregateResult(T oldResult, T value) {
//        return value;
//    }
}

