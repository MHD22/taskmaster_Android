# Taskmaster Android App:

This App will contains tasks to do.. and you can add tasks and display your all tasks
There ara a few main activities:

1. `MainActivity`
2. `AddTaskActivity`
3. `AllTasksActivity`
4. `TaskDetailsActivity`
5. `SettingsActivity`

### _________________________________

***first day changes (lab 26):***

* created the three activities and add the appropriate components inside them.
* connect them with the buttons in the MainActivity(Add Task, All Tasks).
* No functionalities yet, except the move on the activities using the previous two buttons

*Screenshots:*

MainActivity:

<img src="screenshots/mainActivity1.png" alt="Main activity" width="300" height="500"/>

<br>
<br>
<br>
<br>


AddTaskActivity:

<img src="screenshots/addTask1.png" alt="Add Task Activity" width="300" height="500"/>
<br>
<br>
<br>
<br>


AllTasksActivity:

<img src="screenshots/allTasks1.png" alt="All Tasks Activity" width="300" height="500"/>

### _________________________________


***Second day changes (lab 27):***

* update the home page (Main Activity) to be contains main title which hold the user name (which is set by user from the settings page) and three tasks buttons, and one button for settings.
* Add TaskDetailActivity that contains title (from the tapped button of the home page), and hard coded description for now.
* Add SettingsActivity, which contain field to enter the userName, and save button to save the user name in the sharedPreferences.. to persist and access this name from the homePage.

*Screenshots:*

MainActivity:

<img src="screenshots/mainActivity2.png" alt="Main activity" width="300" height="500"/>

<br>
<br>
<br>
<br>


TaskDetailsActivity:

<img src="screenshots/taskDetails2.png" alt="Add Task Activity" width="300" height="500"/>
<br>
<br>
<br>
<br>


SettingsActivity:

<img src="screenshots/settings2.png" alt="All Tasks Activity" width="300" height="500"/>

### _________________________________


***Third day's changes (lab 28):***

* update the home page (Main Activity) to be contains main title which hold the user name (which is set by user from the settings page) and RecyclerView which hold the list of tasks and view it as a list for the user, and one button for settings.
* create a fragment to hold the style for each task..(title, body, state)
* create taskAdapter for binding the data with the view(fragment styles)
* connect them together and set onClick listener on each ViewHolder to response for user click and go on the task details page with the title of the tapped task

*Screenshots:*

MainActivity:

<img src="screenshots/mainActivity3.png" alt="Main activity" width="300" height="500"/>

<br>
<br>
<br>
<br>


TaskDetailsActivity: (same as the previous one)

<img src="screenshots/taskDetails3.png" alt="Add Task Activity" width="300" height="500"/>
<br>
<br>
<br>
<br>


SettingsActivity: (same as the previous one)

<img src="screenshots/settings2.png" alt="All Tasks Activity" width="300" height="500"/>

### _________________________________


***Forth day's changes (lab 29):***

* update the home page to be contains main title which hold the user name (which is set by user from the settings page) and RecyclerView which hold the list of tasks and view it as a list for the user, a button for settings, and a button for add a task.
* Edit the TaskDetailsActivity to reflect the all data entered by the user (Title, Body, State), Not just the title.
* create a Room DataBase which is local db that hold the user's data (task).
* create the task entity and task DAO (Data Access Object) to let me manipulate the data in the DB (read, add, update, delete).
* Edit in the mainActivity especially in the decleration of the Adapter .. to take the list of tasks from the DB (getAllTasks())

*Screenshots:*

MainActivity:

<img src="screenshots/mainActivity4.png" alt="Main activity" width="300" height="500"/>

<br>
<br>
<br>
<br>


TaskDetailsActivity:

<img src="screenshots/taskDetails4.png" alt="Add Task Activity" width="300" height="500"/>
<br>
<br>
<br>
<br>

<img src="screenshots/addTask1.png" alt="Add Task Activity" width="300" height="500"/>
<br>
<br>
<br>
<br>


SettingsActivity: (same as the previous one)

<img src="screenshots/settings2.png" alt="All Tasks Activity" width="300" height="500"/>



### _________________________________


***Forth day's changes (lab 29):***

* Add the utomated testing to the app.. using Espresso.
* assert that the basic components are displayed
* write tests that check for some functionalities are working correctly Like( addTask button, Settings and save buttons, click on the recyclerView's viewHolder...)

*Screenshots:*

No changes in the style of pages or additional activities.

### _________________________________

***Fifth day's changes (lab 29):***

* configure and add the Amplify dynamoDB to the app
* create GraphQL model as a schema and the Amplify generated it as model class.
* save and retrieve the data from the dynamoDB instead of RoomDB
* edit on styles for the fragment and the all activities.
* add spinner in the add task form to select the state of the task.
* add goBack button in the action bar to go back from the (TaskDetailsActivity, SettingsActivity, AddTaskActivity) to the MainActivity.

*Screenshots:*

MainActivity:

<img src="screenshots/mainActivity5.png" alt="Main activity" width="300" height="500"/>

<br>
<br>
<br>
<br>


TaskDetailsActivity:

<img src="screenshots/taskDetails5.png" alt="Add Task Activity" width="300" height="500"/>
<br>
<br>
<br>
<br>


AddTaskActivity:
<img src="screenshots/addTask5_1.png" alt="Add Task Activity" width="250" height="500"/> 
<img src="screenshots/addTask5_2.png" alt="Add Task Activity" width="250" height="500"/>
<br>
<br>
<br>
<br>

### _________________________________