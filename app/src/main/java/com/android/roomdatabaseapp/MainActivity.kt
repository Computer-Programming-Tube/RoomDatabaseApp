package com.android.roomdatabaseapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.roomdatabaseapp.data.Word
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row_item.view.*

class MainActivity : AppCompatActivity() {

    val REQUEST_CODE: Int = 1
    lateinit var viewModel: WordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this)
                .get(WordViewModel::class.java)

        val adapter = WordAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        viewModel.allWords.observe(this, Observer {
            it.let {
                adapter.submitList(it)
            }
        })


        floatingActionButton.setOnClickListener {
            startActivityForResult(Intent(this, AddWordActivity::class.java), REQUEST_CODE)

        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE){
            if (resultCode == Activity.RESULT_OK){
                val word = data!!.getStringExtra(AddWordActivity.EXTRA_REPLY)
                viewModel.insert(word = Word(word))
            }
        }
    }

}



class WordAdapter : ListAdapter<Word, WordAdapter.WordViewHolder>(diffUtil) {
    class WordViewHolder(view:View) : RecyclerView.ViewHolder(view) {

        fun bind(item:Word){
            itemView.name.text = item.word
        }
    }

    companion object {
        val diffUtil = object :DiffUtil.ItemCallback<Word>(){
            override fun areItemsTheSame(oldItem: Word, newItem: Word) = oldItem.word == newItem.word

            override fun areContentsTheSame(oldItem: Word, newItem: Word) = oldItem.word == newItem.word

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_item, parent, false)
        return WordViewHolder(view)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}