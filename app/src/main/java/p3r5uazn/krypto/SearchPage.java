package p3r5uazn.krypto;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SearchPage extends AppCompatActivity
{
    private ArrayList<KryptoCurrency> data;
    private ArrayList<KryptoCurrency> filteredData;
    private ArrayList<KryptoCurrency> favorites;
    private ListView listView;
    private static AutoCompleteTextView searchBar;
    private static ArrayAdapter<KryptoCurrency> searchBarAdapter;
    private SearchScreenListAdapter searchScreenListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_screen);
        final Intent intent = getIntent();
        data = (ArrayList<KryptoCurrency>) intent.getSerializableExtra("data");
        favorites = (ArrayList<KryptoCurrency>) intent.getSerializableExtra("favorites");
        filteredData=data;
        // filters out currencies already in the favorites list
        for(int i = 0; i< favorites.size(); i++)
        {
            filteredData.remove(filteredData.indexOf(favorites.get(i)));
        }
        searchScreenListAdapter = new SearchScreenListAdapter(this, favorites, filteredData);

        //Builds search_bar with auto complete
        searchBarAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, filteredData);
        searchBar = findViewById(R.id.add_search_bar);
        searchBar.setAdapter(searchBarAdapter);
        searchBar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<KryptoCurrency> tempSearch = new ArrayList<>();
                KryptoCurrency selected = (KryptoCurrency) parent.getAdapter().getItem(position);
                tempSearch.add(selected);
                searchScreenListAdapter = new SearchScreenListAdapter(view.getContext(), tempSearch, filteredData);
                listView.setAdapter(searchScreenListAdapter);
            }
        });

        //Builds list view
        listView = findViewById(R.id.data_list);
        listView.setAdapter(searchScreenListAdapter);
    }

    @Override
    public void finish()
    {
        Intent intent = new Intent();
        intent.putExtra("favorites", favorites);
        intent.putExtra("data",data);

        setResult(RESULT_OK, intent);
        super.finish();
    }

    // Required to update auto correct listing from within the adapter
    protected static void updateAdapter(Context context, ArrayList<KryptoCurrency> newList)
    {
        searchBarAdapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, newList);
        searchBar.setAdapter(searchBarAdapter);
    }
}
