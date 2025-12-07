import core.LinkedLst;
import core.Node;

public class Main {


    public static void main(String[] args) {
        LinkedLst<String> linkedLst = new LinkedLst<>();

        linkedLst.addItemToTail("asdasdas");
        linkedLst.addItemToTail("ad");
        linkedLst.addItemToTail("asdddd");

        linkedLst.printAllItems();


    }


}