package core.profiling;

import java.util.List;

public class EntropyVisualizer {

    public String asciiBars(List<Double> profile,int maxBarWidth,Double threshold,int windowSize,int step) {
        if (profile == null || profile.isEmpty()) return "(empty profile)\n";
        if (maxBarWidth <= 0) maxBarWidth = 40;

        double max = 0.0;
        for (double v : profile) max = Math.max(max, v);
        if (max == 0.0) max = 1.0;

        StringBuilder out = new StringBuilder();
        out.append("idx | chars | entropy | graph\n");
        out.append("------------------------------------\n");
        for (int i = 0; i < profile.size(); i++) {
            double e = profile.get(i);
            int startChar = i * step;
            int endChar = startChar + windowSize - 1;
            int len = (int) Math.round((e / max) * maxBarWidth);
            out.append(String.format("%3d | %5d–%-5d | %6.3f | ", i, startChar, endChar, e));
            for (int j = 0; j < len; j++) out.append('█');
            if (threshold != null && e >= threshold) out.append("  !");
            out.append('\n');
        }

        out.append(String.format("\nmax=%.3f  window=%d  step=%d",max, windowSize, step));
        if (threshold != null) out.append(String.format("  threshold=%.3f (!)", threshold));
        out.append('\n');

        return out.toString();
    }
}
