package org.lkop.MINIC2C;

import org.lkop.MINIC2C.treecomponents.BaseTreeElement;

public class CodeContainer extends BaseTreeElement {
    private CodeNodeType node_type;
    private String name;
    private int context;
    //public StringBuilder code;
    public String code="";

    public CodeContainer(CodeNodeType node_type, String name, int context) {
        this.node_type = node_type;
        this.name = name;

        if(context != -1) {
            this.context = context;
        }

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
        for (BaseTreeElement elem : super.getChildren() ){
            CodeContainer cast_elem = (CodeContainer)elem;
            if (cast_elem.context == context) {
                return cast_elem;
            }
        }
        return null;
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


}

class CodeIfStatement extends CodeContainer {
    public static final int CB_IF_STATEMENT_CONDITION = 0, CB_IF_STATEMENT_BODY = 1, CB_ELSE_STATEMENT = 2;
    public static final String[] context_names = {
        "IF_EXPRESSION" ,"IF_STATEMENT", "ELSE_STATEMENT"
    };

    public CodeIfStatement(int context) {
        super(CodeNodeType.CB_IFSTATEMENT, "CodeIfStatement", context);
    }
}

class CodeWhileStatement extends CodeContainer {
    //public static final int CB_IF_EXPRESSION = 0, CB_IF_STATEMENT = 1, CB_ELSE_STATEMENT = 2;
//    public static final String[] context_names = {
//        "IF_EXPRESSION" ,"IF_STATEMENT", "ELSE_STATEMENT"
//    };

    public CodeWhileStatement(int context) {
        super(CodeNodeType.CB_CODEREPOSITORY, "CodeWhileStatement", context);
    }
}

class CodeAssignment extends CodeContainer {

    public CodeAssignment(int context) {
        super(CodeNodeType.CB_CODEREPOSITORY, "CodeAssignment", context);
    }
}


