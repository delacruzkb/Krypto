package p3r5uazn.krypto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


//TODO: y'all...
public class DetailsPage extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_page);
        Intent intent = getIntent();
        KryptoCurrency currency = (KryptoCurrency) intent.getSerializableExtra("KryptoCurrency");

        TextView name = findViewById(R.id.name);
        name.setText(currency.getName());
        TextView cost = findViewById(R.id.cost);
        cost.setText(Double.toString(currency.getPriceUSD()));
        TextView change = findViewById(R.id.change);
        change.setText(Double.toString(currency.getPerChange1h()));

    }
}
