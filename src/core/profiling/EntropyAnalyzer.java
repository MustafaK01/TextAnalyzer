package core.profiling;

import core.Node;
import core.map.HMap;
import core.map.MapEntry;
import core.DoublyLinkedLst;

import java.util.ArrayList;
import java.util.List;

public class EntropyAnalyzer {

    public double globalEntropy(String text) {
        if (text == null || text.isEmpty()) return 0.0;
        HMap<Character, Integer> freq = new HMap<>();
        int total = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            inc(freq, c);
            total++;
        }
        return entropyFromFreq(freq, total);
    }

    public List<Double> slidingEntropy(String text, int windowSize, int step) {
        if (text == null || text.isEmpty()) return List.of();
        if (windowSize <= 0) throw new IllegalArgumentException("windowSize > 0 olmalı");
        if (step <= 0) throw new IllegalArgumentException("step > 0 olmalı");
        if (step > windowSize) step = windowSize;
        if (windowSize >= text.length()) {
            return List.of(globalEntropy(text));
        }

        List<Double> profile = new ArrayList<>();
        HMap<Character, Integer> freq = new HMap<>();
        for (int i = 0; i < windowSize; i++) {
            inc(freq, text.charAt(i));
        }

        profile.add(entropyFromFreq(freq, windowSize));
        for (int i = step; i + windowSize <= text.length(); i += step) {
            int prevStart = i - step;
            for (int j = 0; j < step; j++) {
                char out = text.charAt(prevStart + j);
                char in = text.charAt(i + windowSize - step + j);
                dec(freq, out);
                inc(freq, in);
            }

            profile.add(entropyFromFreq(freq, windowSize));
        }

        return profile;
    }

    private void inc(HMap<Character, Integer> freq, char c) {
        Integer v = freq.getVal(c);
        if (v == null) freq.put(c, 1);
        else freq.put(c, v + 1);
    }

    private void dec(HMap<Character, Integer> freq, char c) {
        Integer v = freq.getVal(c);
        if (v == null) return;
        if (v == 1) freq.remove(c);
        else freq.put(c, v - 1);
    }

    private double entropyFromFreq(HMap<Character, Integer> freq, int total) {
        if (total <= 0) return 0.0;
        double h = 0.0;
        DoublyLinkedLst<MapEntry<Character, Integer>>[] buckets = freq.buckets();
        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i] == null) continue;

            Node<MapEntry<Character, Integer>> curr = buckets[i].getHeadNode();
            while (curr != null) {
                MapEntry<Character, Integer> e = curr.getData();
                int count = e.getValue();
                if (count > 0) {
                    double p = (double) count / total;
                    h += -p * log2(p);
                }
                curr = curr.getNext();
            }
        }
        return h;
    }

    private double log2(double x) {
        return Math.log(x) / Math.log(2);
    }
}