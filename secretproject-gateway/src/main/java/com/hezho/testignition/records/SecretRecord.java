package com.hezho.testignition.records;

import com.inductiveautomation.ignition.common.util.ResourceUtil;
import com.inductiveautomation.ignition.gateway.localdb.persistence.*;
import com.inductiveautomation.ignition.gateway.web.components.editors.PasswordEditorSource;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import simpleorm.dataset.SFieldFlags;

public class SecretRecord extends PersistentRecord {
    public static final RecordMeta<SecretRecord> META =
            new RecordMeta<>(SecretRecord.class, "gi_secrets");

    public static final IdentityField Id = new IdentityField(META, "Id");
    public static final StringField Name = new StringField(META, "Name", SFieldFlags.SMANDATORY,
            SFieldFlags.SDESCRIPTIVE);
    public static final EncodedStringField Secret = new EncodedStringField(META, "Secret", SFieldFlags.SMANDATORY);

    static final Category Main = new Category("SecretRecord.Category", 0).include(Name, Secret);

    static {
        Secret.getFormMeta().setEditorSource(PasswordEditorSource.getSharedInstance());
        Name.addValidator(new IValidator<String>() {
            @Override
            public void validate(IValidatable validatable) {
                String value = (String) validatable.getValue();
                if (!ResourceUtil.isLegalName(value)) {
                    validatable.error(new ValidationError(this).addKey("SecretRecord.Validator.Error"));
                }
            }
        });
    }

    @Override
    public RecordMeta<?> getMeta() {
        return META;
    }

    public String getSecret() {
        return getString(Secret);
    }
}

