package com.hvuitsme.zlpay

import android.app.ComponentCaller
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import vn.zalopay.sdk.Environment
import vn.zalopay.sdk.ZaloPaySDK

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ZaloPaySDK.init(2553, Environment.SANDBOX)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        // Nếu có data deep link (ví dụ: demozpdk://app)
        intent.data?.let { uri: Uri ->
            if (uri.scheme == "demozpdk" && uri.host == "app") {
                // Gọi callback của Zalopay để xử lý kết quả (sẽ gọi listener callback trong OrderPaymentFragment nếu đang chạy)
                ZaloPaySDK.getInstance().onResult(intent)
                // Chuyển sang PaymentNotificationFragment với thông báo kết quả
                val bundle = Bundle().apply {
                    // Bạn có thể điều chỉnh thông báo ở đây theo kết quả thực nhận được,
                    // ví dụ: "Thanh toán thành công" khi mà listener onPaymentSucceeded được gọi
                    putString("result", "Thanh toán thành công")
                }
                // Sử dụng navController từ NavHostFragment đã khai báo trong layout activity_main.xml
                findNavController(R.id.nav_host_fragment).navigate(R.id.paymentNotificationFragment, bundle)
                // Xóa data của intent sau khi xử lý để tránh trigger lại
                intent.data = null
            }
        }
    }

//    override fun onNewIntent(intent: Intent) {
//        super.onNewIntent(intent)
//
//        ZaloPaySDK.getInstance().onResult(intent)
//    }

}

//class MainActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityMainBinding
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//    }
//}