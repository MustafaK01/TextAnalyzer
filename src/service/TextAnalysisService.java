package service;

import core.map.HMap;
import java.util.*;

public class TextAnalysisService {

    private static final Set<String> STOP_WORDS = new HashSet<>(Arrays.asList(
            "the", "a", "an", "and", "or", "but", "if", "because", "as", "until", "while", "of", "in", "on", "at", "to", "for", "with", "by", "from", "up", "about", "into", "over", "after",
            "i", "you", "he", "she", "it", "we", "they", "me", "him", "her", "us", "them", "my", "your", "his", "its", "our", "their", "this", "that", "these", "those",
            "is", "are", "was", "were", "be", "been", "being", "have", "has", "had", "do", "does", "did", "can", "could", "will", "would", "should", "not", "no", "just", "very", "so", "too", "all", "any", "some"
    ));

    // Metni tararken aranan kelimeyi her bulduğunda aynı kelimeyi tekrar
    // aramamak için her döngüde başlangıç noktasını önceden bulduğu kelimenin bittiği yere kaydırır
    public Integer wordFrequency(String mainText, String searchedWord){
        if (searchedWord == null || searchedWord.isEmpty()) return 0;
        String content = mainText.toLowerCase();
        String target = searchedWord.toLowerCase();
        HMap<String, Integer> map = new HMap<>();
        map.put(target, 0);
        int index = content.indexOf(target);
        while (index != -1) {
            putAndCount(target,map);
            index = content.indexOf(target, index + target.length());
        }
        return map.getVal(target);
    }

    public HMap<String,Integer> frequencyWindow(String mainText, String searchedWord, int windowSize){
        if (searchedWord == null || searchedWord.isEmpty()) return null;
        String target = searchedWord.trim().toLowerCase();
        String[] words = mainText.split("[^a-zA-Z0-9]+");
        HMap<String,Integer> map = new HMap<>();
        for (int i = 0; i < words.length; i++) {
            String w = words[i].toLowerCase();
            if(w.equals(target)){
                int start = Math.max(0,i-windowSize);
                int end = Math.min(words.length-1, i + windowSize);
                for (int j = start; j <= end; j++) {
                    if(i == j || isStopWord(words[j]) || words[j].isEmpty()) continue;
                    putAndCount(words[j].toLowerCase(),map);
                }
            }
        }
        return map;
    }

    public void putAndCount(String w,HMap<String,Integer> map){
        if(!w.isEmpty()){
            if(map.getVal(w)!=null){
                map.put(w,map.getVal(w)+1);
            }else{
                map.put(w,1);
            }
        }
    }

    public boolean isStopWord(String word) {
        if (word == null || word.isEmpty()) {
            return true;
        }
        return STOP_WORDS.contains(word.toLowerCase());
    }
}
