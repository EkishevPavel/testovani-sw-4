package archive;

import shop.Item;

public class ItemPurchaseArchiveEntry {
    private Item refItem;
    private int soldCount;
    
    public ItemPurchaseArchiveEntry(Item refItem) {
        this.refItem = refItem;
        soldCount = 1;
    }
    
    void increaseCountHowManyTimesHasBeenSold(int x) {
        soldCount += x;
    }
    
    int getCountHowManyTimesHasBeenSold() {
        return soldCount;
    }
    
    public Item getRefItem() {
        return refItem;
    }

    public int getSoldCount() {
        return soldCount;
    }

    @Override
    public String toString() {
        return "ITEM  "+refItem.toString()+"   HAS BEEN SOLD "+soldCount+" TIMES";
    }
}
