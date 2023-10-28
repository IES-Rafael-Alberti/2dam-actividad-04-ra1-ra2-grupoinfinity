package com.evilrey844.actividad04

import android.content.Context
import android.widget.Toast


/**
 * Clase se encarga de realizar cálculos básicos como sumar, restar, multiplicar y dividir.
 * @property context Contexto en el que se mostrarán los Toasts.
 */
@Suppress("SpellCheckingInspection")
class Calculo2 (private val context: Context) {
    var num1: String = ""
    var num2: String = ""
    var operacion: String = ""
    var resultado: Double = 0.0
    var fase: Int = 0 // Controla la fase en la que está mi calculadora (de 0 a 3).
    private var resultadoCalculado = false // Controlo que el botón C quede bloqueado cuando obtengo Resultado.
    private var operacionRecienIntroducida = false


    /**
     * Los siguientes funciones se encargan de crear la lógica operacional de la calculadora.
     */
    private fun sumar() {
        resultado = num1.toDouble() + num2.toDouble()
    }

    private fun restar() {
        resultado = num1.toDouble() - num2.toDouble()
    }

    private fun multiplicar() {
        resultado = num1.toDouble() * num2.toDouble()
    }

    private fun dividir() {
        resultado = num1.toDouble() / num2.toDouble()
    }

    /**
     * Muestra un Toast con el mensaje proporcionado.
     *
     * @param mensaje El mensaje a mostrar en el Toast.
     */
    private fun mostrarToast(mensaje: String) {
        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
    }

    /**
     * La función calcular maneja la situación en la que se dispara el Toast. Además, euipara los símbolos Strings
     * de operaciones a los métodos de sumar, restar, multiplicar y dividir. Añado un condicional para comprobar que
     * el decimal sea 0, si es 0, convierto el número a entero antes de convertirlo a String. Por otra parte, al haberse
     * pulsado el operador, la calculadora pasa a fase 3 (Inserte num2).
     */
    fun calcular() {
        // Si num1, num2 u operacion están vacíos, muestro un toast y termino la función.
        if (num1.isEmpty() || num2.isEmpty() || operacion.isEmpty()) {
            mostrarToast("Debe introducir 2 números y una operación para mostrar un resultado.")
            return
        }
        // Realizo la operación correspondiente según el valor de 'operacion'.
        when (operacion) {
            "+" -> sumar()
            "-" -> restar()
            "x" -> multiplicar()
            "/" -> dividir()
        }
        // Compruebo si el resultado es un número entero para descartar decimales "0" y mostrar el resultado bonito.
        // Si el resultado es un número entero, convierto el resultado a Int y luego a String.
        num1 = if (resultado % 1 == 0.0) resultado.toInt().toString()

        // Si no, convierto el resultado directamente a String.
        else resultado.toString()

        // Reinicio num2, operacion y fase para la próxima operación.
        num2 = ""
        operacion = ""
        fase = 3 // Se ha realizado una operación, si se presiona un número la calculadora se resetea.
        resultadoCalculado = true // Añadido para restablecer cuando se presiona un número.
    }

    /**
     * Añade el número pulsado a 'num1', 'num2' o resetea la calculadora dependiendo de la fase
     * en la que se encuentre.
     *
     * @param num El número pulsado por el usuario.
     */
    fun setNumClicked(num: String) {
        resultadoCalculado = false
        when (fase) {
            0 -> num1 += num
            2 -> num2 += num
            3 -> {
                resetear()
                num1 += num
                fase = 0 // Esperando a num1
            }
        }
    }

    /**
     * Comprueba que 'num1' no esté vacío. Si es así, la calculadora pasa a fase 2 (esperando a 'num2').
     *
     * @param oper La operación seleccionada por el usuario.
     */
    fun setOperaciones(oper: String) {
        resultadoCalculado = false
        if (num1.isNotEmpty()) {
            if (operacion.isNotEmpty() && num2.isNotEmpty()) {
                calcular()
            }
            operacion = oper
            fase = 2 // Esperando a num2
        }
    }

    /**
     * Todas las variables quedan vacias cuando se llama a esta función. Calculadora pasa a fase 0,
     * (espera a num1). Llamo a este método cuando quiero resetear.
     */
    fun resetear() {
        num1 = ""
        num2 = ""
        operacion = ""
        resultado = 0.0
        fase = 0
        operacionRecienIntroducida = false
    }

    /**
     * Añade un punto decimal al número actual si aún no tiene uno.
     * Si la calculadora está en la fase 0 (esperando a 'num1') y 'num1' no contiene un punto, se añade un punto a 'num1'.
     * Si la calculadora está en la fase 2 (esperando a 'num2') y 'num2' no contiene un punto, se añade un punto a 'num2'.
     */
    fun setPuntoClicked() {
        if (fase == 0 && !num1.contains(".")){
            num1 += "."
        } else if (fase == 2 && !num2.contains(".")) {
            num2 += "."
        }
    }

    /**
     * Método `botonLimpiarC` que se llama cuando se pulsa el botón C.
     * Borra los dígitos uno a uno del número actual.
     * Si no hay más dígitos para borrar, borra la operación.
     * Si no hay operación para borrar, borra los dígitos del primer número.
     * Si no hay nada que eliminar, se emite un mensaje tipo toast "No hay nada más que borrar".
     */
    fun botonLimpiarC() {
        if (resultadoCalculado) { // Si se ha calculado un resultado
            mostrarToast("No hay nada más que borrar.")
        } else {
            if (num2.isNotEmpty()) {
                num2 = num2.dropLast(1)
            } else if (operacion.isNotEmpty()) {
                operacion = ""
                fase = 0 // Esperando a num1
            } else if (num1.isNotEmpty()) {
                num1 = num1.dropLast(1)
            } else {
                mostrarToast("No hay nada más que borrar.")
            }
        }
    }
}
