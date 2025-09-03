package dinino.marc.games.userflow.tictactoe.domain

import dinino.marc.games.userflow.common.data.Repository
import dinino.marc.games.userflow.common.domain.RepositoryUseCases
import dinino.marc.games.userflow.common.domain.RepositoryUseCasesImpl
import dinino.marc.games.userflow.tictactoe.data.TicTacToeGameData

class TicTacToeUseCases(
    repository: Repository<TicTacToeGameData>,
): RepositoryUseCases<Repository<TicTacToeGameData>, TicTacToeGameData>
    by RepositoryUseCasesImpl(repository = repository)