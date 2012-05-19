package org.randoom.setlx.statements;

import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.expressions.Expr;
import org.randoom.setlx.types.Om;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.Environment;

/*
grammar rule:
statement
    : [...]
    | anyExpr ';'
    ;

implemented here as:
      =======
       mExpr
*/

public class ExpressionStatement extends Statement {
    private Expr   mExpr;

    public ExpressionStatement(Expr expression) {
        mExpr   = expression;
    }

    public void exec() throws SetlException {
        Value v = mExpr.eval();
        if (Environment.isPrintAfterEval() && (v != Om.OM || !((Om) v).isHidden() )) {
            Environment.outWriteLn("Result: " + v);
        }
    }

    /* string operations */

    public String toString(int tabs) {
        return Environment.getLineStart(tabs) + mExpr.toString(tabs) + ";";
    }

    /* term operations */

    public Value toTerm() {
        return mExpr.toTerm();
    }
}

