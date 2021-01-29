package org.lkop.MINIC2C.treecomponents;

import java.util.ArrayList;
import java.util.List;


public class BaseTreeElement {

    //Auto increment
    private static int count = 0;

    private int serial_id;
    private List<BaseTreeElement> parents = null;
    private List<BaseTreeElement> children = null;

    public BaseTreeElement(){
        serial_id = count++;
    }

    public int getSerialId() {
        return serial_id;
    }

    public void addChild(BaseTreeElement child) {
        if (child.parents == null){
            child.parents = new ArrayList<>();
        }
        child.parents.add(this);

        if (children == null){
            children = new ArrayList<>();
        }
        children.add(child);
    }

    public List<BaseTreeElement> getChildren() {
        return children;
    }

    public BaseTreeElement getChild(int pos) {
        return children.get(pos);
    }

    public List<BaseTreeElement> getParents() {
        return parents;
    }

    public BaseTreeElement getParent(int pos) {
        return parents.get(pos);
    }
}
