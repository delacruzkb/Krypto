package p3r5uazn.krypto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private ListView listView;
    private ArrayList<KryptoCurrency> data;
    private HomeScreenListAdapter homeScreenListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        listView = findViewById(R.id.currency_list);

        //generating test data
        data = new ArrayList<>();
        KryptoCurrency test;
        for( int i = 0; i < 30;i++)
        {
            test = new KryptoCurrency();
            test.setName("test #"+i);
            test.setCost(1000000.03 + i);
            test.setChange(i-1000.34);
            data.add(test);
        }

        homeScreenListAdapter = new HomeScreenListAdapter(this, data);
        listView.setAdapter(homeScreenListAdapter);
    }
}
