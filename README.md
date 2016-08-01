# Messenger
A chat program that uses threads to send messages between clients. 

#Program Flow
The server is initiated and then the user inputs a username in LoginWindow.java. The name is passed to Client.java where ServerThread.java and ClientThread.java are initiated to keep track of user input and incoming input. When sending a message, the user types in a textfield, hits enter, and the message is sent to ServerThread.java -> Server.java. Next, the message is sent to every thread in the arrayList.
