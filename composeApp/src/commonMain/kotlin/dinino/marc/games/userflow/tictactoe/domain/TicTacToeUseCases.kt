package dinino.marc.games.userflow.tictactoe.domain

import dinino.marc.games.userflow.common.data.Repository
import dinino.marc.games.userflow.common.domain.GameUseCases
import dinino.marc.games.userflow.common.domain.GameUseCasesImpl
import dinino.marc.games.userflow.tictactoe.data.TicTacToeGameData

class TicTacToeUseCases(
    repository: Repository<TicTacToeGameData>,
): GameUseCases<Repository<TicTacToeGameData>, TicTacToeGameData>
    by GameUseCasesImpl(repository = repository)