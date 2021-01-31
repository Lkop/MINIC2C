package org.lkop.MINIC2C;

import org.lkop.MINIC2C.treecomponents.BaseTreeElement;
import org.lkop.MINIC2C.treecomponents.BaseVisitor;
import org.lkop.MINIC2C.treecomponents.VisitableBaseTreeElement;

import java.util.ArrayList;
import java.util.List;


public class ASTElement extends ContextedElement<ASTElement> {
    private NodeType node_type;
    private String name;

    public ASTElement(NodeType node_type, String name, int context) {
        super(context);
        this.node_type = node_type;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getGraphvizName(){
        return name+"_"+getSerialId();
    }

    @Override
    public ASTElement getChild(int context) {
        return (ASTElement)super.getChild(context);
    }

    @Override
    public ASTElement getParent(int pos) {
        return (ASTElement)super.getParent(pos);
    }

    @Override
    public List<ASTElement> getChildrenInContext(int context) {
        return super.getChildrenInContext(context);
    }

    @Override
    public List<ASTElement> getChildrenInContext(int context, int pos) {
        return super.getChildrenInContext(context, pos);
    }
}

class CCompileUnit extends ASTElement {
    public static final int CT_COMPILEUNIT_STATEMENTS = 0, CT_COMPILEUNIT_FUNDEFS = 1;
    public static String[] context_names = {
        "STATEMENTSCONTEXT", "FUNCTIONDEFINITIONSCONTEXT"
    };

    public CCompileUnit(int context) {
        super(NodeType.NT_COMPILEUNIT, "CCompileunit", context);
    }

    @Override
    public <T> T accept(BaseVisitor<? extends T> visitor) {
        ASTVisitor v = (ASTVisitor)visitor;
        if (v != null) {
            return (T) v.visitCCompileUnit(this);
        }
        return null;
    }
}

class CIf extends ASTElement {
    public static final int CT_IF_EXPRESSION = 0, CT_IF_STATEMENT = 1, CT_ELSE_STATEMENT = 2;
    public static final String[] context_names = {
        "IF_EXPRESSION_CONTEXT", "IF_STATEMENT_CONTEXT", "ELSE_STATEMENT_CONTEXT"
    };

    public CIf(int context) {
        super(NodeType.NT_IFSTATEMENT, "CIf", context);

        //m_Graphvizname = "CompileUnit" + M_Name;
    }

    @Override
    public <T> T accept(BaseVisitor<? extends T> visitor) {
        ASTVisitor v = (ASTVisitor)visitor;
        if (v != null) {
            return (T) v.visitCIf(this);
        }
        return null;
    }
}

class CAssignment extends ASTElement {
    public static final int CT_LEFT = 0, CT_RIGHT = 1;
    public static final String[] context_names = {
        "ASSIGNMENT_L", "ASSIGNMENT_R"
    };

    public CAssignment(int context) {
        super(NodeType.NT_ASSIGNMENT, "CAssignment", context);

        //m_Graphvizname = "CompileUnit" + M_Name;
    }

    @Override
    public <T> T accept(BaseVisitor<? extends T> visitor) {
        ASTVisitor v = (ASTVisitor)visitor;
        if (v != null) {
            return (T) v.visitCAssignment(this);
        }
        return null;
    }
}

class CIDENTIFIER extends ASTElement {
    private String value;

    public CIDENTIFIER(int context, String value) {
        super(NodeType.NT_IDENTIFIER, "CIDENTIFIER", context);
        this.value = value;
    }

    @Override
    public <T> T accept(BaseVisitor<? extends T> visitor) {
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

class CNUMBER extends ASTElement {
    private String value;

    public CNUMBER(int context, String value) {
        super(NodeType.NT_NUMBER, "CNUMBER", context);
        this.value = value;
    }

    @Override
    public <T> T accept(BaseVisitor<? extends T> visitor) {
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