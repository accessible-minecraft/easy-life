# Health n Hunger Mod

## Discription

This is a __fabric__ mod. This mod shows the player the current coordinates and the direction. This mod also adds warns when health/food/air bubble is decreased.

[Download](https://github.com/shoaib11120/easy-life/raw/1.16.x/easy-life-1.0.0.jar)

*Note: This is a client-side mod. So no need to install it to server.*

If you have any issues with the mod, you can describe your issues in the [issues]() tab.

___

## Requirements

#### These are the current requirements of the mod:-

1. Minecraft Java (1.16.x)
2. [Fabric](https://fabricmc.net/wiki/install)
3. [WinRar](https://www.win-rar.com/download.html) (Only for mod customization)

___

## Mod Installation

Download the [easy-life-1.x.x.jar](https://github.com/shoaib11120/easy-life/raw/1.16.x/easy-life-1.0.0.jar) file and paste it in your mods folder located in :-
>%AppData%\\.minecraft\mods

Your mod is now installed!
___

## Mod Customization

### Step 1: Navigate to your mods folder

>%AppData%\\.minecraft\mods

![Mods Folder](/readmeAssets/mods_folder.png)

### Step 2: Open the mod jar file with WinRar

![Open with WinRar](/readmeAssets/openWithWinrar.png)

### Step 3: Navigate to assets folder

![Navigate to assets folder](/readmeAssets/navigate_to_assets.png)

### Step 4: Open config.txt file and change the configuration

![Open config.txt](/readmeAssets/config.png)

### Step 5: Save the new configurations

To save the configuration save the file and when you will close the file you will get a prompt to update the archive, hit ok!
![Save new configurations](/readmeAssets/save_prompt.png)

Your new configurations for the mod are saved.

__*Note:You can only change the configurations when Minecraft is closed.*__

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

## Mod Features

### Player Coordinates

__Enabled__ by default. This feature/mod will show the current coordinates/position of the player. The Hotkey for this mod is __F6__ .

#### Demo:-

![Player Coordinates Demo](/readmeAssets/player_coordinates.png)

#### Configurations:-

Name | Values | Default Value | Usage
---- | ------ | ------------- | -----
player-coordination-status | on/off | on | To enable/disble this mod
player-coordination-color | [In-Built Colors](#in-built-colors) | White | To change the text color
player-coordination-background | [In-Built Colors](#in-built-colors) | Black | To change the background color
player-coordination-positionx | Integers/Real numbers | 0 | To change the X(horizontal) position of the overlay
player-coordination-positiony | Integers/Real numbers | 40 | To change the Y(vertical) position of the overlay

### Player Direction

__Enabled__ by default. This feature/mod will show the current direction the player is facing. The Hotkey for this mod is also __F6__ .

#### Demo:-

![Player Coordinates Demo](/readmeAssets/player_direction.png)

#### Configurations:-

Name | Values | Default Value | Usage
---- | ------ | ------------- | -----
player-direction-status | on/off | on | To enable/disble this mod
player-direction-color | [In-Built Colors](#in-built-colors) | White | To change the text color
player-direction-background | [In-Built Colors](#in-built-colors) | Black | To change the background color
player-direction-positionx | Integers/Real numbers | 0 | To change the X(horizontal) position of the overlay
player-direction-positiony | Integers/Real numbers | 57 | To change the Y(vertical) position of the overlay

### Health n Hunger

__Enabled__ by default. This feature/mod shows the current health and hunger/food level of the player. The Hotkey for this mod is __R__.

#### Demo:-

![Health n Hunger](/readmeAssets/health_n_hunger.png)

#### Configurations:-

Name | Values | Default Value | Usage
---- | ------ | ------------- | -----
health-n-hunger-status | on/off | on | To enable/disble this mod
health-n-hunger-color | [In-Built Colors](#in-built-colors) | Red | To change the text color
health-n-hunger-scale | Integer/Real numbers | 2 |To change the scale/font size of the overlay. __*Note:Changing the value will result in change in x and y position of the overlay!*__
health-n-hunger-positionx | Integers/Real numbers | 10 | To change the X(horizontal) position of the overlay
health-n-hunger-positiony | Integers/Real numbers | 35 | To change the Y(vertical) position of the overlay

### Player Warnings

__Disabled__ by default. This feature/mod warns the player when the health/food level/air bubble is low.

#### Demo:-

![Player Warnings](/readmeAssets/player_warning.png)

#### Configurations:-

Name | Values | Default Value | Usage
---- | ------ | ------------- | -----
player-warnings-status | on/off | on | To enable/disble this mod
player-warnings-color | [In-Built Colors](#in-built-colors) | Yellow | To change the text color
player-warnings-scale | Integer/Real numbers | 2 |To change the scale/font size of the overlay. __*Note:Changing the value will result in change in x and y position of the overlay!*__
player-warnings-positionx | Integers/Real numbers | 10 | To change the X(horizontal) position of the overlay
player-warnings-positiony | Integers/Real numbers | 35 | To change the Y(vertical) position of the overlay
player-warnings-timeout | Time(in seconds) | 300 |To change the cooldown of the mod.

___

## License

This project is available under the CC0 license. Feel free to learn from it and incorporate it in your own projects.
