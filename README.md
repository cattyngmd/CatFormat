<div align="center">
<h1>Cat Format</h1> 
<p> Simple and easy-to-use text formatting tool for Java (primarily made for Minecraft) </p>

![CatFormatIcon](catformat-fabric/src/main/resources/assets/catformat/icon.png)
</div>

## Why?

I got tired of using Minecraft's `Text` objects like huge builders (e.g `Text.literal("Hello " + Formatting.RED + " world!")`). And if you need to concatenate multiple `Text` objects, it becomes even worse. That's why I've created this library to help people write these kinds of literals easily and without any boilerplate code. Also, it doesn't support things like `ClickEvent` or `HoverEvent`, and I'm not planning on adding them in the future. But if you want, you can make a pull request!

## Basic syntax

There are a few placeholders that you need to know about:
- `{}` - Resets the current color and style to the default one.
- `${}` - Sets the text color to the given variable color (e.g `${red}`, `${cyan}`, check [Minecraft Color Codes](https://minecraft.tools/en/color-code.php)).
- `#{}` - Sets the text color to the given hex value (e.g `#{fff}`, `#{ff00ff}`).
- `+bius` modifiers - Sets the modifier of the text. You can also combine modifiers,
e.g `${+bi}` - ***bold + italic***.
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
implementation("com.github.cattyngmd.catformat:catformat-core:1.0.3")
```
Or if you use fabric
```gradle
modImplementation("com.github.cattyngmd.catformat:catformat-fabric:1.0.3")
```

## Example

Simple usage with Minecraft's built-in `MutableText`:

```java
import dev.cattyn.catformat.fabric.FabricCatFormat;

import java.awt.*;
import java.util.Random;

public static final FabricCatFormat formatter = // you can create only one static instance
        new FabricCatFormat().addVanilla();     // of the Formatter and use it everywhere!

void func() {
  formatter.format("${red} Hello world!"); // red dev.cattyn.catformat.test.colored 'Hello world!'
  formatter.format("#{f0f} Hello world!"); // magenta dev.cattyn.catformat.test.colored 'Hello world!'
  formatter.format("#{0000ff} Hello world!"); // blue dev.cattyn.catformat.test.colored 'Hello world!'
  formatter.format("${red+bold} Hello {} world!"); // red dev.cattyn.catformat.test.colored 'Hello' with bold style and ' world!' without any style

  // It also supports Java's default formatting tool - String.format()
  formatter.format("${red} Hello %s!", "world"); // red dev.cattyn.catformat.test.colored 'Hello world!'

  // You can create your own color namespaces too!
  formatter.add("light_green", 0x99FF99);
  formatter.add("pink", Color.PINK.hashCode());
  formatter.add("random_color", () -> new Random().nextInt(0xFFFFFF));

  formatter.format("${light_green} Hello ${random_color} world ${pink}!");
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
