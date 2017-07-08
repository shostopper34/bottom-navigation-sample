package com.ss.bottomnavigation

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup

import com.ss.bottomnavigation.databinding.FragmentSampleBinding


class SampleFragment : Fragment() {

    private var binding: FragmentSampleBinding? = null
    private var name: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        name = arguments.getString("tag")

        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentSampleBinding.inflate(inflater, container, false)
        binding!!.text.text = name
        return binding!!.root
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

    }

    companion object {

        val TAG_1 = SampleFragment::class.java.simpleName + "1"
        val TAG_2 = SampleFragment::class.java.simpleName + "2"
        val TAG_3 = SampleFragment::class.java.simpleName + "3"

        fun newInstance(tag: String): SampleFragment {
            val args = Bundle()
            args.putString("tag", tag)
            val fragment = SampleFragment()
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
