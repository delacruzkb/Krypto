package p3r5uazn.krypto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;


public class HomeScreenListAdapter extends BaseAdapter
{
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<KryptoCurrency> mFavorites;

    public HomeScreenListAdapter(Context context, ArrayList<KryptoCurrency> favorites)
    {
        mContext =context;
        mFavorites = favorites;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount()
    {
        return mFavorites.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mFavorites.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View rowView = mLayoutInflater.inflate(R.layout.home_screen_list_item, parent,false);

        TextView currencyName = rowView.findViewById(R.id.currency_name);
        TextView currencyCost = rowView.findViewById(R.id.currency_cost);
        TextView currencyChange = rowView.findViewById(R.id.currency_change);

        KryptoCurrency kryptoCurrency = (KryptoCurrency) getItem(position);

        currencyName.setText(kryptoCurrency.getName());
        currencyCost.setText(Double.toString(kryptoCurrency.getPriceUSD()));
        currencyChange.setText(String.format(Double.toString(kryptoCurrency.getPerChange1h())));


        return rowView;
    }
}
