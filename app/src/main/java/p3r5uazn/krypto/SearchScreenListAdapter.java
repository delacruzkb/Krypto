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
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<KryptoCurrency> mData;
    public SearchScreenListAdapter(Context context, ArrayList<KryptoCurrency> data)
    {
        mContext =context;
        mData = data;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount()
    {
        return mData.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mData.get(position);
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
                KryptoCurrency temp = mData.remove(position);
                KryptoDatabase favoritesDatabase = Room.databaseBuilder(mContext, KryptoDatabase.class,"Favorites").build();
                AsyncTaskInsertDatabase insertTask = new AsyncTaskInsertDatabase(favoritesDatabase);
                insertTask.execute(temp);
                KryptoDatabase dataDatabase = Room.databaseBuilder(mContext, KryptoDatabase.class,"Data").build();
            }
        });

        return rowView;
    }
}
