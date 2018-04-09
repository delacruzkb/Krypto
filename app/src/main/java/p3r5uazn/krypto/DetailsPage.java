package p3r5uazn.krypto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


//TODO: y'all...
//Graph on the top 1/3 and information scroll on the bottom.
public class DetailsPage extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_page);
        Intent intent = getIntent();
        KryptoCurrency currency = (KryptoCurrency) intent.getSerializableExtra("KryptoCurrency");

        TextView name = findViewById(R.id.name);
        name.setText(currency.getName());
        TextView cost = findViewById(R.id.cost);
        cost.setText(Double.toString(currency.getPriceUSD()));
        TextView change = findViewById(R.id.change);
        change.setText(Double.toString(currency.getPerChange1h()));

        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);

        /*final TextView txt1 = (TextView) findViewById(R.id.sm);
        Runnable task = new Runnable();

        new Thread(task{
            public void run(){
                txt1.setText("Thread testing");
            }

        }).start();*/

    }
}
