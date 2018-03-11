package p3r5uazn.krypto;


import java.io.Serializable;

/**
 * Created by kenso on 2/26/2018.
 */

public class KryptoCurrency implements Serializable
{

    private static final long serialVersionUID = 1L;

    private String name;
    private double cost;
    private double change;
    private double threshold;

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public KryptoCurrency()
    {
        name = "Insert Name Here";
        cost = 0;
        change = -10;
        threshold = 1000;
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

    public boolean equals(KryptoCurrency currency)
    {
        return this.getName().equalsIgnoreCase(currency.getName());
    }

    public int compareTo(KryptoCurrency currency)
    {
        return this.getName().compareTo(currency.getName());
    }

    public String toString()
    {
        return getName();
    }
    // Rounds a double to the hundredths
    private double roundToCash(double input)
    {
        return ( Math.floor(input *100) / 100 );
    }
}
