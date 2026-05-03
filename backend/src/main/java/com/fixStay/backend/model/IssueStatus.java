package com.fixStay.backend.model;

public enum IssueStatus {
    PENDING,        // 1. Guest reports a problem (The host needs to see it)
    OPEN,           // 2. Host see the prbl and post it
    IN_PROGRESS,    // 3. A provider see the job and accepts
    RESOLVED        // 4. The provider finish the job
}