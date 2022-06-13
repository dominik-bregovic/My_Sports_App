package com.example.sports_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val addButton = findViewById<ImageButton>(R.id.btn_main_add)
        addButton.setOnClickListener{
            startActivity(Intent(this@MainActivity, AddingDataActivity::class.java))
            finish()
        }
        val logoutButton = findViewById<Button>(R.id.btn_main_logout)
        logoutButton.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        }

        createSpinner()
    }

    fun createSpinner(){
        val db = DBHelper(this, null)
        val courser = db.getSports()
        val list: MutableList<String> = ArrayList()
        val spinner = findViewById<Spinner>(R.id.spinnerSports)
        courser!!.moveToFirst()
        try {
            while (courser.moveToNext()) {
                list.add(courser.getString(courser.getColumnIndex(DBHelper.SPORT_COL)))
            }
        } finally {
            courser.close();
        }

        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, list)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                Toast.makeText(this@MainActivity,
                    list[position], Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
    }

    fun changeToSelectedView(sport: String){
        startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        finish()
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