package org.lkop.MINIC2C.treecomponents;

import java.util.ArrayList;
import java.util.List;


public class BaseTreeElement {

    //Auto increment
    private static int count = 0;

    private int serial_id;
    private List<BaseTreeElement> parents = null;
    private List<List<BaseTreeElement>> children = null;
    private int add_pos=0;


    public BaseTreeElement(){
        serial_id = count++;
    }

    public int getSerialId() {
        return serial_id;
    }

    public void addChild(BaseTreeElement child, int context) {
        if (child.parents == null){
            child.parents = new ArrayList<>();
        }
        child.parents.add(this);

        if (children == null){
            children = new ArrayList<>();
        }

        children.add(new ArrayList<>());
        children.add(new ArrayList<>());
        children.add(new ArrayList<>());
        children.add(new ArrayList<>());
        children.add(new ArrayList<>());

        if (context >= 0) {
            children.get(context).add(child);
        }
    }

    public List<List<BaseTreeElement>> getChildren() {
        return children;
    }

    public List<BaseTreeElement> getChildren(int context) {
        return children.get(context);
    }

    public BaseTreeElement getChild(int pos) {
        return children.get(0).get(pos);
    }

    public List<BaseTreeElement> getParents() {
        return parents;
    }

    public BaseTreeElement getParent(int pos) {
        return parents.get(pos);
    }

    public BaseTreeElement getParentMovingNext(int pos) {
        return parents.get(pos+add_pos++);
    }
}
