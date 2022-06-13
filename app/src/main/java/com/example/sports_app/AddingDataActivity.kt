package com.example.sports_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth

/**
 * Here adding Data from app to DB
 * Authors: Gerwald Gindrawady, Dominik Bregovic, Lukas Linzer
 * Last Changed: 13.06.2022
 */
class AddingDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adding_data)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val addNameButton = findViewById<Button>(R.id.addName)
       /* val printNameButton = findViewById<Button>(R.id.printName)*/
        val sportText = findViewById<TextView>(R.id.enterSport)
        val itemText = findViewById<TextView>(R.id.enterItem)

        // below code is to add on click
        // listener to our add name button
        addNameButton.setOnClickListener{

            // below we have created
            // a new DBHelper class,
            // and passed context to it
            val db = DBHelper(this, null)

            // creating variables for values
            // in name and age edit texts
            val sport = sportText.text.toString()
            val item = itemText.text.toString()

            // calling method to add
            // name to our database
            db.addSport(sport, item)

            // Toast to message on the screen
            Toast.makeText(this, item + " added to database", Toast.LENGTH_LONG).show()
            println(db.readableDatabase)

            // at last, clearing edit texts
            sportText.clearComposingText()
            itemText.clearComposingText()
        }

        // below code is to add on  click
        // listener to our print name button
//        printNameButton.setOnClickListener{
//
//            // creating a DBHelper class
//            // and passing context to it
//            val db = DBHelper(this, null)
//            val nameField = findViewById<TextView>(R.id.Sport)
//            val ageField = findViewById<TextView>(R.id.Item)
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
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.tb_menu_logout){
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this@AddingDataActivity, LoginActivity::class.java))
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}