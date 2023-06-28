package com.example.sewakameraapp01.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sewakameraapp01.R
import com.example.sewakameraapp01.application.CameraApp
import com.example.sewakameraapp01.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var applicationContext: Context
    private val cameraViewModel: CameraViewModel by viewModels {
        CameraViewModelFactory((applicationContext as CameraApp).repository)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationContext = requireContext().applicationContext
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CameraListAdapter { camera ->
            // ini list yang bisa di klik dan mendapatkan data camera jadi tidak null
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(camera)
            findNavController().navigate(action)
        }

        binding.dataRecyclerView.adapter = adapter
        binding.dataRecyclerView.layoutManager = LinearLayoutManager(context)
        cameraViewModel.allCameras.observe(viewLifecycleOwner) { cameras ->
            cameras.let {
                if (cameras.isEmpty()) {
                    binding.emptyTextView.visibility = View.VISIBLE
                    binding.illustrationimageView.visibility = View.VISIBLE
                } else {
                    binding.emptyTextView.visibility = View.GONE
                    binding.illustrationimageView.visibility = View.GONE
                }
                adapter.submitList(cameras)
            }
        }

           binding.addFAB.setOnClickListener {
               // ini button tambah jadi camera pasti null
               val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(null)
               findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}