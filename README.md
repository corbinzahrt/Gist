Gist
====

This program is meant to be a simple, plain text, terminal application used to store ideas and information like a sketchbook. 

There is not much to say right now, as I've only written one class, GistIO. 

The classes I am currently contemplating making are:
GistMarkup - this takes Gist objects.
If the object is passed to GistMarkup as a Gist object, then the class tags the contents
of the object so that it can be saved to a plain text file. 
If the object is passed to GistMarkup from a File object, then it removes tags and assigns the contents of the File to a Gist
object. 

GistObject - This is the central class here. The main event.
Basically, a GistObject is a String array. Each index holds something very specific. Here is an excerpt from the plain text file I wrote when I was dreaming this thing up
"private String[] explanation =
	{repositoryID, 
	flag, 
	itemOrder (can be unassigned),
	uniqueID,
	tags, 
	description, 
	content, 
	modificationLog,
	flag
	};"
	So each object holds certain kinds of information about itself. You know what, I'll just copy the plain text file I mentioned here:
	BEGIN PLAIN TEXT FILE:
"Repositories:
I am planning on having many different kinds of logs. So I might have a log that is for 
quotes, another for finances, or whatever, and I would like to have something that tells
the future program whether a certain thing belongs to a particular repository. 

The original thinking with this was to make it possible to have multiple repositories 
existing in one file. That way the program wouldn't have to look for multiple files. 

Should I think of adding a structure above the level of repository? 
Hmm, we'll revisit that later. 

Flags:
Flags will be a special character, or string of characters, that tells the program that a
card has begun. When the flag occurs again the program knows that we have finished with
this card. 

Item Order:
This is a field that can specify the objects position in relation to other objects of the 
same repository. It is completely optional, and should not matter unless the user
specifically requests something relating to order. 

UniqueID:
This will be a unique sequence of characters that uses some file or something to assign 
identities. This way, a table of tags can be built later that list all card IDs which 
use a particular tag. 

Tags:
Tags are a means of labeling the contents of the card. It allows for establishing relationships between pieces of information. I would like tags to be a component of the
program which is accessible by all parts of the program at anytime- even if the current repository belongs to an entirely different directory, the program should be able to open 
cards- or access them in some meaningful way. An interesting problem: how do I make it 
such that a new tag be created easily, but also has a component that ensures that 
I am not duplicating a tag which already exists? Perhaps I create a method which checks to
see if two words share a certain percentage of letters. If the new tag exceeds the limit of similarity the program will ask if the similar word is equivalent to the tag you're creating. 
Another idea for implementing this: 
There is a file which contains all tags. 
When you type a tag for a card, the program checks the file to see if that tag exists.
If it does exist, the program adds the uniqueID to the tag in the master file. 

Description:
A plain English description of what the card is about, and perhaps your intentions for the 
card. 

Content:
The actual thing the card is made to contain. Usually plain text. Stored as a String. 

Modification Log:
Keeps a log of the number of times a particular card has been accessed, the last date it was accessed, and the date the card was created.

Flag:
The flag which tells the program that this card is done. 


Tag Table Example
Politics 1efrt3#, 33r8fh, 383b** <--- With this layout commas can be used as a delimiter
					and the first word is always the tag name. "
END PLAIN TEXT FILE. 
