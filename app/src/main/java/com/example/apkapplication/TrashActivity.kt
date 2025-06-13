package com.example.apkapplication

import android.util.Log
import com.google.firebase.firestore.*

class TrashActivity {

    private lateinit var db: FirebaseFirestore
    private var registration: ListenerRegistration? = null

    fun startListeningForTrash() {
        db = FirebaseFirestore.getInstance()

        registration = db.collection("trash_entries")
            .orderBy("timestamp")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.e("Firestore", "Listen failed", e)
                    return@addSnapshotListener
                }

                val trashList = mutableListOf<Pair<String, String>>()
                snapshot?.documents?.forEach { doc ->
                    val type = doc.getString("type")
                    val timestamp = doc.getTimestamp("timestamp")
                    if (type != null && timestamp != null) {
                        trashList.add(Pair(type, timestamp.toDate().toString()))
                    }
                }

                Log.d("TrashActivity", "Current trash entries: $trashList")
                // You can update RecyclerView or UI here via callback
            }
    }

    fun stopListening() {
        registration?.remove()
    }
}
