package com.example.sports_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast


class AddingDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adding_data)

        val addNameButton = findViewById<Button>(R.id.addName)
       /* val printNameButton = findViewById<Button>(R.id.printName)*/
        val nameText = findViewById<TextView>(R.id.enterName)
        val ageText = findViewById<TextView>(R.id.enterAge)

        // below code is to add on click
        // listener to our add name button
        addNameButton.setOnClickListener{

            // below we have created
            // a new DBHelper class,
            // and passed context to it
            val db = DBHelper(this, null)

            // creating variables for values
            // in name and age edit texts
            val name = nameText.text.toString()
            val age = ageText.text.toString()

            // calling method to add
            // name to our database
            db.addName(name, age)

            // Toast to message on the screen
            Toast.makeText(this, name + " added to database", Toast.LENGTH_LONG).show()
            println(db.readableDatabase)

            // at last, clearing edit texts
            nameText.clearComposingText()
            ageText.clearComposingText()
        }

        // below code is to add on  click
        // listener to our print name button
//        printNameButton.setOnClickListener{
//
//            // creating a DBHelper class
//            // and passing context to it
//            val db = DBHelper(this, null)
//            val nameField = findViewById<TextView>(R.id.Name)
//            val ageField = findViewById<TextView>(R.id.Age)
//
//            // below is the variable for cursor
//            // we have called method to get
//            // all names from our database
//            // and add to name text view
//            val allNames = db.getName()
//
//
//            // moving the cursor to first position and
//            // appending value in the text view
//            allNames!!.moveToFirst()
//            nameField.append(allNames.getString(allNames.getColumnIndex(DBHelper.NAME_COl)) + "\n")
//            ageField.append(allNames.getString(allNames.getColumnIndex(DBHelper.AGE_COL)) + "\n")
//
//            // moving our cursor to next
//            // position and appending values
//            while(allNames.moveToNext()){
//                nameField.append(allNames.getString(allNames.getColumnIndex(DBHelper.NAME_COl)) + "\n")
//                ageField.append(allNames.getString(allNames.getColumnIndex(DBHelper.AGE_COL)) + "\n")
//            }
//
//            // at last we close our cursor
//            allNames.close()
//        }
    }
}