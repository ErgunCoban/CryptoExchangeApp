package com.erguncoban.cryptoexchangeapp.data.datasource

import android.util.Log
import com.erguncoban.cryptoexchangeapp.data.entity.TradeHistory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(private val auth: FirebaseAuth,
                                               private val firestore: FirebaseFirestore) {

    suspend fun createUserProfile(uid: String, email: String) : Boolean{
        return try {
            val userMap = hashMapOf(
                "email" to email,
                "balance" to 0.0
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

    suspend fun updateBalance(amount: Double) : Boolean{
        return try {
            val userId = auth.currentUser?.uid ?: throw Exception("User session not found")

            val userRef = firestore.collection("users").document(userId)
            val portfolioRef = userRef.collection("portfolio").document("tether")
            val tradeRef = userRef.collection("trades").document()

            firestore.runTransaction { transaction ->

                val portfolioSnapshot = transaction.get(portfolioRef)

                val currentCoinAmount = portfolioSnapshot.getDouble("amount") ?: 0.0
                val currentTotalInvested = portfolioSnapshot.getDouble("totalInvested") ?: 0.0

                val newCoinAmount = currentCoinAmount + amount
                val newTotalInvested = currentTotalInvested + amount

                val portfolioUpdates = mapOf(
                    "amount" to newCoinAmount,
                    "totalInvested" to newTotalInvested,
                    "lastUpdated" to System.currentTimeMillis()
                )

                val tradeData = TradeHistory(
                    coinId = "tether",
                    type = "DEPOSIT",
                    amount = amount,
                    price = 1.0,
                    totalVolume = amount
                )

                transaction.set(portfolioRef, portfolioUpdates, SetOptions.merge())
                transaction.set(tradeRef, tradeData)

                null
            }.await()
            true
        }catch (e: Exception){
            Log.e("REPO_ERROR", "DEPOSIT PATLADI: ${e.message}", e)
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