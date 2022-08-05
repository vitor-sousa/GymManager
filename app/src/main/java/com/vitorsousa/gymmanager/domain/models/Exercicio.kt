package com.vitorsousa.gymmanager.domain.models

data class Exercicio(
    var nome: String?,
    var imagem: String?,
    var observacoes: String?
) {
    constructor(): this("", "", "")
}
