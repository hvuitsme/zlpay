package com.hvuitsme.zlpay

import android.os.Bundle
import android.os.StrictMode
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hvuitsme.zlpay.databinding.FragmentOrderPaymentBinding
import org.json.JSONObject
import vn.zalopay.sdk.ZaloPaySDK
import vn.zalopay.sdk.listeners.PayOrderListener
import vn.zalopay.sdk.ZaloPayError

class OrderPaymentFragment : Fragment(R.layout.fragment_order_payment) {

    private var _binding: FragmentOrderPaymentBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentOrderPaymentBinding.bind(view)

        // Cho phép gọi API trên main thread (theo mẫu code gốc, tuy nhiên trong thực tế hãy dùng background thread)
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())

        // Lấy dữ liệu từ Bundle
        val quantity = requireArguments().getString("quantity") ?: ""
        val total = requireArguments().getFloat("total", 0f)
        binding.textViewSoluong.text = quantity
        binding.textViewTongTien.text = total.toString()

        binding.buttonThanhToan.setOnClickListener {
            val createOrder = CreateOrder()
            try {
                // Định dạng tổng tiền không có phần thập phân
                val totalString = String.format("%.0f", total)
                val data: JSONObject = createOrder.createOrder(totalString)
                if (data.getString("return_code") == "1") {
                    val token = data.getString("zp_trans_token")
                    ZaloPaySDK.getInstance().payOrder(requireActivity(), token, "demozpdk://app",
                        object : PayOrderListener {
                            override fun onPaymentSucceeded(
                                transactionId: String?,
                                transToken: String?,
                                appTransId: String?
                            ) {
                                // Tạo Bundle chứa thông báo kết quả
                                val bundle = Bundle().apply {
                                    putString("result", "Thanh toán thành công")
                                }
                                findNavController().navigate(R.id.paymentNotificationFragment, bundle)
                            }

                            override fun onPaymentCanceled(
                                transactionId: String?,
                                transToken: String?
                            ) {
                                val bundle = Bundle().apply {
                                    putString("result", "Hủy thanh toán")
                                }
                                findNavController().navigate(R.id.paymentNotificationFragment, bundle)
                            }

                            override fun onPaymentError(
                                error: ZaloPayError?,
                                transactionId: String?,
                                errorMessage: String?
                            ) {
                                val bundle = Bundle().apply {
                                    putString("result", "Lỗi thanh toán")
                                }
                                findNavController().navigate(R.id.paymentNotificationFragment, bundle)
                            }
                        })
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}