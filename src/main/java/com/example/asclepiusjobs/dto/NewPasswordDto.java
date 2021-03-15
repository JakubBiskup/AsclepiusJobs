package com.example.asclepiusjobs.dto;

import javax.validation.constraints.Pattern;

public class NewPasswordDto {

    @Pattern(regexp = "^(?![\\S]*\\s)(?=.*\\d)(?=[^a-z]*[a-z])(?=[^A-Z]*[A-Z])(?=.*[\\!\\\"\\#\\$\\%\\&\\'\\(\\)\\*\\+\\,\\.\\/\\:\\;\\<\\=\\>\\?\\@\\[\\]\\^\\_\\`\\{\\|\\}\\~\\\\\\-]).*$")
    private String password;

    @Pattern(regexp = "^(?![\\S]*\\s)(?=.*\\d)(?=[^a-z]*[a-z])(?=[^A-Z]*[A-Z])(?=.*[\\!\\\"\\#\\$\\%\\&\\'\\(\\)\\*\\+\\,\\.\\/\\:\\;\\<\\=\\>\\?\\@\\[\\]\\^\\_\\`\\{\\|\\}\\~\\\\\\-]).*$")
    private String confirmedPassword;

    public boolean areMatching(){
        if(this.password.equals(this.confirmedPassword)){
            return true;
        }else {
            return false;
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmedPassword() {
        return confirmedPassword;
    }

    public void setConfirmedPassword(String confirmedPassword) {
        this.confirmedPassword = confirmedPassword;
    }
}
