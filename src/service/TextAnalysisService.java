package service;

import core.map.HMap;

public class TextAnalysisService {

    public Integer wordFrequency(String mainText, String searchedWord){
        if (searchedWord == null || searchedWord.isEmpty()) return 0;
        searchedWord = searchedWord.trim().toLowerCase();
        String cleanText = mainText.replaceAll("[^a-zA-Z0-9\\s]", "").toLowerCase();
        String[] words = cleanText.split("\\s+");
        HMap<String,Integer> map = new HMap<>();
        for (String w : words) {
            if(!w.isEmpty()){
                if(map.getVal(w)!=null){
                    map.put(w,map.getVal(w)+1);
                }else{
                    map.put(w,1);
                }
            }
        }
        System.out.println(map.getVal(searchedWord));
        return map.getVal(searchedWord);
    }


}
