package com.vitorsousa.gymmanager.domain.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class Treino(
    @DocumentId
    val treinoId: String,
    var nome: String?,
    var descricao: String?,
    var data: Timestamp?,
) {
    constructor() : this("","", "", null)
}
