package com.moappdev.solutions.storeapp.tiendas

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.moappdev.solutions.storeapp.R
import com.moappdev.solutions.storeapp.database.TiendaDatabase
import com.moappdev.solutions.storeapp.database.TiendaEntity
import com.moappdev.solutions.storeapp.databinding.FragmentTiendaBinding

class TiendaFragment : Fragment() {
    private enum class INTENT(val opc: Int){ MODIFICAR(0), ELIMINAR(1), LLAMAR(2), WEB(3)}
    private lateinit var mBinding:FragmentTiendaBinding
    private lateinit var mViewModel: TiendaViewModel
    private lateinit var mViewModelFactory: TiendaViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding= FragmentTiendaBinding.inflate(inflater)

        activity?.title=getString(R.string.app_name)

        val application= requireNotNull(this.activity).application
        val db= TiendaDatabase.getInstance(application).tiendaDao
        mViewModelFactory= TiendaViewModelFactory(db)

        mViewModel= ViewModelProvider(this,mViewModelFactory).get(TiendaViewModel::class.java)
        mBinding.viewModel=mViewModel
        mBinding.lifecycleOwner= this

        val adapter= TiendaAdapter(
            TiendaAdapter.TiendaListener {
                mostrarDialog(it)
            },
            TiendaAdapter.FavoritoListener{
                mViewModel.onFavorito(it)
            })

        mBinding.recyclerView.adapter=adapter

        mViewModel.allTiendas.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
        mViewModel.navigate.observe(viewLifecycleOwner, Observer {
            if(it){
                val tienda=TiendaEntity(nombre = "")
                findNavController().navigate(TiendaFragmentDirections.actionTiendaFragmentToDetalleFragment2(tienda))
                mViewModel.onNavigateC()
            }
        })

        return mBinding.root
    }
    private fun mostrarDialog(tienda: TiendaEntity){
        var opciones=resources.getStringArray(R.array.dialog_opciones)
        MaterialAlertDialogBuilder(requireActivity())
            .setTitle(R.string.dialog_title)
            .setIcon(R.drawable.ic_store)
            .setItems(opciones, DialogInterface.OnClickListener { dialog, which ->
                when(which) {
                    INTENT.MODIFICAR.opc-> findNavController().navigate(TiendaFragmentDirections.actionTiendaFragmentToDetalleFragment2(tienda))
                    INTENT.ELIMINAR.opc-> eliminarDialog(tienda)
                    INTENT.LLAMAR.opc-> llamarIntent(tienda.telefono)
                    INTENT.WEB.opc-> webIntent(tienda.sitioWeb)
                }
            })
            .show()
    }
    private fun eliminarDialog(tienda:TiendaEntity){
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.dialog_title_eliminar)
            .setPositiveButton(R.string.dialog_eliminar_si, DialogInterface.OnClickListener { dialog, which ->
                mViewModel.onEliminar(tienda)
                Snackbar.make(requireView(),"Eliminado",Snackbar.LENGTH_SHORT).show()
            })
            .setNegativeButton(R.string.dialog_eliminar_no,null)
            .show()
    }
    private fun llamarIntent(telefono: String){
        if(telefono.isNullOrEmpty())
           Toast.makeText(requireContext(),getString(R.string.msg_no_telefono),Toast.LENGTH_SHORT).show()
        else{
            val i= Intent()
            i.action= Intent.ACTION_DIAL
            i.data= Uri.parse("tel: $telefono")
            dialogIntet(i)
        }
    }
    private fun webIntent(url: String){
        if(url.isNullOrEmpty())
            Toast.makeText(requireContext(),getString(R.string.msg_no_web),Toast.LENGTH_SHORT).show()
        else{
            val i= Intent()
            i.action= Intent.ACTION_VIEW
            i.data= Uri.parse(url)
            startActivity(i)
        }
    }
    private fun dialogIntet(i:Intent){
        if(i.resolveActivity(requireActivity().packageManager)!=null)
            startActivity(i)
        else
            Toast.makeText(requireContext(), getString(R.string.msg_no_intent), Toast.LENGTH_SHORT).show()
    }
}