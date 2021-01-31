package org.lkop.MINIC2C;

import org.lkop.MINIC2C.treecomponents.BaseTreeElement;
import org.lkop.MINIC2C.treecomponents.BaseVisitor;
import org.lkop.MINIC2C.treecomponents.VisitableBaseTreeElement;

import java.util.List;

public class CodeContainer extends ContextedElement<CodeContainer> {
    private CodeNodeType node_type;
    private String name;
    private int context;
    //public StringBuilder code;
    public String code="";

    public CodeContainer(CodeNodeType node_type, String name, int context) {
        super(context);
        this.node_type = node_type;
        this.name = name;

        //code = new StringBuilder();
    }

    public void addCode(String code){
        //this.code.append(code);
        this.code += code;
    }

    public void addCode(CodeContainer cc){
        //this.code.append(code);
        this.code += cc.code;
    }

    @Override
    public CodeContainer getChild(int context) {
        return (CodeContainer)super.getChild(context);
    }

    @Override
    public CodeContainer getParent(int pos) {
        return (CodeContainer)super.getParent(pos);
    }

    @Override
    public List<CodeContainer> getChildrenInContext(int context) {
        return super.getChildrenInContext(context);
    }

    @Override
    public List<CodeContainer> getChildrenInContext(int context, int pos) {
        return super.getChildrenInContext(context, pos);
    }

    public String getName() {
        return name;
    }

    public String getGraphvizName(){
        return name+"_"+getSerialId();
    }
}

class CodeFile extends CodeContainer {
    public static final int CC_FILE_PREPROCESSOR = 0, CC_FILE_GLOBALS = 1, CC_FILE_FUNDEF = 2;
    public static final String[] context_names = {
        "PREPROCESSOR_CONTEXT" ,"GLOBALS_CONTEXT", "FUNDEFS_CONTEXT"
    };

    public CodeFile(int context) {
        super(CodeNodeType.CB_FILE, "CodeFile", context);
    }

    public void addPreprocessorCode(String prep_code) {
//        CodeContainer rep = new CodeContainer(CodeNodeType.CB_CODEREPOSITORY, "global_var", CC_FILE_GLOBALS);
//        rep.addCode(prep_code+";\n");
        getChild(CC_FILE_PREPROCESSOR).addCode(prep_code);
    }

    public void declareGlobalVariable(String varname) {
//        CodeContainer rep = new CodeContainer(CodeNodeType.CB_CODEREPOSITORY, "global_var", CC_FILE_GLOBALS);
//        rep.addCode("float "+varname+";\n");
        getChild(CC_FILE_GLOBALS).addCode("float "+varname+";\n");
    }

    @Override
    public <T> T accept(BaseVisitor<? extends T> visitor) {
        CodeVisitor v = (CodeVisitor)visitor;
        if (v != null) {
            return (T) v.visitCodeFile(this);
        }
        return null;
    }
}

class CodeIfStatement extends CodeContainer {
    public static final int CB_IF_STATEMENT_CONDITION = 0, CB_IF_STATEMENT_BODY = 1, CB_ELSE_STATEMENT_BODY = 2;
    public static final String[] context_names = {
        "IF_STATEMENT_CONDITION" ,"IF_STATEMENT_BODY", "ELSE_STATEMENT_BODY"
    };

    public CodeIfStatement(int context) {
        super(CodeNodeType.CB_IFSTATEMENT, "CodeIfStatement", context);
    }

    @Override
    public <T> T accept(BaseVisitor<? extends T> visitor) {
        CodeVisitor v = (CodeVisitor)visitor;
        if (v != null) {
            return (T) v.visitCodeIfStatement(this);
        }
        return null;
    }
}

class CodeWhileStatement extends CodeContainer {
    public static final int CB_IF_EXPRESSION = 0, CB_IF_STATEMENT = 1, CB_ELSE_STATEMENT = 2;
    public static final String[] context_names = {
        "IF_EXPRESSION" ,"IF_STATEMENT", "ELSE_STATEMENT"
    };

    public CodeWhileStatement(int context) {
        super(CodeNodeType.CB_WHILESTATEMENT, "CodeWhileStatement", context);
    }
}

class CodeAssignment extends CodeContainer {

    public CodeAssignment(int context) {
        super(CodeNodeType.CB_CODEREPOSITORY, "CodeAssignment", context);
    }
}


