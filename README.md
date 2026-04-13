# TrialMod

A Minecraft Forge mod that adds a custom **Charcoal Block** (`trialmod:charcoal_block`).

## Dependencies

- Minecraft `1.20.1`
- Forge `47.3.0`
- Java `17`

## Setup (Development)

1. Open the project in IntelliJ IDEA.
2. Ensure Gradle JVM is Java 17.
3. Build once to download dependencies.

```powershell
Set-Location "C:\Mods\TrialMod"
.\gradlew.bat build
```

## Run in Client (Development)

```powershell
Set-Location "C:\Mods\TrialMod"
.\gradlew.bat runClient
```

## New Content

### Charcoal Block

- Registry name: `trialmod:charcoal_block`
- Type: Placeable block + block item
- Creative tab: Building Blocks
- Custom texture: `assets/trialmod/textures/block/charcoal_block.png`

### Crafting Recipe

- 3x3 charcoal -> 1 charcoal block
- 1 charcoal block -> 9 charcoal (shapeless reverse recipe)

Recipe files:

- `src/main/resources/data/trialmod/recipes/charcoal_block.json`
- `src/main/resources/data/trialmod/recipes/charcoal_from_block.json`

## Build Output

After running `build`, the mod JAR is created in:

- `build/libs/`

Typical filename:

- `trialmod-1.0.0.jar`

## Install (Player)

1. Install Minecraft Forge `47.3.0` for Minecraft `1.20.1`.
2. Copy the built JAR from `build/libs/` into your Minecraft `mods` folder.
3. Start Minecraft with the Forge profile.

