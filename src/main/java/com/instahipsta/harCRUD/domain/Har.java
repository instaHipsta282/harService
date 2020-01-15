package com.instahipsta.harCRUD.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Har {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "har_id_seq")
    @SequenceGenerator(name = "har_id_seq", sequenceName = "har_id_seq", allocationSize = 1)
    private Long id;
    @Column(nullable = false)
    private String version;
    private String browser;
    private String browserVersion;
    private String fileName;

    public Har(String version, String browser, String browserVersion, String fileName) {
        this.version = version;
        this.browser = browser;
        this.browserVersion = browserVersion;
        this.fileName = fileName;
    }

    public Har() { }

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

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Har har = (Har) o;
//        return Objects.equals(id, har.id) &&
//                Objects.equals(version, har.version) &&
//                Objects.equals(browser, har.browser) &&
//                Objects.equals(browserVersion, har.browserVersion) &&
//                Objects.equals(fileName, har.fileName);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, version, browser, browserVersion, fileName);
//    }
}
