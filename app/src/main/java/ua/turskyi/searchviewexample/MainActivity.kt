package ua.turskyi.searchviewexample

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    lateinit var adapter: ExampleAdapter
    lateinit var exampleList: MutableList<ExampleItem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fillExampleList()
        setUpRecyclerView()
    }

    private fun fillExampleList() {
        exampleList = mutableListOf()
        exampleList.apply {
            add(ExampleItem(R.drawable.ic_launcher_foreground, "One", "Ten"))
            add(ExampleItem(R.mipmap.ic_launcher, "Two", "Eleven"))
            add(ExampleItem(R.drawable.ic_audiotrack, "Three", "Twelve"))
            add(ExampleItem(R.drawable.ic_blur, "Four", "Thirteen"))
            add(ExampleItem(R.drawable.ic_zoom, "Five", "Fourteen"))
            add(ExampleItem(R.drawable.ic_watch, "Six", "Fifteen"))
            add(ExampleItem(R.drawable.ic_vpn, "Seven", "Sixteen"))
            add(ExampleItem(R.drawable.ic_wb, "Eight", "Seventeen"))
            add(ExampleItem(R.drawable.ic_wc, "Nine", "Eighteen"))
        }
    }

    private fun setUpRecyclerView() {
        adapter = ExampleAdapter()
        recyclerView.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        adapter.setData(exampleList)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.example_menu, menu)
        val searchItem: MenuItem = menu.findItem(R.id.action_search)
        val searchView: SearchView = searchItem.actionView as SearchView
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
        return true
    }
}