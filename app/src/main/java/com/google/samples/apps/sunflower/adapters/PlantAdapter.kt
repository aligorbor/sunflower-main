package com.google.samples.apps.sunflower.adapters

import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.accompanist.themeadapter.material.MdcTheme
import com.google.samples.apps.sunflower.HomeViewPagerFragmentDirections
import com.google.samples.apps.sunflower.PlantListFragment
import com.google.samples.apps.sunflower.compose.plantlist.PlantListItemView
import com.google.samples.apps.sunflower.data.Plant

/**
 * Adapter for the [RecyclerView] in [PlantListFragment].
 */
class PlantAdapter : ListAdapter<Plant, RecyclerView.ViewHolder>(PlantDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PlantViewHolder(ComposeView(parent.context))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val plant = getItem(position)
        (holder as PlantViewHolder).bind(plant)
    }

    class PlantViewHolder(
        composeView: ComposeView
    ) : RecyclerView.ViewHolder(composeView) {
        fun bind(plant: Plant) {
            (itemView as ComposeView).setContent {
                MdcTheme {
                    PlantListItemView(plant = plant) {
                        navigateToPlant(plant)
                    }
                }
            }
        }

        private fun navigateToPlant(plant: Plant) {
            val direction =
                HomeViewPagerFragmentDirections.actionViewPagerFragmentToPlantDetailFragment(
                    plant.plantId
                )
            itemView.findNavController().navigate(direction)
        }
    }
}

private class PlantDiffCallback : DiffUtil.ItemCallback<Plant>() {

    override fun areItemsTheSame(oldItem: Plant, newItem: Plant): Boolean {
        return oldItem.plantId == newItem.plantId
    }

    override fun areContentsTheSame(oldItem: Plant, newItem: Plant): Boolean {
        return oldItem == newItem
    }
}
