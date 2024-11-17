package database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "GoFitHub.db"
        private const val DATABASE_VERSION = 1

        // Table for Users
        const val TABLE_USERS = "users"
        const val COLUMN_USER_ID = "id"
        const val COLUMN_USER_FIRST_NAME = "first_name"
        const val COLUMN_USER_LAST_NAME = "last_name"
        const val COLUMN_USER_EMAIL = "email"
        const val COLUMN_USER_PASSWORD = "password"
        const val COLUMN_USER_DOB = "dob"
        const val COLUMN_USER_WEIGHT = "weight"
        const val COLUMN_USER_HEIGHT = "height"
        const val COLUMN_USER_GOAL_TYPE = "goal_type"
        const val COLUMN_USER_BIO = "bio"
        const val COLUMN_USER_PROFILE_PICTURE = "profile_picture"

        // Table for Trainers
        const val TABLE_TRAINERS = "trainers"
        const val COLUMN_TRAINER_ID = "id"
        const val COLUMN_TRAINER_FIRST_NAME = "first_name"
        const val COLUMN_TRAINER_LAST_NAME = "last_name"
        const val COLUMN_TRAINER_EMAIL = "email"
        const val COLUMN_TRAINER_PASSWORD = "password"
        const val COLUMN_TRAINER_EXPERIENCE = "experience"
        const val COLUMN_TRAINER_SPECIALTY = "specialty"
        const val COLUMN_TRAINER_CERTIFICATE = "certificate"
        const val COLUMN_TRAINER_BIO = "bio"
        const val COLUMN_HOURLY_RATE = "hourly_rate"
        const val COLUMN_TRAINER_PROFILE_PICTURE = "profile_picture"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // Creating Users Table
        val CREATE_USERS_TABLE = ("CREATE TABLE $TABLE_USERS (" +
                "$COLUMN_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_USER_FIRST_NAME TEXT, " +
                "$COLUMN_USER_LAST_NAME TEXT, " +
                "$COLUMN_USER_EMAIL TEXT UNIQUE, " +
                "$COLUMN_USER_PASSWORD TEXT, " +
                "$COLUMN_USER_DOB TEXT, " +
                "$COLUMN_USER_WEIGHT REAL, " +
                "$COLUMN_USER_HEIGHT REAL, " +
                "$COLUMN_USER_GOAL_TYPE TEXT, " +
                "$COLUMN_USER_BIO TEXT, " +
                "$COLUMN_USER_PROFILE_PICTURE TEXT)")

        // Creating Trainers Table
        val CREATE_TRAINERS_TABLE = ("CREATE TABLE $TABLE_TRAINERS (" +
                "$COLUMN_TRAINER_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_TRAINER_FIRST_NAME TEXT, " +
                "$COLUMN_TRAINER_LAST_NAME TEXT, " +
                "$COLUMN_TRAINER_EMAIL TEXT UNIQUE, " +
                "$COLUMN_TRAINER_PASSWORD TEXT, " +
                "$COLUMN_TRAINER_EXPERIENCE TEXT, " +
                "$COLUMN_TRAINER_SPECIALTY TEXT, " +
                "$COLUMN_TRAINER_CERTIFICATE TEXT, " +
                "$COLUMN_TRAINER_BIO TEXT, " +
                "$COLUMN_HOURLY_RATE REAL, " +
                "$COLUMN_TRAINER_PROFILE_PICTURE TEXT)")

        // Execute the queries to create tables
        db?.execSQL(CREATE_USERS_TABLE)
        db?.execSQL(CREATE_TRAINERS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Drop tables if they exist and recreate
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_TRAINERS")
        onCreate(db)
    }

    // Function to add user to the database
    fun addUser(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        dob: String,
        weight: Double,
        height: Double,
        goalType: String,
        bio: String,
        profilePicture: String
    ): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USER_FIRST_NAME, firstName)
            put(COLUMN_USER_LAST_NAME, lastName)
            put(COLUMN_USER_EMAIL, email)
            put(COLUMN_USER_PASSWORD, password)
            put(COLUMN_USER_DOB, dob)
            put(COLUMN_USER_WEIGHT, weight)
            put(COLUMN_USER_HEIGHT, height)
            put(COLUMN_USER_GOAL_TYPE, goalType)
            put(COLUMN_USER_BIO, bio)
            put(COLUMN_USER_PROFILE_PICTURE, profilePicture)
        }
        val result = db.insert(TABLE_USERS, null, values)
        db.close()
        return result
    }

    // Function to add trainer to the database
    fun addTrainer(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        experience: String,
        specialty: String,
        certificate: String,
        bio: String,
        hourlyRate: Double,
        profilePicture: String
    ): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TRAINER_FIRST_NAME, firstName)
            put(COLUMN_TRAINER_LAST_NAME, lastName)
            put(COLUMN_TRAINER_EMAIL, email)
            put(COLUMN_TRAINER_PASSWORD, password)
            put(COLUMN_TRAINER_EXPERIENCE, experience)
            put(COLUMN_TRAINER_SPECIALTY, specialty)
            put(COLUMN_TRAINER_CERTIFICATE, certificate)
            put(COLUMN_TRAINER_BIO, bio)
            put(COLUMN_HOURLY_RATE,hourlyRate)
            put(COLUMN_TRAINER_PROFILE_PICTURE, profilePicture)
        }
        val result = db.insert(TABLE_TRAINERS, null, values)
        db.close()
        return result
    }
}
