package p3r5uazn.krypto;

import android.content.Intent;
import android.graphics.Color;
import android.media.TimedText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.TextClock;
import java.util.Calendar;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.sql.Date;
import java.sql.Time;



//<<<<<<< HEAD
//TODO: y'all...
//Graph on the top 1/3 and information scroll on the bottom.
//=======
//TODO: DANIEL PLEASE
//>>>>>>> 4ad38a511b3d7f0704dd61aeb6f32dc2208550b1
public class DetailsPage extends AppCompatActivity {
    private TextView high;
    private TextView low;
    private TextView change;
    private TextView data;
    private EditText note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_page);
        Intent intent = getIntent();
        KryptoCurrency currency = (KryptoCurrency) intent.getSerializableExtra("KryptoCurrency");


        // high = findViewById(R.id.high);
        // low = findViewById(R.id.low);



        TextView name = (TextView) findViewById(R.id.name);
        name.setText(currency.getName());
        TextView cost = findViewById(R.id.cost);
        cost.setText(Double.toString(currency.getPriceUSD()));
        TextView change = findViewById(R.id.change);
        change.setText(Double.toString(currency.getPerChange1h()));

        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        series.setColor(Color.BLUE);

        // generate Dates
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
}



