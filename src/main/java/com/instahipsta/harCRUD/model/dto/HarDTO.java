package com.instahipsta.harCRUD.model.dto;

public class HarDTO implements Transferable {

    private Long id;
    private String version;
    private String browser;
    private String browserVersion;
    private String fileName;


    public HarDTO() {}

    public HarDTO(Long id,
                  String version,
                  String browser,
                  String browserVersion,
                  String fileName) {

        this.id = id;
        this.version = version;
        this.browser = browser;
        this.browserVersion = browserVersion;
        this.fileName = fileName;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getVersion() { return version; }

    public void setVersion(String version) { this.version = version; }

    public String getBrowser() { return browser; }

    public void setBrowser(String browser) { this.browser = browser; }

    public String getBrowserVersion() { return browserVersion; }

    public void setBrowserVersion(String browserVersion) { this.browserVersion = browserVersion; }

    public String getFileName() { return fileName; }

    public void setFileName(String fileName) { this.fileName = fileName; }

}
