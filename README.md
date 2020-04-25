README
====

This project implements multiple grid-based games and allows players to play and customize their profile and experience.

Names: Juliet Yznaga (jay18), Natalie Novitsky (nen4), Tanvi Pabby (tp156), Alyssa Shin (as895), Tyler Meier (tkm22)



### Timeline

Start Date: 4/2/20

Finish Date: 4/23/20

Hours Spent: 25-30 hours per week per person

### Primary Roles
* Tanvi was responsible for (along with Natalie) the function of the Engine component of the project. This included designing the representation of a Grid and its Cells. The Engine was also responsible for validating the move of a player, and updating the grid, as well as the game stats (such as score, and loss statistic) according to the rules of the given game.
* Juliet was responsible for the Data back end. The Data end had to parse all of the files for the games and engines and distribute them to the Player and Engine through the Controller. Juliet was also responsible for managing all of the UserProfiles and their data (logging in, leader board, creating new profiles). Also, Juliet handled exceptions, saving/loading files and did a lot of work with the User Defined Games.
* Alyssa and Tyler were responsible for the Player front end. The front end communicates with the Controller to get information from the backend to display the correct screen, which entails a front end grid with the correct dimensions and images, as well as animated buttons and game statistics that update. Player manages multiple screens, including the login screen, new profile screen, start screen, gameplay screen, leaderboard screen, as well as screens that displays the result of the game (win/loss).


* Natalie worked with Tanvi on the Engine primarily, and worked with the Data and Player sections to ensure that the three sections were passing appropriate information. In Engine, she built and integrated auto-generating Grids, two types of Validators and MatchFinders, a Grid class to manage the board, and a GameProgressManager to manage the state of a game. Natalie also worked on the initial Controller, which was then edited by the whole team as the project evolved.

### Resources Used
* StackOverflow
* Kyle Hong! (our TA)
* JUnit testing examples (from lecture)
* Official Java documentation
* zapsplat.com for the sounds
* kisspng.com for the images

### Running the Program

Main class: Controller, run main method from this class to run the program

Data files needed: XML defaults, resource folders and files

Downloads needed:
* Files in test directory, Download JUnit by Project Settings > Libraries > + (from Maven) > name = “org.testfx:testfx-junit5:4.0.15-alpha” > Ok
* Mark data as resources root
* Add Test as test directory
* Add TestData as test resources root


Features implemented: Basic implementation of grid based games, as well as levels, high scores, user profiles, user preferences, saving and loading games, ability to make a user defined game, ability to choose preferences (UI), and sounds.



### Notes/Assumptions

Assumptions or Simplifications: In making this project, we assumed that all games would be won by reaching a target score, all games would be lost with either running out of moves, lives, or time (based on the specific game). We also assumed that, for games that are like bewjeweled and candy crush, matches of 3 or more in a line (not in a square) would count as valid matches. We also assumed that having user profile is allowed in the frontend.

Interesting data files:
Our program is able to operate even if there is not a grid specified by the XML. If you look at any of the BejeweledAction XMLs, they do not include a specified grid. Since no grid is found, the Engine is able to produce a random board. It is also able to do this for minecraft boards.

Known Bugs: Sounds do not work perfectly as intended. Also, the delays as cells are matched, deleted,and opened are not great. The user, when designing their own game, can create games that could be considered 'dumb' - eg. they could theoretically design their initial grid configuration to have matches (three matching cells in a row) before the game even starts.

Extra credit: User defined games, sounds

Easter eggs: board creation for games that don't specify a grid, the minecraft game is quite unlike all of the other ganes so we consider it an easter egg, naughty names not allowed

### Any data or resource files required by the project

Player resource bundles:
* For styling:
    * darkmode.css
    * default.css
    * greenmode.css
    * rainbow.css
    * wavemode.css
* For image paths:
    * BejeweledAction
    * BejeweledEndless
    * BejeweledPuzzle
    * CandyCrush
    * ClassicMemory
    * Memory
    * Minesweeper
* For text:
    * BasicStrings
    * ButtonCreation
    * NamesOfGames
    * NewGameStrings
* For sounds:
    * Sounds
* For building User Defined Games:
    * ImageGroups
    * EngineKeys
    * GameKeys
    * NewGridKeys
    * NewGameStrings

Data resource bundles
* Default paths
    * DefaultEnginePaths
    * DefaultGamePaths
* Profile reading
    * ProfileKeys
    * InvalidNameEntries
* Reading XML (keys for maps with default values)
    * EngineKeys
    * GameKeys
    * NewGridKeys
    * ImageGroups (for user defined games)


### Impressions
Juliet: I loved the assignment of this project. I loved being able to build a game again, I just have way more fun building something to be able to play. Given the circumstances, this was the perfect assignment and I think if we were given a more challenging prompt then we would not have been able to create a finished product. It was extremely hard developing a working model without being in close proximity to our group members. Trying to integrate the different parts remotely was quite challenging but I think it gave me a great set of communication skills which I would not have learned if we had been working in person. It was very helpful that we learned JUnit but I think it would have been more helpful to have learned JUnit a week before because by the time I got to the lecture, I had already written code. The tests would have been immensely helpful to experiment because I ended up writing individual main methods that I had to comment and uncomment every time I wanted to test.

Tanvi: I thought that working on this project given the unique circumstances made things a bit challenging, but overall, the project was not terribly stressful, and it was fun to work on, especially once we established the baseline functionality and were able to add cool features. I learned a lot about how to write well-designed code by looking at my teammates’ code, and learning from them. This project seemed to integrate a lot of the concepts we learned in this course over the semester, so I feel that I was able to get a better handle on a lot of the concepts we have learned such as reflection, binding, just designing overall flexible code. Remembering to make JUnit tests was a bit challenging as it was not something we had used before, but they proved to be helpful. Overall, I think that our group did the best we could to put this project together, and I think it turned out great. We did a good job of communicating, and finishing the tasks that we said we would, and this made for a great final project experience :)

Alyssa: I thought that this project was the perfect final assignment for this class. I enjoyed that this project allowed for a lot of flexibility in design, from which games we wanted to create to what interfaces we wanted to make to how the games looked. We were able to employ everything that we learned and practiced during previous projects and labs, such as inheritance, lambda expressions, good coding practices (reducing redundant lines of code). I learned a lot from making design decisions with my teammates, coding with my teammates, and reading their code. The circumstances under which we finished this project also added an element of challenge and surprise. It pushed me to be proactive about scheduling meetings and using online resources to stay in touch with my teammates across the ocean. Ultimately, I am really proud of what we were able to create in the time we had with the resources that we had.

Natalie: This project effectively summed up all we had learned throughout the class--both design elements and teamwork skills--and was a fun way to show how far we've come. We could take the prompt in many different directions, giving us an opportunity to be creative that often gets left out in academic assignments. Especially in a time when I feel disconnected from the Duke community, this project created an opportunity to spend a lot of time working together (on Zoom or FaceTime, but still better than none) and feel connected again. This project also allowed me to learn a lot from other people's coding/design choices, as it was such a big project that there was no way any one person could influence all design choices. Overall, given the extraordinary circumstances, this project was adaptable as I can imagine would be possible and still gave us a good experience to both learn more and demonstrate our progress.

Tyler: I really enjoyed doing this project, especially with the teammates that I had. It was a great way to wrap up everything that we had learned throughout the semester. It was definitely a bit challenging to do being that we were not in person working on it together, but it was also a good learning experience creating the project remotely. I really enjoyed doing frontend for this project as it was still something I am new with and it was good to keep practicing this type of code. I am also pretty proud of the work that I did, and the work that we did as a group. I think our game turned out really well and is something that I would actually enjoy playing. One thing that I didn't enjoy was doing the junit tests for frontend because 1) it was hard to figure out how to even do it for front end at first and 2) it was hard picking what things to even test. So, I am not a fan of javafx testing. Overall though, I really enjoyed this project, way more than I thought I would be given the remote circumstances.
