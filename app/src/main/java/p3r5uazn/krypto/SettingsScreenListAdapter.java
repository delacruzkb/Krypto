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
        database = Room.databaseBuilder(context, KryptoDatabase.class,"Data").build();
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
        final TextView currencyThreshold = rowView.findViewById(R.id.currency_threshold);

        KryptoCurrency kryptoCurrency = data.get(position);
        ImageButton delete_button = rowView.findViewById(R.id.delete_button);
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //update isFavorites
                KryptoCurrency temp = data.get(position);
                temp.setFavorite(false);
                //re-add to database to update value
                AsyncTaskDeleteDatabase deleteTask = new AsyncTaskDeleteDatabase(favoritesDatabase);
                deleteTask.execute(temp);
                //refresh screen
                AsyncTaskQueryFavorites queryFavorites = new AsyncTaskQueryFavorites(favoritesDatabase,context);
                queryFavorites.execute();

            }
        });

        Button editThresholdButton = rowView.findViewById(R.id.edit_threshold_button);
        editThresholdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                View view = (LayoutInflater.from(parent.getContext())).inflate(R.layout.user_input, null);
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(parent.getContext());
                alertBuilder.setView(view);
                final EditText userInput = view.findViewById(R.id.user_input);

                alertBuilder.setCancelable(true);
                alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        //update Threshold
                        KryptoCurrency temp = data.get(position);
                        temp.setThreshold(Double.parseDouble(userInput.getText().toString()));
                        //re-add to database to update value
                        AsyncTaskInsertDatabase insertTask1 = new AsyncTaskInsertDatabase(favoritesDatabase);
                        insertTask1.execute(temp);
                        AsyncTaskInsertDatabase insertTask2 = new AsyncTaskInsertDatabase(database);
                        insertTask2.execute(temp);
                        //refresh screen
                        AsyncTaskQueryFavorites queryFavorites = new AsyncTaskQueryFavorites(favoritesDatabase,context);
                        queryFavorites.execute();
                    }
                });

                Dialog dialog = alertBuilder.create();
                dialog.show();
                notifyDataSetChanged();
            }
        });

        currencyName.setText(kryptoCurrency.getName());
        currencyThreshold.setText(String.format(Double.toString(kryptoCurrency.getThreshold())));


        return rowView;
    }
}
