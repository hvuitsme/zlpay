package com.hvuitsme.zlpay

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.hvuitsme.zlpay.databinding.FragmentPaymentNotificationBinding

class PaymentNotificationFragment : Fragment(R.layout.fragment_payment_notification) {

    private var _binding: FragmentPaymentNotificationBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPaymentNotificationBinding.bind(view)
        // Lấy kết quả thanh toán từ Bundle
        val result = requireArguments().getString("result") ?: "Notification"
        binding.textViewNotify.text = result
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}