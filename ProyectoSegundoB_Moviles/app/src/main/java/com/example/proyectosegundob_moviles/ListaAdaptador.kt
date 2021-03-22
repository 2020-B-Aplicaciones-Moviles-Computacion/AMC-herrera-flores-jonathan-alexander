package com.example.proyectosegundob_moviles

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.text.Editable
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.proyectosegundob_moviles.Firestore.Companion.db
import org.jetbrains.anko.textColor
import java.lang.reflect.Field


class ListaAdaptador(
    private val parametros: List<String>,
    private val infoIn: List<Array<String>>,

    ) : androidx.recyclerview.widget.RecyclerView.Adapter<
        ListaAdaptador.MyViewHolder
        >() {


    inner class MyViewHolder(view: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        val etNombre: EditText = view.findViewById(R.id.et_nombreItem)
        val etDescripcion: EditText = view.findViewById(R.id.et_descripcionItem)
        val etInicio: EditText = view.findViewById(R.id.et_inicioItem)
        val etFin: EditText = view.findViewById(R.id.et_finItem)
        val etEstado: EditText = view.findViewById(R.id.et_estadoItem)
        val etRazon: EditText = view.findViewById(R.id.et_razonItem)
        val etProgreso: EditText = view.findViewById(R.id.et_progresoItem)
        val etAvance: EditText = view.findViewById(R.id.et_avanceItem)
        val etMeta: EditText = view.findViewById(R.id.et_metaItem)

        val hintNombre: String = etNombre.hint.toString()
        val hintDescripcion: String = etDescripcion.hint.toString()
        val hintFin: String = etInicio.hint.toString()
        val hintInicio: String = etFin.hint.toString()
        val hintEstado: String = etEstado.hint.toString()
        val hintRazon: String = etRazon.hint.toString()
        val hintProgreso: String = etProgreso.hint.toString()
        val hintAvance: String = etAvance.hint.toString()
        val hintMeta: String = etMeta.hint.toString()

        val tvNombre: TextView = view.findViewById(R.id.tv_nombreItem)
        val tvDescripcion: TextView = view.findViewById(R.id.tv_descripcionItem)
        val tvInicio: TextView = view.findViewById(R.id.tv_inicioItem)
        val tvFin: TextView = view.findViewById(R.id.tv_finItem)
        val tvEstado: TextView = view.findViewById(R.id.tv_estadoItem)
        val tvRazon: TextView = view.findViewById(R.id.tv_razonItem)
        val tvProgreso: TextView = view.findViewById(R.id.tv_progresoItem)
        val tvAvance: TextView = view.findViewById(R.id.tv_avanceItem)
        val tvMeta: TextView = view.findViewById(R.id.tv_metaItem)

        val btnCancel: Button = view.findViewById(R.id.btn_cancelEditItem)
        val btnUpdate: Button = view.findViewById(R.id.btn_updateEditItem)
        val btnEdit: ImageView = view.findViewById(R.id.btn_editItem)
        val btnDelete: ImageView = view.findViewById(R.id.btn_deleteItem)
        val btnShow: ImageView = view.findViewById(R.id.btn_showItem)
        val btnHide: ImageView = view.findViewById(R.id.btn_hideItem)


        init {

            if (parametros[0] == "ingresos" || parametros[0] == "egresos") {
                tvProgreso.text = "Amount  "
            }

            etNombre.hint = ""
            etDescripcion.hint = ""
            etInicio.hint = ""
            etFin.hint = ""
            etEstado.hint = ""
            etRazon.hint = ""
            etProgreso.hint = ""
            etAvance.hint = ""
            etMeta.hint = ""

            btnShow
                .setOnClickListener {
                    tvDescripcion.visibility = View.VISIBLE
                    tvInicio.visibility = View.VISIBLE
                    tvFin.visibility = View.VISIBLE

                    etDescripcion.visibility = View.VISIBLE
                    etInicio.visibility = View.VISIBLE
                    etFin.visibility = View.VISIBLE
                    if (parametros[1] == "Inactive") {
                        etRazon.visibility = View.VISIBLE
                        tvRazon.visibility = View.VISIBLE
                    }

                    if (parametros[0] == "metas") {
                        tvAvance.visibility = View.VISIBLE
                        tvMeta.visibility = View.VISIBLE
                        etAvance.visibility = View.VISIBLE
                        etMeta.visibility = View.VISIBLE
                    }

                    btnHide.visibility = View.VISIBLE
                    btnShow.visibility = View.GONE
                    btnEdit.visibility = View.VISIBLE
                    btnDelete.visibility = View.INVISIBLE
                }

            btnHide
                .setOnClickListener {
                    tvDescripcion.visibility = View.GONE
                    tvInicio.visibility = View.GONE
                    tvFin.visibility = View.GONE
                    tvRazon.visibility = View.GONE
                    tvAvance.visibility = View.GONE
                    tvMeta.visibility = View.GONE

                    etDescripcion.visibility = View.GONE
                    etInicio.visibility = View.GONE
                    etFin.visibility = View.GONE
                    etRazon.visibility = View.GONE
                    etAvance.visibility = View.GONE
                    etMeta.visibility = View.GONE

                    btnHide.visibility = View.GONE
                    btnShow.visibility = View.VISIBLE
                    btnEdit.visibility = View.GONE
                    btnDelete.visibility = View.GONE
                }


            btnCancel
                .setOnClickListener {
                    etNombre.hint = ""
                    etDescripcion.hint = ""
                    etInicio.hint = ""
                    etFin.hint = ""
                    etEstado.hint = ""
                    etRazon.hint = ""
                    etProgreso.hint = ""
                    etAvance.hint = ""
                    etMeta.hint = ""

                    btnEdit.visibility = View.VISIBLE
                    btnDelete.visibility = View.INVISIBLE
                    btnUpdate.visibility = View.GONE
                    btnCancel.visibility = View.GONE
                    btnHide.visibility = View.VISIBLE

                    etNombre.inputType = InputType.TYPE_NULL
                    etDescripcion.inputType = InputType.TYPE_NULL
                    etInicio.inputType = InputType.TYPE_NULL
                    etFin.inputType = InputType.TYPE_NULL
                    etEstado.inputType = InputType.TYPE_NULL
                    etRazon.inputType = InputType.TYPE_NULL
                    etProgreso.inputType = InputType.TYPE_NULL
                    etAvance.inputType = InputType.TYPE_NULL
                    etMeta.inputType = InputType.TYPE_NULL
                }

            btnUpdate
                .setOnClickListener {
                    etNombre.hint = ""
                    etDescripcion.hint = ""
                    etInicio.hint = ""
                    etFin.hint = ""
                    etEstado.hint = ""
                    etRazon.hint = ""
                    etProgreso.hint = ""
                    etAvance.hint = ""
                    etMeta.hint = ""

                    btnEdit.visibility = View.VISIBLE
                    btnDelete.visibility = View.INVISIBLE
                    btnUpdate.visibility = View.GONE
                    btnCancel.visibility = View.GONE
                    btnHide.visibility = View.VISIBLE

                    etNombre.inputType = InputType.TYPE_NULL
                    etDescripcion.inputType = InputType.TYPE_NULL
                    etInicio.inputType = InputType.TYPE_NULL
                    etFin.inputType = InputType.TYPE_NULL
                    etEstado.inputType = InputType.TYPE_NULL
                    etRazon.inputType = InputType.TYPE_NULL
                    etProgreso.inputType = InputType.TYPE_NULL
                    etAvance.inputType = InputType.TYPE_NULL
                    etMeta.inputType = InputType.TYPE_NULL

                    var fechaInicio = etInicio.text.toString()
                    var fechaFin = etFin.text.toString()
                    var razon = etRazon.text.toString()

                    if (fechaInicio == "") {
                        fechaInicio = "null"
                    }
                    if (fechaFin == "") {
                        fechaFin = "null"
                    }
                    if (razon == "") {
                        razon = "null"
                    }

                    var nuevoDato = hashMapOf<String, Any>()
                    if (parametros[0] == "metas") {

                        etProgreso.text = (((etAvance.text.toString().toDouble()/etMeta.text.toString().toDouble()*10000).toInt().toDouble()/100).toString()+"%").toEditable()
                        nuevoDato = hashMapOf(
                            "avance" to etAvance.text.toString(),
                            "descripcion" to etDescripcion.text.toString(),
                            "estado" to etEstado.text.toString(),
                            "fechaFin" to fechaFin,
                            "fechaInicio" to fechaInicio,
                            "meta" to etMeta.text.toString(),
                            "nombre" to etNombre.text.toString(),
                            "progreso" to etProgreso.text.toString(),
                            "razon" to razon
                        )
                    } else if (parametros[0] == "ingresos" || parametros[0] == "egresos") {
                        nuevoDato = hashMapOf(
                            "cantidad" to etProgreso.text.toString(),
                            "descripcion" to etDescripcion.text.toString(),
                            "estado" to etEstado.text.toString(),
                            "fechaFin" to fechaFin,
                            "fechaInicio" to fechaInicio,
                            "nombre" to etNombre.text.toString(),
                            "razon" to razon
                        )
                    }

                    val identificadorUsuario = AuthUsuario.usuario?.email.toString()

                    val doc: String
                    if(infoIn[adapterPosition].last()=="@@@new@@@"){
                        doc = infoIn[adapterPosition].takeLast(2)[0]
                    } else {
                        doc = infoIn[adapterPosition].last()
                    }


                    if (doc == "") {
                        db.collection("Usuarios")
                            .document(identificadorUsuario)
                            .collection("${parametros[0]}")
                            .add(nuevoDato)
                            .addOnSuccessListener {
                                Log.i("firebase-firestore", "Se creó el usuario con éxito")
                            }
                            .addOnFailureListener {
                                Log.i("firebase-firestore", "Falló: $it")
                            }
                    } else {
                        db.collection("Usuarios")
                            .document(identificadorUsuario)
                            .collection("${parametros[0]}")
                            .document(doc)
                            .set(nuevoDato)
                            .addOnSuccessListener {
                                Log.i("firebase-firestore", "Se actualizó el usuario con éxito")
                            }
                            .addOnFailureListener {
                                Log.i("firebase-firestore", "Falló: $it")
                            }
                    }

                    val actividad = getActivity()
                    actividad?.finish()
                    actividad?.startActivity(
                        Intent(
                            actividad?.baseContext,
                            actividad?.javaClass
                        )
                    )

                    actividad?.overridePendingTransition(R.anim.no_animation, R.anim.no_animation)

                }

            btnEdit
                .setOnClickListener {
                    etNombre.hint = hintNombre
                    etDescripcion.hint = hintDescripcion
                    etInicio.hint = hintInicio
                    etFin.hint = hintFin
                    etEstado.hint = hintEstado
                    etRazon.hint = hintRazon
                    if(parametros[0]!="metas") {
                        etProgreso.hint = hintProgreso
                    } else {
                        etProgreso.hint = "Automatic field"
                    }
                    etAvance.hint = hintAvance
                    etMeta.hint = hintMeta

                    btnEdit.visibility = View.INVISIBLE
                    btnDelete.visibility = View.VISIBLE
                    btnUpdate.visibility = View.VISIBLE
                    btnCancel.visibility = View.VISIBLE
                    btnHide.visibility = View.GONE

                    etNombre.inputType = InputType.TYPE_CLASS_TEXT
                    etNombre.textColor = Color.parseColor("#244164")
                    etDescripcion.inputType = InputType.TYPE_CLASS_TEXT
                    etDescripcion.textColor = Color.parseColor("#244164")
                    etInicio.inputType = InputType.TYPE_CLASS_DATETIME
                    etInicio.textColor = Color.parseColor("#244164")
                    etFin.inputType = InputType.TYPE_CLASS_DATETIME
                    etFin.textColor = Color.parseColor("#244164")
                    etEstado.inputType = InputType.TYPE_CLASS_TEXT
                    etEstado.textColor = Color.parseColor("#244164")
                    etRazon.inputType = InputType.TYPE_CLASS_TEXT
                    etRazon.textColor = Color.parseColor("#244164")
                    if (parametros[0] != "metas") {
                        etProgreso.inputType = InputType.TYPE_CLASS_NUMBER
                        etProgreso.textColor = Color.parseColor("#244164")
                    } else {
                        etProgreso.textColor = Color.parseColor("#8B94A7")
                    }
                    etAvance.inputType = InputType.TYPE_CLASS_NUMBER
                    etAvance.textColor = Color.parseColor("#244164")
                    etMeta.inputType = InputType.TYPE_CLASS_NUMBER
                    etMeta.textColor = Color.parseColor("#244164")

                }

            btnDelete
                .setOnClickListener {
                    val identificadorUsuario = AuthUsuario.usuario?.email.toString()

                    val doc = infoIn[adapterPosition].takeLast(2)[1]

                    db.collection("Usuarios")
                        .document(identificadorUsuario)
                        .collection("${parametros[0]}")
                        .document(doc)
                        .delete()
                        .addOnSuccessListener {
                            Log.i("firebase-firestore", "Se eliminó el usuario con éxito")
                        }
                        .addOnFailureListener {
                            Log.i("firebase-firestore", "Falló: $it")
                        }

                    val actividad = getActivity()
                    actividad?.finish()
                    actividad?.startActivity(
                        Intent(
                            actividad?.baseContext,
                            actividad?.javaClass
                        )
                    )
                    actividad?.overridePendingTransition(R.anim.no_animation, R.anim.no_animation)
                }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListaAdaptador.MyViewHolder {
        val intemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.lista_view,
                parent,
                false
            )

        return MyViewHolder(intemView)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {

        if (infoIn[position].last() == "@@@new@@@") {
            holder.btnShow.performClick()
            holder.btnEdit.performClick()
            holder.btnUpdate.text = "Save"
            holder.btnCancel.setOnClickListener {
                val actividad = getActivity()
                actividad?.finish()
                actividad?.startActivity(
                    Intent(
                        actividad?.baseContext,
                        actividad?.javaClass
                    )
                )
                actividad?.overridePendingTransition(R.anim.no_animation, R.anim.no_animation)
            }
            holder.btnDelete.visibility = View.INVISIBLE
            holder.etEstado.text = parametros[1].toEditable()
        } else {

            if (parametros[0] == "metas") {
                if (infoIn[position][0] != "null") {
                    holder.etAvance.text = infoIn[position][0].toEditable()
                } else {
                    holder.etAvance.hint = ""
                }
                if (infoIn[position][1] != "null") {
                    holder.etDescripcion.text = infoIn[position][1].toEditable()
                } else {
                    holder.etDescripcion.hint = ""
                }
                if (infoIn[position][2] != "null") {
                    holder.etEstado.text = infoIn[position][2].toEditable()
                } else {
                    holder.etEstado.hint = ""
                }
                if (infoIn[position][3] != "null") {
                    holder.etFin.text = infoIn[position][3].toEditable()
                } else {
                    holder.etFin.hint = ""
                }
                if (infoIn[position][4] != "null") {
                    holder.etInicio.text = infoIn[position][4].toEditable()
                } else {
                    holder.etInicio.hint = ""
                }
                if (infoIn[position][5] != "null") {
                    holder.etMeta.text = infoIn[position][5].toEditable()
                } else {
                    holder.etMeta.hint = ""
                }
                if (infoIn[position][6] != "null") {
                    holder.etNombre.text = infoIn[position][6].toEditable()
                } else {
                    holder.etNombre.hint = ""
                }
                if (infoIn[position][7] != "null") {
                    holder.etProgreso.text = infoIn[position][7].toEditable()
                } else {
                    holder.etProgreso.hint = ""
                }
                if (infoIn[position][8] != "null") {
                    holder.etRazon.text = infoIn[position][8].toEditable()
                } else {
                    holder.etRazon.hint = ""
                }
            } else {
                if (infoIn[position][0] != "null") {
                    holder.etProgreso.text = infoIn[position][0].toEditable()
                } else {
                    holder.etProgreso.hint = ""
                }
                if (infoIn[position][1] != "null") {
                    holder.etDescripcion.text = infoIn[position][1].toEditable()
                } else {
                    holder.etDescripcion.hint = ""
                }
                if (infoIn[position][2] != "null") {
                    holder.etEstado.text = infoIn[position][2].toEditable()
                } else {
                    holder.etEstado.hint = ""
                }
                if (infoIn[position][3] != "null") {
                    holder.etFin.text = infoIn[position][3].toEditable()
                } else {
                    holder.etFin.hint = ""
                }
                if (infoIn[position][4] != "null") {
                    holder.etInicio.text = infoIn[position][4].toEditable()
                } else {
                    holder.etInicio.hint = ""
                }
                if (infoIn[position][5] != "null") {
                    holder.etNombre.text = infoIn[position][5].toEditable()
                } else {
                    holder.etNombre.hint = ""
                }
                if (infoIn[position][6] != "null") {
                    holder.etRazon.text = infoIn[position][6].toEditable()
                } else {
                    holder.etRazon.hint = ""
                }
            }
        }

    }

    fun getActivity(): Activity? {
        val activityThreadClass = Class.forName("android.app.ActivityThread")
        val activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null)
        val activitiesField: Field = activityThreadClass.getDeclaredField("mActivities")
        activitiesField.setAccessible(true)
        val activities = activitiesField.get(activityThread) as Map<Any, Any>
            ?: return null
        for (activityRecord in activities.values) {
            val activityRecordClass: Class<*> = activityRecord.javaClass
            val pausedField: Field = activityRecordClass.getDeclaredField("paused")
            pausedField.setAccessible(true)
            if (!pausedField.getBoolean(activityRecord)) {
                val activityField: Field = activityRecordClass.getDeclaredField("activity")
                activityField.setAccessible(true)
                return activityField.get(activityRecord) as Activity
            }
        }
        return null
    }

    override fun getItemCount(): Int {
        return infoIn.size
    }

    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
}