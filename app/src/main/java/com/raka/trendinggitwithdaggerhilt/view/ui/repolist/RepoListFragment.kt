package com.raka.trendinggitwithdaggerhilt.view.ui.repolist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.raka.myapplication.data.model.State.*
import com.raka.trendinggitwithdaggerhilt.data.model.compact.ItemsCompact
import com.raka.trendinggitwithdaggerhilt.view.adapter.RepoListAdapter
import com.raka.trendinggitwithdaggerhilt.databinding.FragmentRepoListBinding
import com.raka.trendinggitwithdaggerhilt.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_repo_list.*

/**
 * Link to kotlin flow tutorial
 * https://proandroiddev.com/kotlin-flow-on-android-quick-guide-76667e872166
 */
@AndroidEntryPoint
class RepoListFragment : Fragment() {
    private lateinit var viewDataBinding: FragmentRepoListBinding
    private lateinit var adapter: RepoListAdapter
    private val repoListViewModel:RepoListViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentRepoListBinding.inflate(inflater, container, false)
        return viewDataBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupObservers()
    }
    private fun setupObservers() {
        repoListViewModel.stateProcess.observe(viewLifecycleOwner, Observer {
            if (it != null){
                when(it.status){
                    LOADING -> showLoadingBar()
                    FAIL -> showNoData(it.message!!)
                    else -> hideLoadingBar()
                }
            }
        })
        repoListViewModel.pagedListRepo.observe(viewLifecycleOwner, Observer {
            submitData(it)
        })
    }
    private fun submitData(data:PagedList<ItemsCompact>){
        adapter.submitList(data)
        repo_list_rv.visibility = View.VISIBLE
        tv_no_data.visibility = View.GONE
    }
    private fun loadResponse(result:Resource<List<ItemsCompact>>){
        when(result.status){
            SUCCESS ->updateAdapter(result.data!!)
            LOADING -> showLoadingBar()
            else -> showNoData(result.message!!)
        }
    }

    private fun showNoData(message:String){
        repo_list_rv.visibility = View.GONE
        Toast.makeText(context,message,Toast.LENGTH_LONG).show()
        hideLoadingBar()
    }

    private fun updateAdapter(data:List<ItemsCompact>){
        adapter.updateRepoList(data.toMutableList())
        repo_list_rv.visibility = View.VISIBLE
        tv_no_data.visibility = View.GONE
        hideLoadingBar()
    }

    private fun showLoadingBar(){
        pb_repolist.visibility = View.VISIBLE
        repo_list_rv.visibility = View.INVISIBLE
    }
    private fun hideLoadingBar(){
        pb_repolist.visibility = View.GONE
        repo_list_rv.visibility = View.VISIBLE
    }

    private fun setupAdapter() {
            adapter = RepoListAdapter()
            val layoutManager = LinearLayoutManager(activity)
            repo_list_rv.layoutManager = layoutManager
            repo_list_rv.addItemDecoration(DividerItemDecoration(activity,layoutManager.orientation))
            repo_list_rv.adapter = adapter
    }
}
