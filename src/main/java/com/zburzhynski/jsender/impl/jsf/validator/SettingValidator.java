package com.zburzhynski.jsender.impl.jsf.validator;

import static com.zburzhynski.jsender.api.domain.CommonConstant.DOT;
import com.zburzhynski.jsender.api.domain.SettingValueType;
import com.zburzhynski.jsender.impl.domain.Setting;
import com.zburzhynski.jsender.impl.util.DateUtils;
import org.springframework.stereotype.Component;

/**
 * Setting validator.
 * <p/>
 * Date: 18.10.2016
 *
 * @author Vladimir Zburzhynski
 */
@Component
public class SettingValidator extends BaseValidator {

    private static final int DATE_PART_SIZE = 3;

    private static final int MONTHS_IN_YEAR = 12;

    private static final String INCORRECT_DATE_FORMAT = "settingValidator.incorrectDateFormat";

    /**
     * Validates setting.
     *
     * @param setting setting to validate
     * @return false if setting not valid, else true
     */
    public boolean validate(Setting setting) {
        return checkDateFormat(setting);
    }

    private boolean checkDateFormat(Setting setting) {
        if (SettingValueType.DATE.equals(setting.getType())) {
            String[] dateParts = setting.getValue().split("\\" + DOT);
            if (dateParts.length != DATE_PART_SIZE) {
                addMessage(INCORRECT_DATE_FORMAT);
                return false;
            }
            int day = Integer.parseInt(dateParts[0]);
            int month = Integer.parseInt(dateParts[1]);
            int year = Integer.parseInt(dateParts[2]);
            if (day < 1 || day > DateUtils.daysInMonth(month, year)) {
                addMessage(INCORRECT_DATE_FORMAT);
                return false;
            }
            if (month < 1 || month > MONTHS_IN_YEAR) {
                addMessage(INCORRECT_DATE_FORMAT);
                return false;
            }
        }
        return true;
    }

}
