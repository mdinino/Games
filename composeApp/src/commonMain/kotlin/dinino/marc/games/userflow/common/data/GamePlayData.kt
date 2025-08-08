package dinino.marc.games.userflow.common.data

import kotlinx.serialization.Serializable

@Serializable
sealed interface GamePlayData<out GAME_OVER_DETAILS: Any> {
    @Serializable
    class Normal<out GAME_OVER_DETAILS: Any>: GamePlayData<GAME_OVER_DETAILS> {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || this::class != other::class) return false
            return true
        }

        override fun hashCode(): Int {
            return this::class.hashCode()
        }
    }

    @Serializable
    class Paused<out GAME_OVER_DETAILS: Any>: GamePlayData<GAME_OVER_DETAILS> {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || this::class != other::class) return false
            return true
        }

        override fun hashCode(): Int {
            return this::class.hashCode()
        }
    }


    @Serializable
    data class GameOver<out GAME_OVER_DETAILS: Any>(
        /**
         * Null denotes a user initialized game over
         */
        val details: GAME_OVER_DETAILS? = null
    ): GamePlayData<GAME_OVER_DETAILS>
}

