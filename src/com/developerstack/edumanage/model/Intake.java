package com.developerstack.edumanage.model;

import java.util.Date;

public class Intake {
    private String intakeId;
    private Date startDate;
    private String intakeName;
    private String programId;
    private Boolean intakeCompleteness;

    public Intake(String intakeId, Date startDate, String intakeName, String programId, Boolean intakeCompleteness) {
        this.intakeId = intakeId;
        this.startDate = startDate;
        this.intakeName = intakeName;
        this.programId = programId;
        this.intakeCompleteness = intakeCompleteness;
    }

    public Intake() {
    }


    public String getIntakeId() {
        return intakeId;
    }

    public void setIntakeId(String intakeId) {
        this.intakeId = intakeId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getIntakeName() {
        return intakeName;
    }

    public void setIntakeName(String intakeName) {
        this.intakeName = intakeName;
    }

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    public Boolean getIntakeCompleteness() {
        return intakeCompleteness;
    }

    public void setIntakeCompleteness(Boolean intakeCompleteness) {
        this.intakeCompleteness = intakeCompleteness;
    }
}
