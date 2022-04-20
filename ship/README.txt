Extract src zip into an Eclipse workspace

Run runserver.bat first, starts embedded server that generates messages.  Press <enter> in window to exit.

Run runclient.bat to start client that consumes messages.  The client has a deliverate "bug" to print binary messages as
text, to test the exception logic.  Calling getBinaryPayload causes a custom exception if the message is of TEXT type.

The client receives random messages from the server, either text or binary.

The REST URLs for messages can be tested from a browser:
  * http://localhost:9999/messages/gettextmessage
  * http://localhost:9999/messages/getbinarymessage
  * http://localhost:9999/messages/getrandommessage
	
Message type is determined automatically on instantiation by automatically calling the correct constructur from the
method signature, depending on the message content.

Text messages contain a short random Lorem Ipsum sentence

Binary messages contain random byte[]

getDate returns String human readable date even though the date is stored as an Epoch timestamp in the class
