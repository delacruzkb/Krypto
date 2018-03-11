package p3r5uazn.krypto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Set;

public class SettingsPage extends AppCompatActivity
{
    private ArrayList<KryptoCurrency> data;
    private ArrayList<KryptoCurrency> favorites;
    private ListView listView;
    private TextView notificationLabel;
    private Switch notificationSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_screen);
        Intent intent = getIntent();
        data = (ArrayList<KryptoCurrency>) intent.getSerializableExtra("data");
        favorites = (ArrayList<KryptoCurrency>) intent.getSerializableExtra("favorites");

        //Builds list view
        listView = findViewById(R.id.favorites_list);
        SettingsScreenListAdapter settingsScreenListAdapter = new SettingsScreenListAdapter(this, favorites);
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
}
