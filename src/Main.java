import core.DoublyLinkedLst;

public class Main {


    public static void main(String[] args) {
        DoublyLinkedLst<String> doublyLinkedLst = new DoublyLinkedLst<>();
        for (int i = 0; i < 123; i++) {
            doublyLinkedLst.addItemToTail("asd"+i);
        }
        doublyLinkedLst.printAllItems();
        System.out.println("\n\n\n");
//        while(!doublyLinkedLst.isEmpty()){
//            System.out.println(doublyLinkedLst.getAndRemoveItemFromHead());
//        }
        doublyLinkedLst.printItemWithIndex(0);
    }


}