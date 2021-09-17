package com.example.firebasesampleapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebasesampleapp.adapter.PhraseAdapter
import com.example.firebasesampleapp.databinding.FragmentListBinding

class ListFragment : Fragment(), LoadingListener {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var phraseAdapter: PhraseAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val firestoreListener = activity as FirestoreListener
        showProgressBar()

        phraseAdapter = PhraseAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = phraseAdapter
        }

        firestoreListener.getFromFirestore(this)
    }

    override fun updateAdaptersList(list: List<String>) {
        phraseAdapter.submitList(list)
    }

    override fun stopLoading() {
        hideProgressBar()
        showRecyclerView()
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }
    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }
    private fun showRecyclerView() {
        binding.recyclerView.visibility = View.VISIBLE
    }


}


interface LoadingListener {
    fun updateAdaptersList(list: List<String>)
    fun stopLoading()
}