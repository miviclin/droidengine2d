#v0.8.0 (2014-03-15)

**Changes:**

- Licensed the code under the Apache License, Version 2.0.
- Fully redesigned game state management.
- Fully redesigned input processing.
- Renamed MusicManager to MusicPlayer.
- Added MAX_VOLUME and MIN_VOLUME constants in MusicPlayer.
- Added methods to check if a TextureRegion is flipped or not.
- Added support for logging the number of draw calls performed in the previous frame.
- Added a copy constructor in Color.
- Replaced Pool and Poolable with the collections library.
- Removed the Game attribute of EngineActivity.
- Removed the name attribute of Game.
- Renamed loadFromXML(...) to loadFromFile(...) in Font.
- Removed Font.clear().
- Renamed setSprite(...) to setTexturedRectangle(...) in TextureMaterialBatchRendererBase.
- Renamed setRectangularShape(...) to setColoredRectangle(...) in ColorMaterialBatchRenderer.
- Renamed GraphicsBatch to GraphicsBatchRenderer.
- Removed Array.
- Moved UnsupportedMaterialException to *.graphics.material.
- Replaced ID with Id in EngineActivity to keep naming conventions consistent.
- Fixed javadoc in multiple classes.
- Other minor changes.

#v0.7.0 (2013-12-19)

**Changes:**

- Translated all javadoc to english.
- Added Materials.
- The element to be rendered on screen can not be rotated around an external point in the draw method anymore.
- Allowed specifying the origin of the element to be rendered on screen.
- The default origin is the center of the element to be rendered.
- Renamed all SpriteBatches to MaterialRenderers.
- Removed Dimensions2 and Dimensions3. Use Vector2 and Vector3 instead.
- Added support for text rendering with custom fonts imported from BMFont.
- Refactored Graphics to use Materials and added a method to allow setting the background color.
- Replaced ID with Id in the whole project to keep naming consistent.
- Moved all input controllers to *.input.
- The Scene is disposed when it is unregistered from the SceneManager.
- Renamed Animation.getCurrentFrame(float) to Animation.update(float).
- Refactored all shaders.
- Renamed TextureAtlas.loadFromXML(...) to TextureAtlas.loadFromFile(...).
- Other minor changes.

**Bug fixes:**

- Fixed a bug which did not allow rotated objects to be rendered properly.


#v0.6.0 (2013-08-07)

**Changes:**

- Refactored all ShaderPrograms.  
- Refactored all SpriteBatches.
- Added a new shader and a new spritebatch that allows rendering batches of sprites with texture and opacity.
- Added a new shader and a new spritebatch that allows rendering batches of sprites with texture and RGBA color.
- Added a new shader and a new spritebatch that allows rendering batches of rectangles with color.
- Added a new shader and a new spritebatch that allows rendering batches of sprites with texture. It is possible to alter the hue, reduce the saturation and reduce the brightness of the texture.
- Added the Graphics class, which makes drawing things on screen easier.
- Allowed changing the value of the low pass filter in Accelerometer.


#v0.5.0 (2013-05-17)

**Changes:**

- Added AndroidEngineActivity. It encapsulates the life cycle of the Engine.
- Added Accelerometer, this class allows reading values from the accelerometer of the device.
- Added Scenes in order to manage each screen, menu, level, etc of the game.
- Moved TouchController and KeyController to com.miviclin.droidengine2d.
- Added passive error checking capabilities to GLDebugger to improve performance.
- Disabled depth test in DefaultRenderer.
- Added some static utility methods to Vector2 and Vector3.
- Renamed DynamicSpriteBatchShaderProgram to PositionTextureBatchShaderProgram.

**Bug fixes:**

- Fixed a bug when getting the display refresh rate.
- Fixed a bug which prevented some textures to be loaded.


#v0.4.0 (2013-04-01)

**Changes:**

- Changed the way how events are handled in the game.
- Allowed intercepting back button presses.
- Changed the background color of DefaultRenderer to black.
- Added AnimationStateListener and minor changes to Animation.
- Added some methods to MusicManager in order to allow checking if an specified file is playable.
- Allowed setting the GLView of the Game manually.
- Added EngineBuilder to create the Engine.
- TextureRegions are managed by the TextureManager.
- Enabled back face culling in DefaultRenderer.

**Bug fixes:**

- Fixed a bug when updating 1-frame animations.
- Fixed a bug which allowed calling DynamicSpriteBatch.draw() before calling DynamicSpriteBatch.begin().
- Fixed the file encoding of Game.java and EngineLock.java. Both files are now encoded in UTF-8 without BOM.
- Fixed a bug in Sprite.setPosition(Point2D). This method works as expected now.
- Fixed a bug which allowed the game thread to be destroyed before releasing all resources.
- Fixed a bug which makes detectOpenGLES20() always return false if the game is running on the Android Emulator.
- Fixed a bug which prevented some resources to be released. All resources are now released in onDestroy().


#v0.3.0 (2013-03-06)

**Changes:**

- Allowed using a custom renderer.  
- Moved the content of com.miviclin.droidengine2d.engine to com.miviclin.droidengine2d.
- Fixed javadoc in Engine.


#v0.2.0 (2013-03-05)

**Changes:**

- Allowed creating animations.
- Creating Sprites without TextureRegion is not allowed.
- Renamed GLTexture to Texture.
- Renamed GLTextureManager to TextureManager.
- Moved TransformUtilities to com.miviclin.droidengine2d.util.
- Added a name attribute to Game.


#v0.1.0 (2013-03-03)

First release.
