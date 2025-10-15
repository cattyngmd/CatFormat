<div align="center">
<h1>Cat Format</h1> 
<p> Simple and easy-to-use text formatting tool for Java (primarily made for Minecraft) </p>

![CatFormatIcon](images/icon.png)
</div>

## Why?

I got tired of using Minecraft's `Text` objects like huge builders (e.g `Text.literal("Hello " + Formatting.RED + " world!")`). And if you need to concatenate multiple `Text` objects, it becomes even worse. That's why I've created this library to help people write these kinds of literals easily and without any boilerplate code. Also, it doesn't support things like `ClickEvent` or `HoverEvent`, and I'm not planning on adding them in the future. But if you want, you can make a pull request!

## Basic syntax

There are a few placeholders that you need to know about:

- `{}` - Resets the current color and style to the previous one.
- `{var}` - Sets the text color to the given variable color (e.g `{red}`, `{cyan}`, check [Minecraft Color Codes](https://minecraft.tools/en/color-code.php)).
- `{#hex}` - Sets the text color to the given hex value (e.g `{#fff}`, `{#ff00ff}`).
- `+obius` modifiers - Sets the modifier of the text. You can also combine modifiers,
e.g `{+bi}` - ***bold + italic***.
  - o - [obfuscated text](images/obfuscated.gif) 
  - b - **bold text** 
  - i - *italic text*
  - u - <ins> underline text </ins>
  - s - ~~strikethrough text~~

## Installing

You can install this library using Jitpack.io

Add jitpack repository to your `build.gradle`:
```gradle
maven { url "https://jitpack.io"  }
```

And then add CatFormat as a dependency:
```gradle
implementation("com.github.cattyngmd.catformat:catformat-core:2.1.0")
```
Or if you use fabric
```gradle
modImplementation("com.github.cattyngmd.catformat:catformat-fabric:2.1.0")
```

## Example

Simple usage with Minecraft's built-in `MutableText`:

```java
import dev.cattyn.catformat.fabric.FabricCatFormat;

import java.awt.Color;
import java.util.Random;

// you can create only one static instance of the Formatter and use it everywhere!
FabricCatFormat formatter = new FabricCatFormat();

void main() {
  formatter.format("{red} Hello world!"); // red colored 'Hello world!'
  formatter.format("{#f0f} Hello world!"); // magenta colored 'Hello world!'
  formatter.format("{#0000ff} Hello world!"); // blue colored 'Hello world!'
  formatter.format("{red+b} Hello {} world!"); // red colored 'Hello' with bold style and ' world!' without any style

  // It also supports Java's default formatting tool - String.format()
  formatter.format("{red} Hello %s!", "world"); // red colored 'Hello world!'

  // You can create your own color namespaces too!
  formatter.add("light_green", 0x99FF99);
  formatter.add("pink", Color.PINK.hashCode());
  formatter.add("random_color", () -> new Random().nextInt(0xFFFFFF));

  formatter.format("{light_green} Hello {random_color} world {pink}!");
}
```

Also, you can use classes with colors and use them in your CatFormat string:

```java
import dev.cattyn.catformat.stylist.annotations.*;
import dev.cattyn.catformat.stylist.color.*;
import java.util.Random;
import java.awt.*;

class Styles {
    // Mark your color with final keyword if you are not
    // going to change it, because it parses a bit faster
    @Style 
    final int black = Color.BLACK.hashCode();
  
    // You can also use `value` property in @Style
    // to set custom name to the color
    @Style("custom_name")
    final Color red = Color.RED;
    
    @Style("dynamic_color")
    int dynamic = Color.CYAN.hashCode();
    
    // And of course functions :p
    @Style("random")
    int getRandomColor() { // Beware, you cant pass any params
        return new Random().nextInt(0xFFFFFF);
    }
}

// Custom color objects are supported with `ARGBProvider` interface
public record ColorHolder(int rgb) implements ColorProvider { 
    @Override 
    public int getRGB() {
        return rgb;
    }
}

// If the object is mutable you should use `ARGBProvider.Mutable`
public class ColorHolderMutable implements ColorProvider.Mutable {
    private int rgb;
    
    public void setColor(int color) {
        this.color = color;
    }
    
    @Override
    public int getRGB() {
      return rgb;
    }
}
```

To use it in a Minecraft plugin or any other project you can make your own `TextWrapper`. For example
```java
import dev.cattyn.catformat.CatWrapper;
import dev.cattyn.catformat.text.TextWrapper;

class StringWrapper implements TextWrapper<String> { // idk why but you can do that
    String colored(String text, int color) {
        return text; // NOP
    }
    
    String concat(String text, String text2) {
        return text + text2;
    }
    
    String modify(String text, Modifiers modifiers) {
        return text;
    }
    
    String newText(String text) {
        return text;
    }
}

void func() {
    CatFormat format = new CatFormat(new StringWrapper());
}


```
