# Stalactite Update

This update replaces the barrel system with a new stalactite hazard system.

## Changes Made

### New Files Added
- `Stalactite.java`: Main stalactite hazard class
- `GlowParticle.java`: Particle effect system for stalactites

### Files Modified
- `Level_2.java`: Updated spawning system to use stalactites instead of barrels

### Files Removed
- `Barrel.java`
- `BarrelPiece.java`
- `barrel.png`

## Implementation Details

### Stalactite Features
- Different sizes for each stage:
  - Stage 1: Smaller size (30x60)
  - Stage 2: Larger size (40x80)
- Varying fall speeds:
  - Stage 1: Slower falling (MAX_FALL_SPEED = 6)
  - Stage 2: Faster falling (MAX_FALL_SPEED = 12)
- Glowing particle effects
- Impact effects when hitting ground

### Spawn Rates
- Stage 1: Every 2 seconds
- Stage 2: Every 1 second

## Installation
1. Delete the old barrel-related files
2. Add the new Stalactite and GlowParticle classes
3. Update Level_2.java with the new spawning code
4. Add stalactite.png to the images folder

## Notes
- The stalactite system provides a more challenging and visually appealing hazard
- Particle effects create an atmospheric dungeon/cave feeling
- Different behaviors between stages maintain game progression 