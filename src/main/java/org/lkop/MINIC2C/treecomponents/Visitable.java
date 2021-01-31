package org.lkop.MINIC2C.treecomponents;

public interface Visitable {
     <T> T accept(BaseVisitor<? extends T> visitor);
}