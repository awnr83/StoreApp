package com.moappdev.solutions.storeapp.detalle

import android.app.Activity
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.moappdev.solutions.storeapp.MainActivity
import com.moappdev.solutions.storeapp.R
import com.moappdev.solutions.storeapp.database.TiendaDatabase
import com.moappdev.solutions.storeapp.databinding.FragmentDetalleBinding
import kotlinx.android.synthetic.main.fragment_detalle.*

class DetalleFragment : Fragment() {

    private lateinit var mBinding: FragmentDetalleBinding
    private lateinit var mViewModel: DetalleViewModel
    private lateinit var mViewModelFactory: DetalleViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding= FragmentDetalleBinding.inflate(inflater)

        val application= requireNotNull(this.activity).application
        val db=TiendaDatabase.getInstance(application).tiendaDao
        val arg=DetalleFragmentArgs.fromBundle(arguments!!).tienda

        mViewModelFactory= DetalleViewModelFactory(db,arg)
        mViewModel=ViewModelProvider(this,mViewModelFactory).get(DetalleViewModel::class.java)
        mBinding.viewModel=mViewModel
        mBinding.lifecycleOwner=this

        mBinding.etNombre.addTextChangedListener { validarDatos(mBinding.tilNombre) }
        mBinding.etPhoto.addTextChangedListener { validarPhoto(mBinding.tilPhoto) }

        mViewModel.navigate.observe(viewLifecycleOwner, Observer {
            if(it){
                Snackbar.make(mBinding.root,getString(R.string.msg_guardado),Snackbar.LENGTH_SHORT).show()
                findNavController().navigate(DetalleFragmentDirections.actionDetalleFragmentToTiendaFragment())
                mViewModel.onNavigateC()
            }
        })
        setHasOptionsMenu(true)
        return mBinding.root
    }

    private fun validarDatos(vararg til: TextInputLayout):Boolean{
        var ok=true
        til.forEach {
            if (it.editText?.text.isNullOrEmpty()) {
                it.error = getString(R.string.fd_helper)
                it.editText?.requestFocus()
                ok = false
            } else
                it.isErrorEnabled = !it.isErrorEnabled
        }
        return ok
    }
    private fun validarPhoto(til: TextInputLayout){
        Glide.with(requireActivity())
            .load(mBinding.etPhoto.text.toString())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .apply(RequestOptions()
                .placeholder(R.drawable.load_animation)
                .error(R.drawable.ic_broken))
            .centerCrop()
            .into(mBinding.ivPhoto)
    }

    //Menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_detalle,menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mnuGuardar -> {
                if (validarDatos(mBinding.tilNombre))
                    mViewModel.guardar()
                else
                    Toast.makeText(requireContext(), getString(R.string.msg_datos), Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }
}