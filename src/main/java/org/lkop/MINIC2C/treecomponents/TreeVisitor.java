package org.lkop.MINIC2C.treecomponents;


import org.lkop.MINIC2C.treecomponents.VisitableBaseTreeElement;

public interface TreeVisitor {
    <T> T visit(VisitableBaseTreeElement node);
}
