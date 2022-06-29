package eg.esperantgada.internship.room

import androidx.room.Database
import androidx.room.RoomDatabase
import eg.esperantgada.internship.room.dao.ImageDao
import eg.esperantgada.internship.room.dao.RemoteKeyDao
import eg.esperantgada.internship.room.entities.PhotoItem
import eg.esperantgada.internship.room.entities.RemoteKey

@Database(entities = [PhotoItem::class, RemoteKey::class], version = 2, exportSchema = false)
abstract class ImageDatabase : RoomDatabase(){

    abstract fun getImageDao() : ImageDao

    abstract fun getRemoteKeyDao() : RemoteKeyDao
}