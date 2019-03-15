public class Item
{
    private int index;
    private int profit;
    private int weight;
    private double ratio;
    private int node_idx;

    public Item(int index, int profit, int weight, int node_idx) {
        this.index = index;
        this.profit = profit;
        this.weight = weight;
        this.node_idx = node_idx;
        ratio = profit/weight;
    }

    public int getIndex() {
        return index;
    }

    public int getProfit() {
        return profit;
    }

    public int getWeight() {
        return weight;
    }

    public int getNode_idx() {
        return node_idx;
    }

    @Override
    public String toString() {
        return "Item(" + index + ", " + profit + ", " + weight + ", " + node_idx + ")";
    }

}
