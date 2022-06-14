package com.example.sports_app

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth

/**
 * Show the sport with the necessary equipment retrieved from DB
 * Authors: Gerwald Gindrawady, Dominik Bregovic, Lukas Linzer
 * Last Changed: 13.06.2022
 */
class ShowSportActivity : AppCompatActivity() {
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_sport)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val intent = intent
        val choosenSport = intent.getStringExtra("sport")
        val db = DBHelper(this, null)
        val header = findViewById<TextView>(R.id.tv_showsport_header)
        val equipment = findViewById<TextView>(R.id.ml_showequipment_text)
        val courser = db.getSportsTable()
        val items: MutableList<String> = ArrayList()
        val sports: MutableList<String> = ArrayList()


        setSupportActionBar(toolbar)
        header.text = choosenSport
        courser!!.moveToFirst()
        try {
            do {
                items.add(courser.getString(courser.getColumnIndex(DBHelper.ITEM_COL)))
                sports.add(courser.getString(courser.getColumnIndex(DBHelper.SPORT_COL)))
            }while (courser.moveToNext())
        } finally {
            courser.close();
        }
        println(choosenSport)
        var entry = ""
        for (i in sports.indices){
            println(i)
            print(sports[i])
            if(sports[i] == choosenSport){
                entry += "- " + items[i] + "\n"

                println(entry)
            }

        }
        equipment.text = entry
        println(items)
        println(sports)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.tb_menu_logout){
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this@ShowSportActivity, LoginActivity::class.java))
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}