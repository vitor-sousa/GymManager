package com.vitorsousa.gymmanager.domain.models

import android.net.Uri
import com.google.firebase.firestore.DocumentId

data class Exercicio(
    @DocumentId
    var exercicioId: String,
    var nome: String?,
    var imagem: String?,
    var observacoes: String?
) {
    constructor(): this("","", "", "")
}
