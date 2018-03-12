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

import java.util.ArrayList;

public class SearchPage extends AppCompatActivity
{
    private ArrayList<KryptoCurrency> data;
    private static ArrayList<KryptoCurrency> filteredData;
    private static ArrayList<KryptoCurrency> favorites;
    private static ListView listView;
    private static AutoCompleteTextView searchBar;
    private static ArrayAdapter<KryptoCurrency> searchBarAdapter;
    private static SearchScreenListAdapter searchScreenListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_screen);
        final Intent intent = getIntent();
        data = (ArrayList<KryptoCurrency>) intent.getSerializableExtra("data");
        favorites = (ArrayList<KryptoCurrency>) intent.getSerializableExtra("favorites");
        filteredData= filterData(data, favorites);

        searchScreenListAdapter = new SearchScreenListAdapter(this, favorites, filteredData);

        //Builds search_bar with auto complete
        searchBarAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, filteredData);
        searchBar = findViewById(R.id.add_search_bar);
        searchBar.setAdapter(searchBarAdapter);
        searchBar.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                searchBar.setText("");
                ArrayList<KryptoCurrency> tempSearch = new ArrayList<>();
                KryptoCurrency selected = (KryptoCurrency) parent.getAdapter().getItem(position);
                tempSearch.add(selected);
                searchScreenListAdapter = new SearchScreenListAdapter(view.getContext(), favorites, tempSearch);
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

    protected static void reloadListView(Context context, ArrayList<KryptoCurrency> filter)
    {
        filteredData = filterData(filteredData, filter);
        favorites = filter;
        searchBarAdapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, filteredData);
        searchBar.setAdapter(searchBarAdapter);
        searchScreenListAdapter = new SearchScreenListAdapter(context, favorites, filteredData);
        listView.setAdapter(searchScreenListAdapter);
    }

    protected static ArrayList<KryptoCurrency> filterData(ArrayList<KryptoCurrency> source, ArrayList<KryptoCurrency> filterOut)
    {
        ArrayList<KryptoCurrency> filteredList = source;
        for(int i = 0; i< filterOut.size(); i++)
        {
            source.remove(filterOut.get(i));
        }

        return filteredList;
    }
}
