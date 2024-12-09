# MemeMover

A simple intelliJ plugin, to move a meme inside a swing window.


https://github.com/user-attachments/assets/3abf327e-4501-47c3-b097-6916268c6f21



## Features

Enter the frame from any edge and the meme will appear a quarter of its size and increase to its full size as you move the cursor to the middle of the window. Only the axis of the edge you entered accounts for the meme's size (e.g., if you enter from the left only horizontal movement will adjust the size, vertical movement is ignored.)

**Additional features:**
- It's an IntelliJ IDE plugin.
- Supports being resized to any size.
- By clicking, you can switch between a meme and a complex UI panel that takes some time to initialize (artificial delay to simulate a long compute task) and displays a loading indicator.

## Build it yourself

To test the plugin, you **need** IntelliJ IDE.

1. Run the gradle task `runIde` (under Tasks > intellij > runIde).
2. This will open a new IntelliJ window where the plugin is enabled.
3. Open any project in the new window.
4. Click the action `Tools` > `Open Meme Mover` (you can also search for actions with ⇧⌘A on macOS or Control+Shift+A on Windows/Linux)
5. This should have opened a new window with the Meme Mover content.
