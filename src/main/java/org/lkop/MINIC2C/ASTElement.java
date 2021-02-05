package org.lkop.MINIC2C;

import org.lkop.MINIC2C.treecomponents.BaseVisitor;

import java.util.List;


public class ASTElement extends ContextedElement<ASTElement> {
    private ASTNodeType node_type;
    private String name;

    public ASTElement(ASTNodeType node_type, String name, int context) {
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
    public static final int CT_COMPILEUNIT_STATEMENTS = 0, CT_COMPILEUNIT_FUNCTIONDEFINITIONS = 1;
    public static String[] context_names = {
        "STATEMENTS_CONTEXT", "FUNCTION_DEFINITIONS_CONTEXT"
    };

    public CCompileUnit(int context) {
        super(ASTNodeType.NT_COMPILEUNIT, "CCompileunit", context);
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

class CFuntionDefinition extends ASTElement {
    public static final int CT_NAME = 0, CT_ARGS = 1, CT_BODY = 2;
    public static String[] context_names = {
        "FUNCTION_NAME_CONTEXT", "FUNCTION_ARGS_CONTEXT", "FUNCTION_BODY_CONTEXT"
    };

    public CFuntionDefinition(int context) {
        super(ASTNodeType.NT_FUNCTIONDEFINITION, "CFuntionDefinition", context);
    }

    @Override
    public <T> T accept(BaseVisitor<? extends T> visitor) {
        ASTVisitor v = (ASTVisitor)visitor;
        if (v != null) {
            return (T) v.visitCFunctionDefinition(this);
        }
        return null;
    }
}

class CExpressionStatement extends ASTElement {
    public static final int CT_EXRESSION = 0;
    public static String[] context_names = {
        "EXPRESSION_EXPRESSION_CONTEXT"
    };

    public CExpressionStatement(int context) {
        super(ASTNodeType.NT_EXPRESSIONSTATEMENT, "CExpressionStatement", context);
    }

    @Override
    public <T> T accept(BaseVisitor<? extends T> visitor) {
        ASTVisitor v = (ASTVisitor)visitor;
        if (v != null) {
            return (T) v.visitCExpressionStatement(this);
        }
        return null;
    }
}

class CReturnStatement extends ASTElement {
    public static final int CT_EXRESSION = 0;
    public static String[] context_names = {
        "RETURN_EXPRESSION_CONTEXT"
    };

    public CReturnStatement(int context) {
        super(ASTNodeType.NT_RETURNSTATEMENT, "CReturnStatement", context);
    }

    @Override
    public <T> T accept(BaseVisitor<? extends T> visitor) {
        ASTVisitor v = (ASTVisitor)visitor;
        if (v != null) {
            return (T) v.visitCReturnStatement(this);
        }
        return null;
    }
}

class CBreakStatement extends ASTElement {
    public static final int CT_BREAK = 0;
    public static String[] context_names = {
        "BREAK"
    };

    public CBreakStatement(int context) {
        super(ASTNodeType.NT_BREAKSTATEMENT, "CBreakStatement", context);
    }

    @Override
    public <T> T accept(BaseVisitor<? extends T> visitor) {
        ASTVisitor v = (ASTVisitor)visitor;
        if (v != null) {
            return (T) v.visitCBreakStatement(this);
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
        super(ASTNodeType.NT_IFSTATEMENT, "CIf", context);
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

class CWhile extends ASTElement {
    public static final int CT_WHILE_EXPRESSION = 0, CT_WHILE_STATEMENT = 1;
    public static final String[] context_names = {
        "WHILE_EXPRESSION_CONTEXT", "WHILE_STATEMENT_CONTEXT"
    };

    public CWhile(int context) {
        super(ASTNodeType.NT_WHILESTATEMENT, "CWhile", context);
    }

    @Override
    public <T> T accept(BaseVisitor<? extends T> visitor) {
        ASTVisitor v = (ASTVisitor)visitor;
        if (v != null) {
            return (T) v.visitCWhile(this);
        }
        return null;
    }
}

class CCompound extends ASTElement {
    public static final int CT_COMPOUND_STATEMENTSLIST = 0;
    public static final String[] context_names = {
        "COMPOUND_STATEMENTSLIST_CONTEXT"
    };

    public CCompound(int context) {
        super(ASTNodeType.NT_COMPOUNDSTATEMENT, "CCompound", context);
    }

    @Override
    public <T> T accept(BaseVisitor<? extends T> visitor) {
        ASTVisitor v = (ASTVisitor)visitor;
        if (v != null) {
            return (T) v.visitCCompound(this);
        }
        return null;
    }
}

class CFunctionCall extends ASTElement {
    public static final int CT_ARGS = 0;
    public static final String[] context_names = {
        "FCALL_ARGS_CONTEXT"
    };

    public CFunctionCall(int context) {
        super(ASTNodeType.NT_FUNCTIONCALL, "CFunctionCall", context);
    }

    @Override
    public <T> T accept(BaseVisitor<? extends T> visitor) {
        ASTVisitor v = (ASTVisitor)visitor;
        if (v != null) {
            return (T) v.visitCFunctionCall(this);
        }
        return null;
    }
}

class CDivision extends ASTElement {
    public static final int CT_LEFT = 0, CT_RIGHT = 1;
    public static final String[] context_names = {
        "DIVISION_LEFT_CONTEXT", "DIVISION_RIGHT_CONTEXT"
    };

    public CDivision(int context) {
        super(ASTNodeType.NT_DIVISION, "CDivision", context);
    }

    @Override
    public <T> T accept(BaseVisitor<? extends T> visitor) {
        ASTVisitor v = (ASTVisitor)visitor;
        if (v != null) {
            return (T) v.visitCDivision(this);
        }
        return null;
    }
}

class CMultiplication extends ASTElement {
    public static final int CT_LEFT = 0, CT_RIGHT = 1;
    public static final String[] context_names = {
        "MULTIPLICATION_LEFT_CONTEXT", "MULTIPLICATION_RIGHT_CONTEXT"
    };

    public CMultiplication(int context) {
        super(ASTNodeType.NT_MULTIPLICATION, "CMultiplication", context);
    }

    @Override
    public <T> T accept(BaseVisitor<? extends T> visitor) {
        ASTVisitor v = (ASTVisitor)visitor;
        if (v != null) {
            return (T) v.visitCMultiplication(this);
        }
        return null;
    }
}

class CAddition extends ASTElement {
    public static final int CT_LEFT = 0, CT_RIGHT = 1;
    public static final String[] context_names = {
        "ADDITION_LEFT_CONTEXT", "ADDITION_RIGHT_CONTEXT"
    };

    public CAddition(int context) {
        super(ASTNodeType.NT_ADDITION, "CAddition", context);
    }

    @Override
    public <T> T accept(BaseVisitor<? extends T> visitor) {
        ASTVisitor v = (ASTVisitor)visitor;
        if (v != null) {
            return (T) v.visitCAddition(this);
        }
        return null;
    }
}

class CSubtraction extends ASTElement {
    public static final int CT_LEFT = 0, CT_RIGHT = 1;
    public static final String[] context_names = {
        "SUBTRACTION_LEFT_CONTEXT", "SUBTRACTION_RIGHT_CONTEXT"
    };

    public CSubtraction(int context) {
        super(ASTNodeType.NT_SUBTRACTION, "CSubtraction", context);
    }

    @Override
    public <T> T accept(BaseVisitor<? extends T> visitor) {
        ASTVisitor v = (ASTVisitor)visitor;
        if (v != null) {
            return (T) v.visitCSubtraction(this);
        }
        return null;
    }
}

class CUnaryPlus extends ASTElement {
    public static final int CT_RIGHT = 0;
    public static final String[] context_names = {
        "UNARYPLUS_RIGHT_CONTEXT"
    };

    public CUnaryPlus(int context) {
        super(ASTNodeType.NT_UNARYPLUS, "CUnaryPlus", context);
    }

    @Override
    public <T> T accept(BaseVisitor<? extends T> visitor) {
        ASTVisitor v = (ASTVisitor)visitor;
        if (v != null) {
            return (T) v.visitCUnaryPlus(this);
        }
        return null;
    }
}

class CUnaryMinus extends ASTElement {
    public static final int CT_RIGHT = 0;
    public static final String[] context_names = {
        "UNARYMINUS_RIGHT_CONTEXT"
    };

    public CUnaryMinus(int context) {
        super(ASTNodeType.NT_UNARYMINUS, "CUnaryMinus", context);
    }

    @Override
    public <T> T accept(BaseVisitor<? extends T> visitor) {
        ASTVisitor v = (ASTVisitor)visitor;
        if (v != null) {
            return (T) v.visitCUnaryMinus(this);
        }
        return null;
    }
}

class CAssignment extends ASTElement {
    public static final int CT_LEFT = 0, CT_RIGHT = 1;
    public static final String[] context_names = {
        "ASSIGNMENT_LEFT_CONTEXT", "ASSIGNMENT_RIGHT_CONTEXT"
    };

    public CAssignment(int context) {
        super(ASTNodeType.NT_ASSIGNMENT, "CAssignment", context);
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

class CNot extends ASTElement {
    public static final int  CT_RIGHT = 0;
    public static final String[] context_names = {
        "OR_RIGHT_CONTEXT"
    };

    public CNot(int context) {
        super(ASTNodeType.NT_NOT, "CNot", context);
    }

    @Override
    public <T> T accept(BaseVisitor<? extends T> visitor) {
        ASTVisitor v = (ASTVisitor)visitor;
        if (v != null) {
            return (T) v.visitCNot(this);
        }
        return null;
    }
}

class CAnd extends ASTElement {
    public static final int  CT_LEFT = 0, CT_RIGHT = 1;
    public static final String[] context_names = {
        "AND_LEFT_CONTEXT", "AND_RIGHT_CONTEXT"
    };

    public CAnd(int context) {
        super(ASTNodeType.NT_AND, "CAnd", context);
    }

    @Override
    public <T> T accept(BaseVisitor<? extends T> visitor) {
        ASTVisitor v = (ASTVisitor)visitor;
        if (v != null) {
            return (T) v.visitCAnd(this);
        }
        return null;
    }
}

class COr extends ASTElement {
    public static final int  CT_LEFT = 0, CT_RIGHT = 1;
    public static final String[] context_names = {
        "OR_LEFT_CONTEXT", "OR_RIGHT_CONTEXT"
    };

    public COr(int context) {
        super(ASTNodeType.NT_OR, "COr", context);
    }

    @Override
    public <T> T accept(BaseVisitor<? extends T> visitor) {
        ASTVisitor v = (ASTVisitor)visitor;
        if (v != null) {
            return (T) v.visitCOr(this);
        }
        return null;
    }
}

class CGt extends ASTElement {
    public static final int  CT_LEFT = 0, CT_RIGHT = 1;
    public static final String[] context_names = {
        "GT_LEFT_CONTEXT", "GT_RIGHT_CONTEXT"
    };

    public CGt(int context) {
        super(ASTNodeType.NT_GT, "CGt", context);
    }

    @Override
    public <T> T accept(BaseVisitor<? extends T> visitor) {
        ASTVisitor v = (ASTVisitor)visitor;
        if (v != null) {
            return (T) v.visitCGt(this);
        }
        return null;
    }
}

class CGte extends ASTElement {
    public static final int  CT_LEFT = 0, CT_RIGHT = 1;
    public static final String[] context_names = {
        "GTE_LEFT_CONTEXT", "GTE_RIGHT_CONTEXT"
    };

    public CGte(int context) {
        super(ASTNodeType.NT_GTE, "CGte", context);
    }

    @Override
    public <T> T accept(BaseVisitor<? extends T> visitor) {
        ASTVisitor v = (ASTVisitor)visitor;
        if (v != null) {
            return (T) v.visitCGte(this);
        }
        return null;
    }
}

class CLt extends ASTElement {
    public static final int  CT_LEFT = 0, CT_RIGHT = 1;
    public static final String[] context_names = {
        "LT_LEFT_CONTEXT", "LT_RIGHT_CONTEXT"
    };

    public CLt(int context) {
        super(ASTNodeType.NT_LT, "CLt", context);
    }

    @Override
    public <T> T accept(BaseVisitor<? extends T> visitor) {
        ASTVisitor v = (ASTVisitor)visitor;
        if (v != null) {
            return (T) v.visitCLt(this);
        }
        return null;
    }
}

class CLte extends ASTElement {
    public static final int  CT_LEFT = 0, CT_RIGHT = 1;
    public static final String[] context_names = {
        "LTE_LEFT_CONTEXT", "LTE_RIGHT_CONTEXT"
    };

    public CLte(int context) {
        super(ASTNodeType.NT_LTE, "CLte", context);
    }

    @Override
    public <T> T accept(BaseVisitor<? extends T> visitor) {
        ASTVisitor v = (ASTVisitor)visitor;
        if (v != null) {
            return (T) v.visitCLte(this);
        }
        return null;
    }
}

class CEqual extends ASTElement {
    public static final int  CT_LEFT = 0, CT_RIGHT = 1;
    public static final String[] context_names = {
        "EQUAL_LEFT_CONTEXT", "EQUAL_RIGHT_CONTEXT"
    };

    public CEqual(int context) {
        super(ASTNodeType.NT_EQUAL, "CEqual", context);
    }

    @Override
    public <T> T accept(BaseVisitor<? extends T> visitor) {
        ASTVisitor v = (ASTVisitor)visitor;
        if (v != null) {
            return (T) v.visitCEqual(this);
        }
        return null;
    }
}

class CNequal extends ASTElement {
    public static final int  CT_LEFT = 0, CT_RIGHT = 1;
    public static final String[] context_names = {
        "NEQUAL_LEFT_CONTEXT", "NEQUAL_RIGHT_CONTEXT"
    };

    public CNequal(int context) {
        super(ASTNodeType.NT_NEQUAL, "CNequal", context);
    }

    @Override
    public <T> T accept(BaseVisitor<? extends T> visitor) {
        ASTVisitor v = (ASTVisitor)visitor;
        if (v != null) {
            return (T) v.visitCNequal(this);
        }
        return null;
    }
}

class CIDENTIFIER extends ASTElement {
    private String value;

    public CIDENTIFIER(int context, String value) {
        super(ASTNodeType.NT_IDENTIFIER, "CIDENTIFIER", context);
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
        super(ASTNodeType.NT_NUMBER, "CNUMBER", context);
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