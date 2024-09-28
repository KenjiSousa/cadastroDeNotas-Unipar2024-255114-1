package com.example.trabalho_202409_kenji_2551141

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.GridView
import android.widget.TextView
import androidx.core.view.*
import com.google.android.material.textfield.TextInputEditText
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<GridView>(R.id.gridResultado).isVisible = false

        findViewById<Button>(R.id.btConfirmar).setOnClickListener { confirmar() }

        findViewById<Button>(R.id.btLimparRes).setOnClickListener {
            val editable = Editable.Factory.getInstance().newEditable("")

            for (input in arrayOf<TextInputEditText>(
                findViewById(R.id.cadastro_nome),
                findViewById(R.id.cadastro_email),
                findViewById(R.id.cadastro_idade),
                findViewById(R.id.cadastro_disciplina),
                findViewById(R.id.cadastro_nota1),
                findViewById(R.id.cadastro_nota2)
            )) {
                input.text = editable
                input.error = null
            }

            findViewById<GridView>(R.id.gridResultado).isVisible = false
            findViewById<TextView>(R.id.res_final).text = ""
        }
    }

    private fun confirmar() {
        var erroEncontrado = false

        fun setaErro(input: TextInputEditText, mensagem: String) {
            input.error = mensagem

            if (!erroEncontrado) { // foca no primeiro item que deu erro
                input.requestFocus()
            }

            erroEncontrado = true
        }

        fun validaInput(validacao: Boolean, input: TextInputEditText, mensagem: String) {
            if (validacao) return

            setaErro(input, mensagem)
        }

        val inputNome: TextInputEditText = findViewById(R.id.cadastro_nome)
        val txtNome: String = inputNome.text.toString()
        validaInput(txtNome.isNotEmpty(), inputNome, "O nome é obrigatório")

        val inputEmail: TextInputEditText = findViewById(R.id.cadastro_email)
        val txtEmail: String = inputEmail.text.toString()
        // https://regexr.com/3e48o
        validaInput(
            Regex("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}\$").matches(txtEmail),
            inputEmail,
            "E-mail inválido"
        )

        val inputIdade: TextInputEditText = findViewById(R.id.cadastro_idade)
        val txtIdade: String = inputIdade.text.toString()
        var idade = 0
        try {
            idade = txtIdade.toInt()
            validaInput(idade > 0, inputIdade, "Idade deve ser positiva")
        } catch (e: NumberFormatException) {
            setaErro(inputIdade, "Idade inválida")
        }

        val inputDisciplina: TextInputEditText = findViewById(R.id.cadastro_disciplina)
        val txtDisciplina: String = inputDisciplina.text.toString()
        validaInput(txtDisciplina.isNotEmpty(), inputDisciplina, "A disciplina é obrigatória")

        val inputNota1: TextInputEditText = findViewById(R.id.cadastro_nota1)
        val txtNota1: String = inputNota1.text.toString()
        validaInput(txtNota1.isNotEmpty(), inputNota1, "A nota é obrigatória")
        var nota1 = 0.0
        try {
            nota1 = txtNota1.toDouble()
            validaInput(nota1 in 0.0..10.0, inputNota1, "Nota deve ser entre 0.0 e 10.0")
        } catch (e: NumberFormatException) {
            setaErro(inputNota1, "Nota inválida")
        }

        val inputNota2: TextInputEditText = findViewById(R.id.cadastro_nota2)
        val txtNota2: String = inputNota2.text.toString()
        validaInput(txtNota2.isNotEmpty(), inputNota2, "A nota é obrigatória")
        var nota2 = 0.0
        try {
            nota2 = txtNota2.toDouble()
            validaInput(nota2 in 0.0..10.0, inputNota2, "Nota deve ser entre 0.0 e 10.0")
        } catch (e: NumberFormatException) {
            setaErro(inputNota2, "Nota inválida")
        }

        // Exibe resultados
        if (!erroEncontrado) {
            findViewById<GridView>(R.id.gridResultado).isVisible = true

            findViewById<TextView>(R.id.res_nome).text = txtNome
            findViewById<TextView>(R.id.res_email).text = txtEmail
            findViewById<TextView>(R.id.res_idade).text = idade.toString()
            findViewById<TextView>(R.id.res_disciplina).text = txtDisciplina
            findViewById<TextView>(R.id.res_nota1).text = String.format("%.2f", nota1)
            findViewById<TextView>(R.id.res_nota2).text = String.format("%.2f", nota2)

            val media = (nota1 + nota2) / 2
            findViewById<TextView>(R.id.res_media).text = String.format("%.2f", media)

            if (media < 6) {
                findViewById<TextView>(R.id.res_final).text = "Reprovado"
            } else {
                findViewById<TextView>(R.id.res_final).text = "Aprovado"
            }
        }
    }
}
