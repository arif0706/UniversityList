package com.example.universitylist.Room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.universitylist.Model.Model;

import java.util.List;

@Dao
public interface DatabaseDao {

    @Query("Select distinct * from University")
    List<Model> getUniversities();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertUniversity(Model model);
}
