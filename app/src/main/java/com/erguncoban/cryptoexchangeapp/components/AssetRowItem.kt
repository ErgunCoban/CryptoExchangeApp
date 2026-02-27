package com.erguncoban.cryptoexchangeapp.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import com.erguncoban.cryptoexchangeapp.data.entity.AssetItemUiModel
import com.erguncoban.cryptoexchangeapp.ui.theme.CryptoGray
import com.erguncoban.cryptoexchangeapp.ui.theme.CryptoWhite
import okhttp3.Dns
import okhttp3.OkHttpClient
import okhttp3.Protocol
import java.net.Inet4Address
import java.net.InetAddress
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale
import java.util.concurrent.TimeUnit

@Composable
fun AssetRowItem(asset: AssetItemUiModel) {

    val symbols = DecimalFormatSymbols(Locale("tr", "TR"))
    val usdFormatter = DecimalFormat("#,##0.00", symbols)
    val coinFormatter = DecimalFormat("#,##0.#####", symbols)

    val context = LocalContext.current
    val ipv4ImageLoader = remember {
        ImageLoader.Builder(context)
            .okHttpClient {
                OkHttpClient.Builder()
                    // 1. ZAMAN AŞIMI SÜRELERİNİ UZATIYORUZ (10 saniyeden 30 saniyeye)
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .writeTimeout(120, TimeUnit.SECONDS)
                    // 2. HTTP/2 YERİNE DAHA STABİL OLAN HTTP/1.1 KULLANMAYA ZORLUYORUZ
                    .protocols(listOf(Protocol.HTTP_1_1))
                    .dns(object : Dns {
                        override fun lookup(hostname: String): List<InetAddress> {
                            val allAddresses = Dns.SYSTEM.lookup(hostname)
                            val ipv4Addresses = allAddresses.filterIsInstance<Inet4Address>()
                            return ipv4Addresses.ifEmpty { allAddresses }
                        }
                    })
                    .build()
            }
            .build()
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(asset.imageUrl)
                .crossfade(true)
                .build(),
            imageLoader = ipv4ImageLoader,
            contentDescription = "${asset.name} logo",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape),
            onState = { state ->
                if (state is AsyncImagePainter.State.Error) {
                    Log.e("COIL_ERROR", "Resim yüklenemedi URL: ${asset.imageUrl}")
                    Log.e("COIL_ERROR", "Hata Detayı: ${state.result.throwable.message}", state.result.throwable)
                }
            }
        )
        Spacer(modifier = Modifier.size(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = asset.symbol.uppercase(),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = CryptoWhite
            )
            Spacer(modifier = Modifier.size(2.dp))

            Text(
                text = asset.name,
                fontSize = 13.sp,
                color = CryptoGray
            )

        }

        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = coinFormatter.format(asset.amount),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = CryptoWhite
            )
            Spacer(modifier = Modifier.size(2.dp))

            Text(
                text = "${usdFormatter.format(asset.totalValue)} USDT",
                fontSize = 13.sp,
                color = CryptoGray
            )
        }
    }

}