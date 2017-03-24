package com.zburzhynski.jsender.impl.jsf.validator;

import com.zburzhynski.jsender.impl.domain.MessageTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Message template validator.
 * <p/>
 * Date: 24.03.2017
 *
 * @author Nikita Shevtsou
 */
@Component
public class MessageTemplateValidator extends BaseValidator {

    private static final String SUBJECT_NOT_SPECIFIED = "messageTemplateValidator.subjectNotSpecified";

    private static final String TEXT_NOT_SPECIFIED = "messageTemplateValidator.textNotSpecified";

    private static final String SUBJECT_TO_LONG = "messageTemplateValidator.subjectToLong";

    private static final String TEXT_TO_LONG = "messageTemplateValidator.textToLong";

    private static final int MAX_SUBJECT_LENGTH = 250;

    private static final int MAX_TEXT_LENGTH = 2000;

    /**
     * Validates message template.
     *
     * @param message message to validates
     * @return true if message valid, else false
     */
    public boolean validate(MessageTemplate message) {
        if (StringUtils.isBlank(message.getSubject())) {
            addMessage(SUBJECT_NOT_SPECIFIED);
            return false;
        }
        if (StringUtils.isBlank(message.getText())) {
            addMessage(TEXT_NOT_SPECIFIED);
            return false;
        }
        if (message.getSubject().length() > MAX_SUBJECT_LENGTH) {
            addMessage(SUBJECT_TO_LONG);
            return false;
        }
        if (message.getText().length() > MAX_TEXT_LENGTH) {
            addMessage(TEXT_TO_LONG);
            return false;
        }
        return true;
    }

}
