# Magus Chatbot User Guide

Magus is a Command-Line Interface application for managing your tasks

* [Quick start](#quick-start)
* [Features](#features)
    * [Adding a todo task: `todo`](#adding-a-todo-task-todo)
    * [Adding a deadline task: `deadline`](#adding-a-deadline-task-deadline)
    * [Adding a event task: `event`](#adding-a-event-task-event)
    * [Listing all tasks: `list`](#listing-all-tasks-list)
    * [Mark task as done: `mark`](#marking-a-task-as-completed-mark)
    * [Mark task as not done: `unmark`](#marking-a-task-as-incomplete-unmark)
    * [Find task by description: `find`](#finding-tasks-by-description-find)
    * [Find task by date: `find`](#finding-tasks-by-date-find)
    * [Find task by task type and date / description: `find`](#finding-tasks-by-date-and-task-type-find)
    * [Delete task: `delete`](#deleting-a-task-delete)
    * [Exit program: `bye`](#exiting-the-program-bye)
    * [Saving the data](#saving-the-data)
* [FAQ](#faq)
* [Command summary](#command-summary)

## Quick start
1. Ensure you have Java `11` or above installed in your computer.
2. Download the latest `Magus.java` [release](https://github.com/wenenhoe/ip/releases/latest).
3. Copy the file to the folder you want to use the chatbot.
4. Open a terminal, `cd` into the folder you put the jar file in, and use `java -jar Magus.jar` command to run chatbot.
5. Refer to the [Features](#features) below for implementations of each command.

## Features

### Adding a todo task: `todo`

Adds a todo task to the task list. Todo tasks are tasks with a description and no additional parameters.

Format: `todo <DESCRIPTION>`

Examples:

* todo Complete the quiz
* todo Rewatch lecture recording
* todo Finish reading before lecture

Usage:
```
-----------------------------------------------
> todo Complete the quiz
	Got it. I've added this task:
		[T][ ] Complete the quiz
	Now you have 1 tasks in the list.
-----------------------------------------------
> 
```

### Adding a deadline task: `deadline`

Adds a deadline task to the task list with a date to be completed by.

Format: `deadline <DESCRIPTION> /by <DATE>`

Examples:

* deadline Sign up for workshop /by 2024-03-10
* deadline Complete quiz /by 2024-03-15
* deadline Setup environment /by 2024-03-08

Usage:
```
-----------------------------------------------
> deadline Sign up for workshop /by 2024-03-10
	Got it. I've added this task:
		[D][ ] Sign up for workshop (by: 2024-03-10)
	Now you have 2 tasks in the list.
-----------------------------------------------
>
```

### Adding a event task: `event`

Adds an event task to the task list with a start and an end time.

Format: `event <DESCRIPTION> /from <START_DATE> /to <END_DATE>`

Examples:

* event Attend workshop /from 2024-03-14 /to 2024-03-16
* event Join Zoom conference /from 2024-03-14 /to 2024-03-18
* event Attend project meeting /from 2024-03-18 /to 2024-03-20

Usage:
```
-----------------------------------------------
> event Attend workshop /from 2024-03-14 /to 2024-03-16
	Got it. I've added this task:
		[E][ ] Attend workshop  (from: 2024-03-14 to: 2024-03-16)
	Now you have 3 tasks in the list.
-----------------------------------------------
> 
```

### Listing all tasks: `list`

Shows a numbered list of all tasks in the task list.

Format: `list`

Usage:
```
-----------------------------------------------
> list
	Here are the tasks in your list:
	1.[T][ ] Complete the quiz
	2.[D][ ] Sign up for workshop (by: 2024-03-10)
	3.[E][ ] Attend workshop  (from: 2024-03-14 to: 2024-03-16)
-----------------------------------------------
> 
```

### Marking a task as completed: `mark`

Marks a task as done, using the task number from the `list` command.

Format: `mark <TASK_NUMBER>`

### Marking a task as incomplete: `unmark`

Marks a task as incomplete, using the task number from the `list` command.

Format: `unmark <TASK_NUMBER>`

### Finding tasks by description: `find`

Finds tasks whose description contains the search terms.

Format: `find <DESCRIPTION>`

Usage:
```
-----------------------------------------------
> find workshop
	Here are the matching tasks in your list:
	1.[D][ ] Sign up for workshop (by: 2024-03-10)
	2.[E][ ] Attend workshop  (from: 2024-03-14 to: 2024-03-16)
-----------------------------------------------
> 
```

### Finding tasks by date: `find`

Finds tasks whose date matches the search date. 

Format: `find /date <DATE>`

Usage:
```
-----------------------------------------------
> find /date 2024-03-10
	Here are the matching tasks in your list:
	1.[D][ ] Sign up for workshop (by: 2024-03-10)
-----------------------------------------------
> 
```

### Finding tasks by date and task type: `find`

Finds tasks whose date matches the task type and, search date or description.

Format: `find <TASK_TYPE> <ADDITIONAL_INPUT>`

* The field `<ADDITIONAL_INPUT>` corresponds to a description or the keyword arguments specified in their creation

| Task Type | Supported Formats                                                                                                                   |
|-----------|-------------------------------------------------------------------------------------------------------------------------------------|
| Todo      | `find todo <DESCRIPTION>`                                                                                                           |
| Deadline  | `find deadline <DESCRIPTION>` <br/> `find deadline /by <DATE>`                                                                      |
| Event     | `find event <DESCRIPTION>` <br/> `find event /from <DATE>` <br/> `find event /to <DATE>` <br/> `find event /from <DATE> /to <DATE>` |

Usage:
```
-----------------------------------------------
> find event workshop
	Here are the matching tasks in your list:
	1.[E][ ] Attend workshop  (from: 2024-03-14 to: 2024-03-16)
-----------------------------------------------
> find deadline workshop
	Here are the matching tasks in your list:
	1.[D][ ] Sign up for workshop (by: 2024-03-10)
-----------------------------------------------
> find event /from 2024-03-14
	Here are the matching tasks in your list:
	1.[E][ ] Attend workshop  (from: 2024-03-14 to: 2024-03-16)
-----------------------------------------------
> 
```

### Deleting a task: `delete`

Deletes the specified task from the task list, using the task number from the `list` command.

Format: `delete <TASK_NUMBER>`

Usage:
```
-----------------------------------------------
> list
	Here are the tasks in your list:
	1.[T][ ] Complete the quiz
	2.[D][ ] Sign up for workshop (by: 2024-03-10)
	3.[E][ ] Attend workshop  (from: 2024-03-14 to: 2024-03-16)
-----------------------------------------------
> delete 1
	Noted. I've removed this task:
		[T][ ] Complete the quiz
	Now you have 2 tasks in the list.
-----------------------------------------------
> 
```

### Exiting the program: `bye`

Exits the program.

Format: `bye`

### Saving the data

The task list data are saved automatically to the file system after each command that modifies the task list.
The default location of the save file is a relative file path from `Magus.jar` and is located in a subfolder 
`./data/Magus.txt`.

--- 

## FAQ

**Q:** How do I transfer my data to another Computer?

**A:** Install the app on the other computer and overwrite the empty data file
it creates with the file that contains the data.

---

## Command summary

| **Commands** | **Usage**                                                                                   |
|--------------|---------------------------------------------------------------------------------------------|
| **todo**     | `todo <DESCRIPTION>`                                                                        |
| **deadline** | `deadline <DESCRIPTION> /by <DATE>`                                                         |
| **event**    | `event <DESCRIPTION> /from <START_DATE> /to <END_DATE>`                                     |
| **list**     | `list`                                                                                      |
| **mark**     | `mark <TASK_NUMBER>`                                                                        |
| **unmark**   | `unmark <TASK_NUMBER>`                                                                      |
| **find**     | `find <DESCRIPTION>` <br/> `find /date <DATE>` <br/> `find <TASK_TYPE> <ADDITIONAL_INPUT>`  |
| **delete**   | `delete <TASK_NUMBER>`                                                                      |
| **bye**      | `bye`                                                                                       |

---