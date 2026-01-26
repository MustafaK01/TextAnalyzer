package core.profiling;

import java.util.ArrayList;
import java.util.List;

public class EntropyHeatmap {

    public List<EntropySegment> buildSegments(List<Double> profile, int windowSize, int step, double threshold) {
        List<EntropySegment> segs = new ArrayList<>();
        if (profile == null || profile.isEmpty()) return segs;
        if (windowSize <= 0 || step <= 0) return segs;
        boolean currHigh = profile.get(0) >= threshold;

        int segStart = 0;
        double sum = profile.get(0);
        int count = 1;

        for (int i = 1; i < profile.size(); i++) {
            boolean high = profile.get(i) >= threshold;
            if (high == currHigh) {
                sum += profile.get(i);
                count++;
            } else {
                segs.add(toSegment(segStart, i - 1, sum / count, currHigh, windowSize, step));
                // yeni seg başlat
                currHigh = high;
                segStart = i;
                sum = profile.get(i);
                count = 1;
            }
        }
        segs.add(toSegment(segStart, profile.size() - 1, sum / count, currHigh, windowSize, step));
        return segs;
    }

    private EntropySegment toSegment(int wStartIdx, int wEndIdx, double avg, boolean high, int windowSize, int step) {
        int startChar = wStartIdx * step;
        int endChar = (wEndIdx * step) + windowSize - 1;
        return new EntropySegment(startChar, endChar, avg, high);
    }

    public String formatHeatmap(List<EntropySegment> segs, int windowSize, int step, double threshold) {

        StringBuilder sb = new StringBuilder();
        sb.append("Entropy Heatmap (segments)\n");
        sb.append(String.format("window=%d step=%d threshold=%.3f\n\n", windowSize, step, threshold));

        if (segs == null || segs.isEmpty()) {
            sb.append("(no segments)\n");
            return sb.toString();
        }

        for (EntropySegment s : segs) {
            sb.append(String.format("[%d..%d]  %s  avg=%.3f", s.start, s.end, s.high ? "HIGH" : "LOW ", s.avg));
            if (s.high) sb.append("  !!!!!");
            sb.append("\n");
        }

        return sb.toString();
    }
}
