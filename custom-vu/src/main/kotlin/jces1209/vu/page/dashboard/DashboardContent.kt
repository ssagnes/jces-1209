package jces1209.vu.page.dashboard

interface DashboardContent {

    fun getDashboardsCount(): Int
    fun getDashboardsKeys(): Collection<String>

}
