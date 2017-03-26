package com.zburzhynski.jsender.impl.rest.domain;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Patient search request.
 * <p/>
 * Date: 23.05.15
 *
 * @author Vladimir Zburzhynski
 */
@XmlRootElement
public class SearchPatientRequest implements Serializable {

    private Integer cardNumber;

    private Date startCardDate;

    private Date endCardDate;

    private String surname;

    private String name;

    private String patronymic;

    private Date startBirthdayDate;

    private Date endBirthdayDate;

    private boolean birthdayIgnoreYear;

    private String gender;

    private String socialStatus;

    private String residenceType;

    private String address;

    private String homePhoneNumber;

    private String mobilePhoneNumber;

    private String job;

    private String jobPosition;

    private String workAddress;

    private String workPhoneNumber;

    private String email;

    private String passportSeries;

    private String passportNumber;

    private String additionalInformation;

    private Date startVisitDate;

    private Date endVisitDate;

    private Long start;

    private Long end;

    private String visitType;

    private String treatmentType;

    private boolean onlyDebtor;

    public Integer getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Integer cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getStartCardDate() {
        return startCardDate;
    }

    public void setStartCardDate(Date startCardDate) {
        this.startCardDate = startCardDate;
    }

    public Date getEndCardDate() {
        return endCardDate;
    }

    public void setEndCardDate(Date endCardDate) {
        this.endCardDate = endCardDate;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Date getStartBirthdayDate() {
        return startBirthdayDate;
    }

    public void setStartBirthdayDate(Date startBirthdayDate) {
        this.startBirthdayDate = startBirthdayDate;
    }

    public Date getEndBirthdayDate() {
        return endBirthdayDate;
    }

    public void setEndBirthdayDate(Date endBirthdayDate) {
        this.endBirthdayDate = endBirthdayDate;
    }

    public boolean isBirthdayIgnoreYear() {
        return birthdayIgnoreYear;
    }

    public void setBirthdayIgnoreYear(boolean birthdayIgnoreYear) {
        this.birthdayIgnoreYear = birthdayIgnoreYear;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSocialStatus() {
        return socialStatus;
    }

    public void setSocialStatus(String socialStatus) {
        this.socialStatus = socialStatus;
    }

    public String getResidenceType() {
        return residenceType;
    }

    public void setResidenceType(String residenceType) {
        this.residenceType = residenceType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHomePhoneNumber() {
        return homePhoneNumber;
    }

    public void setHomePhoneNumber(String homePhoneNumber) {
        this.homePhoneNumber = homePhoneNumber;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(String jobPosition) {
        this.jobPosition = jobPosition;
    }

    public String getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }

    public String getWorkPhoneNumber() {
        return workPhoneNumber;
    }

    public void setWorkPhoneNumber(String workPhoneNumber) {
        this.workPhoneNumber = workPhoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassportSeries() {
        return passportSeries;
    }

    public void setPassportSeries(String passportSeries) {
        this.passportSeries = passportSeries;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public Date getStartVisitDate() {
        return startVisitDate;
    }

    public void setStartVisitDate(Date startVisitDate) {
        this.startVisitDate = startVisitDate;
    }

    public Date getEndVisitDate() {
        return endVisitDate;
    }

    public void setEndVisitDate(Date endVisitDate) {
        this.endVisitDate = endVisitDate;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }

    public String getVisitType() {
        return visitType;
    }

    public void setVisitType(String visitType) {
        this.visitType = visitType;
    }

    public String getTreatmentType() {
        return treatmentType;
    }

    public void setTreatmentType(String treatmentType) {
        this.treatmentType = treatmentType;
    }

    public boolean isOnlyDebtor() {
        return onlyDebtor;
    }

    public void setOnlyDebtor(boolean onlyDebtor) {
        this.onlyDebtor = onlyDebtor;
    }

}
