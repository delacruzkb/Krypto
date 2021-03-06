package p3r5uazn.krypto;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

public class SearchPage extends AppCompatActivity
{
    private AutoCompleteTextView searchBar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_screen);

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

    //pulls from the website and updates values
    private void refreshScreen()
    {
        Toast.makeText(this, getString(R.string.update_loading_message), Toast.LENGTH_SHORT).show();
        AsyncAPI asyncAPI = new AsyncAPI(this);
        asyncAPI.execute();
    }

    //Builds all of the views within the screen and populates them with data
    private void buildViews()
    {
        //Builds search_bar with auto complete
        searchBar = findViewById(R.id.add_search_bar);
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
    }
}
