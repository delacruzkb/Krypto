package p3r5uazn.krypto;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.widget.ListView;

import java.util.List;

@Dao
public interface KryptoCurrencyDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertKryptoCurrency(KryptoCurrency kryptoCurrency);

    @Query("SELECT * FROM KryptoCurrency WHERE isFavorite LIKE :isfav")
    List<KryptoCurrency> getAllFavorites(boolean isfav);
}
