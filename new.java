import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;


public class Main{
    static void main(String[] args){
        Scanner input = new Scanner(System.in);
        
//The purpose of the HashMap is for searching or retrieval of a note.
        HashMap<String, StickyNote> noteMap = new HashMap<>();
        
//I used arrayList for this one because it is not really used for retrieval. It is only to display the list of the deleted note(s).
        ArrayList<StickyNote> deletedList = new ArrayList<>();
        
//This arrayList's sole purpose is for displaying the notes if the user chooses choices 2-4. This arrayList serves as the "Menu".
        ArrayList<StickyNote> existingList = new ArrayList<>();


        System.out.println("Welcome to Sticky Notes CLI!");
        int choice;
        do {
            
//This variable is used as a "bait" so if the user input is an error, it is initialized as this number.
            int number = 404;
            System.out.println(
"""
                    1 - Create Note
\
                    
                    2 - Read Note
\
                    
                    3 - Update Note
\
                    
                    4 - Delete Note
\
                    
                    Press 0 to Exit""");
            System.out.print("What do you want to do? (1-5): ");


            
//This is the error handling using that variable.
            try{
                number = input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("\nError: Invalid Input!");
            }


            
//The input.nextInt() in Line 35 is terminated here (Short Explanation but the reason is long).
            input.nextLine();
            System.out.println();


            
//Finally, this choice variable that was initialized outside the loop is assigned as the number based from the try-catch block.
            choice = number;
            StickyNote note;
            switch (choice) {
                case 1: 
//Create
                    //Setting the Title and the Content
                    System.out.print("Title: ");
                    String name = input.nextLine();
                    System.out.println();


                    System.out.println("Write your content: ");
                    String content = input.nextLine();
                    System.out.println();


                    
//The sticky note is now newly created as the <Title> with <Content> using a constructor
                    note = new StickyNote(name, content);
                    noteMap.put(name, note);
                    existingList.add(note);
                    break;
                case 2: 
//Read
                    //This is so that the system does not return an error if the user views an empty menu.
                    if (existingList.isEmpty()) {
                        System.out.println("You don't have any notes yet, you may create one!");
                    } else {
                        for (StickyNote noteDisplay : existingList) {
                            System.out.println("* " + noteDisplay.getName());
                        }


                        
//The user is prompted to put the title of the desired note based on the displayed list.
                        System.out.print("\nWhich note would you like to read?: ");
                        String view = input.nextLine();
                        System.out.println();


                        
//It is then retrieved using this code block. I used this way because HashMaps are used better in this task.
                        if (noteMap.containsKey(view)) {
                            note = noteMap.get(view);
                            System.out.println(note.getContent());
                        } else {
                            System.out.println("Note is not found!");
                        }
                    }
                    System.out.println();
                    break;
                case 3:
                    if (existingList.isEmpty()) {
                        System.out.println("You don't have any notes yet, you may create one!");
                    } else {
                        for (StickyNote noteDisplay : existingList) {
                            System.out.println("* " + noteDisplay.getName());
                        }


                        System.out.print("Which note would you like to edit?: ");
                        String update = input.nextLine();
                        System.out.println();


                        if (noteMap.containsKey(update)) {
                            note = noteMap.get(update);
                            System.out.println("1 - Title\n2 - Content");
                            System.out.print("What would you like to edit?: ");
                            int editChoice;
                            int num = 0;
                            try{
                                num = input.nextInt();
                            } catch (InputMismatchException e){
                                System.out.println("Invalid");
                            }
                            editChoice = num;
                            input.nextLine();


                            if (editChoice == 1) {
                                System.out.println("Current Title:" + note.getName());
                                System.out.print("New Title: ");
                                String newName = input.nextLine();
                                StickyNote updatedNote = noteMap.remove(update);
                                noteMap.put(newName, updatedNote);
                                note.setName(newName);
                            } else if (editChoice == 2) {
                                System.out.println(note.getContent());
                                System.out.println("\nWrite your new content below:");
                                String updatedContent = input.nextLine();
                                note.setContent(updatedContent);
                            }


                        } else {
                            System.out.println("Note is not found!");
                        }
                    }
                    System.out.println();
                    break;
                case 4:
                    if (existingList.isEmpty()) {
                        System.out.println("You don't have any notes yet, you may create one!");
                    } else {
                        for (StickyNote noteDisplay : existingList) {
                            System.out.println("* " + noteDisplay.getName());
                        }


                        System.out.print("What code would you like to delete?: ");
                        String delete = input.nextLine();
                        System.out.println();


                        if (noteMap.containsKey(delete)) {
                            note = noteMap.get(delete);
                            existingList.remove(note);
                            deletedList.add(note);
                        } else {
                            System.out.println("Note is not found!");
                        }
                    }
                    System.out.println();
                    break;
                
//Cases 5&6 are optional. Just a safety net for the system in case the user accidentally deletes a note.
                case 5:
                    if (deletedList.isEmpty()) {
                        System.out.println("There are no recently deleted notes!");
                    } else {
                        for (StickyNote noteDisplay : deletedList) {
                            System.out.println("* " + noteDisplay.getName());
                        }


                        System.out.println("Which note do you want to delete permanently?: ");
                        String delPerm = input.nextLine();
                        note = noteMap.get(delPerm);
                        deletedList.remove(note);
                        noteMap.remove(delPerm);
                    }
                    break;
                case 6:
                    if (deletedList.isEmpty()) {
                        System.out.println("There are no recently deleted notes!");
                    } else {
                        for (StickyNote noteDisplay : deletedList) {
                            System.out.println("* " + noteDisplay.getName());
                        }


                        System.out.println("Which note do you want to recover?: ");
                        String delPerm = input.nextLine();
                        note = noteMap.get(delPerm);
                        deletedList.remove(note);
                        existingList.add(note);


                    }
                    break;
                
//The default case is for general errors that are outside the scope of InputMismatchException.
                default:
                    if (choice != 404 && choice != 0){
                        System.out.println("Error: Unknown Command\n");
                    }
            }


        } while (choice != 0);
        System.out.println("Program terminated!");
    }
}


class StickyNote{
    
//Fields - This is so that we can set or "define" something that can be used for our class.
    private String name;
    private String content;


    
//Constructor - this is to set the definitions of the fields, the bread and butter of OOP.
    public StickyNote(String name, String content){
        this.name = name;
        this.content = content;
    }


    
//The getters and the setters method - gets input from Main class and then sets it to "define" one content.
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getContent(){
        return content;
    }
    public void setContent(String content){
        this.content = content;
    }
}