package p3r5uazn.krypto;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsPage extends AppCompatActivity
{
    public final int BACKGROUND_CODE = 1;
    private AutoCompleteTextView searchBar;
    private ListView listView;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_screen);
        listView = findViewById(R.id.favorites_list);
        listView.setEmptyView(findViewById(R.id.empty_list_item));

        //Builds all of the views within the screen and populates them with data
        buildViews();

        //start to update the list
        refreshScreen();
    }

    //Refresh values when returning from an activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
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
        AsyncUpdateFavoritesOnly updateTask = new AsyncUpdateFavoritesOnly(this,false);
        updateTask.execute();
    }

    //Builds all of the views within the screen with no data
    private void buildViews()
    {
        //Builds search_bar with auto complete and populates the search listing
        searchBar = findViewById(R.id.settings_search_bar);
        //When clicking an item, remake the listView so that any krypto that contains the item's name is shown
        searchBar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String keyWord = ((KryptoCurrency) parent.getAdapter().getItem(position)).toString();
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
