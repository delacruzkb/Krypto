package p3r5uazn.krypto;

import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.ArrayList;

public class SettingsScreenListAdapter extends BaseAdapter
{
    private Context context;
    private LayoutInflater mLayoutInflater;
    private ArrayList<KryptoCurrency> data;
    KryptoDatabase database;
    KryptoDatabase favoritesDatabase;
    public SettingsScreenListAdapter(Context context, ArrayList<KryptoCurrency> data)
    {
        this.context =context;
        this.data = data;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        favoritesDatabase = Room.databaseBuilder(context, KryptoDatabase.class,"Favorites").build();
        database = Room.databaseBuilder(context, KryptoDatabase.class,"Kryptos").build();
    }

    @Override
    public int getCount()
    {
        return data.size();
    }

    @Override
    public Object getItem(int position)
    {
        return data.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent)
    {
        View rowView = mLayoutInflater.inflate(R.layout.settings_screen_list_item, parent,false);

        TextView currencyName = rowView.findViewById(R.id.currency_name);

        KryptoCurrency kryptoCurrency = data.get(position);
        ImageButton delete_button = rowView.findViewById(R.id.delete_button);
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //update isFavorites
                KryptoCurrency temp = data.get(position);
                //re-add to database to update value
                AsyncTaskDeleteDatabase deleteTask = new AsyncTaskDeleteDatabase(favoritesDatabase);
                deleteTask.execute(temp);
                //refresh screen
                AsyncTaskQueryFavorites queryFavorites = new AsyncTaskQueryFavorites(context);
                queryFavorites.execute();

            }
        });


        currencyName.setText(kryptoCurrency.getName());
        return rowView;
    }
}
