package com.example.sports_app

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth

/**
 * Selecting the sport of your choice
 * Here jumping from one activity to another: AddingData, ShowSport and back to login
 * Authors: Gerwald Gindrawady, Dominik Bregovic, Lukas Linzer
 * Last Changed: 13.06.2022
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val addButton = findViewById<ImageButton>(R.id.btn_main_add)
        addButton.setOnClickListener{
            startActivity(Intent(this@MainActivity, AddingDataActivity::class.java))
//            finish()
        }

        createSpinner()
    }

    @SuppressLint("Range")
    fun createSpinner(){
        val db = DBHelper(this, null)
        val courser = db.getSportsTable()
        val list: MutableList<String> = ArrayList()
        val spinner = findViewById<Spinner>(R.id.spinnerSports)

        // getting the Data from DB
        list.add("Select...")
        courser!!.moveToFirst()
        try {
            if (courser != null &&  courser.moveToFirst()){
                do {
                    var course = courser.getString(courser.getColumnIndex(DBHelper.SPORT_COL))
                    if (!list.contains(course)){
                        list.add(course)
                    }
                }while (courser.moveToNext())
            }

        } finally {
            courser.close();
        }

        // adding the Data to Spinner
        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, list)
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                if (position > 0 )
                changeToSelectedView(list[position])
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
    }

    fun changeToSelectedView(sport: String){
        val intent = Intent(this@MainActivity, ShowSportActivity::class.java)
        intent.putExtra("sport",sport)
        startActivity(intent)
//        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.tb_menu_logout){
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}