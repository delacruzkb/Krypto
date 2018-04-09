package p3r5uazn.krypto;

import android.app.Activity;
import android.arch.persistence.room.Room;
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
    private ListView listView;
    private AutoCompleteTextView searchBar;
    private ArrayAdapter<KryptoCurrency> searchBarAdapter;
    private SearchScreenListAdapter searchScreenListAdapter;
    private KryptoDatabase kryptoDatabase;
    private KryptoDatabase favoritesDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_screen);
        kryptoDatabase = Room.databaseBuilder(this, KryptoDatabase.class,"Data").build();
        favoritesDatabase = Room.databaseBuilder(this, KryptoDatabase.class,"Favorites").build();
        //Builds all of the views within the screen and populates them with data
        buildViews();
        //update the list with data from the database
        refreshScreen();
    }

    //Returns to settings page when finished
    @Override
    public void finish()
    {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        super.finish();
    }

    //updates values on all views
    private void refreshScreen()
    {
        AsyncTaskQueryFilteredData queryTask = new AsyncTaskQueryFilteredData(kryptoDatabase, favoritesDatabase,this);
        queryTask.execute();
    }
    //Builds all of the views within the screen and populates them with data
    private void buildViews()
    {
        ArrayList<KryptoCurrency> temp = new ArrayList<>();
        //Builds search_bar with auto complete
        searchBarAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, temp);
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
                searchScreenListAdapter = new SearchScreenListAdapter(view.getContext(), tempSearch);
                listView.setAdapter(searchScreenListAdapter);
            }
        });

        //Builds list view
        searchScreenListAdapter = new SearchScreenListAdapter(this, temp);
        listView = findViewById(R.id.data_list);
        listView.setAdapter(searchScreenListAdapter);
    }
}
