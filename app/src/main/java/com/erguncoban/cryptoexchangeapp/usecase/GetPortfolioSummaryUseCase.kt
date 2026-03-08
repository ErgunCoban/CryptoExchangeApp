package com.erguncoban.cryptoexchangeapp.usecase

import com.erguncoban.cryptoexchangeapp.data.entity.CryptoCoin
import com.erguncoban.cryptoexchangeapp.data.entity.PortfolioItem
import com.erguncoban.cryptoexchangeapp.data.entity.PortfolioSummary
import com.erguncoban.cryptoexchangeapp.data.repository.CoinRepository
import com.erguncoban.cryptoexchangeapp.data.repository.PortfolioRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPortfolioSummaryUseCase @Inject constructor(
    private val portfolioRepository: PortfolioRepository,
    private val coinRepository: CoinRepository
) {

    // operator fun invoke() -> Bu fonksiyon sayesinde usecase classımızı viewModel içerisinde bir fonksiyonmuş gibi çağırabileceğiz.
    operator fun invoke() : Flow<PortfolioSummary>{

        //sanal flow
        val liveMarketFlow = flow {
            while (true){
                try {
                    val coins = coinRepository.getCoins()
                    emit(coins)
                }catch (e: Exception){
                    e.printStackTrace()
                }
                delay(30_000)
            }
        }

        return combine(
            portfolioRepository.getPortfolioFlow(),
            liveMarketFlow
        ){portfolioItems, marketCoins ->
            if (portfolioItems.isEmpty() || marketCoins.isEmpty()){
                return@combine PortfolioSummary()
            }

            calculateSummary(portfolioItems, marketCoins)
        }
    }

    private fun calculateSummary(
        portfolioItems: List<PortfolioItem>,
        marketCoins: List<CryptoCoin>
    ) : PortfolioSummary{
        var totalBalance = 0.0
        var totalAbsoluteChange = 0.0

        portfolioItems.forEach { item ->
            val coinData = marketCoins.find { it.id == item.coinId}

            if (coinData != null){
                totalBalance += item.amount * coinData.current_price
                totalAbsoluteChange += item.amount * coinData.priceChanged24h
            }
        }

        val value24hAgo = totalBalance - totalAbsoluteChange
        val percentageChange = if (value24hAgo > 0.0){
            (totalAbsoluteChange / value24hAgo) * 100
        }else{
            0.0
        }

        return PortfolioSummary(
            totalBalanceUsd = totalBalance,
            absoluteChange24h = totalAbsoluteChange,
            percentageChange24h = percentageChange
        )
    }

}