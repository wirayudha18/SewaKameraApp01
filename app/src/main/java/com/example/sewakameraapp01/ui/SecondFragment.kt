package com.example.sewakameraapp01.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.sewakameraapp01.R
import com.example.sewakameraapp01.application.CameraApp
import com.example.sewakameraapp01.databinding.FragmentSecondBinding
import com.example.sewakameraapp01.model.Camera

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var applicationContext: Context
    private val cameraViewModel: CameraViewModel by viewModels {
        CameraViewModelFactory((applicationContext as CameraApp).repository)
    }
    private val args : SecondFragmentArgs by navArgs()
    private var camera: Camera? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationContext = requireContext().applicationContext
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        camera = args.camera
        if (camera != null) {
            binding.deleteButton.visibility = View.VISIBLE
            binding.saveButton.text ="ubah"
            binding.nameEditText.setText(camera?.name)
            binding.itemEditText.setText(camera?.item)
            binding.timeEditText.setText(camera?.time)
        }
        val name = binding.nameEditText.text
        val item = binding.itemEditText.text
        val time = binding.timeEditText.text
        binding.saveButton.setOnClickListener {
            if(name.isEmpty()) {
                Toast.makeText(context, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else if(item.isEmpty()) {
            Toast.makeText(context, "Item tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else if(time.isEmpty()) {
                Toast.makeText(context, "Waktu tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else {

                if (camera == null) {
                    val camera  = Camera(0, name.toString(), item.toString(), time.toString())
                    cameraViewModel.insert(camera)
                } else {
                    val camera  = Camera(camera?.id!!, name.toString(), item.toString(), time.toString())
                    cameraViewModel.update(camera)
                }
                findNavController().popBackStack() // untuk dismiss halaman ini
            }
        }

        binding.deleteButton.setOnClickListener {
            camera?.let { cameraViewModel.delete(it) }
            findNavController().popBackStack()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}