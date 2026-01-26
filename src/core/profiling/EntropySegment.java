package core.profiling;

public class EntropySegment {
    public final int start;
    public final int end;
    public final double avg;
    public final boolean high;

    public EntropySegment(int start, int end, double avg, boolean high) {
        this.start = start;
        this.end = end;
        this.avg = avg;
        this.high = high;
    }
}
