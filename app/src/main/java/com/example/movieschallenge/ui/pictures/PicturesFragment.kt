package com.example.movieschallenge.ui.pictures

import android.R.attr.data
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieschallenge.databinding.FragmentPicturesBinding
import com.example.movieschallenge.provider.ViewModelFactory
import org.koin.android.ext.android.inject
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date


class PicturesFragment : Fragment() {

    private val binding: FragmentPicturesBinding by lazy {
        FragmentPicturesBinding.inflate(layoutInflater, null, false)
    }
    private val viewModelFactory: ViewModelFactory by inject()
    private val viewModel: PicturesViewModel by viewModels { viewModelFactory }
    private lateinit var uri:Uri

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.uploadPicture(mutableListOf(uri))
        }
    }
    var resultGalery = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            if (result.data?.clipData != null) {
                val count: Int = result.data?.clipData?.itemCount?:0
                val listaUri = mutableListOf<Uri>()
                for (i in 0 until count) {
                    result.data?.clipData?.getItemAt(i)?.getUri()?.let {
                        listaUri.add(it)
                    }
                }
                viewModel.uploadPicture(listaUri)

            }
        }
    }

    private val adapterRated: PicturesAdapter by lazy {
        PicturesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPictures()
        binding.rvPictures.apply {
            adapter = this@PicturesFragment.adapterRated
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        }
        viewModel.pictures.observe(viewLifecycleOwner){adapterRated.putPictures(it)}
        binding.btnAdd.setOnClickListener {
            startCamera()
        }
        binding.btnAddGFallery.setOnClickListener {
            launchGallery()
        }
    }

    fun startCamera() {
        val imagesFolder = File(Environment.getExternalStorageDirectory(), "pictures")
        if (!imagesFolder.exists()) {
            val isCreated = imagesFolder.mkdirs()
            if (!isCreated) {
                Toast.makeText(requireContext(), "Errore storage", Toast.LENGTH_LONG).show()
                return
            }
        }
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "IMG_" + timeStamp + "_"
        val storageDir: File = requireActivity().cacheDir
        try {
            val image = File.createTempFile(imageFileName, ".jpg", storageDir)
            uri = FileProvider.getUriForFile(
                requireContext(),
                "com.example.movieschallenge.fileprovider",
                image
            )
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            resultLauncher.launch(intent)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun launchGallery(){
        val intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        resultGalery.launch(Intent.createChooser(intent, "Selecciona imagenes"))
    }


}