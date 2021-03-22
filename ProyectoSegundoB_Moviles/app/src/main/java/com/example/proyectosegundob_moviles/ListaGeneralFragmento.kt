package com.example.proyectosegundob_moviles

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectosegundob_moviles.AuthUsuario.Companion.usuario
import com.example.proyectosegundob_moviles.Firestore.Companion.db
import com.example.proyectosegundob_moviles.dto.IngresoEgresoDTO
import com.example.proyectosegundob_moviles.dto.MetaDTO
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.textColor

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "ubicacion"

/**
 * A simple [Fragment] subclass.
 * Use the [ListaGeneralFragmento.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListaGeneralFragmento : Fragment() {
    // TODO: Rename and change types of parameters
    private var ubicacion: String? = null
    private var btnActivo: Button? = null
    private var btnInactivo: Button? = null
    private var btnAnadir: ImageView? = null
    private var esActivo: Boolean = true
    private var infoIn = arrayListOf<Array<String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            ubicacion = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista_general_fragmento, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnActivo = getView()?.findViewById(R.id.btn_listaActivos)
        btnInactivo = getView()?.findViewById(R.id.btn_listaInactivos)
        btnAnadir = getView()?.findViewById(R.id.btn_anadirItem)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btnInactivo?.setOnClickListener {
            btnInactivo?.backgroundColor = Color.parseColor("#9CE37D")
            btnInactivo?.textColor = Color.parseColor("#49535A")
            btnActivo?.backgroundColor = Color.parseColor("#305685")
            btnActivo?.textColor = Color.parseColor("#FFFFFF")
            esActivo = false
            val usuarioLocal = FirebaseAuth.getInstance().currentUser
            if(usuarioLocal != null && !usuarioLocal!!.isAnonymous) {
                iniciarRecyclerView(
                    this.activity!!.findViewById(R.id.rv_listaGeneralFragmento),
                    arrayListOf(ubicacion!!, "Inactive")
                )
            }
        }

        btnActivo?.setOnClickListener {
            btnInactivo?.backgroundColor = Color.parseColor("#305685")
            btnInactivo?.textColor = Color.parseColor("#FFFFFF")
            btnActivo?.backgroundColor = Color.parseColor("#9CE37D")
            btnActivo?.textColor = Color.parseColor("#49535A")
            esActivo = true
            val usuarioLocal = FirebaseAuth.getInstance().currentUser
            if(usuarioLocal != null && !usuarioLocal!!.isAnonymous) {
                iniciarRecyclerView(
                    this.activity!!.findViewById(R.id.rv_listaGeneralFragmento),
                    arrayListOf(ubicacion!!, "Active")
                )
            }
        }

        btnAnadir?.setOnClickListener {
            if(ubicacion!! == "metas") {
                infoIn.add(arrayOf("","","","","","","","","","","@@@new@@@"))
            } else {
                infoIn.add(arrayOf("","","","","","","","","@@@new@@@"))
            }
            val usuarioLocal = FirebaseAuth.getInstance().currentUser
            if(usuarioLocal != null && !usuarioLocal!!.isAnonymous) {
                iniciarRecyclerView(
                    this.activity!!.findViewById(R.id.rv_listaGeneralFragmento),
                    arrayListOf(ubicacion!!, "Active")
                )
            }
        }

        val usuarioLocal = FirebaseAuth.getInstance().currentUser
        if(usuarioLocal != null && !usuarioLocal!!.isAnonymous) {
            iniciarRecyclerView(
                this.activity!!.findViewById(R.id.rv_listaGeneralFragmento),
                arrayListOf(ubicacion!!, "Active")
            )
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListaGeneralFragmento.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListaGeneralFragmento().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }

    fun iniciarRecyclerView(
        recyclerView: RecyclerView,
        parameters: List<String>
    ) {

        if(parameters[0] == "metas") {

            db.collection("Usuarios").document("${usuario!!.email}")
                .collection("${parameters[0]}")
                .get()
                .addOnSuccessListener {

                    for (document in it) {
                        val meta = document.toObject(MetaDTO::class.java)
                        if (parameters[1] == meta.estado) {
                            infoIn.add(
                                arrayOf(
                                    "${meta.avance}",
                                    "${meta.descripcion}",
                                    "${meta.estado}",
                                    "${meta.fechaFin}",
                                    "${meta.fechaInicio}",
                                    "${meta.meta}",
                                    "${meta.nombre}",
                                    "${meta.progreso}",
                                    "${meta.razon}",
                                    "${document.id}"
                                )
                            )
                        }
                    }

                    val adaptador = ListaAdaptador(parameters, infoIn)

                    recyclerView.adapter = adaptador
                    recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
                    recyclerView.layoutManager =
                        androidx.recyclerview.widget.LinearLayoutManager(this.activity)

                    infoIn = arrayListOf<Array<String>>()

                }.addOnFailureListener {
                    Log.e("firestore", "Falló: $it")
                }
        }
        else if (parameters[0] == "ingresos"){

            db.collection("Usuarios").document("${usuario!!.email}")
                .collection("${parameters[0]}")
                .get()
                .addOnSuccessListener {

                    for (document in it) {
                        val ingreso = document.toObject(IngresoEgresoDTO::class.java)
                        if (parameters[1] == ingreso.estado) {
                            infoIn.add(
                                arrayOf(
                                    "${ingreso.cantidad}",
                                    "${ingreso.descripcion}",
                                    "${ingreso.estado}",
                                    "${ingreso.fechaFin}",
                                    "${ingreso.fechaInicio}",
                                    "${ingreso.nombre}",
                                    "${ingreso.razon}",
                                    "${document.id}"
                                )
                            )
                        }
                    }

                    val adaptador = ListaAdaptador(parameters, infoIn)

                    recyclerView.adapter = adaptador
                    recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
                    recyclerView.layoutManager =
                        androidx.recyclerview.widget.LinearLayoutManager(this.activity)
                    infoIn = arrayListOf<Array<String>>()

                }.addOnFailureListener {
                    Log.e("firestore", "Falló: $it")
                }

        }
        else if (parameters[0] == "egresos"){

            db.collection("Usuarios").document("${usuario!!.email}")
                .collection("${parameters[0]}")
                .get()
                .addOnSuccessListener {

                    for (document in it) {
                        val egreso = document.toObject(IngresoEgresoDTO::class.java)
                        if (parameters[1] == egreso.estado) {
                            infoIn.add(
                                arrayOf(
                                    "${egreso.cantidad}",
                                    "${egreso.descripcion}",
                                    "${egreso.estado}",
                                    "${egreso.fechaFin}",
                                    "${egreso.fechaInicio}",
                                    "${egreso.nombre}",
                                    "${egreso.razon}",
                                    "${document.id}"
                                )
                            )
                        }
                    }

                    val adaptador = ListaAdaptador(parameters, infoIn)

                    recyclerView.adapter = adaptador
                    recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
                    recyclerView.layoutManager =
                        androidx.recyclerview.widget.LinearLayoutManager(this.activity)
                    infoIn = arrayListOf<Array<String>>()

                }.addOnFailureListener {
                    Log.e("firestore", "Falló: $it")
                }
        }
    }
}