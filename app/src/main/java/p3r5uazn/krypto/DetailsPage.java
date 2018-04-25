package p3r5uazn.krypto;

import android.content.Intent;
import android.graphics.Color;
import android.icu.text.DecimalFormat;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;


import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.BarGraphSeries;




//<<<<<<< HEAD
//TODO: y'all...
//Graph on the top 1/3 and information scroll on the bottom.
//=======
//TODO: DANIEL PLEASE
//>>>>>>> 4ad38a511b3d7f0704dd61aeb6f32dc2208550b1
public class DetailsPage extends AppCompatActivity {
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
            simple = currency.getVolume24()/ 1000;
            temp = df.format(simple) + " K";
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
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[]{
                new DataPoint(1, currency.getPerChange1h()),
                new DataPoint(2, currency.getPerChange24h()),
                new DataPoint(3, currency.getPerChange7d())

        });
        graph.addSeries(series);

        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter()
        {
            @Override
            public String formatLabel(double value, boolean isValX)
            {
                if(value == 1)
                {
                    return "1Hr";
                }
                else if(value == 2)
                {
                    return "24Hrs";
                }
                else if(value == 3)
                {
                    return "7Days";
                }
                else if(value == 1.5 || value == 2.5 || value == 3.5)
                {
                    return "";
                }
                else
                {
                    return super.formatLabel(value, isValX) + " %";
                }
            }
        });

        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });
        graph.setTitle("Percentage");
        graph.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
        series.setSpacing(50);


        GraphView graph2 = (GraphView) findViewById(R.id.graph2);
        BarGraphSeries<DataPoint> series2 = new BarGraphSeries<>(new DataPoint[]{
                new DataPoint(1, currency.getAvailableSupply()/MILL),
                new DataPoint(2, currency.getMaxSupply()/MILL),
                new DataPoint(3, currency.getTotalSupply()/MILL)
        });

        graph2.addSeries(series2);
        graph2.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter()
        {
            @Override
            public String formatLabel(double value, boolean isValX)
            {
                if(value == 1)
                {
                    return "Available";
                }
                else if(value == 2)
                {
                    return "Max";
                }
                else if(value == 3)
                {
                    return "Total";
                }
                else if(value == 1.5 || value == 2.5 || value == 3.5)
                {
                    return "";
                }
                else
                {
                    return super.formatLabel(value, isValX) + "M";
                }
            }
        });

        graph2.setTitle("Supplies");
        graph2.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
        //graph2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        series2.setSpacing(50);
        series2.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });


    }


}
