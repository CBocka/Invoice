package com.hamilton.featureinvoice.usecase

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hamilton.entity.featureinvoice.FeatureInvoice
import com.hamilton.repository.InvoiceRepository
import java.text.SimpleDateFormat

class FeatureInvoiceViewModel : ViewModel() {
    val TAG = "ViewModel"

    var billName = MutableLiveData<String>()
    var username = MutableLiveData<String>()
    var ObjectTV = MutableLiveData<String>()
    var creationDate = MutableLiveData<String>()
    var EndDate = MutableLiveData<String>()

    private var state = MutableLiveData<FeatureInvoiceCreationState>()
    fun validateCredentials() {
        when {
            TextUtils.isEmpty(billName.value) -> state.value =
                FeatureInvoiceCreationState.BillNameIsMandatory

            TextUtils.isEmpty(creationDate.value) -> state.value =
                FeatureInvoiceCreationState.CreationDateIsMandatory

            TextUtils.isEmpty(EndDate.value) -> state.value =
                FeatureInvoiceCreationState.EndDateIsMandatory

            !isDateFormatValid(creationDate.value ?: "") -> state.value =
                FeatureInvoiceCreationState.CreationDateInvalidDateFormat

            !isDateFormatValid(EndDate.value ?: "") -> state.value =
                FeatureInvoiceCreationState.EndDateInvalidDateFormat

            !datesAreLogical(creationDate.value ?: "", EndDate.value ?: "") -> state.value =
                FeatureInvoiceCreationState.EndDateMustBeMoreRecentThanCreationDate

            TextUtils.equals(ObjectTV.toString(), "") -> state.value =
                FeatureInvoiceCreationState.ObjectIsMandatory

            else -> {
                state.value = FeatureInvoiceCreationState.OnSuccess
            }
        }
    }

    fun datesAreLogical(creation: String, end: String): Boolean {
        val parts1 = creation.split("/")
        val parts2 = end.split("/")

        val day1 = parts1[0].toInt()
        val month1 = parts1[1].toInt()
        val year1 = parts1[2].toInt()

        val day2 = parts2[0].toInt()
        val month2 = parts2[1].toInt()
        val year2 = parts2[2].toInt()

        if (year1 < year2) {
            return true
        } else if (year1 > year2) {
            return false
        } else {
            if (month1 < month2) {
                return true
            } else if (month1 > month2) {
                return false
            } else {
                return day1 < day2
            }
        }
    }

    //Funcion que comprueba el formato de la fecha
    fun isDateFormatValid(date: String): Boolean {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        dateFormat.isLenient = false
        try {
            dateFormat.parse(date)
            return true
        } catch (e: Exception) {
            return false
        }
    }

    fun addInvoice(featureInvoice: FeatureInvoice) {
        InvoiceRepository.addFeatureInvoice(featureInvoice)
    }

    fun updateInvoice(featureInvoice: FeatureInvoice){
        InvoiceRepository.updateFeatureInvoice(featureInvoice)
    }
    fun getInvoiceFromID(id: String): FeatureInvoice {
        return InvoiceRepository.getInvoiceByID(id)
    }

    fun getState(): LiveData<FeatureInvoiceCreationState> {
        return state
    }
}