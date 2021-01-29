package org.lkop.MINIC2C.treecomponents;

import org.lkop.MINIC2C.ASTBaseVisitor;

public interface Visitable {
     <T> T accept(ASTBaseVisitor<? extends T> visitor);
}