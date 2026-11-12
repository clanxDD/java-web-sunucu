package com.ogrenci.webserver.model;

/**
 * Sayfada gösterilecek öğrenci bilgilerini tutan model sınıfı.
 * Yalnızca veri taşır; HTML üretmez, ağ ile ilgilenmez.
 */
public final class StudentProfile {

    private final String fullName;
    private final String studentNumber;
    private final String university;
    private final String department;
    private final int grade;
    private final String[] biography;
    private final String[] skills;

    public StudentProfile(String fullName, String studentNumber, String university,
                          String department, int grade, String[] biography, String[] skills) {
        this.fullName = fullName;
        this.studentNumber = studentNumber;
        this.university = university;
        this.department = department;
        this.grade = grade;
        this.biography = biography.clone();
        this.skills = skills.clone();
    }

    public String getFullName() {
        return fullName;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public String getUniversity() {
        return university;
    }

    public String getDepartment() {
        return department;
    }

    public int getGrade() {
        return grade;
    }

    public String[] getBiography() {
        return biography.clone();
    }

    public String[] getSkills() {
        return skills.clone();
    }
}
