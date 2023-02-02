package cpsat;

import com.google.ortools.Loader;
import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.CpSolver;
import com.google.ortools.sat.CpSolverStatus;
import com.google.ortools.sat.IntVar;

/** Solves a problem with a time limit. */
public final class AllDifferent {
    public static void main(String[] args) {
        Loader.loadNativeLibraries();
        // Create the model.
        CpModel model = new CpModel();
        // Create the variables.
        int numVals = 3;

        IntVar x = model.newIntVar(0, numVals - 1, "x");
        IntVar y = model.newIntVar(0, numVals - 1, "y");
        IntVar z = model.newIntVar(0, numVals - 1, "z");
        // Create the constraint.
        model.addDifferent(x, y);
        model.addDifferent(y, z);
        model.addDifferent(z, x);

        // Create a solver and solve the model.
        CpSolver solver = new CpSolver();
        solver.getParameters().setMaxTimeInSeconds(10.0);
        CpSolverStatus status = solver.solve(model);

        if (status == CpSolverStatus.OPTIMAL) {
            System.out.println("x = " + solver.value(x));
            System.out.println("y = " + solver.value(y));
            System.out.println("z = " + solver.value(z));
        }
    }

    private AllDifferent() {}
}