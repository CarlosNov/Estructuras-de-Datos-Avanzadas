package usecase;


import material.Position;
import material.tree.binarytree.LinkedBinaryTree;
import material.tree.iterators.BFSIterator;

import java.util.Iterator;

public class MorseTranslator {

    private LinkedBinaryTree<String> tree = new LinkedBinaryTree<>();

    /**
     * Generates a new MorseTranslator instance given two arrays:
     * one with the character set and another with their respective
     * morse code.
     *
     * @param charset
     * @param codes
     */
    public MorseTranslator(char[] charset, String[] codes)
    {
        if(charset.length != codes.length)
            throw new RuntimeException("Different length");

        tree.addRoot("Start");
        Position<String> currentPosition = tree.root();
        int cont = 0;

        for (String s : codes)
        {
            for(int i = 0; i < s.length(); i++)
            {
                if(s.charAt(i) == '.')
                {
                    if(tree.hasLeft(currentPosition))
                    {
                        currentPosition = tree.left(currentPosition);
                    }
                    else
                    {
                        if(i == s.length()-1)
                            tree.insertLeft(currentPosition, String.valueOf(charset[cont]));
                        else {
                            tree.insertLeft(currentPosition, "");
                            currentPosition = tree.left(currentPosition);
                        }
                    }
                }
                else if (s.charAt(i) == '-')
                {
                    if(tree.hasRight(currentPosition))
                    {
                        currentPosition = tree.right(currentPosition);
                    }
                    else
                    {
                        if(i == s.length()-1)
                            tree.insertRight(currentPosition, String.valueOf(charset[cont]));
                        else {
                            tree.insertRight(currentPosition, "");
                            currentPosition = tree.right(currentPosition);
                        }
                    }
                }
            }
            cont++;
            currentPosition = tree.root();
        }
    }

    /**
     * Decodes a String with a message in morse code and returns
     * another String in plaintext. The input String may contain
     * the characters: ' ', '-' '.'.
     *
     * @param morseMessage
     * @return a plain text translation of the morse code
     */
    public String decode(String morseMessage)
    {
        Position<String> currentPosition = tree.root();
        String message = "";

        for(int i = 0; i < morseMessage.length(); i++)
        {
            if(morseMessage.charAt(i) == ' ')
            {
                if(!tree.isRoot(currentPosition))
                    message = message + currentPosition.getElement();
                else
                    message = message + " ";
                currentPosition = tree.root();
            }
            else if(morseMessage.charAt(i) == '.')
            {
                if(tree.hasLeft(currentPosition))
                {
                    currentPosition = tree.left(currentPosition);
                }
                else
                {
                    message = message + currentPosition.getElement();
                    currentPosition = tree.root();
                    currentPosition = tree.left(currentPosition);
                }

                if(i == morseMessage.length()-1) {
                    message = message + currentPosition.getElement();
                    currentPosition = tree.root();
                }
            }
            else if (morseMessage.charAt(i) == '-') {
                if (tree.hasRight(currentPosition)) {
                    currentPosition = tree.right(currentPosition);
                } else {
                    message = message + currentPosition.getElement();
                    currentPosition = tree.root();
                    currentPosition = tree.right(currentPosition);
                }

                if (i == morseMessage.length() - 1) {
                    message = message + currentPosition.getElement();
                    currentPosition = tree.root();
                }
            }
        }
        return message;
    }


    /**
     * Receives a String with a message in plaintext. This message
     * may contain any character in the charset.
     *
     * @param plainText
     * @return a morse code message
     */
    public String encode(String plainText)
    {
        String message = "";
        for(int i = 0; i < plainText.length(); i++)
        {
            String s = searchString(String.valueOf(plainText.charAt(i)));
            System.out.println(s);
            message = message + s;
        }
        return message;
    }

    private String searchString (String s)
    {
        Iterator<Position<String>> it = new BFSIterator<String>(tree);
        String message = "";

        if(s.equals(" "))
            return " ";

        while (it.hasNext())
        {
            Position<String> position = it.next();


            if(position.getElement().equals(s))
            {
                if(tree.hasRight(position) || tree.hasLeft(position))
                    message = message + " ";

                while (!tree.isRoot(position))
                {
                    Position<String> parent = tree.parent(position);

                    if (tree.left(parent).equals(position)) {
                        message = "." + message;
                    } else {
                        message = "-" + message;
                    }
                    position = tree.parent(position);
                }
                return message;
            }
        }
        return message;
    }
}
