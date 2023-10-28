package com.evilrey844.actividad04

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import java.text.DecimalFormat

@Suppress("SpellCheckingInspection")
class Calculadora1 : AppCompatActivity() {

    private lateinit var textView: TextView //Este parámetro servirá para modificar lo que aparece en textView
    private lateinit var btnNum: ArrayList<Button> //Este parámetro sirve para modificar la acción del botón almacenados en la lista
    private lateinit var buttonplus: Button
    private lateinit var buttonmenos: Button
    private lateinit var buttonmultiplicar: Button
    private lateinit var buttondividir: Button
    private lateinit var buttonigual: Button
    private lateinit var buttondelete: Button
    private lateinit var buttonce: Button
    private var calculo = Calculo1() //Objeto calculo vacío
    private val df = DecimalFormat("#.##") //Formato decimal para trabajar con él
    private lateinit var btnVolver: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculadora1)
        textView = findViewById(R.id.textView)

        // Botón para volver a la pantalla de inicio
        btnVolver = findViewById(R.id.buttonVolverKike)
        btnVolver.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Cambio el color de la barra de estado a negro para pulir diseño.
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.black)

        buttonplus = findViewById(R.id.buttonplus)
        buttonmenos = findViewById(R.id.buttonmenos)
        buttonmultiplicar = findViewById(R.id.buttonmultiplicar)
        buttondividir = findViewById(R.id.buttondividir)
        buttonigual = findViewById(R.id.buttonigual)
        buttonce = findViewById(R.id.buttonce)
        buttondelete = findViewById(R.id.buttondelete)

        textView.text = ""


        /**
         * Cada vez que pulsemos los botones se realiza una acción
         * es lo mismo de los operadores, pero los botones están almacenados
         * en una función para mejorar la optimización
         */
        inicializaBtnNum()

        //Llamamos al método creado para que se realicen los eventos
        inicializaListeners()

    }

    /**
     * En este método se almacena en el array los findViewbyID de cada número
     * para llamarlos desde el OnCreate
     */

    private fun inicializaBtnNum() {
        btnNum = ArrayList()
        btnNum.add(findViewById(R.id.button0))
        btnNum.add(findViewById(R.id.button1))
        btnNum.add(findViewById(R.id.button2))
        btnNum.add(findViewById(R.id.button3))
        btnNum.add(findViewById(R.id.button4))
        btnNum.add(findViewById(R.id.button5))
        btnNum.add(findViewById(R.id.button6))
        btnNum.add(findViewById(R.id.button7))
        btnNum.add(findViewById(R.id.button8))
        btnNum.add(findViewById(R.id.button9))
        btnNum.add(findViewById(R.id.buttondec))
    }

    /**
     * Establecemos los eventos para que cada vez que pulsemos los botones realice una acción
     */
    private fun inicializaListeners() {
        for (i in 0..<btnNum.count()) { //Usamos el for para recorrer la lista de botones
            btnNum[i].setOnClickListener { setNumClicked(i) }
        }

        buttonplus.setOnClickListener {
            asignarOperacion('+')
        }
        buttonmenos.setOnClickListener {
            asignarOperacion('-')
        }
        buttonmultiplicar.setOnClickListener {
            asignarOperacion('*')
        }
        buttondividir.setOnClickListener {
            asignarOperacion('/')
        }

        /**
         * El botón igual es el más complejo ya que realiza toda la operacion si se cumplen las condiciones correctas
         * por ello creamos un método que llame al ser pulsado el igual
         */
        buttonigual.setOnClickListener { resultClicked() }

        //Al pulsar CE volvemos a reiniciar la calculadora

        buttonce.setOnClickListener { reiniciarCalculadora() }

        //Al pulsar < borramos el último número si hay en la calculadora
        buttondelete.setOnClickListener { borrarNum() }
    }

    /**
     * En este método insertamos los numeros según pulsemos los botones
     *
     * @param numero, del número pulsado del 1 al 9 o 10 si se ha pulsado decimal
     */
    private fun setNumClicked(numero: Int) {
        calculo.setDigito(numero)

        //Mostramos info actualizada en los TextView de la app
        if (calculo.reseteoPantalla) {
            muestraValor(calculo.num1)
        } else {
            muestraValor(calculo.num2)
        }
    }

    /**
     * En este método asignamos la operacion al char que hemos creado
     *
     * @param op, el char que le pasamos según la operación que hemos pulsado
     */
    private fun asignarOperacion(op: Char) {
        if (calculo.reseteoPantalla) {
            //Acciones cuando estamos introduciendo el primer número.

            if (calculo.operacion != ' ' && calculo.num1 == "") {
                //Almacenamos el resultado anterior en el num1 para el siguiente cálculo.
                calculo.num1 = df.format(calculo.resultado).toString()
            }

            //Asignamos el operador pasado por parámetro al objeto calculo, mostramos info en pantalla y actualizamos los datos
            calculo.operacion = op
            muestraValor(calculo.operacion.toString())
            calculo.num2 = ""
            calculo.reseteoPantalla = false
        } else if (calculo.num2 == "") {
            //Si se introduce una operación y no existe el segundo número la nueva operación reemplaza la anterior.

            calculo.operacion = op
            //Actualizamos la pantalla.
            muestraValor(calculo.operacion.toString())
        } else {
            calculo.calcular()

            //Mostramos en pantalla el resultado
            muestraValor(df.format(calculo.resultado).toString())

            //Actualizamos las características del objeto calculo, para seguir realizando operaciones
            calculo.num1 = df.format(calculo.resultado).toString()
            calculo.operacion = op
            calculo.num2 = ""
        }
        calculo.boolOperacion = true //Señalamos que ya hay una operación en curso
        calculo.boolResultado =
            false //Lo señalamos para después de pulsar igual si se pulsa un operador y queremos borrar el num2

    }


    /**
     * El botón igual es el más complejo, este método realiza la acción al pulsar el botón
     * y si al pulsar el operador alguno de los números está vacío, te muestra el mensaje de error
     * con el toast llamando a mostrar Mensaje
     */
    private fun resultClicked() {
        if (!calculo.reseteoPantalla && calculo.num2 != "") {

            calculo.calcular()

            //Mostramos en pantalla el resultado
            muestraValor(df.format(calculo.resultado).toString())

            //Actualizamos los datos en calculo.
            calculo.num1 = df.format(calculo.resultado).toString()
            calculo.num2 = ""
            calculo.operacion = ' '
            calculo.reseteoPantalla = true
            calculo.boolResultado =
                true //Se introduce en el programa para mejorar el funcionamiento de DELETE
        } else {
            mostrarmensaje("Debe introducir 2 números y una operación para mostrar un resultado")
        }
    }

    /**
     * Acciones a realizar al pulsar el botón <.
     * Solo se ejecutará si hay número en pantalla si no, dará un mensaje de error.
     * Borra el último número pulsado y el último operador pulsado, si se llega a el después
     * de borrar num2
     * Además dará un mensaje de error si hay un resultado en pantalla, ya que no queremos borrar
     * el resultado
     */
    private fun borrarNum() {
        if (calculo.num1.isEmpty() && !calculo.boolResultado) {
            mostrarmensaje("No existe nada para borrar") //Si no hay nada para borrar salta este error
            //Además inicializamos las características del objeto calc. para solucionar posibles errores
            reiniciarCalculadora()

        } else if (calculo.num1.isNotEmpty() && !calculo.boolOperacion && !calculo.boolResultado) {
            calculo.num1 =
                calculo.num1.dropLast(1) //Eliminamos el último número llamando a dropLast
            muestraValor(calculo.num1)

            //Seguimos borrando hasta borrarlo todo, borramos el operador
        } else if (calculo.num2.isEmpty() && !calculo.boolResultado) {
            calculo.operacion = ' ' //Reiniciamos el valor de la operación
            muestraValor(calculo.num1)
            calculo.boolOperacion = false //indicamos que ya no hay operador
            calculo.reseteoPantalla = true

        } else if (calculo.num2.isNotEmpty() && !calculo.boolResultado) {
            //Hacemos lo mismo de num1 pero con num2
            //--> Pero si es el último número que borra aparecerá el operador en pantalla
            if (calculo.num2.length == 1) {
                calculo.num2 = calculo.num2.dropLast(1)
                muestraValor(calculo.operacion.toString())
            } else {
                calculo.num2 = calculo.num2.dropLast(1)
                muestraValor(calculo.num2)
            }


        } else {
            //Si se ha pulsado el igual sale el mensaje de Error llamando al método donde está el Toast
            mostrarmensaje("No se puede borrar el resultado")
        }
    }

    /**
     * Mostramos la información en componentest TextView textPantalla.
     *
     * @param pantalla info a mostrar en textView
     */
    private fun muestraValor(pantalla: String) {
        textView.text = getString(R.string.textPantalla, pantalla)
    }

    /**
     * Reiniciamos la calculadora y ponemos todos sus valores a 0 en este método
     */
    private fun reiniciarCalculadora() { //Todos los valores se vuelven a 0 y la pantalla se limpia
        muestraValor("")
        calculo.reseteoPantalla = true
        calculo.num1 = ""
        calculo.num2 = ""
        calculo.resultado = 0F
        calculo.operacion = ' '
        calculo.boolOperacion = false
        calculo.boolResultado = false
    }

    /**
     * Mostramos el mensaje TOAST (emergente) si cumple la condición que le hemos pasado antes
     *
     * @param mensaje de error que le pasamos por parámetro
     */
    private fun mostrarmensaje(mensaje: String) {
        Toast.makeText(
            applicationContext,
            mensaje,
            Toast.LENGTH_SHORT
        ).show()
    }

}