package service;

import core.map.HMap;

public class TextAnalysisService {


    // Metni tararken aranan kelimeyi her bulduğunda aynı kelimeyi tekrar
    // aramamak için her döngüde başlangıç noktasını kelimenin bittiği yere kaydırır
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

    public void putAndCount(String w,HMap<String,Integer> map){
        if(!w.isEmpty()){
            if(map.getVal(w)!=null){
                map.put(w,map.getVal(w)+1);
            }else{
                map.put(w,1);
            }
        }
    }

}
