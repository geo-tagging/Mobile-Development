package com.dicoding.geotaggingjbg.ui.detail

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.geotaggingjbg.data.Repository
import java.lang.reflect.InvocationTargetException

class DetailViewModelFactory(private val repository: Repository?, private val id: Int): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        try {
            return modelClass.getConstructor(Repository::class.java, Int::class.java).newInstance(repository, id)
        } catch (e: InstantiationException) {
            throw RuntimeException("Cannot create an instance of $modelClass", e)
        } catch (e: IllegalAccessException) {
            throw RuntimeException("Cannot create an instance of $modelClass", e)
        } catch (e: NoSuchMethodException) {
            throw RuntimeException("Cannot create an instance of $modelClass", e)
        } catch (e: InvocationTargetException) {
            throw RuntimeException("Cannot create an instance of $modelClass", e)
        }
    }

    companion object {
        fun createFactory(activity: Activity, id: Int): DetailViewModelFactory {
            val context = activity.applicationContext
                ?: throw IllegalStateException("Not yet attached to Application")

            return DetailViewModelFactory(Repository.getInstance(context), id)
        }
    }
}