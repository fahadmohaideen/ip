# Project Tom

**Tom** is a friendly chatbot who helps you keep track of your **recurring tasks, events, and deadlines**.  
It saves your data in a file and loads them into an array of tasks whenever Tom is run.  

Tom allows you to:  
- **Mark or unmark tasks** (once done)  
- **Remove tasks** from the list  
- **Search tasks** either by **date/time** or by **keywords**  

---

## How to Download Tom

1. Click on **Releases** in the repository.  
2. Download **ip1.jar** to get the Java program for Tom on your computer.  

> You may also download the `.zip` or `.tar.gz` files to view source code,  
> but note that they will **NOT contain executable files**.  
> In that case, you would need to manually locate and run `Main.java`.  

---

## How to Run Tom

Open a **command line terminal**, then run:

```bash
java -jar ip1.jar
```
## Commands

### Adding Tasks

- **To add a ToDo task**:
```
todo <TASK_NAME>
```
Example:
```
todo read book
```
- **To add an event**:
```
event <EVENT_NAME> /from <dd/MM/YYYY HHmm> /to <dd/MM/YYYY HHmm>
```
Example:
```
event cs2113 lecture /from 03/10/2025 1600 /to 03/10/2025 1800
```
- **To add a deadline**:
```
deadline <DEADLINE_NAME> /by <dd/MM/YYYY HHmm>
```
Example:
```
deadline cs2113 lecture quiz /by 03/10/2025 2359
```
### Manage Tasks

- **To list all current tasks**:
```
list
```
- **To mark or unmark a task**:
```
mark <INDEX_OF_TASK_IN_LIST>
unmark <INDEX_OF_TASK_IN_LIST>
```
Example:
```
mark 3
unmark 4
```
- **To delete a specific task**:
```
delete <INDEX_OF_TASK_IN_LIST>
```
Example:
```
delete 5
```

### Search Tasks
- **To search by date and time**:
```
search_by_date <dd/MM/YYYY HHmm>
```
Example:
```
search_by_date 12/10/2025 1700
```

> For ***deadlines***, as long as they are due on the entered date,
they will still be displayed even if the time differs.
For example, a deadline at ```12/10/2025 2359``` will still appear when searching ```12/10/2025 1700```

- **To search by keyword**:
```
search_by_keyword <KEYWORD>
```
Example:
```
search_by_keyword math quiz
```

### Error Handling
> If you enter commands in the ***wrong format***, Tom will throw an ***exception message** to indicate that the arguments were entered incorrectly. If you are unable to understand where you went wrong, please feel free to refer back to this user guide. 
 









