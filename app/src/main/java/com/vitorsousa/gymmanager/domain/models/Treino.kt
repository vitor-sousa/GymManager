package com.vitorsousa.gymmanager.domain.models

import com.google.firebase.Timestamp

data class Treino(
    var nome: String?,
    var descricao: String?,
    var data: Timestamp?,
    var exercicios: List<Exercicio>?,
    var usuario: String?
) {
    constructor() : this("", "", null, null, "")
}
