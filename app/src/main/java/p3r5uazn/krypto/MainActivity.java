package p3r5uazn.krypto;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.support.design.widget.FloatingActionButton;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private static final int BACKGROUND_CODE = 1;
    private AutoCompleteTextView searchBar;
    private ListView listView;
    private ImageButton settingsButton;
    private FloatingActionButton fab;
    private ImageButton refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        //Builds & Instantiates all of the views
        buildViews();
        //update the list with data from the database
        refreshScreen(true);

    }

    //Refresh values when returning from an activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == BACKGROUND_CODE && resultCode == Activity.RESULT_OK) {
            refreshScreen(false);
        }
    }

    //updates values on all views
    private void refreshScreen(boolean showToast)
    {
        if(showToast) {
            Toast.makeText(this, getString(R.string.update_loading_message), Toast.LENGTH_SHORT).show();
        }
        AsyncUpdateFavoritesOnly updateTask = new AsyncUpdateFavoritesOnly(this, showToast);
        updateTask.execute();
    }


    //Builds & Instantiates all of the views
    private void buildViews()
    {
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
        refresh = findViewById(R.id.imageButton);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshScreen(true);
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
        listView.setEmptyView(findViewById(R.id.empty_list_item));

        //Builds the floating action button
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(fab.getContext(), SearchPage.class);
                startActivityForResult(intent, BACKGROUND_CODE);
            }
        });
    }

}
