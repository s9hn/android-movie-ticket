package woowacourse.movie.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.domain.MovieInfo
import woowacourse.movie.ui.home.adapter.HomeRecyclerViewType.AD
import woowacourse.movie.ui.home.adapter.HomeRecyclerViewType.MOVIE

class HomeAdapter(
    private val onItemClick: ItemClickListener,
    private val movieInfo: List<MovieInfo>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (HomeRecyclerViewType.from(viewType)) {
            AD -> AdViewHolder(createView(parent, R.layout.ad_list_item)) {
                onItemClick.onAdItemClick(movieInfo[it] as MovieInfo.Advertisement)
            }
            MOVIE -> MovieViewHolder(createView(parent, R.layout.movie_list_item)) {
                onItemClick.onMovieItemClick(movieInfo[it] as MovieInfo.MovieUnit)
            }
        }

    private fun createView(parent: ViewGroup, resId: Int): View =
        LayoutInflater.from(parent.context).inflate(resId, parent, false)

    override fun getItemViewType(position: Int): Int {
        return when (movieInfo[position]) {
            is MovieInfo.MovieUnit -> MOVIE.value
            is MovieInfo.Advertisement -> AD.value
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieViewHolder -> holder.onBind(movieInfo[position] as MovieInfo.MovieUnit)
            is AdViewHolder -> holder.onBind(movieInfo[position] as MovieInfo.Advertisement)
        }
    }

    override fun getItemCount(): Int = movieInfo.size
}