package com.example.gofithub.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "FitnessApp.db"
        const val DATABASE_VERSION = 1

        // Trainer table
        const val TABLE_TRAINER = "trainer_info"
        const val COLUMN_TRAINER_ID = "id"
        const val COLUMN_FIRST_NAME = "first_name"
        const val COLUMN_LAST_NAME = "last_name"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_PROFILE_PIC = "profile_pic"
        const val COLUMN_CERTIFICATE = "certificate"
        const val COLUMN_EXPERIENCE = "experience"
        const val COLUMN_SPECIALITY = "speciality"
        const val COLUMN_BIO = "bio"

        const val CREATE_TRAINER_TABLE = """
            CREATE TABLE $TABLE_TRAINER (
                $COLUMN_TRAINER_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_FIRST_NAME TEXT,
                $COLUMN_LAST_NAME TEXT,
                $COLUMN_EMAIL TEXT UNIQUE,
                $COLUMN_PASSWORD TEXT,
                $COLUMN_PROFILE_PIC TEXT,
                $COLUMN_CERTIFICATE TEXT,
                $COLUMN_EXPERIENCE TEXT,
                $COLUMN_SPECIALITY TEXT,
                $COLUMN_BIO TEXT
            )
        """

        // User table
        const val TABLE_USER = "user_info"
        const val COLUMN_USER_ID = "id"
        const val COLUMN_USER_FIRST_NAME = "first_name"
        const val COLUMN_USER_LAST_NAME = "last_name"
        const val COLUMN_USER_EMAIL = "email"
        const val COLUMN_USER_PASSWORD = "password"
        const val COLUMN_USER_PROFILE_PIC = "profile_pic"
        const val COLUMN_USER_WEIGHT = "weight"
        const val COLUMN_USER_HEIGHT = "height"
        const val COLUMN_USER_DOB = "date_of_birth"
        const val COLUMN_USER_GOALS = "goals"
        const val COLUMN_USER_GOALS_DESCRIPTION = "goals_description"

        const val CREATE_USER_TABLE = """
            CREATE TABLE $TABLE_USER (
                $COLUMN_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_USER_FIRST_NAME TEXT,
                $COLUMN_USER_LAST_NAME TEXT,
                $COLUMN_USER_EMAIL TEXT UNIQUE,
                $COLUMN_USER_PASSWORD TEXT,
                $COLUMN_USER_PROFILE_PIC TEXT,
                $COLUMN_USER_WEIGHT REAL,
                $COLUMN_USER_HEIGHT REAL,
                $COLUMN_USER_DOB TEXT,
                $COLUMN_USER_GOALS TEXT,
                $COLUMN_USER_GOALS_DESCRIPTION TEXT
            )
        """
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TRAINER_TABLE)
        db.execSQL(CREATE_USER_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TRAINER")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USER")
        onCreate(db)
    }

    // Insert trainer
    fun insertTrainer(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        profilePic: String?,
        certificate: String?,
        experience: String,
        speciality: String,
        bio: String?
    ): Long {
        val db = writableDatabase
        val values = ContentValues()
        values.put(COLUMN_FIRST_NAME, firstName)
        values.put(COLUMN_LAST_NAME, lastName)
        values.put(COLUMN_EMAIL, email)
        values.put(COLUMN_PASSWORD, password)
        values.put(COLUMN_PROFILE_PIC, profilePic)
        values.put(COLUMN_CERTIFICATE, certificate)
        values.put(COLUMN_EXPERIENCE, experience)
        values.put(COLUMN_SPECIALITY, speciality)
        values.put(COLUMN_BIO, bio)
        return db.insert(TABLE_TRAINER, null, values)
    }

    // Insert user
    fun insertUser(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        profilePic: String?,
        weight: Double?,
        height: Double?,
        dob: String?,
        goals: String,
        goalsDescription: String?
    ): Long {
        val db = writableDatabase
        val values = ContentValues()
        values.put(COLUMN_USER_FIRST_NAME, firstName)
        values.put(COLUMN_USER_LAST_NAME, lastName)
        values.put(COLUMN_USER_EMAIL, email)
        values.put(COLUMN_USER_PASSWORD, password)
        values.put(COLUMN_USER_PROFILE_PIC, profilePic)
        values.put(COLUMN_USER_WEIGHT, weight)
        values.put(COLUMN_USER_HEIGHT, height)
        values.put(COLUMN_USER_DOB, dob)
        values.put(COLUMN_USER_GOALS, goals)
        values.put(COLUMN_USER_GOALS_DESCRIPTION, goalsDescription)
        return db.insert(TABLE_USER, null, values)
    }
}
