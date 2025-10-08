package com.example.academictracker.controller;

// Deprecated/Unused: This class name conflicted with `dto.LoginRequest` and
// caused the controller to reference the wrong type (losing validation).
// Keeping a renamed, package-private class here only to avoid file deletion.
// Do not use this class. Use `com.example.academictracker.dto.LoginRequest` instead.
class LegacyLoginRequestUnused {
    private String email;
    private String password;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}

