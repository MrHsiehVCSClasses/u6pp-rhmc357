# u6pp - Uno

In this project, you will make a slightly altered version of the hit card game Uno.

## Rules of Uno

Youtube it :)
Or, you can read about it here: <https://www.unorules.com/>

### Changes from actual UNO

- you may choose to skip your turn at anytime and draw a card
- you may play a wild at any time
- if you do not have a card to play, you skip your turn and draw a card
- no special rules for when there are only 2 players
- no "stacking" punishments. If player 1 plays a draw 4, player 2 cannot also play a draw 4, thereby passing a draw 8 punishment to player 3. Player 2 simply draws 4 and skips their turn.

Reminder: Draw 2 and Draw 4 skip the affected player's turn.

## Implementation Overview

- `Card.java` - represents a single uno card
- `Player.java` - has a name and a hand of cards
- `CardStack.java` - a stack of uno cards, where you can really only access the top of the stack
- `Uno.java` - the main game logic, designed to be run by any frontend
- `UnoFrontend.java` - all the pretty output that tells the user what is going on. Mainly just calls the functions in `Uno.java`. Handles all input.

Optional:

- `InputHelper.java` - will not be tested, but feel free to bring it over from your previous assignments. If you do, you might want to create a new function that takes in a prompt and an `String[]` of allowed answers, which ignores all other answers.

## Implementation details

At this point, you should be able to figure out the required functions by reading the test code, so the instructions will be presented in a way that encourages you to read more code. Start with the tests at the top of a file and move downwards. If you ever get lost, ask Mr. Hsieh.

Your process should be like so:

1. create a small bit of functionality
2. test out the little bit of code you just made
3. Be 100% certain your code works.
4. goto step 1 until complete.

The method above will save you a LOT of debugging time. It's a bit more diligence throughout the process, but will save you literal hours in the end.

Tip: Although not required, consider writing some `toString` methods, for debug purposes.

### `Card.java`

First, create the `Card` class so that it passes all the given tests. Watch out for the edge cases. For example, a taking in `null` for a String parameter, or setting the color on a non-wild card.

HINT: Consider making a helper function that helps you check if a given `String` is in a given `String[]`;

### `Player.java`

Then, create the `Player` class. It's by far the simplest class this project.

### `CardStack.java`

Next, create the `CardStack` class. It purposely restricts the access to its internal `ArrayList`, only allowing uses to look at/remove/add to the very top of the stack. This is more similar to how stacks work in real life. The `ArrayList` analogs in stack are as follows: `pop` means `remove`, `push` means `add`, and `peek` means `get`.

You might notice in `CardStackTests` that there is a function at the top that is tagged with `@BeforeEach`. This function runs once before each test, and each test starts with a clean slate. No variables or anything are saved between tests.

For `shuffle`, use the [Fisher-Yates algorithm](https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle).

### `Uno.java`

After `CardStack`, you should create the `Uno class`. This class has a lot of different functionality. However, if you go down the tests from top to bottom, it will lead you in a reasonable implementation order. There are a lot of tests because each test only tests a small part of a function's effects. Additionally, there are a lot of required methods so that it is very clear what each method does. This prevents any method from having too many intertwined responsibilities, leading to clearer abstraction. This is also a great reason for making helper methods.

HINT: Consider making a helper method for ending the current player's turn and selecting the next player.

HINT: Consider making a helper method for drawing a given number of cards, that also handles shuffling the discard pile when necessary.

You might notice that the `Uno` class has two constructors - one is bare bones and intended to be used to initialize a fresh game of uno, with the right amount and types of cards, hand sizes, etc. (BTW, see the included .txt file for a pre-written method that creates the correct number/type of cards). The other constructor has a bunch of parameters. Its purpose is to initialize the game in any state that we might want for testing. In a real game, this would also be used for save/load functionality, or reconnecting after losing signal.

### `UnoFrontend.java`

Almost there! You will now write the final class, `UnoFrontend.java`. This class will have only 1 `public` method with no parameters: `void play()`. This class basically just uses everything you've written, and wraps it in a game loop. It also handles all the input, and outputting what happens. Because `Uno` was pretty decomposed (made into smaller functions), you really only have to get the most important information and print it out for the player, handle the player's input, and call the appropriate `Uno` functions. Should be less than 100 lines for a bare-bones version.

HINT: certain methods in other classes have been designed to give information when using them. For example, `Uno.playCard()` returns whether the play was successful or not. Use this to determine what to print.

HINT: Make helper methods! For example, Mr. Hsieh made the `printCurrentStatusAndOptions(Player player)` and the `boolean attemptToPlayCard(Card cardToPlay)` methods. Helper methods such as these will make your `play` method much easier to create/read/debug.

### Grading Breakdown

- Formatting/Indentation: 2 points
- All Code is DRY: 2 points
- All Code Properly JavaDoc'ed: 2 points
- no `public` methods/attributes besides the ones specified: 2 point
- Passes All Test Cases: 16 points (~2 pt per 10 tests passed)
- Manual testing of `UnoFrontend`: 4 points
- UX/UI: 2 point (nothing annoys/confuses Mr. Hsieh when he is testing)

Total: 30 points
