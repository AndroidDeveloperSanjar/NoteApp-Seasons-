package com.example.noteapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_items.*
import kotlinx.android.synthetic.main.app_bar_items.*
import kotlinx.android.synthetic.main.content_items.*
import kotlinx.android.synthetic.main.layout_settings_toolbar.*

class ItemsActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener,
    IItems{

    private val s = "ItemsActivity"

    var accountFragment: AccountFragment? = null

    var settingsFragment: SettingsFragment? = null

    private val noteLayoutManager by lazy {
        LinearLayoutManager(this)
    }

    private val noteRecyclerAdapter by lazy {
        NotesItemAdapter(this)
    }

    private val courseLayoutManager by lazy {
        GridLayoutManager(this, 2)
    }

    private val courseRecyclerAdapter by lazy {
        CoursesItemAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items)

        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            val intent = Intent(this,NoteActivity::class.java)
            startActivity(intent)
        }

    val toggle = ActionBarDrawerToggle(
        this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
    drawer_layout.addDrawerListener(toggle)
    toggle.syncState()

    nav_view.setNavigationItemSelectedListener(this)

    displayNotes()

    }

    override fun onResume() {
        super.onResume()
        noteRecyclerAdapter.notifyDataSetChanged()
    }

    private fun displayNotes() {
        recyclerItems?.layoutManager = noteLayoutManager
        recyclerItems?.adapter = noteRecyclerAdapter
        nav_view.menu.findItem(R.id.nav_notes).isChecked = true
    }

    private fun displayCourses() {
        recyclerItems?.layoutManager = courseLayoutManager
        recyclerItems?.adapter = courseRecyclerAdapter
        nav_view.menu.findItem(R.id.nav_courses).isChecked = true
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START))
            drawer_layout.closeDrawer(GravityCompat.START)
        else
            super.onBackPressed()

        correctSettingsToolbarVisibility()
    }

    private fun correctSettingsToolbarVisibility(){
        if(settingsFragment != null){
            if(settingsFragment!!.isVisible)
                showSettingsAppBar()
            else
                hideSettingsAppBar()
            return
        }
        hideSettingsAppBar()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.items, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                inflateSettingsFragment()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun inflateAccountFragment() {
        if(accountFragment == null){
            accountFragment = AccountFragment()
        }
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.account_container, accountFragment!!, FRAGMENT_ACCOUNT)
        transaction.addToBackStack(FRAGMENT_ACCOUNT)
        transaction.commit()
    }

    private fun inflateSettingsFragment(){
        printToLog("Inflating Settings Fragment")
        if(settingsFragment == null){
            settingsFragment = SettingsFragment()
        }
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.settings_container, settingsFragment!!,FRAGMENT_SETTINGS)
        transaction.addToBackStack(FRAGMENT_SETTINGS)
        transaction.commit()
    }

    override fun showSettingsAppBar() {
        settings_app_bar.visibility = View.VISIBLE
    }

    override fun hideSettingsAppBar() {
        settings_app_bar.visibility = View.GONE
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_notes -> displayNotes()

            R.id.nav_courses -> displayCourses()

            R.id.nav_send -> showSnackbar("Clicked send menu item!")

            R.id.nav_share -> showSnackbar("Clicked share menu item!")
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(recyclerItems, message, Snackbar.LENGTH_LONG).show()
    }

    private fun printToLog(message: String){
        Log.d(s, message)
    }

}
