package org.randoom.setlx.functions;

import org.apache.commons.math3.distribution.TDistribution;
import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.parameters.ParameterDefinition;
import org.randoom.setlx.plot.types.Canvas;
import org.randoom.setlx.plot.utilities.ConnectJFreeChart;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.Checker;
import org.randoom.setlx.utilities.Defaults;
import org.randoom.setlx.utilities.State;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * stat_studentCDF_plot(nu, canvas):
 *                  Plots the cumulative distribution function for student distributions with 'nu' degrees of freedom on a given canvas.
 */
public class PD_stat_studentCDF_plot extends PreDefinedProcedure {

    private final static ParameterDefinition NU          = createParameter("nu");
    private final static ParameterDefinition CANVAS      = createParameter("canvas");
    private final static ParameterDefinition LOWER_BOUND = createOptionalParameter("lowerBound", Defaults.createSetlDoubleValue(-5.0));
    private final static ParameterDefinition INTERVAL    = createOptionalParameter("interval", Defaults.getDefaultPlotInterval());
    private final static ParameterDefinition UPPER_BOUND = createOptionalParameter("upperBound", Defaults.createSetlDoubleValue(5.0));

    /** Definition of the PreDefinedProcedure 'stat_studentCDF_plot' */
    public final static PreDefinedProcedure DEFINITION = new PD_stat_studentCDF_plot();

    private PD_stat_studentCDF_plot() {
        super();
        addParameter(NU);
        addParameter(CANVAS);
        addParameter(LOWER_BOUND);
        addParameter(INTERVAL);
        addParameter(UPPER_BOUND);
    }

    @Override
    public Value execute(State state, HashMap<ParameterDefinition, Value> args) throws SetlException {
        final Value nu         = args.get(NU);
        final Value canvas     = args.get(CANVAS);
        final Value lowerBound = args.get(LOWER_BOUND);
        final Value interval   = args.get(INTERVAL);
        final Value upperBound = args.get(UPPER_BOUND);

        Checker.checkIfNumber(state, lowerBound, upperBound);
        Checker.checkIfUpperBoundGreaterThanLowerBound(state, lowerBound, upperBound);
        Checker.checkIfNumberAndGreaterZero(state, interval);
        Checker.checkIfNaturalNumberAndGreaterZero(state, nu);
        Checker.checkIfCanvas(state, canvas);

        TDistribution td = new TDistribution(nu.toJDoubleValue(state));

        /** The valueList is the list of every pair of coordinates [x,y] that the graph consists of.
         *  It is filled by iteratively increasing the variable 'counter' (x), and calculating the cumulative probability for every new value of 'counter' (y).
         */
        List<List<Double>> valueList = new ArrayList<>();
        for (double counter = lowerBound.toJDoubleValue(state); counter < upperBound.toJDoubleValue(state); counter += interval.toJDoubleValue(state)) {
            valueList.add(new ArrayList<Double>(Arrays.asList(counter, td.cumulativeProbability(counter))));
        }

        return ConnectJFreeChart.getInstance().addListGraph((Canvas) canvas, valueList, "Cumulative Distribution Function (" + nu.toString() + " degree(s) of freedom)", Defaults.DEFAULT_COLOR_SCHEME, false);
    }
}
