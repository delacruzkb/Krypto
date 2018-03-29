package p3r5uazn.krypto;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ListView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int BACKGROUND_CODE = 1;
    private AutoCompleteTextView searchBar;
    private ListView listView;
    private ImageButton settingsButton;
    private HomeScreenListAdapter homeScreenListAdapter;
    private ArrayAdapter<KryptoCurrency> searchBarAdapter;
    private KryptoDatabase kryptoDatabase;
    private KryptoDatabase favoritesDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        //Sets up Database
        kryptoDatabase = Room.databaseBuilder(this, KryptoDatabase.class,"Data").build();
        favoritesDatabase = Room.databaseBuilder(this, KryptoDatabase.class,"Favorites").build();
        //generating test data
        KryptoCurrency test;
        for (int i = 0; i < 20; i++)
        {

            test = new KryptoCurrency();
            if (i % 2 == 0) {
                test.setName("Even test #" + i);
            } else {
                test.setName("Odd test #" + i);
            }
            test.setPriceUSD(1000000.03 + i);
            test.setPerChange1h(i - 1000.34);
            if(i % 5 ==0)
            {
                AsyncTaskInsertDatabase insertTask2 = new AsyncTaskInsertDatabase(favoritesDatabase);
                insertTask2.execute(test);
            }
            AsyncTaskInsertDatabase insertTask1 = new AsyncTaskInsertDatabase(kryptoDatabase);
            insertTask1.execute(test);
        }

        //Builds all of the views within the screen with no data
        buildViews();
        //update the list with data from the database
        refreshScreen();
    }

    //Refresh values when returning from an activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == BACKGROUND_CODE && resultCode == Activity.RESULT_OK) {
            refreshScreen();
        }
    }

    //updates values on all views
    private void refreshScreen()
    {
        AsyncTaskQueryFavorites queryTask = new AsyncTaskQueryFavorites(favoritesDatabase,this);
        queryTask.execute();
    }


    //Builds all of the views within the screen with no data within them
    private void buildViews()
    {
        ArrayList<KryptoCurrency> temp = new ArrayList<>();

        //Builds search_bar with auto complete
        searchBarAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, temp);
        searchBar = findViewById(R.id.search_bar);
        searchBar.setAdapter(searchBarAdapter);
            //When clicked on an item, remake the listView so that it is the only one present
        searchBar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                searchBar.setText("");
                ArrayList<KryptoCurrency> tempSearch = new ArrayList<>();
                KryptoCurrency selected = (KryptoCurrency) parent.getAdapter().getItem(position);
                tempSearch.add(selected);
                homeScreenListAdapter = new HomeScreenListAdapter(view.getContext(), tempSearch);
                listView.setAdapter(homeScreenListAdapter);
            }
        });

        //Builds the settings button
        settingsButton = findViewById(R.id.settings_button);
        //when clicked, go to the settings page
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(listView.getContext(), SettingsPage.class);
                startActivityForResult(intent, BACKGROUND_CODE);
            }
        });


        //builds the ListView
        listView = findViewById(R.id.currency_list);
            //when an listing is clicked, go to the item's details page
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), DetailsPage.class);
                KryptoCurrency selected = (KryptoCurrency) parent.getAdapter().getItem(position);
                intent.putExtra("KryptoCurrency", selected);
                startActivity(intent);
            }
        });
        homeScreenListAdapter = new HomeScreenListAdapter(this, temp);
        listView.setAdapter(homeScreenListAdapter);

    }

}
