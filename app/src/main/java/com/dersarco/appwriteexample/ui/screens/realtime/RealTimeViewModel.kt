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

    @Suppress("UNUSED_EXPRESSION")
    fun subscribe() {
        _subscription =
            appWriteRealtime.realtimeSubscription(DataEntity::class.java) { realtimeResponse ->
                val newList = _documentsStream.value?.map {
                    it
                }?.toMutableList()
                with(realtimeResponse.events.first()) {
                    when {
                        contains("create") -> {
                            //TODO: Still adding duplicated, need to find a way to fix this.
                            if (validate(realtimeResponse.payload)) {
                                newList?.add(realtimeResponse.payload)
                                _documentsStream.value = newList
                            }
                        }
                        contains("delete") -> {
                            newList?.remove(realtimeResponse.payload)
                            _documentsStream.value = newList
                        }
                        else -> false
                    }
                }
            }
    }

    //TODO: Remove this when a solution is found.
    private fun validate(payload: DataEntity): Boolean {
        val exist = _documentsStream.value?.find { it == payload }
        if (exist != null) {
            return false
        }
        return true
    }

    private fun originalData() {
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
