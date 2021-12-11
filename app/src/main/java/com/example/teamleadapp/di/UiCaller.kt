package com.example.teamleadapp.di

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.teamleadapp.data.remote.models.Status
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface UiProvider {
    val statusLiveData: LiveData<Status>
    val errorLiveData: LiveData<EventWrapper<String>>
}

interface UiCaller : UiProvider {
    override val statusLiveData: MutableLiveData<Status>

    fun <T> makeRequest(
        call: suspend CoroutineScope.() -> T,
        statusLD: MutableLiveData<Status>? = statusLiveData,
        resultBlock: (suspend (T) -> Unit)? = null
    )

    fun <T> unwrap(
        result: RequestResult<T>,
        errorBlock: ((String) -> Unit)? = { setError(it) },
        successBlock: (T) -> Unit
    ): Unit?

    fun set(status: Status, statusLD: MutableLiveData<Status>? = statusLiveData)

    fun setError(error: String)
    fun <T> makeRequestFor(
        liveData: MutableLiveData<T>,
        statusLD: MutableLiveData<Status>? = statusLiveData,
        call: suspend CoroutineScope.() -> RequestResult<T>
    )
}

class UiCallerImpl(
    private val scope: CoroutineScope,
    private val scopeProvider: CoroutineProvider,
    _statusLiveData: MutableLiveData<Status> = MutableLiveData(),
    _errorLiveData: MutableLiveData<EventWrapper<String>> = MutableLiveData()
) : UiCaller {
    override val statusLiveData: MutableLiveData<Status> = _statusLiveData
    override val errorLiveData: MutableLiveData<EventWrapper<String>> = _errorLiveData

    override fun <T> makeRequest(
        call: suspend CoroutineScope.() -> T,
        statusLD: MutableLiveData<Status>?,
        resultBlock: (suspend (T) -> Unit)?
    ) {
        scope.launch(scopeProvider.Main) {
            set(Status.SHOW_LOADING, statusLD)
            try {
                val result = withContext(scopeProvider.IO, call)
                resultBlock?.invoke(result)
            } catch (e: Exception) {
                if (e !is CancellationException) {
                    setError(e.message.orEmpty())
                }
            }
            set(Status.HIDE_LOADING, statusLD)
        }
    }

    private var requestCounter = 0

    override fun set(status: Status, statusLD: MutableLiveData<Status>?) {
        statusLD ?: return
        if (statusLD === statusLiveData) {
            when (status) {
                Status.SHOW_LOADING -> {
                    requestCounter++
                }
                Status.HIDE_LOADING -> {
                    requestCounter--
                    if (requestCounter > 0) return
                    requestCounter = 0
                }
            }
        }
        scope.launch(scopeProvider.Main) {
            statusLD.value = status
        }
    }


    override fun setError(error: String) {
        scope.launch(scopeProvider.Main) {
            errorLiveData.value = EventWrapper(error)
        }
    }

    override fun <T> unwrap(
        result: RequestResult<T>,
        errorBlock: ((String) -> Unit)?,
        successBlock: (T) -> Unit
    ) = when (result) {
        is RequestResult.Success -> result.result?.let { successBlock(it) }
        is RequestResult.Error -> errorBlock?.invoke(result.error)
    }

    override fun <T> makeRequestFor(
        liveData: MutableLiveData<T>,
        statusLD: MutableLiveData<Status>?,
        call: suspend CoroutineScope.() -> RequestResult<T>
    ) = makeRequest(call, statusLD) {
        when (it) {
            is RequestResult.Success<T> -> liveData.value = it.result
            is RequestResult.Error -> setError(it.error)
        }
    }
}