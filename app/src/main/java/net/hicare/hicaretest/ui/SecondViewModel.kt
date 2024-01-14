package net.hicare.hicaretest.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ejlim.data.database.entity.Facility
import com.ejlim.data.model.onFailure
import com.ejlim.data.model.onSuccess
import com.ejlim.data.repository.FacilityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SecondViewModel @Inject constructor(
    private var repository: FacilityRepository
) : ViewModel() {

    private val _toastMsg = MutableSharedFlow<String>()
    val toastMsg = _toastMsg.asSharedFlow()

    val queryString: MutableStateFlow<String> = MutableStateFlow("시설")

    private var _selectedFacility = MutableStateFlow<Facility?>(null)
    var selectedFacility = _selectedFacility.asStateFlow()

    val savedFacility =
        repository
            .getAllFacility()
            .flowOn(Dispatchers.IO)

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val searchedFacilityList = queryString
        .debounce(300)
        .flatMapLatest { query ->
            flow <List<Facility>>{
                //빈 문자를 검색한 경우
                if (query.isBlank()) {
                    emit(listOf())
                    return@flow
                }
                repository.searchFacility(query)
                    .onSuccess { facilityList ->
                        //검색 성공 시 list update
                        emit(facilityList?: listOf())
                    }
                    .onFailure { _, msg ->
                        //검색 실패 시 메시지 출력
                        emit(listOf())
                        _toastMsg.emit(msg)
                    }
            }
        }


    //Facility 리스트 중 한개를 선택한 경우 setting
    fun setSelectedFacility(facility: Facility?) {
        viewModelScope.launch(Dispatchers.IO) {
            _selectedFacility.emit(facility)
        }
    }

    fun saveFacilityToDatabase() {
        //선택된 facility가 없는 경우
        val facility = selectedFacility.value ?: return

        viewModelScope.launch (Dispatchers.IO){
            repository.insertFacility(facility)
        }
    }
}