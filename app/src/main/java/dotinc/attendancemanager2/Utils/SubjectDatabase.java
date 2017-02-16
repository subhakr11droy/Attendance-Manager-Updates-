package dotinc.attendancemanager2.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import dotinc.attendancemanager2.Objects.SubjectsList;
import dotinc.attendancemanager2.Objects.TimeTableList;

/**
 * Created by vellapanti on 18/1/16.
 */
public class SubjectDatabase extends SQLiteOpenHelper {
    public static final int Database_Version = 2;
    public static final String Subject_Name_Databse = "subject_name";
    public static final String Subjects_Table = "Semester_Subjects";
    public static final String Subject_Id = "subject_id";
    public static final String Subject_List = "subject_list";
    public static final String past_total_classes = "past_total_classes";
    public static final String past_attended_classes = "past_attended_classes";


    public SubjectDatabase(Context context) {
        super(context, Subject_Name_Databse, null, Database_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SUBJECTS = "CREATE TABLE " + Subjects_Table + "(" + Subject_Id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Subject_List + " VARCHAR(30)," + past_attended_classes + " INTEGER DEFAULT 0,"
                + past_total_classes + " INTEGER DEFAULT 0);";
        db.execSQL(SUBJECTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("option_ver_old", String.valueOf(oldVersion));
        Log.d("option_ver_new", String.valueOf(newVersion));
        String table_update = "ALTER TABLE " + Subjects_Table + " ADD COLUMN " + past_attended_classes + " INTEGER DEFAULT 0";
        String table_update_2 = "ALTER TABLE " + Subjects_Table + " ADD COLUMN " + past_total_classes + " INTEGER DEFAULT 0";

        db.execSQL(table_update);
        db.execSQL(table_update_2);
    }

    public void addsubject(SubjectsList list) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Subject_List, list.getSubjectName());
        db.insert(Subjects_Table, null, values);
        db.close();
    }

    public void editSubject(String new_subject, String old_subject) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + Subjects_Table + " SET " + Subject_List + " = '" + new_subject +
                "' WHERE " + Subject_List + " = '" + old_subject + "'";
        db.execSQL(query);
        db.close();
    }

    public void deleteSubject(String subject) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + Subjects_Table + " WHERE " + Subject_List + " = '" + subject + "'";
        db.execSQL(query);
        db.close();
    }

    public ArrayList<SubjectsList> getAllSubjects() {
        ArrayList<SubjectsList> SubjectName = new ArrayList<SubjectsList>();
        String query = "SELECT * FROM " + Subjects_Table;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToNext()) {
            do {
                SubjectsList subjectsList = new SubjectsList();
                subjectsList.setId(cursor.getInt(0));
                subjectsList.setSubjectName(cursor.getString(1));
                SubjectName.add(subjectsList);

            } while (cursor.moveToNext());
        }
        db.close();

        return SubjectName;
    }

    public void addPastAttendace(int subject_id ,  int attended_classes , int total_classes){
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "UPDATE " + Subjects_Table + " SET " + past_total_classes + " = " + total_classes+
                " , " + past_attended_classes + " = "+ attended_classes+
                " WHERE " + Subject_Id + " = " + subject_id;
        database.execSQL(query);
        database.close();

    }
    public int getPastTotalAttendance(int subject_id){
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT "+ past_total_classes +" FROM "+ Subjects_Table +
                " WHERE " +Subject_Id + " = "+subject_id;
        int past_total_attendace =0;
        Cursor cursor = database.rawQuery(query , null);

             while (cursor.moveToNext()){
                 past_total_attendace = cursor.getInt(0);
             }
        Log.d("option_tot", String.valueOf(past_total_attendace));
        return  past_total_attendace;
    }
    public int getPastAttendedAttendance(int subject_id){
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT "+ past_attended_classes +" FROM "+ Subjects_Table +
                " WHERE " +Subject_Id + " = "+subject_id;
        int past_attended_attendace =0;
        Cursor cursor = database.rawQuery(query , null);

        while (cursor.moveToNext()){
            past_attended_attendace = cursor.getInt(0);
        }
        Log.d("option_attende", String.valueOf(past_attended_attendace));
        return  past_attended_attendace;
    }


    public void addMultipleSubjects(ArrayList<String> subjects) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        for (int size = 0; size < subjects.size(); size++) {
            values.put(Subject_List, subjects.get(size));
            database.insert(Subjects_Table, null, values);
        }
        database.close();
    }

    public ArrayList<TimeTableList> getAllSubjectsForExtra() {
        ArrayList<TimeTableList> SubjectName = new ArrayList<>();
        String query = "SELECT * FROM " + Subjects_Table + " GROUP BY " + Subject_List;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToNext()) {
            do {
                TimeTableList timeTableList = new TimeTableList();
                timeTableList.setId(cursor.getInt(0));
                timeTableList.setSubjectName(cursor.getString(1));
                SubjectName.add(timeTableList);

            } while (cursor.moveToNext());
        }
        db.close();
        return SubjectName;
    }

    public boolean exportData() {
        return Helper.exportDatabase(Subject_Name_Databse);
    }

    public boolean importData() {
        return Helper.importDatabase(Subject_Name_Databse);
    }
}