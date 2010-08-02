package @projectName@.webapp.pages;

import joist.domain.uow.UoW;
import joist.domain.validation.ValidationException;
import joist.web.AbstractPage;
import joist.web.controls.MessagesDiv;

public abstract class Abstract@ProjectName@Page extends AbstractPage {

    protected MessagesDiv messages = new MessagesDiv();

    public boolean commit() {
        try {
            UoW.commit();
            return true;
        } catch (ValidationException ve) {
            for (String message : ve.getValidationErrorMessages()) {
                this.messages.addError(message);
            }
            return false;
        }
    }

    public MessagesDiv getMessages() {
        return this.messages;
    }

}
