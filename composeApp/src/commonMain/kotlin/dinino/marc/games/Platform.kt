package dinino.marc.games

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform