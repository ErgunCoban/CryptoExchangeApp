package com.erguncoban.cryptoexchangeapp.data.datasource

import com.erguncoban.cryptoexchangeapp.data.entity.TradeHistory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TransferDataSource @Inject constructor(private val auth: FirebaseAuth,
                                             private val firestore: FirebaseFirestore){

    suspend fun checkUserExists(userId: String) : Boolean{
        return try {
            val documentSnapshot = firestore.collection("users").document(userId).get().await()

            documentSnapshot.exists()
        }catch (e: Exception){
            false
        }

    }

    suspend fun executeTransfer(
        receiverId: String,
        coinId: String,
        amount: Double
    ): Result<Unit>{
        return try {
            val senderId = auth.currentUser?.uid ?: throw Exception("User not logged in")

            val senderWalletRef = firestore.collection("users").document(senderId).collection("portfolio").document(coinId)
            val receiverWalletRef = firestore.collection("users").document(receiverId).collection("portfolio").document(coinId)

            val senderHistoryRef = firestore.collection("users").document(senderId).collection("trades").document()
            val receiverHistoryRef = firestore.collection("users").document(receiverId).collection("trades").document()

            firestore.runTransaction { transaction ->
                val senderSnapshot = transaction.get(senderWalletRef)
                val currentSenderAmount = senderSnapshot.getDouble("amount") ?: 0.0

                if (currentSenderAmount < amount){
                    throw Exception("Insufficient funds!")
                }

                if (senderId == receiverId){
                    throw Exception("You can't make a transfer for yourself!")  //kullanıcının kendisine transfer yapması engellendi.
                }

                val receiverSnapshot = transaction.get(receiverWalletRef)
                val currentReceiverAmount = receiverSnapshot.getDouble("amount") ?: 0.0

                transaction.set(senderWalletRef, mapOf("amount" to (currentSenderAmount - amount)))
                transaction.set(receiverWalletRef, mapOf("amount" to (currentReceiverAmount + amount)))

                val senderHistory = TradeHistory(
                    documentId = senderHistoryRef.id,
                    coinId = coinId,
                    type = "TRANSFER",
                    amount = amount,
                    price = 0.0,
                    totalVolume = 0.0,
                    timestamp = System.currentTimeMillis()
                )
                transaction.set(senderHistoryRef, senderHistory)

                val receiverHistory = TradeHistory(
                    documentId = receiverHistoryRef.id,
                    coinId = coinId,
                    type = "DEPOSIT",  //başkasından gelen transfer alıcıda yatırım olarak değerlendirildi.
                    amount = amount,
                    price = 0.0,
                    totalVolume = 0.0,
                    timestamp = System.currentTimeMillis()
                )
                transaction.set(receiverHistoryRef, receiverHistory)

                null
            }.await()

            Result.success(Unit)
        }catch (e: Exception){
            Result.failure(e)
        }
    }

}