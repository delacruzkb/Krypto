package p3r5uazn.krypto;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class SettingsPage extends AppCompatActivity
{
    private ArrayList<KryptoCurrency> data;
    private ArrayList<KryptoCurrency> favorites;
    private ListView listView;
    private TextView notificationLabel;
    private Switch notificationSwitch;
    private static AutoCompleteTextView searchBar;
    private static ArrayAdapter<KryptoCurrency> searchBarAdapter;
    private SettingsScreenListAdapter settingsScreenListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_screen);
        Intent intent = getIntent();
        data = (ArrayList<KryptoCurrency>) intent.getSerializableExtra("data");
        favorites = (ArrayList<KryptoCurrency>) intent.getSerializableExtra("favorites");
        settingsScreenListAdapter = new SettingsScreenListAdapter(this, favorites);

        //Builds search_bar with auto complete
        searchBarAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, favorites);
        searchBar = findViewById(R.id.settings_search_bar);
        searchBar.setAdapter(searchBarAdapter);
        searchBar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<KryptoCurrency> tempSearch = new ArrayList<>();
                KryptoCurrency selected = (KryptoCurrency) parent.getAdapter().getItem(position);
                tempSearch.add(data.get(data.lastIndexOf(selected)));
                settingsScreenListAdapter = new SettingsScreenListAdapter(view.getContext(), tempSearch);
                listView.setAdapter(settingsScreenListAdapter);
            }
        });

        //Builds list view
        listView = findViewById(R.id.favorites_list);
        listView.setAdapter(settingsScreenListAdapter);

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
                if(notificationSwitch.isChecked())
                {
                    notificationLabel.setText(R.string.switch_on_text);
                }
                else
                {
                    notificationLabel.setText(R.string.switch_off_text);
                }
            }
        });

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

    // Required to update auto correct listing
    protected static void updateAdapter(Context context, ArrayList<KryptoCurrency> newList)
    {
        searchBarAdapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, newList);
        searchBar.setAdapter(searchBarAdapter);
    }
}
