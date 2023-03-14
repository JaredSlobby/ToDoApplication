package com.example.todoapplication;

public class ArrayLists
{
    private String Title;
    private String Description;
    private String day;
    private String priority;
    private String dayOfWeek;
    private String month;
    private String dueDate;

public ArrayLists(String Title, String Description, String day, String priority, String dayOfWeek, String month, String dueDate)
    {
        this.Title = Title;
        this.Description = Description;
        this.day = day;
        this.priority = priority;
        this.dayOfWeek = dayOfWeek;
        this.month = month;
        this.dueDate = dueDate;
    }

    public String getTitle()
    {
        return Title;
    }

    public String getDescription()
    {
        return Description;
    }

    public String getDay()
    {
        return day;
    }

    public String getPriority()
    {
        return priority;
    }

    public String getDayOfWeek()
    {
        return dayOfWeek;
    }

    public String getMonth()
    {
        return month;
    }

    public String getDueDate()
    {
        return dueDate;
    }

    public void setDueDate(String dueDate)
    {
        this.dueDate = dueDate;
    }

    public void setMonth(String month)
    {
        this.month = month;
    }

    public void setDayOfWeek(String dayOfWeek)
    {
        this.dayOfWeek = dayOfWeek;
    }

    public void setTitle(String Title)
    {
        this.Title = Title;
    }

    public void setDescription(String Description)
    {
        this.Description = Description;
    }

    public void setDay(String day)
    {
        this.day = day;
    }

    public void setPriority(String priority)
    {
        this.priority = priority;
    }

}
