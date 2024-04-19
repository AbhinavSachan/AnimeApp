package com.abhinavdev.animeapp.ui.more

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abhinavdev.animeapp.core.BaseFragment
import com.abhinavdev.animeapp.databinding.FragmentMoreBinding
import com.abhinavdev.animeapp.ui.main.MainActivity

class MoreFragment : BaseFragment(), View.OnClickListener {
    private var _binding: FragmentMoreBinding? = null
    private val binding get() = _binding!!
    private var parentActivity: MainActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentActivity = context as MainActivity
    }

    override fun onDetach() {
        super.onDetach()
        parentActivity = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoreBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initComponents()
        setAdapters()
        setListeners()
        setObservers()
    }

    private fun initComponents() {

    }

    private fun setAdapters() {

    }

    private fun setListeners() {

    }

    override fun onClick(v: View?) {
        when (v) {

        }
    }

    private fun setObservers() {

    }

    companion object {
        @JvmStatic
        fun newInstance() = MoreFragment()
    }
}