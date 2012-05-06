package org.randoom.setlx.functions;

import org.randoom.setlx.exceptions.AbortException;
import org.randoom.setlx.types.Value;

import java.util.List;

// abort(message)          : stops execution and displays given error message(s)

public class PD_abort extends PreDefinedFunction {
    public final static PreDefinedFunction DEFINITION = new PD_abort();

    private PD_abort() {
        super("abort");
        addParameter("message");
        enableUnlimitedParameters();
    }

    public Value execute(List<Value> args, List<Value> writeBackVars) throws AbortException {
        String msg = "";
        for (Value arg : args) {
            msg += arg.getUnquotedString();
        }
        throw new AbortException("abort: " + msg);
    }
}
