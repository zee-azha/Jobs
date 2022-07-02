package com.example.recuirtmentapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recuirtmentapp.model.CV

import com.example.recuirtmentapp.util.NODE_Apply
import com.google.firebase.database.*

class ApplicationViewModel: ViewModel() {



    private val dbApplication = FirebaseDatabase.getInstance().getReference(NODE_Apply)


    private val _result = MutableLiveData<Exception?>()


    private val _application = MutableLiveData<CV>()
    val job: LiveData<CV> get() = _application



    private val childEventListener = object: ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val application = snapshot.getValue(CV::class.java)
                application?.applicationid = snapshot.key
            _application.value = application!!
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            TODO("Not yet implemented")
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            TODO("Not yet implemented")
        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            TODO("Not yet implemented")
        }

        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }

    }

    fun getRealtimeUpdate(jobId: String){
        dbApplication.orderByChild("jobId").equalTo(jobId).addChildEventListener(childEventListener)
    }


    override fun onCleared() {
        super.onCleared()
        dbApplication.removeEventListener(childEventListener)
    }



}
