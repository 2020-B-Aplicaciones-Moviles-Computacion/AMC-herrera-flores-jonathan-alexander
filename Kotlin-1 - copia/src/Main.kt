
import java.util.*

fun main() {
    println("Hola mundo");
    //Ejemplo JAVA Int edad =31
    var edadProfesor = 31;
    var sueldoProfesor = 31.23;
    //DuckTyping
    var edadCachorro: Int;

    //Mutables
    edadCachorro = 4;
    edadCachorro = 5;

    //Inmutables
    val numeroCedula = 1782934472;
    //numeroCedula = 0;

    //Tipos de variables

    val nombreProfesor = 12.2;
    val sueldo: Double = 12.2;
    val estadoCivil = 'S';
    val fechaNacimiento = Date();

    //condicionales
    if (sueldo == 12.2) {
        //verdadero
    } else {

    }

    when (sueldo) {
        12.2 -> { //Inicio de bloque código
            println("Sueldo normal")
        }//Fin de bloque de código
        -12.2 -> println("Sueldo negativo")
        else -> println("sueldo no reconocido")
    }

    val sueldoMayorAlEstablecido: Boolean = if (sueldo == 12.2) true else false;
    //condicion ? bloque-true : bloque-false

    imprimirNombre("Adrian")
    calcularSueldo(1000.0)
    calcularSueldo(1000.0, 14.0)
    calcularSueldo(1000.0, 14.0, 3)

    //Para enviar variables específicas, manteniendo las otras por defecto saltando parámetros sin importar orden
    calcularSueldo(
            1000.0,
            calculoEspecial = 3,    //parámetros nombrados
            tasa = 14.0             //no importa el orden de los parámetros nombrados
    )

    //Arreglos

    //arreglo Inmutable
    val arregloInmutable: Array<Int> = arrayOf(1, 2, 3);
    //arreglo Mutable
    //ctrl + alt + L para identación
    val arregloMutable: ArrayList<Int> = arrayListOf(1, 2, 3);
    println(arregloMutable)
    arregloMutable.add(4);
    println(arregloMutable)

    //-------------------------------------------------------------------
    //Métodos útiles para el manejo de arreglos

    //Para cualquier tipo de arreglo

    //Operador foreach
    val respuestaFE: Unit = arregloMutable.forEach { valorIteracion -> //Sin definir el tipo de dato porque vuelve a hacer el ducktyping
        println("valor: ${valorIteracion}")
    }

    //Operador ForEachIndexed
    val respuestaFEI = arregloMutable.forEachIndexed { index, i ->
        println("valor: ${i} Indice: ${index}")
    }

    //Operador Map, muta el arreglo
    //1) envía el nuevo valorIteration
    //2) devuelve el arreglo con los valores modificados
    val respuestaMap: List<Int> = arregloMutable.map { valorActualIteracion ->
        return@map valorActualIteracion * 10
    }
    println(respuestaMap);

    arregloMutable.map { valorActualIteracion ->
        valorActualIteracion * 10
    }

    //Filter -> Filtra el arreglo
    //1) devolver una expresión (True o False)
    //2) Nuevo arreglo filtrado
    val respuestafilter = arregloMutable.filter { valorActualIteracion ->
        val mayoresACinco = valorActualIteracion > 5000
        return@filter mayoresACinco
    }
    println(respuestafilter);
    arregloMutable.filter { i -> i > 5 };

    arregloMutable.filter { it > 5 }

    //any All - > Revisar una condición dentro del arreglo
    //OR - AND
    //OR (FALSO - TODOS TIENEN QUE SER FLASO)
    //OR (TRUE - UNO ES TRUE YA ES TRUE)
    //AND (FALSO - SI UNO ES FALSO YA ES FALSO)
    //AND (TRUE - TODOS SON TRUE ES TRUE)

    //Operadores OR = Any, AND = All
    val respuestaAny = arregloMutable
            .any { valorActualIterator ->
                return@any (valorActualIterator >= 5) //True or False
            }
    println()
    println(arregloMutable)
    println(respuestaAny)

    val respuestaAll = arregloMutable
            .all { valorActualIterator ->
                return@all (valorActualIterator >= 5) //True or False
            }
    println()
    println(arregloMutable)
    println(respuestaAll)

    //REDUCE
    // 1) Devuelve el acumulado
    // 2) en qué valor empieza
    // (1,2,3,4,5)
    val respuestaReduce = arregloMutable
            .reduce { acumulado, valorActualIterator ->
                return@reduce acumulado + valorActualIterator

            }
    println()
    println(arregloMutable)
    println(respuestaReduce)

    val respuestaReduceFold = arregloMutable
            .fold(
                    100,
                    { acumulado, valorActualIterator ->
                        return@fold acumulado - valorActualIterator
                    }
            )
    println()
    println(arregloMutable)
    println(respuestaReduceFold)

    val vidaActual = arregloMutable
            .map { it * 10.5 } //arreglo
            .filter { it > 20 } //arreglo
            .fold(100.00, { acc, i -> acc - i }) //valor
            .also { println(it) } //mostrar el valor
    println("Valor de vida actual ${vidaActual}")

    println()
    val ejemploUno = Suma (1, 2, 3)
    val ejemploDos = Suma(1,null,3)
    val ejemploTres = Suma(null, null, null)
    println(ejemploUno.sumar())
    println(Suma.historialsuma)
    println(ejemploDos.sumar())
    println(Suma.historialsuma)
    println(ejemploTres.sumar())
    println(Suma.historialsuma)
}

fun imprimirNombre(nombre: String) {
    println("Nombre: ${nombre}");
}

fun calcularSueldo(
        sueldo: Double, //Requerido
        tasa: Double = 12.00, //Opcional con valor por defecto 12.00
        calculoEspecial: Int? = null //cálculo especial es un entero con valor inicial de null
): Double {
    //return sueldo * (100/tasa) * calculoEspecial
    if (calculoEspecial == null) {
        return sueldo * (100 / tasa)
    } else {
        return sueldo * (100 / tasa) * calculoEspecial
    }
}

abstract class NumerosJava {
    protected val numeroUno: Int
    private val numeroDos: Int

    constructor(uno: Int, dos: Int) { //Bloque de código del constructor primario
        numeroUno = uno
        numeroDos = dos
        println(numeroUno)
        println(numeroDos)
    }
}

abstract class Numeros( //val numerosNumero = Numeros(1,2) así se utiliza
        protected var numeroUno: Int,
        protected var numeroDos: Int) {
    init {
        //println(numeroUno)
        //println(numeroDos)
    }
}

class Suma(
        uno: Int,
        dos: Int,
        protected var tres: Int
        //cuatro: Int
) : Numeros(uno, dos) {
    init {
        //println("Constructor primario init")
    }

    constructor(
            uno: Int,
            dos: Int?,
            tres: Int) : this(
            uno,
            if (dos == null) 0 else dos,
            tres
    ) {

    }

    constructor(
            uno: Int?,
            dos: Int?,
            tres: Int?) : this(
            if (uno == null) 0 else uno,
            if (dos == null) 0 else dos,
            if (tres == null) 0 else tres) {

    }

    public fun sumar(): Int {
        return this.numeroUno + this.numeroDos
        val total: Int = this.numeroUno + this.numeroDos
        Suma.agregarHistorial(total)
        return total
    }

    companion object {
        val historialsuma = arrayListOf<Int>()
        fun agregarHistorial(numero:Int){
            this.historialsuma.add(numero)
        }
    }
}