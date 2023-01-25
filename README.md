# CustomPriorityQueue
Custom Priority Queue in Java


Bonus: One way to generalize the Enqueue Throttle Rate(TR) for each Priority Class is to have a fixed bucket size. i.e in my scenario a fixed LinkedList.
And the for the Dequeu TR we could set the TR as the count and decrement it.
example:
{
    priorityClass: TR  // 1: 2
}

Question is do we set the TR at the beginning of the program arbitrarily or is there some formula we will use.