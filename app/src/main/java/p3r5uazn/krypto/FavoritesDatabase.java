package p3r5uazn.krypto;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;


@Database(entities = {KryptoCurrency.class}, version = 1)
public abstract class FavoritesDatabase extends RoomDatabase
{
    public abstract KryptoCurrencyDao kryptoCurrencyDao();
}
