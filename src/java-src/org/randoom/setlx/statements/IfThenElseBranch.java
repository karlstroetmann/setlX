package org.randoom.setlx.statements;

import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.exceptions.TermConversionException;
import org.randoom.setlx.expressions.Variable;
import org.randoom.setlx.types.Term;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.State;
import org.randoom.setlx.utilities.TermConverter;

import java.util.List;

/*
grammar rule:
statement
    : [...]
    | 'if' '(' condition ')' '{' block '}' ('else' 'if' '(' condition ')' '{' block '}')* ('else' '{' block '}')?
    ;

implemented here as:
                                                                                                      =====
                                                                                                   mStatements
*/

public class IfThenElseBranch extends IfThenAbstractBranch {
    // functional character used in terms
    /*package*/ final static String FUNCTIONAL_CHARACTER = "^ifThenElseBranch";

    private final Block mStatements;

    public IfThenElseBranch(final Block statements){
        mStatements = statements;
    }

    @Override
    public boolean evalConditionToBool(final State state) {
        return true;
    }

    @Override
    public Value execute(final State state) throws SetlException {
        return mStatements.execute(state);
    }

    @Override
    protected Value exec(final State state) throws SetlException {
        return execute(state);
    }

    /* Gather all bound and unbound variables in this statement and its siblings
          - bound   means "assigned" in this expression
          - unbound means "not present in bound set when used"
          - used    means "present in bound set when used"
       Optimize sub-expressions during this process by calling optimizeAndCollectVariables()
       when adding variables from them.
    */
    @Override
    public void collectVariablesAndOptimize (
        final List<Variable> boundVariables,
        final List<Variable> unboundVariables,
        final List<Variable> usedVariables
    ) {
        mStatements.collectVariablesAndOptimize(boundVariables, unboundVariables, usedVariables);
    }

    /* string operations */

    @Override
    public void appendString(final State state, final StringBuilder sb, final int tabs) {
        sb.append(" else ");
        mStatements.appendString(state, sb, tabs, true);
    }

    /* term operations */

    @Override
    public Term toTerm(final State state) {
        final Term result = new Term(FUNCTIONAL_CHARACTER, 1);
        result.addMember(state, mStatements.toTerm(state));
        return result;
    }

    public static IfThenElseBranch termToBranch(final Term term) throws TermConversionException {
        if (term.size() != 1) {
            throw new TermConversionException("malformed " + FUNCTIONAL_CHARACTER);
        } else {
            final Block block = TermConverter.valueToBlock(term.firstMember());
            return new IfThenElseBranch(block);
        }
    }
}
