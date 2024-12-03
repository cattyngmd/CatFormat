<div align="center">
<h1>Cat Format</h1> 
<p> Simple and easy-to-use text formatting tool for Java (primarily made for Minecraft). </p>

![CatFormatIcon](catformat-fabric/src/main/resources/assets/catformat/icon.png)
</div>

## Why?
I got tired coding Minecrafts text objects like huge builders (e.g `Text.literal("Hello " + Formatting.RED + " world!")`).
And if you need to concatenate multiple `Text` objects, it becomes even worse. 
That's why I've created this library to help people write these kinds of literals easily 
and without any boilerplate code. Also, it doesn't support things like `ClickEvent` or 
`HoverEvent`, and I'm not planning on adding them in the future. But if you want, you can 
make a pull request!

## Basic syntax
There are a few placeholders that you need to know about:
- `{}` - Resets the current color and style to the default one.
- `${}` - Sets the text color to the given variable color (e.g `${red}`, `${cyan}`).
- `#{}` - Sets the text color to the given hex value (e.g `#{fff}`, `#{ff00ff}`).
- `+bius` modifiers - Sets the modifier of the text. You can also combine modifiers,
e.g `${+bi}` - ***bold + italic***.
  - b - **bold text** 
  - i - *italic text*
  - u - <ins> underline text </ins>
  - s - ~~strikethrough text~~


## Example
Simple usage with Minecraft's built-in `MutableText`:

```java
import dev.cattyn.catformat.mod.VanillaCatFormat;

import java.util.Random;

void func() {
  VanillaCatFormat formatter = new VanillaCatFormat();
  formatter.addVanilla();

  formatter.format("${red} Hello world!"); // red colored 'Hello world!'
  formatter.format("#{f0f} Hello world!"); // magenta colored 'Hello world!'
  formatter.format("#{0000ff} Hello world!"); // blue colored 'Hello world!'
  formatter.format("#{red+bold} Hello {} world!"); // red colored 'Hello' with bold style
  // and ' world!' without any style

  // It also supports javas default formatting tool - String.format()
  formatter.format("${red} Hello %s!", "world"); // red colored 'Hello world!'

  // You can create your own color namespaces too!
  formatter.add("light_green", 0x99FF99);
  formatter.add("random_color", () -> new Random().nextInt(0xFFFFFF));

  formatter.format("${light_green} Hello ${random_color} world");
}
```

To use it in minecraft plugin or any other project you can make your own TextWrapper. For example
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