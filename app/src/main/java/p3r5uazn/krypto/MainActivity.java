package p3r5uazn.krypto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    public static final int BACKGROUND_CODE = 1;
    private AutoCompleteTextView searchBar;
    private ListView listView;
    private ArrayList<KryptoCurrency> data;
    private HomeScreenListAdapter homeScreenListAdapter;
    private ArrayAdapter<KryptoCurrency> searchBarAdapter;

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
            if(i%2 == 0)
            {
                test.setName("Even test #" + i);
            }
            else
            {
                test.setName("Odd test #" + i);
            }
            test.setCost(1000000.03 + i);
            test.setChange(i-1000.34);
            data.add(test);
        }
        updateList(data);

        //Builds search_bar with auto complete
        searchBarAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, data);
        searchBar = findViewById(R.id.search_bar);
        searchBar.setAdapter(searchBarAdapter);
        searchBar.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                ArrayList<KryptoCurrency> tempSearch = new ArrayList<>();
                KryptoCurrency selected = (KryptoCurrency) parent.getAdapter().getItem(position);
                tempSearch.add(data.get(data.lastIndexOf(selected)));
                updateList(tempSearch);
            }
        });

        //builds the ListView
        updateList(data);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(view.getContext(),DetailsPage.class);
                KryptoCurrency selected = (KryptoCurrency)parent.getAdapter().getItem(position);
                intent.putExtra("KryptoCurrency", selected);

                startActivity(intent);
            }
        });

    }

    private void updateList(ArrayList<KryptoCurrency> list)
    {
        homeScreenListAdapter = new HomeScreenListAdapter(this, list);
        listView.setAdapter(homeScreenListAdapter);
    }
}
