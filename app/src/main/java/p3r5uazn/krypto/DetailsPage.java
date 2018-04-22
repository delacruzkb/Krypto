package p3r5uazn.krypto;

import android.content.Intent;
import android.graphics.Color;
import android.icu.text.DecimalFormat;
import android.media.TimedText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.TextClock;

import java.util.BitSet;
import java.util.Calendar;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.sql.Date;
import java.sql.Time;
import java.util.MissingFormatArgumentException;


//<<<<<<< HEAD
//TODO: y'all...
//Graph on the top 1/3 and information scroll on the bottom.
//=======
//TODO: DANIEL PLEASE
//>>>>>>> 4ad38a511b3d7f0704dd61aeb6f32dc2208550b1
public class DetailsPage extends AppCompatActivity {
    private TextView high;
    private TextView low;
    private TextView bid;
    private TextView ask;
    private TextView changes;
    private TextView data;
    private EditText note;
    private double simple;
    private String temp;
    private final int MILL = 1000000;
    private final int BILL = 1000000000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_page);
        Intent intent = getIntent();
        KryptoCurrency currency = (KryptoCurrency) intent.getSerializableExtra("KryptoCurrency");


        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        //high = findViewById(R.id.high);
        //low = findViewById(R.id.low);


       TextView btc = (TextView) findViewById(R.id.btc);
       btc.setText(Double.toString(currency.getPriceBTC()));

        TextView volume = (TextView) findViewById(R.id.volume);
        if(currency.getVolume24() == 0)
        {
            volume.setText("N/A");
        }
        else if(currency.getVolume24() >= BILL)
        {
            simple = currency.getVolume24()/ BILL;
            temp = df.format(simple) + " B";
            volume.setText(temp);
        }
        else
        {
            simple = currency.getVolume24()/MILL;
            temp = df.format(simple) + " M";
            volume.setText(temp);
        }

        TextView cap = (TextView) findViewById(R.id.cap);
        if(currency.getMarketCap() == 0)
        {
            cap.setText("N/A");
        }
        else if(currency.getMarketCap() >= BILL)
        {
            simple = currency.getMarketCap()/BILL;
            temp = df.format(simple) + " B";
            cap.setText(temp);
        }
        else
        {
            simple = currency.getMarketCap()/ MILL;
            temp = df.format(simple) + " M";
            cap.setText(temp);
        }

        TextView availableSupp = (TextView) findViewById(R.id.available);
        if(currency.getAvailableSupply() == 0)
        {
            availableSupp.setText("N/A");
        }
        else if(currency.getAvailableSupply() >= BILL)
        {
            simple = currency.getAvailableSupply()/BILL;
            temp = df.format(simple) + " B";
            availableSupp.setText(temp);
        }
        else
        {
            simple = currency.getAvailableSupply()/MILL;
            temp = df.format(simple) + " M";
            availableSupp.setText(temp);
        }

        TextView totalSupp = (TextView) findViewById(R.id.total);
        if(currency.getTotalSupply() == 0)
        {
            totalSupp.setText("N/A");
        }
        else if(currency.getTotalSupply() >= BILL)
        {
            simple = currency.getTotalSupply()/BILL;
            temp = df.format(simple) + " B";
            totalSupp.setText(temp);
        }
        else
        {
            simple = currency.getTotalSupply()/MILL;
            temp = df.format(simple) + " M";
            totalSupp.setText(temp);
        }

        TextView maxSupp = (TextView) findViewById(R.id.max);
        if(currency.getMaxSupply() == 0)
        {
            maxSupp.setText("N/A");
        }
        else if(currency.getMaxSupply() >= BILL)
        {
            simple = currency.getMaxSupply()/BILL;
            temp = df.format(simple) + " B";
            maxSupp.setText(temp);
        }
        else
        {
            simple = currency.getMaxSupply()/MILL;
            temp = df.format(simple) + " M";
            maxSupp.setText(temp);
        }

        TextView name = (TextView) findViewById(R.id.name);
        name.setText(currency.getName());

        TextView cost = findViewById(R.id.cost);
        cost.setText(Double.toString(currency.getPriceUSD()));

        TextView change1 = findViewById(R.id.change1);
        change1.setText(Double.toString(currency.getPerChange1h()));

        TextView change24 = findViewById(R.id.change24);
        change24.setText(Double.toString(currency.getPerChange24h()));

        TextView change7 = findViewById(R.id.change7);
        change7.setText(Double.toString(currency.getPerChange7d()));


        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, currency.getPriceUSD()),
                new DataPoint(1, currency.getPriceUSD()),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        series.setColor(Color.BLUE);

    /*    // generate Dates
        Calendar calendar = Calendar.getInstance();
        Date d1 = (Date) calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d2 = (Date) calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d3 = (Date) calendar.getTime();

        GraphView graph2 = (GraphView) findViewById(R.id.graph);

// you can directly pass Date objects to DataPoint-Constructor
// this will convert the Date to double via Date#getTime()
        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(d1, 1),
                new DataPoint(d2, 5),
                new DataPoint(d3, 3)
        });

        graph.addSeries(series);

// set date label formatter
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        graph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space

// set manual x bounds to have nice steps
        graph.getViewport().setMinX(d1.getTime());
        graph.getViewport().setMaxX(d3.getTime());
        graph.getViewport().setXAxisBoundsManual(true);

// as we use dates as labels, the human rounding to nice readable numbers
// is not necessary
        graph.getGridLabelRenderer().setHumanRounding(false);
    }
}*/

        }
    }
