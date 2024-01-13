package net.hicare.hicaretest

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ejlim.data.model.response.Facility
import com.ejlim.data.repository.FacilityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SecondViewModel @Inject constructor(
    private var repository: FacilityRepository
): ViewModel() {

    private val _toastMsg = MutableSharedFlow<String>()
    val toastMsg = _toastMsg.asSharedFlow()

    val queryString: MutableStateFlow<String> = MutableStateFlow("시설")

    val searchedFacilityList = queryString
        .debounce(300)
        .flatMapLatest { query ->
            flow {
                //빈 문자를 검색한 경우
                if(query.isBlank()) {
                    emit(listOf())
                    return@flow
                }

                val result = repository.searchFacility(query)
                emit(result?.data?: listOf())

                //TODO:: error handling 필요
                if(result == null){
                    _toastMsg.emit("에러났어요")
                }
            }
        }

    private var _selectedFacility = MutableStateFlow<Facility?>(null)
    var selectedFacility = _selectedFacility.asStateFlow()

    fun setSelectedFacility(facility: Facility?){
        viewModelScope.launch (Dispatchers.IO){
            _selectedFacility.emit(facility)
        }
    }

    fun saveFacilityToDatabase(){
        //선택된 facility가 없는 경우
        val facility = selectedFacility.value ?: return

        //TODO:: DB에 저장
    }
}