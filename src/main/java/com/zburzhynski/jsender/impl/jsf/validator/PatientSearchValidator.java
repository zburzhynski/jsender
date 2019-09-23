package com.zburzhynski.jsender.impl.jsf.validator;

import static org.apache.commons.lang3.StringUtils.isBlank;
import com.zburzhynski.jsender.impl.rest.domain.SearchPatientRequest;
import org.springframework.stereotype.Component;

/**
 * Patient search validator.
 * <p/>
 * Date: 15.04.2017
 *
 * @author Nikita Shevtsou
 */
@Component
public class PatientSearchValidator extends BaseValidator {

    private static final String CRITERIA_EMPTY = "patientSearchValidator.criteriaEmpty";

    private static final String CARD_DATE_RANGE_INCORRECT = "patientSearchValidator.cardDateRangeIncorrect";

    private static final String BIRTHDAY_RANGE_INCORRECT = "patientSearchValidator.birthdayRangeIncorrect";

    private static final String VISIT_DATE_RANGE_INCORRECT = "patientSearchValidator.visitDateRangeIncorrect";

    private static final String PLANEND_VISIT_DATE_RANGE_INCORRECT =
        "patientSearchValidator.plannedVisitDateRangeIncorrect";

    private static final String IGNORE_YEAR_NOT_SELECTED = "patientSearchValidator.ignoreYearNotSelected";

    private static final String LAST_VISIT_DATE_NOT_SELECTED = "patientSearchValidator.lastVisitDateNotSelected";

    /**
     * Validates patient search request.
     *
     * @param request {@link SearchPatientRequest} to validates
     * @return true if request valid, else false
     */
    public boolean validate(SearchPatientRequest request) {
        return checkIsEmpty(request) && checkCardDateRange(request) &&
            checkBirthdayRange(request) && checkVisitDateRange(request) && checkPlannedVisitDateRange(request);
    }

    /**
     * Checks is request empty.
     *
     * @param request {@link SearchPatientRequest} to check
     */
    private boolean checkIsEmpty(SearchPatientRequest request) {
        if (request.getCardNumber() == null &&
            request.getStartCardDate() == null &&
            request.getEndCardDate() == null &&
            isBlank(request.getSurname()) &&
            isBlank(request.getName()) &&
            isBlank(request.getPatronymic()) &&
            request.getStartBirthdayDate() == null &&
            request.getEndBirthdayDate() == null &&
            !request.isBirthdayIgnoreYear() &&
            request.getGender() == null &&
            request.getMaritalStatus() == null &&
            request.getResidenceType() == null &&
            isBlank(request.getAddress()) &&
            isBlank(request.getHomePhoneNumber()) &&
            isBlank(request.getMobilePhoneNumber()) &&
            isBlank(request.getJob()) &&
            isBlank(request.getJobPosition()) &&
            isBlank(request.getWorkAddress()) &&
            isBlank(request.getWorkPhoneNumber()) &&
            isBlank(request.getEmail()) &&
            isBlank(request.getPassportSeries()) &&
            isBlank(request.getPassportNumber()) &&
            isBlank(request.getAdditionalInformation()) &&
            isEmptyDentalVisit(request) &&
            !request.isOnlyDebtor()) {
            addMessage(CRITERIA_EMPTY);
            return false;
        }
        return true;
    }

    /**
     * Checks card creation date.
     *
     * @param request {@link SearchPatientRequest} to check
     */
    private boolean checkCardDateRange(SearchPatientRequest request) {
        if (request.getStartCardDate() != null && request.getEndCardDate() != null) {
            if (request.getStartCardDate().after(request.getEndCardDate())) {
                addMessage(CARD_DATE_RANGE_INCORRECT);
                return false;
            }
        }
        return true;
    }

    /**
     * Checks birthday date range.
     *
     * @param request {@link SearchPatientRequest} to check
     */
    private boolean checkBirthdayRange(SearchPatientRequest request) {
        if (request.isBirthdayIgnoreYear() && request.getStartBirthdayDate() == null
            && request.getEndBirthdayDate() == null) {
            addMessage(IGNORE_YEAR_NOT_SELECTED);
            return false;
        }
        if (request.getStartBirthdayDate() != null && request.getEndBirthdayDate() != null) {
            if (request.getStartBirthdayDate().after(request.getEndBirthdayDate())) {
                addMessage(BIRTHDAY_RANGE_INCORRECT);
                return false;
            }
        }
        return true;
    }

    /**
     * Checks visit date range.
     *
     * @param request {@link SearchPatientRequest} to check
     */
    private boolean checkVisitDateRange(SearchPatientRequest request) {
        if (request.isSearchByLastVisitDate() && request.getStartVisitDate() == null
            && request.getEndVisitDate() == null) {
            addMessage(LAST_VISIT_DATE_NOT_SELECTED);
            return false;
        }
        if (request.getStartVisitDate() != null && request.getEndVisitDate() != null) {
            if (request.getStartVisitDate().after(request.getEndVisitDate())) {
                addMessage(VISIT_DATE_RANGE_INCORRECT);
                return false;
            }
        }
        return true;
    }

    /**
     * Checks planned visit date range.
     *
     * @param request {@link SearchPatientRequest} to check
     */
    private boolean checkPlannedVisitDateRange(SearchPatientRequest request) {
        if (request.getStartPlannedVisitDate() != null && request.getEndPlannedVisitDate() != null) {
            if (request.getStartPlannedVisitDate().after(request.getEndPlannedVisitDate())) {
                addMessage(PLANEND_VISIT_DATE_RANGE_INCORRECT);
                return false;
            }
        }
        return true;
    }

    private boolean isEmptyDentalVisit(SearchPatientRequest request) {
        return request.getStartVisitDate() == null &&
            request.getEndVisitDate() == null &&
            !request.isSearchByLastVisitDate() &&
            request.getVisitType() == null &&
            request.getTreatmentType() == null &&
            request.getStartPlannedVisitDate() == null &&
            request.getEndPlannedVisitDate() == null;
    }

}
