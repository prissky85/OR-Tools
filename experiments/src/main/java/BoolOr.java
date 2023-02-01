import com.google.ortools.Loader;
import com.google.ortools.sat.BoolVar;
import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.CpSolver;
import com.google.ortools.sat.CpSolverSolutionCallback;

public class BoolOr {
    static {
        Loader.loadNativeLibraries();
//        System.loadLibrary("jniortools");
    }
    static CpModel model = new CpModel();
    static CpSolver cpSolver = new CpSolver();
    static CpSolverSolutionCallback callback;
    static BoolVar a = model.newBoolVar("A");
    static BoolVar b = model.newBoolVar("B");
    static BoolVar c = model.newBoolVar("C");
    static BoolVar aNot = model.newBoolVar("A'");
    static BoolVar bNot = model.newBoolVar("B'");
    static BoolVar cNot = model.newBoolVar("C'");

    public static void main(String[] args) {
        initializeModel();

/* i]   OR modelling
            (A v B v C) excludes [0, 0, 0]) */
        testBoolOr(a, b, c);

/* ii]
            (A v B v C') <=> ((A' and B') => C) excludes [0, 0, 1]  */
//        testBoolOr(a, b , cNot);

/* iii] implication modelling
            (A' v B' v C) <=> ((A and B) => C) excludes [1, 1, 0]   */
//        testBoolOr(aNot, bNot , c);

/* iv]  equivalence modelling
            (A' v B' v C)   \
            (A v C')         }  <=> ((A and B) <=> C)
            (B v C')        /                                       */
//        model.addBoolOr(new BoolVar[]{a, cNot});
//        model.addBoolOr(new BoolVar[]{b, cNot});
//        testBoolOr(aNot, bNot , c);
    }

    private static void initializeModel() {
        model.addEquality(a, aNot.not());
        model.addEquality(b, bNot.not());
        model.addEquality(c, cNot.not());

        System.out.println("A | B | C");
        System.out.println("--+---+--");
        callback = new CpSolverSolutionCallback() {
            @Override
            public void onSolutionCallback() {
                System.out.println(value(a) + " | " + value(b) + " | " + value(c) + "   ");
            }
        };
    }

    static void testBoolOr(BoolVar x, BoolVar y, BoolVar z) {
        model.addBoolOr(new BoolVar[]{x, y, z});
        cpSolver.getParameters().setEnumerateAllSolutions(true);
        cpSolver.solve(model, callback);
    }
}