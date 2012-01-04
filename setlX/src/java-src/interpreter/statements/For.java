package interpreter.statements;

import interpreter.exceptions.SetlException;
import interpreter.types.Term;
import interpreter.types.Value;
import interpreter.utilities.Environment;
import interpreter.utilities.Iterator;
import interpreter.utilities.IteratorExecutionContainer;

/*
grammar rule:
statement
    : [...]
    | 'for' '(' iteratorChain ')' '{' block '}'
    ;

implemented here as:
                ========-----         =====
                  mIterator        mStatements
*/

public class For extends Statement {
    private Iterator    mIterator;
    private Block       mStatements;

    private class Exec implements IteratorExecutionContainer {
        private Block   mStatements;

        public Exec (Block statements) {
            mStatements = statements;
        }

        public void execute(Value lastIterationValue) throws SetlException {
            mStatements.execute();
            // ContinueException and BreakException are handled by outer iterator
        }
    }

    public For(Iterator iterator, Block statements) {
        mIterator   = iterator;
        mStatements = statements;
    }

    public void execute() throws SetlException {
        Exec e = new Exec(mStatements);
        mIterator.eval(e);
    }

    /* string operations */

    public String toString(int tabs) {
        String result = Environment.getTabs(tabs);
        result += "for (" + mIterator.toString(tabs) + ") ";
        result += mStatements.toString(tabs, true);
        return result;
    }

    /* term operations */

    public Term toTerm() throws SetlException {
        Term result = new Term("'for");
        result.addMember(mIterator.toTerm());
        result.addMember(mStatements.toTerm());
        return result;
    }
}

