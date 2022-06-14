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
    private var maxItems: Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adding_data)

        // Retrieve fields
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val addToDbButton = findViewById<Button>(R.id.addName)
        val addAnotherItemButton = findViewById<ImageButton>(R.id.btn_addanother_item)
        val editLinearLayout = findViewById<LinearLayout>(R.id.ll_add_items)
        val sportsName = findViewById<EditText>(R.id.enterSport)
        //val firstItem = findViewById<EditText>(R.id.enterItem)
        var itemText = getString(R.string.et_addingdata_item_text)

        //init commands for screen
        setSupportActionBar(toolbar)
        createEditText(editLinearLayout, itemText)


        //Submit Button for adding item-boxes
        addAnotherItemButton.setOnClickListener{

            if(maxItems != 6){
                // Create EditText
                createEditText(editLinearLayout, itemText)
                maxItems += 1
            }else{
                Toast.makeText(
                    this@AddingDataActivity,
                    "Max amount of items reached",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

        // below code is to add on click
        // listener to our add name button
        addToDbButton.setOnClickListener{

            // below we have created
            // a new DBHelper class,
            // and passed context to it
            val db = DBHelper(this, null)

            // creating variables for values
            // in name and age edit texts
            val sport = sportsName.text.toString()
            val items: MutableList<EditText> = ArrayList()
//            items.add(firstItem)
            for (i in 0 until editLinearLayout.getChildCount()) if (editLinearLayout.getChildAt(i) is EditText) items.add(
                editLinearLayout.getChildAt(i) as EditText
            )

            // calling method to add
            // name to our database
            db.addSport(sport, items)

            // Toast to message on the screen
            Toast.makeText(this, "Items added to database", Toast.LENGTH_LONG).show()
            println(db.readableDatabase)

            // at last, clearing edit texts
            clearTextFields(sportsName, items)
        }

    }
    fun createEditText(layout: LinearLayout, text: String){
        val editText = EditText(this)
        editText.isSingleLine = true
        editText.hint = text
        editText.setBackgroundResource(R.drawable.textview_border2)
        editText.textSize = 22F
        editText.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        editText.setPadding(20, 20, 20, 20)

        // Add EditText to LinearLayout
        layout?.addView(editText)
    }

    fun clearTextFields(sport: EditText, items: List<EditText>){
        sport.setText("")
        for (item in items){
            item.setText("")
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