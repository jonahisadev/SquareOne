# Square One

### What is it?

Square One is a very simple game engine for 2D games.
Once macOS Sierra was released, none of my older game
engines worked anymore, so I had to create a new one.
The only working version of LWJGL for me was 3.1 so
I ran into the problem of not having the Slick Util libraries
at my disposal. I was forced to think outside the box,
especially in the text rendering department. Overall, it
was a good learning experience, and I can make games now.

### What do I need?

* LWJGL 3.1
* Java 1.8.0_60
* Creating compatible fonts may require [Hiero](https://libgdx.badlogicgames.com/tools.html)

### Philosophy

As I've been touching up this engine for a while after
its creation (12/28/16), I've had to consider the addition
of certain features. Some features may not be great to add
to the engine, because that might only be the way I do it,
and not the way someone else does it. At the moment, the engine
does not have the capabilites to implement multiples ways for doing
something specific. For now, it functions as a head start, while
still providing a few utilities one might not want to make themselves.
As such, some features are still being added and worked on,
but some features should be created by the programmer. I hope
to continue constructing the engine to provide the tools to do
that effectively.

### Features

* Easy to get up and running
* Basic quad/texture rendering
* Font loading (.fnt/.bmp)
* Text rendering
* Networking
* WAV loading/playback
* Spritesheets

### To Do

* Add more tests
* OGG support
* TCP support

### Other notes

I will be updating this if I notice any bugs in the process
of creating my own games. If you end up using this instead
of a high quality engine, please submit any issues you find.

I may also update this with new features I find might be
helpful to other people.