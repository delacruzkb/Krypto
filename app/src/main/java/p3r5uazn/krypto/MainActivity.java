package p3r5uazn.krypto;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ListView;
import java.util.ArrayList;

/**
 * TODO: refresh button
 * */

public class MainActivity extends AppCompatActivity {
    private static final int BACKGROUND_CODE = 1;
    private AutoCompleteTextView searchBar;
    private ListView listView;
    private ImageButton settingsButton;
    private HomeScreenListAdapter homeScreenListAdapter;
    private ArrayAdapter<KryptoCurrency> searchBarAdapter;
    private KryptoDatabase favoritesDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        //Sets up Database for favorites
        favoritesDatabase = Room.databaseBuilder(this, KryptoDatabase.class,"Favorites").build();

        //Builds & Instantiates all of the views
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
        /**TODO: Shaina's pull code to update the Favorites Database
         * */
        AsyncTaskQueryFavorites queryTask = new AsyncTaskQueryFavorites(this);
        queryTask.execute();
    }


    //Builds & Instantiates all of the views
    private void buildViews()
    {
        ArrayList<KryptoCurrency> temp = new ArrayList<>();

        //Builds searchBar
        searchBar = findViewById(R.id.search_bar);
        //When clicking an item, remake the listView so that any krypto that contains the item's name is shown
        searchBar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String keyWord = ((KryptoCurrency) parent.getAdapter().getItem(position)).toString();
                searchBar.setText("");
                AsyncTaskCustomSearch customSearch = new AsyncTaskCustomSearch(searchBar.getContext(),keyWord);
                customSearch.execute();
            }
        });
            //When pressing enter, remake the listView so that any krypto that contains the keyword in it's name is shown
        searchBar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if((keyCode==KeyEvent.KEYCODE_ENTER) && event.getAction() == KeyEvent.ACTION_UP)
                {
                    String keyWord = searchBar.getText().toString();
                    AsyncTaskCustomSearch customSearch = new AsyncTaskCustomSearch(searchBar.getContext(),keyWord);
                    customSearch.execute();
                }
                return false;
            }
        });
        searchBarAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, temp);
        searchBar.setAdapter(searchBarAdapter);

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
            //When an listing is clicked, go to the item's details page
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
