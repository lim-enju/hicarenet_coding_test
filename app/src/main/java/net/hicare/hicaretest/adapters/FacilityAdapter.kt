package net.hicare.hicaretest.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ejlim.data.model.response.Facility
import net.hicare.hicaretest.databinding.ListitemFacilityBinding

class FacilityAdapter(
    var facilityList: List<Facility> = listOf(),
    var onClickFacility: (facility: Facility) -> Unit
): RecyclerView.Adapter<FacilityAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ListitemFacilityBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(facility: Facility){
            with(binding){
                txtId.text = facility.facilityId
                txtName.text = facility.facilityName

                root.setOnClickListener {
                    onClickFacility(facility)
                }
                //시설 이미지 표시
//                Glide.with(root.context)
//                    .load(facility.logoUrl)
//                    .into(imgFacility)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListitemFacilityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = facilityList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("EJLIM", "onBindViewHolder: $position")

        val facility = facilityList[position]
        holder.bind(facility)
    }
}