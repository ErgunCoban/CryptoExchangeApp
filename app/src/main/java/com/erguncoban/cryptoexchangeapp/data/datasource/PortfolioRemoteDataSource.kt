package com.erguncoban.cryptoexchangeapp.data.datasource

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
            val portfolioRef = userRef.collection("portfolio").document(coinId)
            val tradeRef = userRef.collection("trades").document()

            firestore.runTransaction { transaction ->

                val userSnapshot = transaction.get(userRef)
                val currentBalance = userSnapshot.getDouble("balance") ?: 0.0

                if (currentBalance < totalCost){
                    throw FirebaseFirestoreException(
                        "Insufficient balance. Current: $currentBalance, Requested: $totalCost",
                        FirebaseFirestoreException.Code.ABORTED
                    )
                }

                val portfolioSnapshot = transaction.get(portfolioRef)
                val currentCoinAmount = portfolioSnapshot.getDouble("amount") ?: 0.0
                val currentTotalInvested = portfolioSnapshot.getDouble("totalInvested") ?: 0.0

                val newBalance = currentBalance - totalCost
                val newCoinAmount = currentCoinAmount + amount
                val newTotalInvested = currentTotalInvested + totalCost

                transaction.update(userRef, "balance", newBalance)

                val portfolioUpdates = mapOf(
                    "amount" to newCoinAmount,
                    "totalInvested" to newTotalInvested,
                    "lastUpdated" to System.currentTimeMillis()
                )
                transaction.set(portfolioRef, portfolioUpdates, SetOptions.merge())

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
            Result.failure(e)
        }
    }

    suspend fun sellCoin(coinId: String, amount: Double, currentPrice: Double) : Result<Unit>{
        return try {
            val userId = auth.currentUser?.uid ?: throw Exception("User session not found")

            val totalEarnings = amount * currentPrice

            val userRef = firestore.collection("users").document(userId)
            val portfolioRef = userRef.collection("portfolio").document(coinId)
            val tradeRef = userRef.collection("trades").document()

            firestore.runTransaction { transaction ->

                val portfolioSnapshot = transaction.get(portfolioRef)
                val currentCoinAmount = portfolioSnapshot.getDouble("amount") ?: 0.0
                val currentTotalInvested = portfolioSnapshot.getDouble("totalInvested") ?: 0.0

                if (currentCoinAmount < amount){
                    throw FirebaseFirestoreException(
                        "Insufficient coin amount. Current: $currentCoinAmount, Required: $amount",
                        FirebaseFirestoreException.Code.ABORTED
                    )
                }

                val userSnapshot = transaction.get(userRef)
                val currentBalance = userSnapshot.getDouble("balance") ?: 0.0

                val newBalance = currentBalance + totalEarnings
                val newCoinAmount = currentCoinAmount - amount

                val avgCost = if (currentCoinAmount > 0){
                    currentTotalInvested / currentCoinAmount
                }else{
                    0.0
                }
                val newTotalInvested = if (newCoinAmount > 0){
                    newCoinAmount * avgCost
                }else{
                    0.0
                }

                transaction.update(userRef, "balance", newBalance)

                if (newCoinAmount <= 0.000001){
                    transaction.delete(portfolioRef)
                }else{
                    val portfolioUpdates = mapOf(
                        "amount" to newCoinAmount,
                        "totalInvested" to newTotalInvested,
                        "lastUpdates" to System.currentTimeMillis()
                    )
                    transaction.set(portfolioRef, portfolioUpdates, SetOptions.merge())
                }

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