package dinino.marc.games.platform

sealed class PlatformType {
    data object Android: PlatformType()

    data object IOS: PlatformType()
    data object Desktop: PlatformType()
    data object WasmJs: PlatformType()
}