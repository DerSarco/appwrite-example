package com.dersarco.appwriteexample.ui.screens.realtime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dersarco.appwriteexample.appwrite.AppWriteDatabase
import com.dersarco.appwriteexample.appwrite.AppWriteRealtime
import com.dersarco.appwriteexample.appwrite.AppWriteResponse
import com.dersarco.appwriteexample.data.entities.DataEntity
import com.dersarco.appwriteexample.data.entities.Person
import io.appwrite.models.RealtimeSubscription
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class RealTimeViewModel(
    private val appWriteRealtime: AppWriteRealtime,
    private val appWriteDatabase: AppWriteDatabase
) : ViewModel() {

    private var _subscription: RealtimeSubscription? = null

    private val _documentsStream = MutableStateFlow<List<DataEntity>?>(null)
    val documentsStream: StateFlow<List<DataEntity>?>
        get() = _documentsStream

    init {
        originalData()
    }

    fun subscribe() {
        _subscription = appWriteRealtime.realtime.subscribe(
            "databases.63fba37bbf99b2eae6fb.collections.63fba37f873c649fbdf8.documents",
            payloadType = DataEntity::class.java,
        ) { realtimeResponse ->
            val newList = _documentsStream.value!!.map {
                it
            }.toMutableList()
            if (_documentsStream.value.isNullOrEmpty()) {
                _documentsStream.value = newList
            } else {
                newList.add(realtimeResponse.payload)
                _documentsStream.value = newList
            }

        }
    }


    private fun originalData() {
        if (_documentsStream.value != null) {
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = appWriteDatabase.listDocuments()) {
                is AppWriteResponse.Success -> {
                    val mapped = response.data.documents.map {
                        it.data
                    }
                    _documentsStream.value = mapped
                }
                is AppWriteResponse.Error -> Unit
            }
        }
    }

    fun createNewEntry() {
        viewModelScope.launch(Dispatchers.IO) {
            val person = Person(
                name = "String${Random.nextInt(0, 1000)}",
                age = Random.nextInt(0, 100)
            )
            appWriteDatabase.createDocument(person)
        }
    }

    override fun onCleared() {
        _subscription!!.close()
        super.onCleared()
    }
}
