package p3r5uazn.krypto;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import java.io.Serializable;

/**
 * Created by kenso on 2/26/2018.
 */
@Entity
public class KryptoCurrency implements Serializable, Comparable<Object>
{
    @PrimaryKey @NonNull
    private String name;

    private static final long serialVersionUID = 1L;

    private String id, symbol;
    private double priceUSD, priceBTC, volume24, marketCap, availableSupply, totalSupply;
    private double perChange1h, perChange24h, perChange7d, lastUpdated, maxSupply;
    private int rank;


    public KryptoCurrency()
    {
        id = "some id";
        name = "Insert Name Here";
        symbol = "A";
        rank = 1;
        priceUSD = 0;
        priceBTC = 0;
        volume24 = 0;
        marketCap = 0;
        availableSupply = 0;
        totalSupply = 0;
        perChange1h = 0;
        perChange24h = 0;
        perChange7d = 0;
        lastUpdated = 0;
        maxSupply = 0;

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getPriceBTC() {
        return priceBTC;
    }

    public void setPriceBTC(double priceBTC) {
        this.priceBTC = priceBTC;
    }

    public double getVolume24() {
        return volume24;
    }

    public void setVolume24(double volume24) {
        this.volume24 = volume24;
    }

    public double getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(double marketCap) {
        this.marketCap = marketCap;
    }

    public double getAvailableSupply() {
        return availableSupply;
    }

    public void setAvailableSupply(double availableSupply) {
        this.availableSupply = availableSupply;
    }

    public double getTotalSupply() {
        return totalSupply;
    }

    public void setTotalSupply(double totalSupply) {
        this.totalSupply = totalSupply;
    }
    public double getMaxSupply() {
        return maxSupply;
    }

    public void setMaxSupply(double maxSupply) {
        this.maxSupply = maxSupply;
    }

    public double getPerChange1h() {
        return perChange1h;
    }

    public void setPerChange1h(double perChange1h) {
        this.perChange1h = perChange1h;
    }

    public double getPerChange24h() {
        return perChange24h;
    }

    public void setPerChange24h(double perChange24h) {
        this.perChange24h = perChange24h;
    }

    public double getPerChange7d() {
        return perChange7d;
    }

    public void setPerChange7d(double perChange7d) {
        this.perChange7d = perChange7d;
    }

    public double getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(double lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPriceUSD() {
        return priceUSD;
    }

    public void setPriceUSD(double cost) {
        this.priceUSD = roundToCash(cost);
    }


    public boolean equals(Object currency)
    {
        return this.getName().equalsIgnoreCase(((KryptoCurrency)currency).getName());
    }

    public int compareTo(Object currency)
    {
        return this.getName().compareTo(((KryptoCurrency)currency).getName());
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
