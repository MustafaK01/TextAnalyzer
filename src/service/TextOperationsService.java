package service;

import core.DLLStack;

public class TextOperationsService {

    private DLLStack<String> undo = new DLLStack<>();
    private DLLStack<String> redo = new DLLStack<>();

    public void saveState(String text){
        undo.push(text);
        redo = new DLLStack<>();
    }

    public String undo(String text){
        if (undo.isEmpty()) return null;
        redo.push(text);
        return undo.pop();
    }

    public String redo(String text){
        if (redo.isEmpty()) return null;
        undo.push(text);
        return redo.pop();
    }

}
