package br.com.aramizu.themoviedb.presentation.ui.base

/**
 * Interfac to interact with FragNavController
 */
interface FragManagerListerner {
    fun pushFragment(fragment: BaseFragment)
    fun popFragment()
}
