package com.erguncoban.cryptoexchangeapp.data.datasource

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(private val firestore: FirebaseFirestore) {

    suspend fun createUserProfile(uid: String, email: String) : Boolean{
        return try {
            val userMap = hashMapOf(
                "email" to email,
                "balance" to 10000.0
            )
            firestore.collection("users").document(uid).set(userMap).await()
            true
        }catch (e: Exception){
            false
        }
    }

    fun getUserBalance(uid: String) : Flow<Double> = callbackFlow {

        val documentRef = firestore.collection("users").document(uid)

        val listener = documentRef.addSnapshotListener { snapshot, error ->
            if (error != null){
                close(error)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()){
                val balance = snapshot.getDouble("balance") ?: 0.0
                trySend(balance)
            }

        }

        awaitClose {
            listener.remove()
        }

    }

    suspend fun updateBalance(uid: String, amount: Double) : Boolean{
        return try{
            firestore.collection("users").document(uid)
                .update("balance", FieldValue.increment(amount))
                .await()
            true
        }catch (e: Exception){
            false
        }
    }

    suspend fun toggleFavorite(uid: String, coinId: String, isAdd: Boolean) : Boolean {
        return try {
            val userRef = firestore.collection("users").document(uid)

            if (isAdd){
                userRef.update("favoriteCoins", FieldValue.arrayUnion(coinId)).await()
            }else{
                userRef.update("favoriteCoins", FieldValue.arrayRemove(coinId)).await()
            }
            true
        }catch (e: Exception){
            false
        }
    }

    fun getFavoriteCoins(uid: String) : Flow<List<String>> = callbackFlow {
        val documentRef = firestore.collection("users").document(uid)

        val listener = documentRef.addSnapshotListener { snapshot, error ->
            if (error != null){
                close(error)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()){
                val favorites = snapshot.get("favoriteCoins") as? List<String> ?: emptyList()
                trySend(favorites)
            }
        }
        awaitClose { listener.remove() }
    }

}