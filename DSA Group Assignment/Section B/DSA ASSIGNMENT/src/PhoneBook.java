import java.util.Scanner;
import java.io.*;
/*
 * @Authors France Lugambo, Gabriel Justin, Abed B Indongo, Mathias Indongo, Redemptus Muyeu Jr, Stern Nyambe
 * */
public class PhoneBook {

    private class ListNode{
        //declaring private instances of the node
        private String name;
        private String phoneNumber;
        private ListNode next;

        public ListNode(String name, String phoneNumber) { //setting up node structure
            this.name = name;
            this.phoneNumber = phoneNumber;
            this.next = null;
        }
    }

    //Declaring private instances for head pointer, tail pointer and linked list length
    private ListNode head;
    private ListNode tail;

    //constructor private instances for head pointer, tail pointer and linked list length
    public PhoneBook() {
        this.head = null;
        this.tail = null;
    }

    //function that checks if the linked list is empty
    public boolean isEmpty (){
        return head == null;
    }

    //function that inserts a new node to the end of the linked list
    public void insertLast(String name, String phoneNumber){
        ListNode newNode = new ListNode(name,phoneNumber);  //add data to the new node


        if(isEmpty())  // check if the list is empty
        {
            head = newNode;  //point head pointer to newly created node
        }else
        {
            tail.next = newNode;  //add the new node to the list
        }
        tail = newNode;  //make newly created node the last node
        System.out.println("Phone number added successfully!");
    }

    //function for displaying data of the linked list
    public void display(){
        if(isEmpty())  // check if the list is empty
        {
            System.out.println("List is empty");
            return;  //exit the function
        }

        ListNode temp = head;  //initialize temporary pointer to traverse the list
        while(temp != null)  //while loop for list traversal and displaying contacts
        {
            System.out.print(temp.name + " " +temp.phoneNumber + " -->");
            temp = temp.next;
        }
        System.out.print("null");
    }

    //function to delete a specific node in the list
    public void delete(String name, String number){
        if(isEmpty())  // check if the list is empty
        {
            System.out.println("List is empty");
            return;  //exit the function

        }

        ListNode temp = head;  //initialize temporary pointer to traverse the list
        ListNode prev = null;  //initialize temporary pointer point at the temp node

        while(temp != null && !temp.phoneNumber.equals(number) && !temp.name.equals(name))  //while loop for list traversal
        {
            prev = temp;
            temp = temp.next;
        }

        if(temp == null)  //check if the contact has not been found in the list
        {
            System.out.println("Phone number not found!");
            return;  //exit the function
        }else if(temp == head)//check if the contact is first in the list
        {
            head = head.next;  //change head pointer to point to the second contact in the list
        }else
        {
            prev.next = temp.next; //change the node before the node being deleted to point to the node after the node to be deleted
        }

        if(temp == tail)//check if the contact is last in the list
        {
            tail = prev;  //change tail pointer to point to the second last node
        }

        System.out.println(temp.name + " " + temp.phoneNumber + " deleted!");
    }

    //function to update data of a specific node
    public void update(String name,String newName,String number,String newNumber, int newSortNumber){
        if(isEmpty())  // check if the list is empty
        {
            System.out.print("List is empty");
            return; //exit the function

        }

        ListNode temp = head;  //initialize temporary pointer to traverse the list
        while(temp != null && !temp.phoneNumber.equals(number) && !temp.name.equals(name))  //while loop for list traversal
        {
            temp = temp.next;
        }

        if(temp == null)  //check if the contact has not been found in the list
        {
            System.out.println("Node not found");
            return;  //exit the function
        }

        if(!newName.equals("0"))
        {
            temp.name = newName; //update name of node
        }
        if(!newNumber.equals("0"))
        {
            temp.phoneNumber = newNumber;  //update phone number of node
        }
        System.out.println("Phone number succesfully updated");
    }

    //function to check if entered phone number is in valid format
    public static int parseInteger(String num){
        try{
            int number = Integer.parseInt(num);
            if(number > 0)
            {
                return number;
            }
        }catch(NumberFormatException e){
            System.out.println("Invalid phone number format. Please enter a valid number.");
        }
        return -1;
    }

    //function that searches for searching the list by first sorting the list
    public void search(String name, String number){
        if(isEmpty())  // check if the list is empty
        {
            System.out.println("List is empty");
            return; //exit the function

        }

        ListNode temp = head;  //initialize temporary pointer to traverse the list
        while(temp != null && !temp.phoneNumber.equals(number) && !temp.name.equals(name))  //while loop for list traversal
        {
            temp = temp.next;
        }

        if(temp == null)  //check if the contact has not been found in the list
        {
            System.out.println("Phone number not found!");  //display found contact
            return;
        }else
        {
            System.out.println("Phone number found. Name of owner is " + temp.name + " and the phone number is " + temp.phoneNumber + ".");
        }
    }

    public void saveToFile(String PhoneBookList)throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(PhoneBookList));
        ListNode temp = head;
        while (temp != null)
        {
            writer.write(temp.name + "," + temp.phoneNumber);
            writer.newLine();
            temp = temp.next;
        }
        writer.close();
    }

    public void loadFromFile(String PhoneBookList) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(PhoneBookList));
        String line;
        while ((line = reader.readLine()) != null)
        {
            String[] parts = line.split(",");
            if (parts.length == 2)
            {
                insertLast(parts[0], parts[1]);
            }
        }
    }

    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);
        PhoneBook phoneBook = new PhoneBook();

        try {
            phoneBook.loadFromFile("PhoneBookList.txt");
        }catch(IOException e) {
            System.out.println("No existing file found or an error occurred while loading the file.");
        }

        String userInput;
        do {
            //Prompt user for what action they want to take and get input
            System.out.println("Choose among the options below using the option numbers:");
            System.out.println("1. Insert contact");
            System.out.println("2. Search contact");
            System.out.println("3. Display all contacts");
            System.out.println("4. Delete contact");
            System.out.println("5. Update contact ");
            int option = sc.nextInt();

            int parseNumber = -1;
            switch (option) {
                case 1:
                    //prompt user for name and phone number to be inserted and get input
                    System.out.print("Enter name: ");
                    String insertName = sc.next();
                    System.out.print("Enter phone number: ");
                    String insertPhoneNumber  = sc.next();

                    parseNumber = parseInteger(insertPhoneNumber);  //check if number is in a valid format

                    if(parseNumber != -1 && !insertName.isEmpty()) {
                        phoneBook.insertLast(insertName, insertPhoneNumber);
                    }else{
                        System.out.println("Enter all fields correctly");
                    }
                    break;
                case 2:
                    //prompt user whether they want to use a phone number or name to search for contact details
                    String searchName = null;
                    String searchPhoneNumber = null;

                    //prompt user whether to search with name or phone number
                    System.out.println("Search by phone number or name, enter 1 for phone number or 2 for name: ");
                    int searchOption = sc.nextInt();
                    switch (searchOption) {
                        case 1:
                            System.out.println("Enter phone number: ");  //prompt the user for the phone number to search for
                            searchPhoneNumber = sc.next();

                            //check if number is correct format
                            parseNumber = parseInteger(searchPhoneNumber);  //check if number is in a valid format

                            if(parseNumber != -1) {
                                phoneBook.search(searchName, searchPhoneNumber); //call function to search for contact details
                            }
                            break;
                        case 2:
                            System.out.println("Enter name: ");  //prompt the user for the name to search for
                            searchName = sc.next();

                            if(searchName != null) {
                                phoneBook.search(searchName, searchPhoneNumber);  //call function to search for contact detail
                            }else{
                                System.out.println("Enter all fields correctly");
                            }
                            break;
                        default:
                            System.out.print("Choice not available!");
                            break;
                    }
                    break;
                case 3:
                    //call function to display all the contact details
                    phoneBook.display();
                    break;
                case 4:
                    String deleteName = null;
                    String deletePhoneNumber = null;

                    //prompt user whether they want to use a phone number or name to delete contact details
                    System.out.println("Delete using a phone number or name, enter 1 for phone number or 2 for name: ");
                    int deleteOption = sc.nextInt();

                    switch (deleteOption) {
                        case 1:
                            System.out.println("Enter phone number: ");  //prompt the user for the phone number to delete
                            deletePhoneNumber = sc.next();

                            parseNumber = parseInteger(deletePhoneNumber);  //check if number is in a valid format
                            if(parseNumber != -1) {
                                phoneBook.delete(deleteName, deletePhoneNumber);  //call the function to delete contact details
                            }

                            break;
                        case 2:
                            System.out.println("Enter name: ");  //prompt the user for the name to delete
                            deleteName = sc.next();

                            if(deleteName != null) {
                                phoneBook.delete(deleteName, deletePhoneNumber);  //call the function to delete contact details
                            }else{
                                System.out.println("Enter all fields correctly");
                            }

                            break;
                        default:
                            System.out.print("Choice not available!");
                            break;
                    }
                    break;
                case 5:
                    String updateName = null;
                    String updatePhoneNumber = null;
                    String newName = null;
                    String newPhoneNumber = null;

                    //prompt user whether to search with name or phone number
                    System.out.println("Update by phone number or name, enter 1 for phone number or 2 for name: ");
                    int updateOption = sc.nextInt();

                    switch (updateOption) {
                        case 1:
                            System.out.println("Enter phone number: ");  //prompt the user for the phone number to update
                            updatePhoneNumber = sc.next();

                            System.out.println("Enter new name (if no changes enter 0): ");
                            newName = sc.next();
                            System.out.println("Enter new phone number (if no changes enter 0): ");
                            newPhoneNumber = sc.next();

                            int parseNumber2 = parseInteger(updatePhoneNumber);  //check if number is in a valid format
                            parseNumber = parseInteger(newPhoneNumber);  //check if number is in a valid format
                            if(parseNumber != -1 && !newName.equals(null) && parseNumber2 != -1) {
                                phoneBook.update(updateName, newName, updatePhoneNumber, newPhoneNumber, parseNumber);  //call the function to update contact details
                            }else{
                                System.out.println("Enter all fields correctly");
                            }
                            break;
                        case 2:
                            System.out.println("Enter name: "); //prompt the user for the name to delete
                            updateName = sc.next();

                            System.out.println("Enter new name (if no changes enter 0): ");
                            newName = sc.next();
                            System.out.println("Enter new phone number (if no changes enter 0): ");
                            newPhoneNumber = sc.next();

                            parseNumber = parseInteger(newPhoneNumber);  //check if number is in a valid format
                            if(parseNumber != -1 && !newName.equals(null) && !updateName.equals(null)) {
                                phoneBook.update(updateName, newName, updatePhoneNumber, newPhoneNumber, parseNumber);  //call the function to update contact details
                                System.out.println("Enter all fields correctly");
                            }
                            break;
                        default:
                            System.out.print("Choice not available!");
                            break;
                    }
                    break;
                default:
                    System.out.print("Choice not available!");
                    sc.close();
                    break;
            }
            System.out.println("Do you want to choose another option? (yes/no): "); //prompt the user if the want to take more actions
            userInput = sc.next();
        } while (userInput.equalsIgnoreCase("yes"));

        try{
            phoneBook.saveToFile("PhoneBookList.txt");
        }catch(IOException e){
            System.out.println("An error occured while saving to file.");

            e.printStackTrace();
        }
    }
}

