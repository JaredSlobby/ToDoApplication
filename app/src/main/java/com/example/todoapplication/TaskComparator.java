package com.example.todoapplication;

import java.util.Comparator;

public class TaskComparator  implements Comparator<ArrayLists>
{
        @Override
        public int compare(ArrayLists task1, ArrayLists task2)
        {
            int priorityComparison = task1.getPriority().compareTo(task2.getPriority());
            if (priorityComparison != 0)
            {
                return priorityComparison;
            }
            return task1.getDueDate().compareTo(task2.getDueDate());
        }

}

