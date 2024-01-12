package imgui.extension.implot.flag;

public final class ImPlotColormap {
    private ImPlotColormap() {
    }

    /** a.k.a. seaborn deep             (qual=true,  n=10) (default) */
    public static final int Deep     = 0;
    /** a.k.a. matplotlib "Set1"        (qual=true,  n=9 ) */
    public static final int Dark     = 1;
    /** a.k.a. matplotlib "Pastel1"     (qual=true,  n=9 ) */
    public static final int Pastel   = 2;
    /** a.k.a. matplotlib "Paired"      (qual=true,  n=12) */
    public static final int Paired   = 3;
    /** a.k.a. matplotlib "viridis"     (qual=false, n=11) */
    public static final int Viridis  = 4;
    /** a.k.a. matplotlib "plasma"      (qual=false, n=11) */
    public static final int Plasma   = 5;
    /** a.k.a. matplotlib/MATLAB "hot"  (qual=false, n=11) */
    public static final int Hot      = 6;
    /** a.k.a. matplotlib/MATLAB "cool" (qual=false, n=11) */
    public static final int Cool     = 7;
    /** a.k.a. matplotlib/MATLAB "pink" (qual=false, n=11) */
    public static final int Pink     = 8;
    /** a.k.a. MATLAB "jet"             (qual=false, n=11) */
    public static final int Jet      = 9;
    /** a.k.a. matplotlib "twilight"    (qual=false, n=11) */
    public static final int Twilight = 10;
    /** red/blue, Color Brewer          (qual=false, n=11) */
    public static final int RdBu     = 11;
    /** brown/blue-green, Color Brewer  (qual=false, n=11) */
    public static final int BrBG     = 12;
    /** pink/yellow-green, Color Brewer (qual=false, n=11) */
    public static final int PiYG     = 13;
    /** color spectrum, Color Brewer    (qual=false, n=11) */
    public static final int Spectral = 14;
    /** white/black                     (qual=false, n=2 ) */
    public static final int Greys    = 15;
}
