# Tab Inventory Bugfix (Minecraft 1.16.1)

This is a small, client-side Fabric mod that restores the ability to close
container screens (inventory, chests, crafting table, etc.) using the TAB key
when TAB is bound to the inventory keybind. 

This was built off of a forge mod that fixed this same behavior linked here: https://github.com/Darkere/TabInventory

## Purpose
This mod fixes a vanilla Minecraft 1.16.1 input bug where TAB does not close
handled container screens, despite being bound to the inventory key.

## Behavior
- Pressing TAB closes any handled container screen
- Only triggers if TAB is bound to the inventory key
- Does not affect chat or menus
- No tick-based logic
- No automation
- Mixin-only implementation

## Scope
This mod does not:
- Change gameplay logic
- Automate inputs
- Modify timing or RNG
- Affect server-side behavior

## Intended Use
Personal use and speedrunning transparency.

Source is public for verification purposes.
