package app.tse.emosewa.Model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import app.tse.emosewa.Database.UserDao;

@Database(entities = {User.class}, version=1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    public abstract UserDao getUserDao();
}
