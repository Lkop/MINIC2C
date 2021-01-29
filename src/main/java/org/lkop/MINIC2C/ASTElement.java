package org.lkop.MINIC2C;

import org.lkop.MINIC2C.treecomponents.BaseTreeElement;
import org.lkop.MINIC2C.treecomponents.Visitable;

import java.util.ArrayList;
import java.util.List;


public class ASTElement extends BaseTreeElement {
    private NodeType node_type;
    private String name;
    private int context;

    public ASTElement(NodeType node_type, String name, int context) {
        this.node_type = node_type;
        this.name = name;

        if(context != -1) {
            this.context = context;
        }
    }

    @Override
    public ASTElement getChild(int context) {
        for (BaseTreeElement elem : super.getChildren() ){
            ASTElement cast_elem = (ASTElement)elem;
            if (cast_elem.context == context) {
                return cast_elem;
            }
        }
        return null;
    }

    @Override
    public ASTElement getParent(int pos) {
        return (ASTElement)super.getParent(pos);
    }

    public List<ASTElement> getChildrenInContext(int context) {
        List<ASTElement> list = new ArrayList<>();

        for (BaseTreeElement elem : super.getChildren() ){
            ASTElement cast_elem = (ASTElement)elem;
            if (cast_elem.context == context) {
                list.add(cast_elem);
            }
        }
        return list;
    }

    public List<ASTElement> getChildrenInContext(int context, int pos) {
        List<ASTElement> list = new ArrayList<>();

        int i=0;
        for (BaseTreeElement elem : super.getChildren() ){
            ASTElement cast_elem = (ASTElement)elem;
            if (cast_elem.context == context && i >= pos) {
                list.add(cast_elem);
                i++;
            }
        }
        return list;
    }

    public int getContext() {
        return context;
    }

    public String getName() {
        return name;
    }

    public String getGraphvizName(){
        return name+"_"+getSerialId();
    }
}

abstract class ASTVisitableElement extends ASTElement implements Visitable {

    public ASTVisitableElement(NodeType node_type, String name, int context) {
        super(node_type, name, context);
    }
}

class CCompileUnit extends ASTVisitableElement {
    public static final int CT_COMPILEUNIT_STATEMENTS = 0, CT_COMPILEUNIT_FUNDEFS = 1;
    public static String[] context_names = {
        "STATEMENTSCONTEXT", "FUNCTIONDEFINITIONSCONTEXT"
    };

    public CCompileUnit(int context) {
        super(NodeType.NT_COMPILEUNIT, "CCompileunit", context);
    }

    @Override
    public <T> T accept(ASTBaseVisitor<? extends T> visitor) {
        ASTVisitor v = (ASTVisitor)visitor;
        if (v != null) {
            return (T) v.visitCCompileUnit(this);
        }
        return null;
    }
}

class CIf extends ASTVisitableElement {
    public static final int CT_IF_EXPRESSION = 0, CT_IF_STATEMENT = 1, CT_ELSE_STATEMENT = 2;
    public static final String[] context_names = {
        "IF_EXPRESSION_CONTEXT", "IF_STATEMENT_CONTEXT", "ELSE_STATEMENT_CONTEXT"
    };

    public CIf(int context) {
        super(NodeType.NT_IFSTATEMENT, "CIf", context);

        //m_Graphvizname = "CompileUnit" + M_Name;
    }

    @Override
    public <T> T accept(ASTBaseVisitor<? extends T> visitor) {
        ASTVisitor v = (ASTVisitor)visitor;
        if (v != null) {
            return (T) v.visitCIf(this);
        }
        return null;
    }
}

class CAssignment extends ASTVisitableElement {
    public static final int CT_LEFT = 0, CT_RIGHT = 1;
    public static final String[] context_names = {
        "ASSIGNMENT_L", "ASSIGNMENT_R"
    };

    public CAssignment(int context) {
        super(NodeType.NT_ASSIGNMENT, "CAssignment", context);

        //m_Graphvizname = "CompileUnit" + M_Name;
    }

    @Override
    public <T> T accept(ASTBaseVisitor<? extends T> visitor) {
        ASTVisitor v = (ASTVisitor)visitor;
        if (v != null) {
            return (T) v.visitCAssignment(this);
        }
        return null;
    }
}

class CIDENTIFIER extends ASTVisitableElement {
    private String value;

    public CIDENTIFIER(int context, String value) {
        super(NodeType.NT_IDENTIFIER, "CIDENTIFIER", context);
        this.value = value;
    }

    @Override
    public <T> T accept(ASTBaseVisitor<? extends T> visitor) {
        ASTVisitor v = (ASTVisitor)visitor;
        if (v != null) {
            return (T) v.visitCIDENTIFIER(this);
        }
        return null;
    }

    public String getValue() {
        return value;
    }
}

class CNUMBER extends ASTVisitableElement {
    private String value;

    public CNUMBER(int context, String value) {
        super(NodeType.NT_NUMBER, "CNUMBER", context);
        this.value = value;
    }

    @Override
    public <T> T accept(ASTBaseVisitor<? extends T> visitor) {
        ASTVisitor v = (ASTVisitor)visitor;
        if (v != null) {
            return (T) v.visitCNUMBER(this);
        }
        return null;
    }

    public String getValue() {
        return value;
    }
}