package com.erguncoban.cryptoexchangeapp.data.datasource

import com.erguncoban.cryptoexchangeapp.data.entity.TradeHistory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class TransactionDataSource @Inject constructor(private val auth: FirebaseAuth,
                                                private val firestore: FirebaseFirestore) {

    fun getTransactions() : Flow<List<TradeHistory>> = callbackFlow{
        val userId = auth.currentUser?.uid
        if (userId == null){
            close(Exception("User not logged in"))
            return@callbackFlow
        }

        val tradesRef = firestore.collection("users").document(userId).collection("trades")

        val subscription = tradesRef
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener{ snapshot, error ->

                if (error != null){
                    close(error)
                    return@addSnapshotListener
                }

                val items = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(TradeHistory::class.java)
                } ?: emptyList()

                trySend(items)
        }
        awaitClose { subscription.remove() }

    }

}