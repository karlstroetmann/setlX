package org.randoom.setlx.functions;

import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.parameters.ParameterDefinition;
import org.randoom.setlx.utilities.State;

import java.util.HashMap;

/**
 * int(stringOrNumber) : Convert string or number into an integer, returns om on failure.
 */
public class PD_int extends PreDefinedProcedure {

    private final static ParameterDefinition VALUE      = createParameter("value");

    /** Definition of the PreDefinedProcedure `int'. */
    public  final static PreDefinedProcedure DEFINITION = new PD_int();

    private PD_int() {
        super();
        addParameter(VALUE);
    }

    @Override
    public Value execute(final State state, final HashMap<ParameterDefinition, Value> args) throws SetlException {
        return args.get(VALUE).toInteger(state);
    }
}

