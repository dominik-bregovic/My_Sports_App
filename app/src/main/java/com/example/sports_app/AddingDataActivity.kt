package com.example.sports_app

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth

//import com.tutorialwing.dynamicedittext.databinding.ActivityMainBinding

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
        val addAnotherItemButton = findViewById<ImageButton>(R.id.btn_addanother_item)
        val editLinearLayout = findViewById<LinearLayout>(R.id.ll_add_items)
       /* val printNameButton = findViewById<Button>(R.id.printName)*/
        val sportText = findViewById<TextView>(R.id.enterSport)
        val itemText = findViewById<TextView>(R.id.enterItem)


        addAnotherItemButton.setOnClickListener{
            // Create EditText
            val editText = EditText(this)
            editText
            editText.hint = "Enter item"
            editText.setBackgroundResource(R.drawable.textview_border2)
            editText.textSize = 22F
            editText.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
            editText.setPadding(20, 20, 20, 20)

            // Add EditText to LinearLayout
            editLinearLayout?.addView(editText)
        }

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
            val items: MutableList<EditText> = ArrayList()
//            items.add(item)
            for (i in 0 until editLinearLayout.getChildCount()) if (editLinearLayout.getChildAt(i) is EditText) items.add(
                editLinearLayout.getChildAt(i) as EditText
            )

            // calling method to add
            // name to our database
            db.addSport(sport, items)

            // Toast to message on the screen
            Toast.makeText(this, item + " added to database", Toast.LENGTH_LONG).show()
            println(db.readableDatabase)

            // at last, clearing edit texts
            sportText.clearComposingText()
            itemText.clearComposingText()
        }

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