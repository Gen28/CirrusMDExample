package com.gen28.cirrusmdexample.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gen28.cirrusmdexample.R
import com.gen28.cirrusmdexample.entity.Channel

/**
 * A fragment representing a list of Channels.
 */
class ChannelFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val view = inflater.inflate(R.layout.fragment_channel_list, container, false)
        viewModel.channels.observe(viewLifecycleOwner, { list: List<Channel> ->
            // Set the adapter
            if (view is RecyclerView) {
                with(view) {
                    layoutManager = LinearLayoutManager(context)
                    adapter = ChannelRecyclerViewAdapter(list)
                }
            }
        })

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ChannelFragment()
    }
}