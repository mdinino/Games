package dinino.marc.games.userflow.tetris.domain

import dinino.marc.games.userflow.common.data.Repository
import dinino.marc.games.userflow.common.domain.RepositoryUseCases
import dinino.marc.games.userflow.common.domain.RepositoryUseCasesImpl
import dinino.marc.games.userflow.tetris.data.TetrisGameData

class TetrisUseCases(
    repository: Repository<TetrisGameData>,
): RepositoryUseCases<Repository<TetrisGameData>, TetrisGameData>
by RepositoryUseCasesImpl(repository = repository)