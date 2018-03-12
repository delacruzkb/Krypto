package p3r5uazn.krypto;

import android.app.Activity;
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
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    public static final int BACKGROUND_CODE = 1;
    private AutoCompleteTextView searchBar;
    private ListView listView;
    private ImageButton settingsButton;
    private ArrayList<KryptoCurrency> data;
    private ArrayList<KryptoCurrency> favorites;
    private HomeScreenListAdapter homeScreenListAdapter;
    private ArrayAdapter<KryptoCurrency> searchBarAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        //generating test data
        data = new ArrayList<>();
        favorites = new ArrayList<>();
        KryptoCurrency test;
        for (int i = 0; i < 20; i++) {
            test = new KryptoCurrency();
            if (i % 2 == 0) {
                test.setName("Even test #" + i);
            } else {
                test.setName("Odd test #" + i);
            }
            test.setCost(1000000.03 + i);
            test.setChange(i - 1000.34);
            data.add(test);
            if(i % 5 ==0)
            {
                favorites.add(test);
            }
        }
        Collections.sort(favorites);
        Collections.sort(data);


        //Builds search_bar with auto complete
        searchBarAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, favorites);
        searchBar = findViewById(R.id.search_bar);
        searchBar.setAdapter(searchBarAdapter);
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
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(listView.getContext(), SettingsPage.class);
                intent.putExtra("data", data);
                intent.putExtra("favorites", favorites);
                startActivityForResult(intent, BACKGROUND_CODE);         // To transfer values of favorites
            }
        });


        //builds the ListView
        listView = findViewById(R.id.currency_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), DetailsPage.class);
                KryptoCurrency selected = (KryptoCurrency) parent.getAdapter().getItem(position);
                intent.putExtra("KryptoCurrency", selected);
                startActivity(intent);
            }
        });
        homeScreenListAdapter = new HomeScreenListAdapter(this, favorites);
        listView.setAdapter(homeScreenListAdapter);

    }

    //What to do when coming back from the settings page
    // updates the values of the interface
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == BACKGROUND_CODE && resultCode == Activity.RESULT_OK) {
            data = (ArrayList<KryptoCurrency>) intent.getSerializableExtra("data");
            favorites = (ArrayList<KryptoCurrency>) intent.getSerializableExtra("favorites");
            homeScreenListAdapter = new HomeScreenListAdapter(this, favorites);
            listView.setAdapter(homeScreenListAdapter);
            searchBarAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, favorites);
            searchBar.setAdapter(searchBarAdapter);
        }
    }

}
