package eg.esperantgada.internship.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import eg.esperantgada.internship.adapter.FlickrImageAdapter
import eg.esperantgada.internship.adapter.FlickrImageLoadStateAdapter
import eg.esperantgada.internship.databinding.FragmentHomeBinding
import eg.esperantgada.internship.viewmodel.FlickrImageViewModel

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get()  = _binding!!

    private val viewModel : FlickrImageViewModel by viewModels()

    private lateinit var flickrImageAdapter: FlickrImageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        flickrImageAdapter = FlickrImageAdapter()

        binding.recyclerView.apply {
            adapter = flickrImageAdapter.withLoadStateHeaderAndFooter(
                header = FlickrImageLoadStateAdapter{flickrImageAdapter.retry()},
                footer = FlickrImageLoadStateAdapter{flickrImageAdapter.retry()}
            )
            setHasFixedSize(true)
            layoutManager = StaggeredGridLayoutManager(
                if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 2
            else 3, StaggeredGridLayoutManager.VERTICAL
            )
        }

        viewModel.retrievedPhotos.observe(viewLifecycleOwner){ photo ->
            flickrImageAdapter.submitData(viewLifecycleOwner.lifecycle, photo)
        }

        flickrImageAdapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
            }
        }


        flickrImageAdapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                retryButton.isVisible = loadState.source.refresh is LoadState.Error
                resultStatusTextView.isVisible = loadState.source.refresh is LoadState.Error

                //If the recyclerView is empty, sets its visibility to false
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    flickrImageAdapter.itemCount < 1){

                    recyclerView.isVisible = false
                    emptyTextStatus.isVisible = true

                }else{
                    emptyTextStatus.isVisible = false
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null

    }

}