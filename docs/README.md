## LDTS_1407 - jet-set-willy

A kinda faithful recreation of the cult classic game for the ZX Spectrum, Jet Set Willy.
Jet Set Willy is a platform game where a tired Billy has to collect all the amulets spread out throughout multiple rooms.
These rooms are constantly filled with enemies, and Billy must avoid these in order to complete his objective of collecting
said amulets and be able to finally go have a good night sleep in his comfy bed.

-This project was made by Pedro Beirão(up202108718), João Dias da Silva(202108713) and Maria Sofia Minnemann(202007342)

### IMPLEMENTED FEATURES

- **Jumping** - The player is able to jump up, or in the direction he was walking before by pressing the space bar, experiencing a bit of acceleration during said jump.
- **Collision** - Collisions are an extremely important feature, enabling the creation of well programmed enemies and objects.
- **Stationary enemies** - Stationary enemies and objects are spread out through the house to challenge the player, forcing the player to jump while taking the movement of the enemies into account.
- **Moving enemies** - Moving enemies provide a bigger challenge for the player, having to jump but account for the movement of the enemy itself.
- **Toilet** - Just like the original game, in the starting room a toilet, which is constantly opening and closing, can be found in the starting room.
- **Multiple Rooms** - In order to provide a better experience, multiple rooms are available for the player to traverse through during his adventure.
- **Stairs** - Stairs can either allow the player to go upward, onto a higher room or platform or to jump through them and proceed onwards.
- **Ladders** - Ladders give you the ability to go upwards onto platforms too far for your reach without them.

### PLANNED FEATURES

- **Amulets** - Amulets spread throughout the rooms that the player must collect to finish the game.
- **Menu** - A start menu for the game.
- **Bed** - A comfy bed for billy to rest at the end of the game.
- **colors** - Different colors for the enemies.

### DESIGN

- **Problem in Context:** While developing the game, we noticed that some classes had many different methods, resulting in a visually unappealing code. It was also hard to change some features without affecting other part of the code.
 
- **The Pattern:** We have applied the MVC design pattern. This pattern is used to divide the app in three parts:
  - Model: contains the data;
  - View: displays the model. it can access the data in it but cannot change it;
  - Control: exists between the model and the view. It provides data for the model to give the view and interprets actions made by the user;

  This pattern allowed to help break up the frontend and backend code into separate components. This way, it is much easier to manage and make changes/add features. It also helped solve the problem identified before by (......COMPLETAR)

- **Implementation:** //meter UML e aonde estao as classes ver no exemplos

- **Consequences:** Using this architecture has the following benefits:
  - One person can develop View while other is making changes on other components, making the development faster;
  - It is possible to provide multiple views (for example the game and the menu);
  - It is easier to add or change features
  
  On the other hand:
  - Using the MVC architecture patterns adds complexity to the code;


**Example of one of such subsections**:

------

#### THE JUMP ACTION OF THE KANGAROOBOY SHOULD BEHAVE DIFFERENTLY DEPENDING ON ITS STATE

**Problem in Context**

There was a lot of scattered conditional logic when deciding how the KangarooBoy should behave when jumping, as the jumps should be different depending on the items that came to his possession during the game (an helix will alow him to fly, driking a potion will allow him to jump double the height, etc.). This is a violation of the **Single Responsability Principle**. We could concentrate all the conditional logic in the same method to circumscribe the issue to that one method but the **Single Responsability Principle** would still be violated.

**The Pattern**

We have applied the **State** pattern. This pattern allows you to represent different states with different subclasses. We can switch to a different state of the application by switching to another implementation (i.e., another subclass). This pattern allowed to address the identified problems because […].

**Implementation**

The following figure shows how the pattern’s roles were mapped to the application classes.

![img](https://www.fe.up.pt/~arestivo/page/img/examples/lpoo/state.svg)

These classes can be found in the following files:

- [Character](https://web.fe.up.pt/~arestivo/page/courses/2021/lpoo/template/src/main/java/Character.java)
- [JumpAbilityState](https://web.fe.up.pt/~arestivo/page/courses/2021/lpoo/template/src/main/java/JumpAbilityState.java)
- [DoubleJumpState](https://web.fe.up.pt/~arestivo/page/courses/2021/lpoo/template/src/main/java/DoubleJumpState.java)
- [HelicopterState](https://web.fe.up.pt/~arestivo/page/courses/2021/lpoo/template/src/main/java/HelicopterState.java)
- [IncreasedGravityState](https://web.fe.up.pt/~arestivo/page/courses/2021/lpoo/template/src/main/java/IncreasedGravityState.java)

**Consequences**

The use of the State Pattern in the current design allows the following benefits:

- The several states that represent the character’s hability to jump become explicit in the code, instead of relying on a series of flags.
- We don’t need to have a long set of conditional if or switch statements associated with the various states; instead, polimorphism is used to activate the right behavior.
- There are now more classes and instances to manage, but still in a reasonable number.


### TESTING

- Screenshot of coverage report.
- Link to mutation testing report.

### SELF-EVALUATION

> In this section describe how the work regarding the project was divided between the students. In the event that members of the group do not agree on a work distribution, the group should send an email to the teacher explaining the disagreement.

**Example**:

- John Doe: 40%