package ph41045.fpoly.onthi.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import ph41045.fpoly.onthi.Model.ThiSinh;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "appdb.sqlite";
    public static final int DB_VERSION = 1;

    public static final String TABLE_THISINH = "ThiSinh";
    public static final String COL_ID = "id";
    public static final String COL_HOTEN = "hoten";
    public static final String COL_CATHI = "cathi";
    public static final String COL_PHONGTHI = "phongthi";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_THISINH + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_HOTEN + " TEXT, " +
                COL_CATHI + " INTEGER, " +
                COL_PHONGTHI + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_THISINH);
        onCreate(db);
    }

    // Thêm thí sinh từ model ThiSinh
    public boolean themThiSinh(ThiSinh thiSinh) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_HOTEN, thiSinh.getHoTen());
        values.put(COL_CATHI, thiSinh.getCaThi());
        values.put(COL_PHONGTHI, thiSinh.getPhongThi());
        long result = db.insert(TABLE_THISINH, null, values);
        return result != -1;
    }

    // Lấy toàn bộ thí sinh từ CSDL
    public List<ThiSinh> getAllThiSinh() {
        List<ThiSinh> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_THISINH, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
                String hoTen = cursor.getString(cursor.getColumnIndexOrThrow(COL_HOTEN));
                int caThi = cursor.getInt(cursor.getColumnIndexOrThrow(COL_CATHI));
                String phongThi = cursor.getString(cursor.getColumnIndexOrThrow(COL_PHONGTHI));

                list.add(new ThiSinh(id, hoTen, caThi, phongThi));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }
}
