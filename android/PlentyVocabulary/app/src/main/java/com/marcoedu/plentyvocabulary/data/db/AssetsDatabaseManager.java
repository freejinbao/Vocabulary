package com.marcoedu.plentyvocabulary.data.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/*
 * /data/data/pkg/database
 * 1. Initialize AssetsDatabaseManager
 * 2. Get AssetsDatabaseManager
 * 3. Get a SQLiteDatabase object through database file
 * 4. Use this database object
 * example:
 * AssetsDatabaseManager.initManager(getApplication());
 * AssetsDatabaseManager mg = AssetsDatabaseManager.getManager();  // get a AssetsDatabaseManager object
 * SQLiteDatabase db1 = mg.getDatabase("test.db");
 */
public class AssetsDatabaseManager {
    private static String tag = "AssetsDatabase"; // for LogCat
    private static String databasepath = "/data/data/%s/database"; // %s is packageName
    // A mapping from assets database file to SQLiteDatabase object
    private Map<String, SQLiteDatabase> mDatabases = new HashMap<String, SQLiteDatabase>();
    // Context of application
    private Context context = null;
    // Singleton Pattern
    private static AssetsDatabaseManager sInstance = null;
    /**
     * Initialize AssetsDatabaseManager
     * @param context, context of application
     */
    public static void initManager(Context context){
        if(sInstance == null){
            sInstance = new AssetsDatabaseManager(context);
        }
    }
    /**
     * Get a AssetsDatabaseManager object
     * @return, if success return a AssetsDatabaseManager object, else return null
     */
    public static AssetsDatabaseManager getInstance(){
        return sInstance;
    }
    private AssetsDatabaseManager(Context context){
        this.context = context;
    }
    /**
     * Get a assets database, if this database is opened this method is only return a copy of the opened database
     * @param dbfile, the assets file which will be opened for a database
     * @return, if success it return a SQLiteDatabase object else return null
     */
    public SQLiteDatabase getDatabase(String dbfile) {
        if(mDatabases.get(dbfile) != null){
            Log.i(tag, String.format("Return a database copy of %s", dbfile));
            return (SQLiteDatabase) mDatabases.get(dbfile);
        }
        if(context==null)
            return null;
        Log.i(tag, String.format("Create database %s", dbfile));
        String spath = getDatabaseFilepath();
        String sfile = getDatabaseFile(dbfile);
        File file = new File(sfile);
        SharedPreferences dbs = context.getSharedPreferences(AssetsDatabaseManager.class.toString(), 0);
        boolean flag = dbs.getBoolean(dbfile, false); // Get Database file flag, if true means this database file was copied and valid
        if(!flag || !file.exists()){
            file = new File(spath);
            if(!file.exists() && !file.mkdirs()){
                Log.i(tag, "Create \""+spath+"\" fail!");
                return null;
            }
            if(!copyAssetsToFilesystem(dbfile, sfile)){
                Log.i(tag, String.format("Copy %s to %s fail!", dbfile, sfile));
                return null;
            }
            dbs.edit().putBoolean(dbfile, true).commit();
        }
        SQLiteDatabase db = SQLiteDatabase.openDatabase(sfile, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        if(db != null){
            mDatabases.put(dbfile, db);
        }
        return db;
    }

    private String getDatabaseFilepath(){
        return String.format(databasepath, context.getApplicationInfo().packageName);
    }

    private String getDatabaseFile(String dbfile){
        return getDatabaseFilepath()+"/"+dbfile;
    }

    private boolean copyAssetsToFilesystem(String assetsSrc, String des){
        Log.i(tag, "Copy "+assetsSrc+" to "+des);
        InputStream istream = null;
        OutputStream ostream = null;
        try{
            AssetManager am = context.getAssets();
            istream = am.open(assetsSrc);
            ostream = new FileOutputStream(des);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = istream.read(buffer))>0){
                ostream.write(buffer, 0, length);
            }
            istream.close();
            ostream.close();
        }
        catch(Exception e){
            e.printStackTrace();
            try{
                if(istream!=null)
                    istream.close();
                if(ostream!=null)
                    ostream.close();
            }
            catch(Exception ee){
                ee.printStackTrace();
            }
            return false;
        }
        return true;
    }

    /**
     * Close assets database
     * @param dbfile, the assets file which will be closed soon
     * @return, the status of this operating
     */
    public boolean closeDatabase(String dbfile){
        if(mDatabases.get(dbfile) != null){
            SQLiteDatabase db = (SQLiteDatabase) mDatabases.get(dbfile);
            db.close();
            mDatabases.remove(dbfile);
            return true;
        }
        return false;
    }

    /**
     * Close all assets database
     */
    static public void closeAllDatabase(){
        Log.i(tag, "closeAllDatabase");
        if(sInstance != null){
            for(int i=0; i<sInstance.mDatabases.size(); ++i){
                if(sInstance.mDatabases.get(i)!=null){
                    sInstance.mDatabases.get(i).close();
                }
            }
            sInstance.mDatabases.clear();
        }
    }
}
