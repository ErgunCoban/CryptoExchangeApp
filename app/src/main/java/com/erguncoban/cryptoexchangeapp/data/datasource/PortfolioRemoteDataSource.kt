package com.erguncoban.cryptoexchangeapp.data.datasource

import android.util.Log
import com.erguncoban.cryptoexchangeapp.data.entity.PortfolioItem
import com.erguncoban.cryptoexchangeapp.data.entity.TradeHistory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PortfolioRemoteDataSource @Inject constructor(private val firestore: FirebaseFirestore, private val auth: FirebaseAuth) {

    suspend fun buyCoin(coinId: String, amount: Double, currentPrice: Double) : Result<Unit>{
        return try {
            val userId = auth.currentUser?.uid ?: throw Exception("User session not found")

            val totalCost = amount * currentPrice

            val userRef = firestore.collection("users").document(userId)
            val tetherRef = userRef.collection("portfolio").document("tether")
            val coinRef = userRef.collection("portfolio").document(coinId)
            val tradeRef = userRef.collection("trades").document()

            firestore.runTransaction { transaction ->

                val tetherSnapshot = transaction.get(tetherRef)
                val coinSnapshot = transaction.get(coinRef)

                val currentTetherAmount = tetherSnapshot.getDouble("amount") ?: 0.0

                if (currentTetherAmount < totalCost) {
                    throw FirebaseFirestoreException(
                        "Insufficient USDT balance. Current: $currentTetherAmount, Requested: $totalCost",
                        FirebaseFirestoreException.Code.ABORTED
                    )
                }

                val newTetherAmount = currentTetherAmount - totalCost

                val currentCoinAmount = coinSnapshot.getDouble("amount") ?: 0.0
                val currentTotalInvested = coinSnapshot.getDouble("totalInvested") ?: 0.0

                val newCoinAmount = currentCoinAmount + amount
                val newTotalInvested = currentTotalInvested + totalCost

                val tetherUpdates = mapOf(
                    "amount" to newTetherAmount,
                    "lastUpdated" to System.currentTimeMillis()
                )
                transaction.set(tetherRef, tetherUpdates, SetOptions.merge())

                val portfolioUpdates = mapOf(
                    "amount" to newCoinAmount,
                    "totalInvested" to newTotalInvested,
                    "lastUpdated" to System.currentTimeMillis()
                )
                transaction.set(coinRef, portfolioUpdates, SetOptions.merge())

                val tradeData = TradeHistory(
                    coinId = coinId,
                    type = "BUY",
                    amount = amount,
                    price = currentPrice,
                    totalVolume = totalCost
                )
                transaction.set(tradeRef, tradeData)

                null
            }.await()

            Result.success(Unit)
        }catch (e: Exception){
            Log.e("REPO_ERROR", "Buy Coin Patladı: ${e.message}", e)
            Result.failure(e)
        }
    }

    suspend fun sellCoin(coinId: String, amount: Double, currentPrice: Double) : Result<Unit>{
        return try {
            val userId = auth.currentUser?.uid ?: throw Exception("User session not found")

            val totalEarnings = amount * currentPrice

            val userRef = firestore.collection("users").document(userId)
            val tetherRef = userRef.collection("portfolio").document("tether")
            val coinRef = userRef.collection("portfolio").document(coinId)
            val tradeRef = userRef.collection("trades").document()

            firestore.runTransaction { transaction ->

                val tetherSnapshot = transaction.get(tetherRef)
                val coinSnapshot = transaction.get(coinRef)

                val currentCoinAmount = coinSnapshot.getDouble("amount") ?: 0.0

                if (currentCoinAmount < amount){
                    throw FirebaseFirestoreException(
                        "Insufficient coin balance. Current: $currentCoinAmount, Requested: $amount",
                        FirebaseFirestoreException.Code.ABORTED
                    )
                }

                val currentTetherAmount = tetherSnapshot.getDouble("amount") ?: 0.0
                val currentTotalInvested = coinSnapshot.getDouble("totalInvested") ?: 0.0

                val newCoinAmount = currentCoinAmount - amount
                val newTetherAmount = currentTetherAmount + totalEarnings

                val newTotalInvested = if (currentCoinAmount > 0.0){
                    currentTotalInvested * (newCoinAmount / currentCoinAmount)
                }else{
                    0.0
                }

                val coinUpdates = mapOf(
                    "amount" to newCoinAmount,
                    "totalInvested" to newTotalInvested,
                    "lastUpdated" to System.currentTimeMillis()
                )
                transaction.set(coinRef, coinUpdates, SetOptions.merge())

                val tetherUpdates = mapOf(
                    "amount" to newTetherAmount,
                    "lastUpdated" to System.currentTimeMillis()
                )
                transaction.set(tetherRef, tetherUpdates, SetOptions.merge())

                val tradeData = TradeHistory(
                    coinId = coinId,
                    type = "SELL",
                    amount = amount,
                    price = currentPrice,
                    totalVolume = totalEarnings
                )
                transaction.set(tradeRef, tradeData)

                null
            }.await()

            Result.success(Unit)
        }catch (e: Exception){
            Log.e("REPO_ERROR", "Sell Coin Patladı: ${e.message}", e)
            Result.failure(e)
        }

    }

    fun getPortfolioFlow() : Flow<List<PortfolioItem>> = callbackFlow {
        val userId = auth.currentUser?.uid
        if (userId == null){
            close(Exception("User not logged in"))
            return@callbackFlow
        }

        val portfolioRef = firestore.collection("users").document(userId).collection("portfolio")

        val subscription = portfolioRef.addSnapshotListener { snapshot, error ->
            if (error != null){
                close(error)
                return@addSnapshotListener
            }

            val items = snapshot?.documents?.mapNotNull { doc ->
                val amount = doc.getDouble("amount") ?: 0.0
                val totalInvested = doc.getDouble("totalInvested") ?: 0.0
                PortfolioItem(doc.id, amount, totalInvested)
            } ?: emptyList()

            trySend(items)
        }

        awaitClose { subscription.remove() }
    }


}