# Easy Life Mod

## Discription

This is a __Minecraft Fabric__ mod. This mod shows the current coordinates and the direction of the player. This mod also adds warns when health/food/air bubble is decreased.

[Download](https://github.com/shoaib11120/easy-life/raw/1.16.x/easy-life-1.0.2.jar)

*Note: This is a client-side mod. So no need to install it to server.*

If you have any issues with the mod, you can describe your issues in the [issues](https://github.com/shoaib11120/easy-life/issues) tab.

___

## Requirements

__These are the current requirements of the mod:-__

1. Minecraft Java (1.16.x)
2. [Fabric](https://fabricmc.net/wiki/install)
3. [Accessibility Plus](https://www.curseforge.com/minecraft/mc-mods/accessibility-plus) (Only for narrator support. Don't download it if you don't want the narrator support.)

___

## Mod Installation

Download the [easy-life-1.x.x.jar](https://github.com/shoaib11120/easy-life/raw/1.16.x/easy-life-1.0.1.jar) file and paste it in your mods folder located in :-

    %AppData%\.minecraft\mods

Your mod is now installed!
___

## Mod Customization

The hotkey to open Customization menu is **'J'**. Now you can configure the mod in-game.

<font size="5em"> **Demo:-** </font>

![Config Menu](/readmeAssets/config_menu.png)
___

#### In-Built Colors

Below are the in-built colors that you can use in the mod:-

1. Red
1. Grey
1. Purple
1. White
1. Black
1. Pink
1. Blue
1. Green
1. Yellow
1. Orange
1. Brown
___

#### Custom Colors

Now you can use custom colors in place of in-built colors. You can find custom colors from this website - [Color Picker](https://htmlcolorcodes.com/color-picker/).

*___Note:- You can only use hex color codes___*

___
## Mod Features

### Player Coordinates

__Enabled__ by default. This feature/mod will show the current coordinates/position of the player. The Hotkey for this mod is __'F6'__ . The Hotkey for position narrator is __'G'__.

<font size="5em"> **Demo:-** </font>

![Player Coordinates Demo](/readmeAssets/player_coordinates.png)

<font size="5em"> **Configurations:-** </font>

**Mod Screen:-**

![Player Coordination Menu Screen](/readmeAssets/pc_mod_screen.png)

Name | Values | Default Value | Usage
---- | ------ | ------------- | -----
**Status** | on/off | on | To enable/disble this mod
**Text Color** | [In-Built Colors](#in-built-colors) / [Custom Colors](#custom-colors) | White | To change the text color and the opacity/transparency.
**Background Color** | [In-Built Colors](#in-built-colors) / [Custom Colors](#custom-colors) | Black | To change the background color and the opacity/transparency.
**Position: X** | Integers/Real numbers | 0 | To change the X(horizontal) position of the overlay
**Position: Y** | Integers/Real numbers | 40 | To change the Y(vertical) position of the overlay

### Player Direction

__Enabled__ by default. This feature/mod will show the current direction the player is facing. The Hotkey for this mod is also __F6__ . The Hotkey for direction narrator is __'H'__.

<font size="5em"> **Demo:-** </font>

![Player Coordinates Demo](/readmeAssets/player_direction.png)

<font size="5em"> **Configurations:-** </font>

**Mod Screen:-**

![Player Coordination Menu Screen](/readmeAssets/pd_mod_screen.png)

Name | Values | Default Value | Usage
---- | ------ | ------------- | -----
**Status** | on/off | on | To enable/disble this mod
**Text Color** | [In-Built Colors](#in-built-colors) / [Custom Colors](#custom-colors) | White | To change the text color and the opacity/transparency.
**Background Color** | [In-Built Colors](#in-built-colors) | Black | To change the background color and the opacity/transparency.
**Position: X** | Integers/Real numbers | 0 | To change the X(horizontal) position of the overlay
**Position: Y** | Integers/Real numbers | 57 | To change the Y(vertical) position of the overlay

### Health n Hunger

__Enabled__ by default. This feature/mod shows the current health and hunger/food level of the player. The Hotkey for this mod is __R__. Added accessibility plus support to this mod.

<font size="5em"> **Demo:-** </font>

![Health n Hunger](/readmeAssets/health_n_hunger.png)

<font size="5em"> **Configurations:-** </font>

**Mod Menu:-**

![Health n Hunger Mod Screen](/readmeAssets/hnh_mod_screen.png)

Name | Values | Default Value | Usage
---- | ------ | ------------- | -----
**Status** | on/off | on | To enable/disble this mod
**Text Color** | [In-Built Colors](#in-built-colors) / [Custom Colors](#custom-colors) | Red | To change the text color and the opacity/transparency.
**Scale** | Integer/Real numbers | 2 |To change the scale/font size of the overlay. __*Note:Changing the value will result in change in x and y position of the overlay!*__
**Position: X** | Integers/Real numbers | 10 | To change the X(horizontal) position of the overlay
**Position: Y** | Integers/Real numbers | 35 | To change the Y(vertical) position of the overlay

### Player Warnings

__Enabled__ by default. This feature/mod warns the player when the health/food level/air bubble is low. Added accessibility plus support to this mod.

<font size="5em"> **Demo:-** </font>

![Player Warnings](/readmeAssets/player_warning.png)

<font size="5em"> **Configurations:-** </font>

**Mod Screen:-**

![Player Warnings Mod Screen](/readmeAssets/pw_mod_screen.png)

Name | Values | Default Value | Usage
---- | ------ | ------------- | -----
**Status** | on/off | on | To enable/disble this mod
**Color** | [In-Built Colors](#in-built-colors) | Yellow | To change the text color
**Scale** | Integer/Real numbers | 2 |To change the scale/font size of the overlay. __*Note:Changing the value will result in change in x and y position of the overlay!*__
**Position: X** | Integers/Real numbers | 10 | To change the X(horizontal) position of the overlay
**Position: Y** | Integers/Real numbers | 35 | To change the Y(vertical) position of the overlay
**Timeout** | Time(in seconds) | 300 |To change the cooldown of the mod.
**Sound** | on/off | on | To enable/disable the anvil landing sound.
**Health Threshold: First** | Double/Real Numbers | 3.0 | To change threshold value of health warning.
**Health Threshold: Second** | Double/Real Numbers | 0 | To change threshold value of health warning.
**Food Threshold** | Double/Real Numbers | 3.0 | To change threshold value of food warning.
**Air Threshold** | Double/Real Numbers | 3.0 | To change threshold value of air bubble warning.

___
