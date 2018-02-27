package p3r5uazn.krypto;


/**
 * Created by kenso on 2/26/2018.
 */

public class KryptoCurrency
{
    private String name;
    private double cost;
    private double change;
    public KryptoCurrency()
    {
        name = "Insert Name Here";
        cost = 0;
        change = -10;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = roundToCash(cost);
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = roundToCash(change);
    }

    // Rounds a double to the hundredths
    private double roundToCash(double input)
    {
        return ( Math.floor(input *100) / 100 );
    }
}
