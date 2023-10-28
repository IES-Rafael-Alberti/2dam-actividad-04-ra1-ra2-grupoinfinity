package com.evilrey844.actividad04

@Suppress("SpellCheckingInspection")
class Calculo1 {

    var num1: String = ""
    var num2: String = ""
    var resultado: Float = 0F
    var operacion: Char = ' '
    var boolOperacion : Boolean = false //true -> existe una operacion en vigor / false -> se ha borrado
    var boolResultado : Boolean = false //true -> se ha pulsado igual / false -> no
    var reseteoPantalla : Boolean = true //true porque no hay nada escrito / false-> cuando lo haya

    /**
     * Llamo a todos los métodos según el char que lea pasado con un when
     * operacion --> ponerlo en los métodos
     */
    fun calcular() {
        when (operacion) {
            '+' -> suma()
            '-' -> resta()
            '*' -> multiplicacion()
            '/' -> division()
            else -> {
                // Aquí puedes manejar el caso en que la operación no sea válida
            }
        }
    }

    /**
     * El metodo suma hace la suma de num1 y num2
     */
    private fun suma() {
        resultado = num1.toFloat() + num2.toFloat()
    }

    /**
     * El metodo resta hace la resta de num1 y num2
     */
    private fun resta() {
        resultado = num1.toFloat() - num2.toFloat()
    }

    /**
     * El metodo multiplicacion hace la multiplicación de num1 y num2
     */
    private fun multiplicacion() {
        resultado = num1.toFloat() * num2.toFloat()
    }

    /**
     * El metodo division hace la division de num1 y num2
     * mientras sea distinto de 0 porque no se puede dividir por 0
     * si se divide por 0 yo he puesto que el resultado sea 0
     */
    private fun division() {
        if(num2.toInt() != 0)
            resultado = num1.toFloat() / num2.toFloat()
        else {
            resultado = 0F
            operacion = ' '
        }
    }

    /**
     * Este método determina que numero se utilizará en la operación, antes de pulsar el operador
     * También si es (10) el número que se le pasa equivale al decimal
     *
     * @param numero, le pasamos un entero para saber de qué número se trata
     */
    fun setDigito(numero: Int){
        if (numero < 10){
            if (this.reseteoPantalla&&!this.boolResultado)
                this.num1 += numero.toString()
            else
                this.num2 += numero.toString()
        }
        else {
            if (this.reseteoPantalla) {
                //Agregamos el decimal a la cadena
                if (this.num1.contains('.')||this.num1.isEmpty())
                    this.num1 +=""
                else if(this.boolResultado)
                    this.num1+=""
                else
                    this.num1 +="."
            }
            else {
                //Y con el número 2
                if (this.num2.contains('.')||this.num2.isEmpty())
                    this.num2 += ""
                else
                    this.num2 += "."
            }
        }
    }
}