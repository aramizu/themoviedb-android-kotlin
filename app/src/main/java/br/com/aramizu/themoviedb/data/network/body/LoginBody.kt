package br.com.aramizu.themoviedb.data.network.body

class LoginBody(email: String, password: String) {

    private var email: String? = null
    private var password: String? = null

    init {
        this.email = email
        this.password = password
    }
}