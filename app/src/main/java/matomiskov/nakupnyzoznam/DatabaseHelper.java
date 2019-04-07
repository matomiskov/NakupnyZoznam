package matomiskov.nakupnyzoznam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "sqLiteDb";
    private static final int DB_VERSION = 1;

    private static final String ZOZNAMY = "zoznamy";
    private static final String POLOZKY = "polozky";

    private static final String ID = "id";
    private static final String NAME = "name";

    private static final String POZNAMKA = "poznamka";
    private static final String CATEGORY = "category";
    private static final String PHOTO = "photo";
    private static final String ZOZNAM_ID = "zoznam_id";

    private static final String sqlZoznamy = "CREATE TABLE " + ZOZNAMY + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + NAME + " VARCHAR);";
    private static final String sqlPolozky = "CREATE TABLE " + POLOZKY + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + NAME + " VARCHAR," + POZNAMKA + " VARCHAR," + CATEGORY + " INTEGER," + PHOTO + " VARCHAR," + ZOZNAM_ID + " INTEGER, FOREIGN KEY(zoznam_id) REFERENCES zoznamy(id));";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(sqlZoznamy);
        sqLiteDatabase.execSQL(sqlPolozky);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ZOZNAMY);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + POLOZKY);

        onCreate(sqLiteDatabase);
    }

    public long createZoznam(Zoznam zoznam) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, zoznam.getName());

        long zoznamId = db.insert(ZOZNAMY, null, contentValues);
Log.d("Zapis zoznamu do DB", String.valueOf(zoznamId));
        return zoznamId;
    }

    public Zoznam getZoznam(long zoznamId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + ZOZNAMY + " WHERE "
                + ID + " = " + zoznamId;

        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null)
            c.moveToFirst();

        Zoznam zoznam = new Zoznam();
        zoznam.setId(c.getInt(c.getColumnIndex(ID)));
        zoznam.setName(c.getString(c.getColumnIndex(NAME)));

        return zoznam;
    }

    public List<Zoznam> getAllZoznamy() {
        List<Zoznam> zoznamy = new ArrayList<Zoznam>();
        String selectQuery = "SELECT  * FROM " + ZOZNAMY;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                Zoznam zoznam = new Zoznam();
                zoznam.setId(c.getInt((c.getColumnIndex(ID))));
                zoznam.setName((c.getString(c.getColumnIndex(NAME))));

                zoznamy.add(zoznam);
            } while (c.moveToNext());
        }
        return zoznamy;
    }

    public int updateZoznam(Zoznam zoznam) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, zoznam.getName());

        return db.update(ZOZNAMY, contentValues, ID + " = ?", new String[]{String.valueOf(zoznam.getId())});
    }

    public void deleteZoznam(int zoznamId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ZOZNAMY, ID + " = ?",
                new String[]{String.valueOf(zoznamId)});
    }

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}
