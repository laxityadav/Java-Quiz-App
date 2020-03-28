package com.example.javaquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class QuizDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "JavaQuiz.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
         this.db = sqLiteDatabase;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuizConstants.QuestionsTable.TABLE_NAME + " ( " +
                QuizConstants.QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuizConstants.QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuizConstants.QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuizConstants.QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuizConstants.QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuizConstants.QuestionsTable.COLUMN_ANSWER_NR + " INTEGER" +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();
    }

    private void fillQuestionsTable() {

        String question = "Which interface provides the capability to store objects using a key-value pair?";
        String option1 = "Java.util.Map";
        String option2 = "Java.util.Set";
        String option3 = "Java.util.Collection";
        Question q1 = new Question(question, option1, option2, option3, 1);
        addQuestion(q1);

        question = "Which constructs an anonymous inner class instance?";
        option1 = "Runnable r = new Runnable() { };";
        option2 = "Runnable r = new Runnable { public void run(){}};";
        option3 = "System.out.println(new Runnable() {public void run() { }});";
        Question q2 = new Question(question, option1, option2, option3, 3);
        addQuestion(q2);

        question = "Which method must be defined by a class implementing the java.lang.Runnable interface?";
        option1 = "void run()";
        option2 = "public void start()";
        option3 = "public void run()";
        Question q3 = new Question(question, option1, option2, option3, 3);
        addQuestion(q3);

        question = "Which statement is true for the class java.util.ArrayList?";
        option1 = "The elements in the collection are ordered.";
        option2 = "The collection is guaranteed to be immutable.";
        option3 = "The elements in the collection are accessed using a unique key.";
        Question q4 = new Question(question, option1, option2, option3, 1);
        addQuestion(q4);

        question = "Which is true about a method-local inner class?";
        option1 = "It must be marked final.";
        option2 = "It can be marked abstract.";
        option3 = "It can be marked public.";
        Question q5 = new Question(question, option1, option2, option3, 2);
        addQuestion(q5);
    }

    private void addQuestion(Question question) {

        ContentValues cv = new ContentValues();
        cv.put(QuizConstants.QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuizConstants.QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuizConstants.QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuizConstants.QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuizConstants.QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        db.insert(QuizConstants.QuestionsTable.TABLE_NAME, null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + QuizConstants.QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    public QuizDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuizConstants.QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuizConstants.QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuizConstants.QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuizConstants.QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuizConstants.QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuizConstants.QuestionsTable.COLUMN_ANSWER_NR)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }
}
