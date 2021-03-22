package com.example.proyectosegundob_moviles

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "titulo"

/**
 * A simple [Fragment] subclass.
 * Use the [TopBar.newInstance] factory method to
 * create an instance of this fragment.
 */
class TopBar : Fragment() {
    // TODO: Rename and change types of parameters
    private var titulo: String? = null
    private var tvTitulo: TextView? = null
    private var btnHam: ImageView? = null
    private var btnProf: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            titulo = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top_bar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvTitulo = getView()?.findViewById<TextView>(R.id.tv_titulo)
        btnHam = getView()?.findViewById<ImageView>(R.id.btn_ham)
        btnProf = getView()?.findViewById<ImageView>(R.id.btn_prof)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (arguments != null) {
            if (titulo != null) {
                tvTitulo?.text = titulo
            }
        }
        btnHam?.setOnClickListener {
            startActivity(
                Intent(
                    this.activity,
                    MenuHam::class.java
                ).putExtra("origen",titulo)
            )
        }

        btnProf?.setOnClickListener {
            startActivity(
                Intent(
                    this.activity,
                    MenuProfile::class.java
                )
            )
            this.activity?.overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TopBar.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(titulo: String) =
            TopBar().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, titulo)
                }
            }
    }
}