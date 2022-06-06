package com.example.project.Models;

import java.util.List;

public class Section {
    private String sectionName;
    private List<Task> sectionItems;

    public Section(String sectionName, List<Task> sectionItems) {
        this.sectionName = sectionName;
        this.sectionItems = sectionItems;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public List<Task> getSectionItems() {
        return sectionItems;
    }

    public void setSectionItems(List<Task> sectionItems) {
        this.sectionItems = sectionItems;
    }



}
