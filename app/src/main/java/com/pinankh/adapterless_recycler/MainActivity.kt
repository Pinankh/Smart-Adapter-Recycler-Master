package com.pinankh.adapterless_recycler

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pinankh.adapterless_recycler.databinding.ActivityMainBinding
import com.pinankh.adapterless_recycler.databinding.ItemRecyclerviewBinding
import com.pinankh.smartadapter.smartRecyclerAdapter


/**
 * Created by @Author Pinankh Patel on 23,January,2025
 * Mobile App Developer,
 * Gujarat, INDIA.
 */

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setUpRecycler()
    }

    /**
     * An explanatory function of all the features of the simplified-recycler library and how to use it
     **/
    private fun setUpRecycler() {
        val listOfStrings = mutableListOf(Test("Test A"), Test("Test B"), Test("Test C"))

        viewBinding.rv.adapter = smartRecyclerAdapter(
            ItemRecyclerviewBinding::inflate,
            listOfStrings,
            this,
        ) { item, position ->

            itemBinding.tv.text = item.text

            itemBinding.ivDelete.setOnClickListener {
                clearItem(layoutPosition) // if you want to delete the current item, just call this method and pass
                // the current position/layoutPosition
            }

            itemBinding.ivEdit.setOnClickListener {
                item.text = "Pinankh"
                updateItem(layoutPosition, itemBinding.llForeground) // call this method to update the current item
            }

            itemBinding.iv.setOnClickListener {
                addItem(layoutPosition + 1, Test("Pinankh added ")) // method responsible for adding an item at specified position
            }

            // to enable swipe on the current item
            enableSwipe(itemBinding.llForeground, onSwiped = {
                Toast.makeText(this@MainActivity, "Swiped", Toast.LENGTH_SHORT).show()
            }, onSwipeReset = {
                Toast.makeText(this@MainActivity, "Swiped reset", Toast.LENGTH_SHORT).show()
            }) // for enabling swipe to action on recycler item

        }
    }

}

data class Test(
    var text: String
)