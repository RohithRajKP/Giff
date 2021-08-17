package com.freshworks.giff.ui.fragment_trending

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.freshworks.giff.R
import com.freshworks.giff.adapters.TrendingAdapter
import com.freshworks.giff.clicklisteners.RecyclerViewClickListener
import com.freshworks.giff.db.entities.Giff
import com.freshworks.giff.model.Data
import com.freshworks.giff.ui.viewmodel.SharedViewModel
import com.freshworks.giff.ui.viewmodel.TrendingViewModel
import com.freshworks.giff.util.Constants.Companion.DEFAULT_LIMIT
import com.freshworks.giff.util.Constants.Companion.MAX_PAGE
import com.freshworks.giff.util.Resource
import kotlinx.android.synthetic.main.fragment_trending.*
import kotlinx.android.synthetic.main.item_error_message.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class TrendingFragment : Fragment(), RecyclerViewClickListener, KodeinAware {
    override val kodein by kodein()
    private val factory: TrendingViewModelFactory by instance()

    /*
         private lateinit var factory: TrendingViewModelFactory
         facory is replaced with dependecncy injection kodein
         we can access the viewmodel factory by calling instance().

    */
    private lateinit var viewModel: TrendingViewModel
    lateinit var trendingAdapter: TrendingAdapter
    var currentpage = 1 //this is for page validation in api, by default it is 1
    var q = ""// for search api, q is holding search text


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_trending, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupRecyclerView()
/*
         val api = RetrofitInstance.api
         val db = AppDatabase.invoke(requireContext())
         val repository = TrendingRepository(api, db)
         factory = TrendingViewModelFactory((requireActivity().application as App)!!, repository)
         
     note: with out Kodein we require these object for accessing viewmodel , but kodein simplifies the 
         dependency.
*/
        viewModel = ViewModelProvider(this, factory).get(TrendingViewModel::class.java)
        viewModel.loadGiffs(currentpage, MAX_PAGE, "")
        viewModel.__gif.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success<*> -> {
                    hideProgressBar()
                    hideErrorMessage()
                    response.data?.let { GiffResponse ->
                        trendingAdapter.differ.submitList(GiffResponse.data.toList())
                        val totalPages = MAX_PAGE //for pagination calculation
                        isLastPage = viewModel.GiffPage == totalPages
                    }
                }

                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(activity, "An error occured: $message", Toast.LENGTH_LONG)
                            .show()
                        showErrorMessage(message)
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
        btnRetry.setOnClickListener {
            if (currentpage >= 1) {
                currentpage = currentpage - 1
            }
            viewModel.loadGiffs(currentpage, MAX_PAGE, q)
        }


        edt_search.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            val Search = edt_search.text.toString().trim()
            if (keyCode == 66) {
                currentpage = 1
                viewModel.loadGiffs(currentpage, 20, Search)
            }
            false
        })

        var job: Job? = null
/*
note : addTextChangedListener is not using now ,user manually search giffs.
 
        edt_search.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_NEWS_TIME_DELAY)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        q = editable.toString()
                        currentpage = 1
                        viewModel.loadGiffs(currentpage, MAX_PAGE, q)
                    }
                }
            }
        }
        
*/
        val model = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        model.id.observe(viewLifecycleOwner, Observer {
            var kk = it
            val size = trendingAdapter.differ.currentList.size
            for (i in 0..size - 1) {
                println("id " + trendingAdapter.differ.currentList.get(i).id)
                val id = trendingAdapter.differ.currentList.get(i).id
                if (id == it) {
                    trendingAdapter.differ.currentList.get(i).isFavorite = false
                }
            }
            trendingAdapter.notifyDataSetChanged()
        })
    }

    override fun onRecyclerViewItemClick(view: View, GiffEntity: Data, position: Int) {
        when (view.id) {
            R.id.img_fav -> {
                GiffEntity.isFavorite = true
                val gGiff = Giff(
                    GiffEntity.id,
                    GiffEntity.title,
                    GiffEntity.images.original.url,
                    true
                )

                lifecycleScope.launch {
                    try {
                        viewModel.saveGiff(gGiff)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                trendingAdapter.notifyDataSetChanged()
                Toast.makeText(
                    requireContext(),
                    GiffEntity.title + " Added to Favourites",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
            R.id.img_unfav -> {
                lifecycleScope.launch {
                    try {
                        viewModel.deleteGiff(GiffEntity.id)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                GiffEntity.isFavorite = false
                trendingAdapter.notifyDataSetChanged()
                Toast.makeText(
                    requireContext(),
                    GiffEntity.title + " Removed from Favourites",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }


    private fun setupRecyclerView() {
        trendingAdapter = TrendingAdapter(this)
        recycler_view_movies.apply {
            adapter = trendingAdapter
            recycler_view_movies.setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(activity)// GridLayoutManager(activity, 2)// LinearLayoutManager(activity)
            addOnScrollListener(this@TrendingFragment.scrollListener)
        }
    }

    //////////////////pagianation///////////////////////
    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }

    private fun hideErrorMessage() {
        itemErrorMessage.visibility = View.INVISIBLE
        isError = false
    }

    private fun showErrorMessage(message: String) {
        itemErrorMessage.visibility = View.VISIBLE
        tvErrorMessage.text = message
        isError = true
    }

    var isError = false
    var isLoading = false
    var isLastPage = false
    var isScrolling = false


    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val visiblefirstItem = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItem = layoutManager.itemCount
            val noloading_nolast = !isLoading && !isLastPage
            val LastItem = visiblefirstItem + visibleItemCount >= totalItem
            val isNotAtBeginning = visiblefirstItem >= 0
            val isTotalMoreThanVisible = totalItem >= DEFAULT_LIMIT
            val shouldPaginate = noloading_nolast && LastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                showProgressBar()
                currentpage = currentpage + 1
                viewModel.loadGiffs(currentpage, MAX_PAGE, q)
                isScrolling = false
            } else {
                recycler_view_movies.setPadding(0, 0, 0, 0)
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }

}

