package com.example.proyectosegundob_moviles

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectosegundob_moviles.AuthUsuario.Companion.usuario
import com.example.proyectosegundob_moviles.Firestore.Companion.db
import com.example.proyectosegundob_moviles.dto.IngresoEgresoDTO
import com.example.proyectosegundob_moviles.dto.MetaDTO
import com.google.firebase.auth.FirebaseAuth
import com.jjoe64.graphview.DefaultLabelFormatter
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.GridLabelRenderer
import com.jjoe64.graphview.series.*
import java.text.SimpleDateFormat
import java.util.*


class Dashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        crearBarra()

        val usuarioLocal = FirebaseAuth.getInstance().currentUser
        if (usuarioLocal != null && !usuarioLocal!!.isAnonymous) {
            setearBalance()
            setearMetas()
        }

        findViewById<ImageView>(R.id.btn_masBalance)
            .setOnClickListener {
                startActivity(
                    Intent(
                        this,
                        Balance::class.java
                    )
                )
            }

        findViewById<ImageView>(R.id.btn_masMetas)
            .setOnClickListener {
                startActivity(
                    Intent(
                        this,
                        Metas::class.java
                    )
                )
            }

        findViewById<ImageView>(R.id.btn_update)
            .setOnClickListener {
                update()
            }
    }

    fun setearBalance() {
        var calendar: Calendar = Calendar.getInstance()

        val grBalance = findViewById<View>(R.id.gr_balance) as GraphView


        var balances = arrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
        val sdf = SimpleDateFormat("dd/MM/yyyy")

        db.collection("Usuarios").document(usuario!!.email).collection("ingresos").get()
            .addOnSuccessListener {
                for (document in it) {
                    calendar = Calendar.getInstance()
                    calendar.add(Calendar.MONTH, -5)
                    val ingreso = document.toObject(IngresoEgresoDTO::class.java)

                    val inicio = ingreso.fechaInicio
                    val fin = ingreso.fechaFin
                    if (fin != "null" && fin != "" && inicio != "null" && inicio != "") {
                        val fechaFin = sdf.parse(fin)
                        val fechaInicio = sdf.parse(inicio)
                        for ((i, _) in balances.withIndex()) {
                            val fechaActual = calendar.time
                            if (fechaActual < fechaFin && fechaActual > fechaInicio) {
                                balances[i] += ingreso.cantidad!!.toDouble()
                            }
                            calendar.add(Calendar.MONTH, 1)
                        }
                    } else if (ingreso.estado == "Active") {
                        for ((i, _) in balances.withIndex()) {
                            balances[i] += ingreso.cantidad!!.toDouble()
                        }
                    }
                }

                db.collection("Usuarios").document(usuario!!.email).collection("egresos").get()
                    .addOnSuccessListener { egresosSnap ->
                        for (document in egresosSnap) {
                            calendar = Calendar.getInstance()
                            calendar.add(Calendar.MONTH, -5)
                            val egreso = document.toObject(IngresoEgresoDTO::class.java)
                            val inicio = egreso.fechaInicio
                            val fin = egreso.fechaFin
                            if (fin != "null" && fin != "" && inicio != "null" && inicio != "") {
                                val fechaFin = sdf.parse(fin)
                                val fechaInicio = sdf.parse(inicio)
                                for ((i, _) in balances.withIndex()) {
                                    val fechaActual = calendar.time
                                    if (fechaActual < fechaFin && fechaActual > fechaInicio) {
                                        balances[i] -= egreso.cantidad!!.toDouble()
                                    }
                                    calendar.add(Calendar.MONTH, 1)
                                }
                            } else if (egreso.estado == "Active") {
                                for ((i, _) in balances.withIndex()) {
                                    balances[i] -= egreso.cantidad!!.toDouble()
                                }
                            }
                        }

                        calendar = Calendar.getInstance()
                        calendar.add(Calendar.MONTH, -6)
                        val months = arrayListOf<Double>()
                        for (bal in balances) {
                            months.add(calendar.time.time.toDouble())
                            calendar.add(Calendar.MONTH, 1)
                        }

                        val points = arrayOf(
                            DataPoint(months[0], balances[0]),
                            DataPoint(months[1], balances[1]),
                            DataPoint(months[2], balances[2]),
                            DataPoint(months[3], balances[3]),
                            DataPoint(months[4], balances[4]),
                            DataPoint(months[5], balances[5])
                        )

                        val seBalance = LineGraphSeries(
                            points
                        )

                        seBalance.isDrawBackground = true

                        seBalance.setOnDataPointTapListener { _, dataPoint ->
                            var newValue = Date(dataPoint.x.toLong())
                            val sdf = SimpleDateFormat("MM/yyyy")
                            val retorno =
                                sdf.format(newValue)
                            val prompt = Toast.makeText(
                                this,
                                "Date: $retorno, Balance: ${dataPoint.y}",
                                Toast.LENGTH_SHORT
                            )
                            prompt.setGravity(
                                Gravity.CENTER_HORIZONTAL,
                                grBalance.x.toInt(),
                                grBalance.y.toInt()
                            )
                            prompt.show();
                        }

                        grBalance.addSeries(
                            seBalance
                        )

                        val seBalancePoints = PointsGraphSeries(
                            points
                        )

                        seBalancePoints.size = 15.toFloat()

                        grBalance.addSeries(
                            seBalancePoints
                        )

                        grBalance.gridLabelRenderer.gridColor = Color.parseColor("#A9B1C5")
                        grBalance.gridLabelRenderer.gridStyle = GridLabelRenderer.GridStyle.BOTH

                        grBalance.gridLabelRenderer.numHorizontalLabels = 2

                        grBalance.viewport.setMinX(months[0]);
                        grBalance.viewport.setMaxX(months[5]);
                        grBalance.viewport.isXAxisBoundsManual = true;

                        grBalance.gridLabelRenderer.setHumanRounding(false);

                        grBalance.gridLabelRenderer.labelFormatter =
                            object : DefaultLabelFormatter() {
                                override fun formatLabel(value: Double, isValueX: Boolean): String {
                                    return if (isValueX) {
                                        ""
                                    } else {
                                        ""
                                    }
                                }
                            }

                    }.addOnFailureListener {
                        Log.e("firestore", "Falló egresos: $it")
                    }
            }.addOnFailureListener {
                Log.e("firestore", "Falló ingresos: $it")
            }

    }

    fun setearMetas() {
        val grMetas = findViewById<View>(R.id.gr_metas) as GraphView

        var metasPoint = arrayListOf<Pair<Double, String>>()

        db.collection("Usuarios").document(usuario!!.email).collection("metas").get()
            .addOnSuccessListener {
                var i = 0
                for (document in it) {
                    val meta = document.toObject(MetaDTO::class.java)
                    if (meta.estado == "Active" && i < 5) {
                        metasPoint.add(
                            Pair(
                                (meta.progreso!!.dropLast(1).toDouble() * 100).toInt()
                                    .toDouble() / 100,
                                meta.nombre!!
                            )
                        )
                    }
                    i++
                    if (i == 5) {
                        break
                    }
                }

                for ((i, meta) in metasPoint.withIndex()) {
                    val seMetas = BarGraphSeries(
                        arrayOf(DataPoint((i + 1).toDouble(), meta.first)),
                    )
                    seMetas.setOnDataPointTapListener { _, dataPoint ->
                        val prompt = Toast.makeText(
                            this,
                            "Goal: ${meta.second}, Progress: ${dataPoint.y.toInt()}%",
                            Toast.LENGTH_SHORT
                        )
                        prompt.setGravity(
                            Gravity.CENTER_HORIZONTAL,
                            grMetas.x.toInt(),
                            grMetas.y.toInt()
                        )
                        prompt.show();
                    }
                    seMetas.color = Color.rgb((i + 1) * 255 / 4, (i + 1) * 255 / 6, (i + 1) * 100)
                    grMetas.addSeries(
                        seMetas
                    )
                }
                graficarMetas(grMetas)

            }.addOnFailureListener {
                Log.e("firestore", "Falló: $it")
            }
    }

    fun graficarMetas(grMetas: GraphView) {
        grMetas.gridLabelRenderer.gridColor = Color.parseColor("#A9B1C5")
        grMetas.gridLabelRenderer.gridStyle = GridLabelRenderer.GridStyle.HORIZONTAL

        grMetas.viewport.setMinX(0.0);
        grMetas.viewport.setMaxX(6.0);
        grMetas.viewport.isXAxisBoundsManual = true;

        grMetas.viewport.setMinY(-10.0);
        grMetas.viewport.setMaxY(100.0);
        grMetas.viewport.isYAxisBoundsManual = true;

        grMetas.gridLabelRenderer.isHorizontalLabelsVisible = false

        grMetas.gridLabelRenderer.labelFormatter = object : DefaultLabelFormatter() {
            override fun formatLabel(value: Double, isValueX: Boolean): String {
                return if (isValueX) {
                    super.formatLabel(value, isValueX)
                } else {
                    super.formatLabel(value, isValueX) + "%"
                }
            }
        }
    }

    fun crearBarra() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val bar = TopBar()
        val argumentos = Bundle()
        argumentos.putString("titulo", "Dashboard")
        bar.arguments = argumentos

        fragmentTransaction.replace(R.id.rl_topBar, bar)
        fragmentTransaction.commit()
    }

    override fun finish() {
        super.finishAffinity()
        overridePendingTransition(R.anim.slide_down, R.anim.slide_down)
    }

    fun update() {
        finish()
        startActivity(
            Intent(
                this,
                Dashboard::class.java
            )
        )
        overridePendingTransition(R.anim.no_animation, R.anim.no_animation)
    }
}
