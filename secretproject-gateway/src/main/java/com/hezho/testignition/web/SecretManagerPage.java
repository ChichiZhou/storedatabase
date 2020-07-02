package com.hezho.testignition.web;

import com.hezho.testignition.records.SecretRecord;
import com.inductiveautomation.ignition.gateway.localdb.persistence.RecordMeta;
import com.inductiveautomation.ignition.gateway.web.components.RecordActionTable;
import com.inductiveautomation.ignition.gateway.web.pages.IConfigPage;
import org.apache.commons.lang3.tuple.Pair;

import static com.inductiveautomation.ignition.gateway.web.models.ConfigCategory.SECURITY;

/**
 * A RecordActionTable is commonly used in the Configure section of the gateway.
 * It displays a table of each record for a given record type.
 * By default, it includes edit and delete links next to each record, and an add link at the bottom.
 * By default, the columns are created by looking at the record's RecordMeta and finding all fields maked with SFieldFlags.SDESCRIPTIVE
 */
public class SecretManagerPage extends RecordActionTable<SecretRecord> {
    public SecretManagerPage(IConfigPage configPage) {
        super(configPage);
    }

    @Override
    protected RecordMeta<SecretRecord> getRecordMeta() {
        return SecretRecord.META;
    }

    @Override
    public Pair<String, String> getMenuLocation() {
        return Pair.of(SECURITY.getName(), "secrets");
    }
}
