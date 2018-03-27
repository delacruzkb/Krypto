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

public class SettingsPage extends AppCompatActivity
{
    public static final int BACKGROUND_CODE = 1;
    private static ArrayList<KryptoCurrency> favorites;
    private ListView listView;
    private TextView notificationLabel;
    private Switch notificationSwitch;
    private AutoCompleteTextView searchBar;
    private ArrayAdapter<KryptoCurrency> searchBarAdapter;
    private SettingsScreenListAdapter settingsScreenListAdapter;
    private ImageButton addButton;
    private FavoritesDatabase favoritesDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_screen);
        getIntent();
        favoritesDatabase = (FavoritesDatabase)getIntent().getSerializableExtra("database");
        favorites = MainActivity.getFavorites();
        //Builds all of the views within the screen and populates them with data
        buildViews();
    }

    //Refresh values when returning from an activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == BACKGROUND_CODE && resultCode == Activity.RESULT_OK)
        {
            refreshList(this);
        }
    }


    @Override
    public void finish()
    {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        super.finish();
    }

    //refreshes the values of the screen
    protected static void refreshList(Context context)
    {
        View rootView = ((Activity)context).getWindow().getDecorView().findViewById(android.R.id.content);
        AutoCompleteTextView searchBar = rootView.findViewById(R.id.settings_search_bar);
        ListView listView = rootView.findViewById(R.id.favorites_list);
        SettingsScreenListAdapter settingsScreenListAdapter = new SettingsScreenListAdapter(context, favorites);
        listView.setAdapter(settingsScreenListAdapter);
        ArrayAdapter<KryptoCurrency> searchBarAdapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, favorites);
        searchBar.setAdapter(searchBarAdapter);
    }

    //Builds all of the views within the screen and populates them with data
    private void buildViews()
    {
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
                    /**
                     * ToDo: Write code to enable push notifications
                     *
                     * **/
                }
                else // is off
                {
                    notificationLabel.setText(R.string.switch_off_text);
                    /**
                     * ToDo: Write code to disable push notifications
                     *
                     * **/
                }
            }
        });

        //Builds add button
        addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(addButton.getContext(), SearchPage.class);
                startActivityForResult(intent, BACKGROUND_CODE);
            }
        });

        //Builds search_bar with auto complete and populates the search listing
        searchBarAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, favorites);
        searchBar = findViewById(R.id.settings_search_bar);
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
                settingsScreenListAdapter = new SettingsScreenListAdapter(view.getContext(), tempSearch);
                listView.setAdapter(settingsScreenListAdapter);
            }
        });

        //Builds listView
        listView = findViewById(R.id.favorites_list);
        //updates the list of favorites
        settingsScreenListAdapter = new SettingsScreenListAdapter(this, favorites);
        listView.setAdapter(settingsScreenListAdapter);
    }
}
