package com.example.newsshort

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.GridLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item.*
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity(),NewsItemClicked,CatItemClicked {

    private lateinit var mAdapter: NewsListAdapter
     var count=0
     var desh="in"
     var cat_data="general"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

//        share.setOnClickListener {
//            val i=Intent(Intent.ACTION_SEND)
//            i.type="text/plain"
//            i.putExtra(Intent.EXTRA_TEXT, newsurl)
//            val chooser=Intent.createChooser(i,"News Share")
//            startActivity(i)
//        }



        recycle.layoutManager = LinearLayoutManager(this)
        fetchData()
        mAdapter = NewsListAdapter(this,this)
        recycle.adapter = mAdapter
        //cat data
        val array= ArrayList<cat_data>()
        array.add((cat_data(R.drawable.general,"General")))
         array.add((cat_data(R.drawable.sports,"Sports")))
        array.add((cat_data(R.drawable.health,"Health")))
        array.add((cat_data(R.drawable.science,"Science")))
        array.add((cat_data(R.drawable.technology,"Technology")))
        array.add((cat_data(R.drawable.entertainment,"Entertainment")))
        cat_recycle.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        val catAdapter=CategoryAdapter(array,this)
         cat_recycle.adapter=catAdapter


        val country = arrayListOf<String>(
            "INDIA",
            "USA",
            "RUSSIA",
            "CHINA",
            "New Zealand",
            "UNITED ARAB EMIRTAS"
        )
        val adapter = ArrayAdapter(
            this,
            R.layout.row_layout, R.id.text, country
        )
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
               position: Int,
                id: Long
            ) {
                  count= position
                  if(position==null){
                       count=0
                  }
//                // get selected item text
//                //Toast.makeText(this@MainActivity, "${parent.getItemAtPosition(position)}", Toast.LENGTH_SHORT).show()
                if(count==0) {
                    desh="in"
                    //Toast.makeText(this@MainActivity, "india", Toast.LENGTH_SHORT).show()
                }
                if(count==1){
                    //Toast.makeText(this@MainActivity, "usa country", Toast.LENGTH_SHORT).show()
                    desh="us"
                }
                if(count==2){
                      desh="ru"
                }
                if(count==3){
                    desh="ch"
                }
                if(count==4){
                    desh="nz"
                }
                if(count==5){
                    desh="ae"
                }
                    fetchData()
//
            }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // another interface callback
                }
            }
        }


        private fun fetchData() {

            val url =
                "https://newsapi.org/v2/top-headlines?country=${desh}&category=${cat_data}&apiKey=9caf9106ea20488581e20b523c1b2216"
            val jsonObjectRequest = object : JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                {
                    val newsJsonArray = it.getJSONArray("articles")
                    val newsArray = ArrayList<News>()
                    for (i in 0 until newsJsonArray.length()) {
                        val newsJsonObject = newsJsonArray.getJSONObject(i)
                        val news = News(
                            newsJsonObject.getString("title"),
                            newsJsonObject.getString("author"),
                            newsJsonObject.getString("url"),
                            newsJsonObject.getString("urlToImage")
                        )
                        newsArray.add(news)
                    }

                    mAdapter.updateNews(newsArray)
                },
                {
                    // Toast.makeText(this, "volly exception", Toast.LENGTH_SHORT).show()
                }
            ) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["User-Agent"] = "Mozilla/5.0"
                    return headers
                }
            }
            MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
        }

        override fun onItemClicked(item: News) {
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(this@MainActivity, Uri.parse(item.url))
        }

    override fun onItemClicked(item: cat_data) {
        if(item.name=="General"){
            cat_data="General"
        }
        if((item.name)=="Sports"){
            cat_data="Sports"
        }
        if((item.name)=="Health"){
            cat_data="Health"
        }
        if((item.name)=="Science"){
            cat_data="Science"
        }
        if((item.name)=="Technology"){
            cat_data="Technology"
        }
        if((item.name)=="Entertainment"){
            cat_data="Entertainment"
        }
        fetchData()
        Toast.makeText(this, "wait your ${item.name} news is loading", Toast.LENGTH_SHORT).show()
    }


    override fun onCreateOptionsMenu(menu:Menu): Boolean {
        menuInflater.inflate(R.menu.link_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.about-> {
                //Toast.makeText(this, "about", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, About_activity::class.java))
            }
            R.id.sign_out->{
                 val alert=AlertDialog.Builder(this)
                           .setTitle("SIGN OUT")
                           .setMessage("Are you sure want to sign out")
                           .setNegativeButton("no",DialogInterface.OnClickListener { dialog, which ->
                           })
                           .setPositiveButton("yes",DialogInterface.OnClickListener {
                                   dialog, which ->
                                 Firebase.auth.signOut()
                                 googleSignInClient.signOut()
                                 startActivity(Intent(this,SignIn::class.java))
                                 finish()
                           })
                     .create()
                     alert.show()
//                Firebase.auth.signOut()
//                googleSignInClient.signOut()
//                startActivity(Intent(this,SignIn::class.java))
//                finish()
            //Toast.makeText(this, "signout", Toast.LENGTH_SHORT).show()
            }
        }

        return super.onOptionsItemSelected(item)
    }

}