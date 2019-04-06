package matomiskov.nakupnyzoznam;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "sqLiteDb";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlZoznamy = "CREATE TABLE zoznamy(id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR);";
        String sqlPolozky = "CREATE TABLE polozky(id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, poznamka VARCHAR, category INTEGER, photo VARCHAR, zoznam_id INTEGER, FOREIGN KEY(zoznam_id) REFERENCES zoznamy(id));";

        sqLiteDatabase.execSQL(sqlZoznamy);
        sqLiteDatabase.execSQL(sqlPolozky);
    }

    public boolean addZoznam (String name){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        db.insert("zoznamy", null, contentValues);
        db.close();
        return true;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sqlZoznamy = "DROP TABLE IF EXISTS zoznamy";
        String sqlPolozky = "DROP TABLE IF EXISTS polozky";

        sqLiteDatabase.execSQL(sqlZoznamy);
        sqLiteDatabase.execSQL(sqlPolozky);

        onCreate(sqLiteDatabase);
    }
}
