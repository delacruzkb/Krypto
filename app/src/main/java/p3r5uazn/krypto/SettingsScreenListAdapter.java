package p3r5uazn.krypto;

import android.app.AlertDialog;
import android.app.Dialog;
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
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<KryptoCurrency> mData;
    public SettingsScreenListAdapter(Context context, ArrayList<KryptoCurrency> data)
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
        View rowView = mLayoutInflater.inflate(R.layout.settings_screen_list_item, parent,false);



        TextView currencyName = rowView.findViewById(R.id.currency_name);
        final TextView currencyThreshold = rowView.findViewById(R.id.currency_threshold);

        final KryptoCurrency kryptoCurrency = (KryptoCurrency) getItem(position);

        ImageButton delete_button = rowView.findViewById(R.id.delete_button);
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.remove(position);
                notifyDataSetChanged();
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
                        kryptoCurrency.setThreshold(Double.parseDouble(userInput.getText().toString()));
                        currencyThreshold.setText(Double.toString(kryptoCurrency.getThreshold()));
                        notifyDataSetChanged();
                    }
                });

                Dialog dialog = alertBuilder.create();
                dialog.show();
                notifyDataSetChanged();
            }
        });

        currencyName.setText(kryptoCurrency.getName());
        currencyThreshold.setText(Double.toString(kryptoCurrency.getThreshold()));


        return rowView;
    }
}
