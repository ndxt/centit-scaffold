package com.centit.support.scaffold;

public class Config {
    private boolean bCreatePojo;
    private boolean bCreateDao;
    private boolean bCreateDaoImpl;
    private boolean bCreateManager;
    private boolean bCreateManagerImpl;
    private boolean bCreateAction;
    private boolean bCreateJsp;
    private boolean bCreateXmlConfig;
    private boolean bInTransaction;
    private boolean bIsWorkFlowOpt;
    private boolean bIsDwz;

    public void setConfig(String sConfig) {
        bCreatePojo = false;
        bCreateDao = false;
        bCreateDaoImpl = false;
        bCreateManager = false;
        bCreateManagerImpl = false;
        bCreateAction = false;
        bCreateXmlConfig = false;
        bCreateJsp = false;
        bInTransaction = false;
        bIsWorkFlowOpt = false;
        bIsDwz = false;
        String s[] = sConfig.split(",");
        if (s == null)
            return;
        int nCL = s.length;
        if (nCL > 0)
            bCreatePojo = "1".equals(s[0]);
        if (nCL > 1)
            bCreateDao = "1".equals(s[1]);
        if (nCL > 2)
            bCreateDaoImpl = "1".equals(s[2]);
        if (nCL > 3)
            bCreateManager = "1".equals(s[3]);
        if (nCL > 4)
            bCreateManagerImpl = "1".equals(s[4]);
        if (nCL > 5)
            bCreateAction = "1".equals(s[5]);
        if (nCL > 6)
            bCreateJsp = "1".equals(s[6]);
        if (bCreateManagerImpl && nCL > 7)
            bCreateXmlConfig = "1".equals(s[7]);
        if (nCL > 8)
            bInTransaction = "1".equals(s[8]);
        if (nCL > 9)
            bIsWorkFlowOpt = "1".equals(s[9]);

        if (nCL > 10) {
            bIsDwz = "1".equals(s[10]);
        }
    }

    public boolean isCreatePojo() {
        return bCreatePojo;
    }

    public boolean isCreateDao() {
        return bCreateDao;
    }

    public boolean isCreateDaoImpl() {
        return bCreateDaoImpl;
    }

    public boolean isCreateManager() {
        return bCreateManager;
    }

    public boolean isCreateManagerImpl() {
        return bCreateManagerImpl;
    }

    public boolean isCreateAction() {
        return bCreateAction;
    }

    public boolean isCreateJsp() {
        return bCreateJsp;
    }

    public boolean isCreateXmlConfig() {
        return bCreateXmlConfig;
    }

    public boolean isInTransaction() {
        return bInTransaction;
    }

    public boolean isWorkFlowOpt() {
        return bIsWorkFlowOpt;
    }

    public void setWorkFlowOpt(boolean bIsWorkFlowOpt) {
        this.bIsWorkFlowOpt = bIsWorkFlowOpt;
    }

    public boolean isIsDwz() {
        return bIsDwz;
    }

}
