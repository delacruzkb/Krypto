package p3r5uazn.krypto;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class SettingsPage extends AppCompatActivity
{
    public static final int BACKGROUND_CODE = 1;
    private ArrayList<KryptoCurrency> data;
    private static ArrayList<KryptoCurrency> favorites;
    private ListView listView;
    private TextView notificationLabel;
    private Switch notificationSwitch;
    private static AutoCompleteTextView searchBar;
    private static ArrayAdapter<KryptoCurrency> searchBarAdapter;
    private SettingsScreenListAdapter settingsScreenListAdapter;
    private ImageButton addButton;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_screen);
        final Intent intent = getIntent();
        data = (ArrayList<KryptoCurrency>) intent.getSerializableExtra("data");
        favorites = (ArrayList<KryptoCurrency>) intent.getSerializableExtra("favorites");
        settingsScreenListAdapter = new SettingsScreenListAdapter(this, favorites);

        //builds notification label and switch
        notificationLabel = findViewById(R.id.notification_label);
        notificationLabel.setText(R.string.switch_off_text);

        notificationSwitch = findViewById(R.id.notification_switch);
        notificationSwitch.setChecked(false);
        notificationSwitch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(notificationSwitch.isChecked()) // is on
                {
                    notificationLabel.setText(R.string.switch_on_text);
                }
                else // is off
                {
                    notificationLabel.setText(R.string.switch_off_text);
                }
            }
        });

        //Builds add_button
        addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(addButton.getContext(), SearchPage.class);
                Collections.sort(favorites);
                Collections.sort(data);
                intent.putExtra("favorites", favorites);
                intent.putExtra("data",data);
                startActivityForResult(intent, BACKGROUND_CODE);
            }
        });

        //Builds search_bar with auto complete
        searchBarAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, favorites);
        searchBar = findViewById(R.id.settings_search_bar);
        searchBar.setAdapter(searchBarAdapter);
        searchBar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<KryptoCurrency> tempSearch = new ArrayList<>();
                KryptoCurrency selected = (KryptoCurrency) parent.getAdapter().getItem(position);
                tempSearch.add(selected);
                settingsScreenListAdapter = new SettingsScreenListAdapter(view.getContext(), tempSearch);
                listView.setAdapter(settingsScreenListAdapter);
            }
        });

        //Builds list view
        listView = findViewById(R.id.favorites_list);
        listView.setAdapter(settingsScreenListAdapter);


    }

    //What to do when coming back from the search page
    // updates the values of the interface
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == BACKGROUND_CODE && resultCode == Activity.RESULT_OK) {
            favorites = (ArrayList<KryptoCurrency>) intent.getSerializableExtra("favorites");
            data = (ArrayList<KryptoCurrency>) intent.getSerializableExtra("data");
            Collections.sort(favorites);
            Collections.sort(data);
            settingsScreenListAdapter = new SettingsScreenListAdapter(this, favorites);
            listView.setAdapter(settingsScreenListAdapter);
            searchBarAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, favorites);
            searchBar.setAdapter(searchBarAdapter);
        }
    }

    @Override
    public void finish()
    {
        Intent intent = new Intent();
        Collections.sort(favorites);
        Collections.sort(data);
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

    protected static void removeFavorite(KryptoCurrency currency)
    {
        favorites.remove(currency);
    }

    protected static void addFavorite(KryptoCurrency currency)
    {
        favorites.add(currency);
        Collections.sort(favorites);
    }
}
