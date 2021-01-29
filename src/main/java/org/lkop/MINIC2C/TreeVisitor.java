package org.lkop.MINIC2C;


public interface TreeVisitor {
    <T> T visit(ASTVisitableElement node);
}
