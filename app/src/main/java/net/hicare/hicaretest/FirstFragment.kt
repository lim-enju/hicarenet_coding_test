package net.hicare.hicaretest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import net.hicare.hicaretest.GetEvenNum.getOnlyEvenNum
import net.hicare.hicaretest.databinding.FragmentFirstBinding
import kotlin.random.Random

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        initView()
    }

    private fun initView(){
        repeat(5){
            val originList = List(5) { Random.nextInt(100) }
            val evenList = originList.getOnlyEvenNum()

            binding.txtEvenList.text = "${binding.txtEvenList.text} origin: $originList \n filter even $evenList \n\n\n"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}