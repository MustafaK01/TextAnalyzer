import core.DLLStack;
import core.DoublyLinkedLst;

public class Main {


    public static void main(String[] args) {
        DoublyLinkedLst<String> doublyLinkedLst = new DoublyLinkedLst<>();
        for (int i = 0; i < 123; i++) {
            doublyLinkedLst.addItemToTail("asd"+i);
        }
//        doublyLinkedLst.printAllItems();
        System.out.println("\n\n\n");
//        while(!doublyLinkedLst.isEmpty()){
//            System.out.println(doublyLinkedLst.getAndRemoveItemFromHead());
//        }
//        doublyLinkedLst.printItemWithIndex(0);
        DLLStack<String> dllStack = new DLLStack<>();
        System.out.println(dllStack.pop());
        System.out.println(dllStack.peek());

        for (int i = 0; i < 123; i++) {
            dllStack.push("asdStack"+i);
        }
        while(!dllStack.isEmpty()){
            System.out.println(dllStack.pop());
//            System.out.println(dllStack.peek());
//            dllStack.pop();
        }

    }


}