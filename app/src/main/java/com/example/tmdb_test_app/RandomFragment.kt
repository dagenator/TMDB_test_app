package com.example.tmdb_test_app

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.tmdb_test_app.data.models.Config
import com.example.tmdb_test_app.data.models.Genre
import com.example.tmdb_test_app.data.models.Movie
import com.example.tmdb_test_app.data.utils.Resource
import com.example.tmdb_test_app.data.utils.Status
import com.example.tmdb_test_app.databinding.RandomFragmentBinding
import com.example.tmdb_test_app.presenter.MainViewModel
import javax.inject.Inject

class RandomFragment @Inject constructor() : Fragment(R.layout.random_fragment) {
    @Inject
    lateinit var viewModel: MainViewModel

    @Inject
    lateinit var config: Config

    private var binding:RandomFragmentBinding? = null

    private val genreObserve = Observer<List<Genre>>{
        val genres = it.map { x -> x.name }
        setUi(genres)
    }

    private val randomMovieObserve = Observer<Resource<Movie>>{
        it?.let {
            when (it.status) {
                Status.LOADING -> {
                    setLoader(true)
                }
                Status.ERROR -> {
                    showError(it.message.toString())
                    setLoader(false)
                }
                Status.SUCCESS -> {
                    setLoader(false)
                    it.data?.let {
                        setRandomMovieUI(it)
                    }
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).fragmentComponent.inject(this)
        viewModel.genres.observe(this, genreObserve)
        viewModel.uploadGenres()
        viewModel.randomMovie.observe(this, randomMovieObserve)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RandomFragmentBinding.inflate(inflater, container, false)
        val view = binding?.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun setUi(genres: List<String>) {
        view?.let {view->

            binding?.let { binding ->
                context?.let {
                    val adapter: ArrayAdapter<String> = ArrayAdapter(it, R.layout.one_string_row_layout, genres)
                    binding.genreDropdown.adapter = adapter
                }
                val yearInput = view.findViewById<EditText>(R.id.year_input)
                binding.randomFindButton.setOnClickListener {
                    viewModel.getRandomMovie( genre = binding.genreDropdown.selectedItem.toString(), year = yearInput.text.toString().toInt())
                    setLoader(true)
                }
            }
        }
    }

    private fun setRandomMovieUI(movie: Movie){
        binding?.let {
            it.randomMovieContainer.visibility = View.VISIBLE
            it.randomMovieContainer.setOnRefreshListener {
                viewModel.getRandomMovie(
                    genre = it.genreDropdown.selectedItem.toString(),
                    year = it.yearInput.text.toString().toInt()
                )
                it.randomMovieContainer.isRefreshing = false
            }
            viewModel.loadImage(config.imageUrl + movie.posterPath, this, it.randomPoster, 400, 600)

            it.randomTitle.text = movie.title
            it.randomYear.text = movie.releaseDate
            it.randomDescription.text = movie.overview
            it.randomScore.text = movie.voteAverage.toString()
            it.randomGoToButton.setOnClickListener { viewModel.navigateToMovieById(movie.id, this) }
        }
    }

    private fun setLoader(isLoading: Boolean) {
        binding?.let {
            it.randomMovieLoader.visibility = if (isLoading) View.VISIBLE else View.GONE
            it.randomMovieContainer.visibility = if (isLoading) View.GONE else View.VISIBLE
        }
    }

    private fun showError(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }

}