package com.zburzhynski.jsender.impl.jsf.validator;

import com.zburzhynski.jsender.impl.domain.MessageTemplate;
import org.springframework.stereotype.Component;

/**
 * Message template select validator.
 * <p/>
 * Date: 22.02.2017
 *
 * @author Nikita Shevtsou
 */
@Component
public class MessageTemplateSelectValidator extends BaseValidator {

    private static final String MESSAGE_TEMPLATE_NOT_SELECTED
        = "messageTemplateSelectValidator.messageTemplateNotSelected";

    /**
     * Validates selected message template.
     *
     * @param selectedTemplate selected message template to validates
     * @return false if selected message template not valid, else true
     */
    public boolean validate(MessageTemplate selectedTemplate) {
        if (selectedTemplate == null) {
            addMessage(MESSAGE_TEMPLATE_NOT_SELECTED);
            return false;
        }
        return true;
    }

}
