package org.lkop.MINIC2C;

import org.lkop.MINIC2C.treecomponents.BaseTreeElement;
import org.lkop.MINIC2C.treecomponents.VisitableBaseTreeElement;

import java.util.ArrayList;
import java.util.List;


public class ContextedElement<T> extends VisitableBaseTreeElement {

    private int context;

    public ContextedElement(int context) {
        this.context = context;
    }

    public void addChild(ContextedElement child) {
        super.addChild(child, child.context);
    }

    public void addChild(ContextedElement child, int context) {
        super.addChild(child, context);
    }

//    @Override
//    public ContextedElement getChild(int context) {
//        for (BaseTreeElement elem : super.getChildren() ){
//            ContextedElement cast_elem = (ContextedElement)elem;
//            if (cast_elem.context == context) {
//                return cast_elem;
//            }
//        }
//        return null;
//    }

    @Override
    public ContextedElement getParent(int pos) {
        return (ContextedElement)super.getParent(pos);
    }

    public <T extends ContextedElement> List<T> getChildrenInContext(int context) {
        List<T> list = new ArrayList<>();

        for (BaseTreeElement elem : super.getChildren(context) ){
            T cast_elem = (T)elem;
            //if (cast_elem.getContext() == context) {
                list.add(cast_elem);
            //}
        }
        return list;
    }

    public <T extends ContextedElement> List<T> getChildrenInContext(int context, int pos) {
        List<T> list = new ArrayList<>();

        int i=0;
        for (BaseTreeElement elem : super.getChildren(context) ){
            T cast_elem = (T)elem;
            //if (cast_elem.getContext() == context && i >= pos) {
            if (i >= pos) {
                list.add(cast_elem);
                i++;
            }
        }
        return list;
    }

    public int getContext() {
        return context;
    }
}
