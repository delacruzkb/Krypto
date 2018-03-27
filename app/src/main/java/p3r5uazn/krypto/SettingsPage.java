package p3r5uazn.krypto;

import android.app.Activity;
import android.arch.persistence.room.Room;
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
    public final int BACKGROUND_CODE = 1;
    private ListView listView;
    private TextView notificationLabel;
    private Switch notificationSwitch;
    private AutoCompleteTextView searchBar;
    private ArrayAdapter<KryptoCurrency> searchBarAdapter;
    private SettingsScreenListAdapter settingsScreenListAdapter;
    private ImageButton addButton;
    private KryptoDatabase favoritesDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_screen);
        favoritesDatabase = Room.databaseBuilder(this, KryptoDatabase.class,"Favorites").build();

        //start to update the list
        refreshScreen();

        /**
         * ToDo
         * Implement notification function in buildViews
         * **/
        //Builds all of the views within the screen and populates them with data
        buildViews();
    }

    //Refresh values when returning from an activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == BACKGROUND_CODE && resultCode == Activity.RESULT_OK)
        {
            refreshScreen();
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
    protected void refreshScreen()
    {
        AsyncTaskQueryFavorites queryTask = new AsyncTaskQueryFavorites(favoritesDatabase,this);
        queryTask.execute();
    }

    //Builds all of the views within the screen with no data
    private void buildViews()
    {
        ArrayList<KryptoCurrency> temp = new ArrayList<>();
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
        searchBarAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, temp);
        searchBar = findViewById(R.id.settings_search_bar);
        searchBar.setAdapter(searchBarAdapter);
        //When clicked on an item, remake the listView so that it is the only one present
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
        settingsScreenListAdapter = new SettingsScreenListAdapter(this, temp);
        listView.setAdapter(settingsScreenListAdapter);
    }
}
