package com.hvuitsme.zlpay

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hvuitsme.zlpay.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        binding.buttonConfirm.setOnClickListener {
            val quantityStr = binding.editTextSoluong.text.toString()
            if (quantityStr.isEmpty()){
                Toast.makeText(requireContext(), "Nhập số lượng muốn mua", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val total = quantityStr.toDouble() * 1_000_000

            // Tạo Bundle truyền dữ liệu sang OrderPaymentFragment
            val bundle = Bundle().apply {
                putString("quantity", quantityStr)
                putFloat("total", total.toFloat())
            }
            findNavController().navigate(R.id.orderPaymentFragment, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}