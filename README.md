## lab-linked-lists
================
### Vivien Yan
### 04/16/2024
    

### Part1
### Purpose: 
    By adding a new dummy node at the beginning of the list, and linking it with the end of the list, there's a circularly, double linked list with dummy node.
### Advantages:
    Simplified the adding and remove. Bying having a dummy node and make it a circular, we don't need to worry about the special cases such as adding or removing the first and second nodes to the list, but just by simply calling the insert and remove can accomplish the effect.
    Also, the behavior of the list will be more predictable and easy to simulate. Also, it can give the coder a easier way to manipulate the iterator and traverse in both directions.


### Part2
### Purpose:
    By adding a counter in the CDLL and a counter in the iterator, a unpdate will happen to the counter in the CDLL and that iterator. By comparing the counters before each call, the newest iterator will be kept and all the old ones will die out by throwing a exception.

### Aknowledgement: 
    Evening tutor: Simon and Diogo
    The doubled linked repo from Sam Rebelsky:
        https://github.com/Grinnell-CSC207/lab-linked-lists.git
