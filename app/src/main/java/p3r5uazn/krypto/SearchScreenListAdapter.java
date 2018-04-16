package p3r5uazn.krypto;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.ArrayList;

public class SearchScreenListAdapter extends BaseAdapter
{
    private Context context;
    private LayoutInflater mLayoutInflater;
    private ArrayList<KryptoCurrency> data;
    private KryptoDatabase favoritesDatabase;
    public SearchScreenListAdapter(Context context, ArrayList<KryptoCurrency> data)
    {
        this.context =context;
        this.data = data;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        favoritesDatabase = Room.databaseBuilder(context, KryptoDatabase.class,"Favorites").build();
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
        View rowView = mLayoutInflater.inflate(R.layout.search_screen_list_item, parent,false);

        TextView currencyName = rowView.findViewById(R.id.currency_name);
        TextView currencyCost = rowView.findViewById(R.id.currency_cost);
        TextView currencyChange = rowView.findViewById(R.id.currency_change);

        KryptoCurrency kryptoCurrency = (KryptoCurrency) getItem(position);

        currencyName.setText(kryptoCurrency.getName());
        currencyCost.setText(Double.toString(kryptoCurrency.getPriceUSD()));
        currencyChange.setText(String.format(Double.toString(kryptoCurrency.getPerChange1h())));


        ImageButton add_button = rowView.findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                KryptoCurrency temp = data.remove(position);;
                //re-add to database to update value
                AsyncTaskInsertDatabase insertTask = new AsyncTaskInsertDatabase(favoritesDatabase);
                insertTask.execute(temp);
                //refresh screen
                AsyncTaskQueryFilteredData refreshTask = new AsyncTaskQueryFilteredData(context, data);
                refreshTask.execute();
            }
        });

        return rowView;
    }
}
