package com.hezho.testignition;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;

import com.hezho.testignition.records.SecretRecord;
import com.hezho.testignition.web.SecretManagerPage;
import com.inductiveautomation.ignition.common.BundleUtil;
import com.inductiveautomation.ignition.common.licensing.LicenseState;
import com.inductiveautomation.ignition.common.project.resource.adapter.ResourceTypeAdapterRegistry;
import com.inductiveautomation.ignition.common.script.ScriptManager;
import com.inductiveautomation.ignition.common.script.hints.PropertiesFileDocProvider;
import com.inductiveautomation.ignition.common.util.LoggerEx;
import com.inductiveautomation.ignition.gateway.dataroutes.RouteGroup;
import com.inductiveautomation.ignition.gateway.model.AbstractGatewayModuleHook;
import com.inductiveautomation.ignition.gateway.model.GatewayContext;
import com.inductiveautomation.ignition.gateway.web.models.ConfigCategory;
import com.inductiveautomation.ignition.gateway.web.models.DefaultConfigTab;
import com.inductiveautomation.ignition.gateway.web.models.IConfigTab;
import com.inductiveautomation.ignition.gateway.web.models.SystemMap;
import com.inductiveautomation.ignition.gateway.web.pages.config.overviewmeta.ConfigOverviewContributor;
import com.inductiveautomation.ignition.gateway.web.pages.status.overviewmeta.OverviewContributor;
import org.jfree.util.Log;

/**
 * Class which is instantiated by the Ignition platform when the module is loaded in the gateway scope.
 */
public class GatewayHook extends AbstractGatewayModuleHook {

    @Override
    public List<? extends IConfigTab> getConfigPanels() {
        return List.of(TAB);
    }

    // 这个是干啥的
    private static final IConfigTab TAB = DefaultConfigTab.builder()
            .category(ConfigCategory.SECURITY)
            .name("secrets")
            .i18n("SecretKeeper.config.secrets.MenuTitle")
            .page(SecretManagerPage.class)    // 这里用到了 page
            .terms("secrets", "keys", "api keys")
            .build();

    private static final LoggerEx LOG = LoggerEx.newBuilder().build("SecretKeeper");

    private GatewayContext context;

    /**
     * Called to before startup. This is the chance for the module to add its extension points and update persistent
     * records and schemas. None of the managers will be started up at this point, but the extension point managers will
     * accept extension point types.
     */
    @Override
    public void setup(GatewayContext context) {
        this.context = context;

        BundleUtil.get().addBundle("SecretKeeper", getClass(), "SecretKeeper");
        // 显示声明一下子
        BundleUtil.get().addBundle("SecretRecord", SecretRecord.class, "SecretRecord");

        try {
            context.getSchemaUpdater().updatePersistentRecords(SecretRecord.META);
        } catch (SQLException e) {
            LOG.error("Unable to create required internal DB table", e);
        }
    }

    @Override
    public void startup(LicenseState licenseState) {
        //no-op
    }

    @Override
    public void shutdown() {
        //no-op
    }

}

