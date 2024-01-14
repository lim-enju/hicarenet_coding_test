package net.hicare.hicaretest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import net.hicare.hicaretest.R
import net.hicare.hicaretest.adapters.FacilityAdapter
import net.hicare.hicaretest.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SecondViewModel by viewModels()

    private lateinit var facilityListAdapter: FacilityAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initObserver()
    }

    private fun initView(){
        with(binding){
            vm = viewModel
            lifecycleOwner = this@SecondFragment

            //Facility List setting
            listFacility.layoutManager = LinearLayoutManager(requireContext())
            facilityListAdapter = FacilityAdapter { clickedFacility ->
                viewModel.setSelectedFacility(clickedFacility)
            }
            listFacility.adapter = facilityListAdapter

            btnSync.setOnClickListener {
                viewModel.saveFacilityToDatabase()
                Toast.makeText(requireContext(), R.string.msg_save_facility_success, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initObserver(){
       viewLifecycleOwner.lifecycleScope.launch {
           viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
               launch {
                   viewModel.searchedFacilityList.collectLatest { facilityList ->
                       //검색 결과를 adapter에 전달
                       facilityListAdapter.facilityList = facilityList
                       facilityListAdapter.notifyDataSetChanged()

                       //검색 내용이 변경되었으므로 선택한 시설 제거
                       viewModel.setSelectedFacility(null)
                   }
               }

               launch {
                   viewModel.selectedFacility
                       .collectLatest { selectedFacility ->
                           //클릭한 시설 명 표시
                           val text = selectedFacility?.facilityName ?: getString(R.string.default_selected_facility)
                           binding.textSelectedFacility.text = text
                   }
               }

               launch {
                   viewModel.toastMsg.collectLatest { msg ->
                       Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                   }
               }

               launch {
                   viewModel.savedFacility.collectLatest { list ->
                       binding.txtSearchedFacilityName.text = list.map { it.facilityName }.toString()
                   }
               }
           }
       }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}